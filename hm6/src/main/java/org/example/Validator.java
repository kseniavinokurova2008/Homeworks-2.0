package org.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Validator {
  public static ValidationResult validate(Object object) {
    List<ValidationError> errors = new ArrayList<>();

    if (object == null) {
      throw new NullPointerException();
    }
    for (Class<?> currentClass = object.getClass(); currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
      Field[] fields = currentClass.getDeclaredFields();
      for (Field field : fields) {
        if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        if (!field.trySetAccessible()) {
          throw new ValidationConfigurationException("No access " + currentClass.getName() + "." + field.getName());
        }
        Object fieldValue;
        try{
          fieldValue = field.get(object);
        }
        catch (IllegalAccessException e){
          throw new ValidationConfigurationException("Unable to retrieve the field value " + currentClass.getName() + "." + field.getName());
        }

        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
          if (annotation.annotationType() == NotNull.class) {
            if (fieldValue == null) {
              ValidationError error = new ValidationError(field.getName(), "NotNull", ((NotNull) annotation).message());
              errors.add(error);
            }
          }
          if (annotation.annotationType() == Size.class) {
            if (((Size) annotation).min() < 0 || ((Size) annotation).min() > ((Size) annotation).max()) {
              throw new ValidationConfigurationException("Invalid minimum and maximum values " + currentClass.getName() + "." + field.getName());
            }
            if (! CharSequence.class.isAssignableFrom(field.getType())) {
              throw new ValidationConfigurationException("Invalid type of field " + currentClass.getName() + "." + field.getName());
            }
            if (fieldValue != null) {
              CharSequence fieldValueNew = (CharSequence) fieldValue;
              if (fieldValueNew.length() < ((Size) annotation).min() || fieldValueNew.length() > ((Size) annotation).max()) {
                ValidationError error = new ValidationError(field.getName(), "Size", ((Size) annotation).message());
                errors.add(error);
              }
            }
          }
          if (annotation.annotationType() == Range.class) {
            if (! (Number.class.isAssignableFrom(field.getType()) || (field.getType().isPrimitive() && field.getType() != boolean.class && field.getType() != char.class))) {
              throw new ValidationConfigurationException("Invalid type of field " + currentClass.getName() + "." + field.getName());
            }
            if (Double.isNaN(((Range) annotation).min()) || Double.isNaN(((Range) annotation).max()) || Double.isInfinite(((Range) annotation).min()) || Double.isInfinite(((Range) annotation).max()) || ((Range) annotation).min() > ((Range) annotation).max()) {
              throw new ValidationConfigurationException("Invalid minimum and maximum values " + currentClass.getName() + "." + field.getName());
            }
            if (fieldValue != null) {
              double fieldValueNew = ((Number) fieldValue).doubleValue();
              if (fieldValueNew < ((Range) annotation).min() || fieldValueNew > ((Range) annotation).max() || Double.isNaN(fieldValueNew)) {
                ValidationError error = new ValidationError(field.getName(), "Range", ((Range) annotation).message());
                errors.add(error);
              }
            }
          }

          if (annotation.annotationType() == Email.class) {
            if (! CharSequence.class.isAssignableFrom(field.getType())) {
              throw new ValidationConfigurationException("Invalid type of field " + currentClass.getName() + "." + field.getName());
            }
            if (fieldValue != null) {
              String fieldValueNew = fieldValue.toString();
              if (! fieldValueNew.matches("[^@\\s]+@[^\\s@]+\\.[^\\s@]+")) {
                ValidationError error = new ValidationError(field.getName(), "Email", ((Email) annotation).message());
                errors.add(error);
              }
            }
          }
        }
      }
    }
    return new ValidationResult(errors);
  }
}

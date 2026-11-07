import org.example.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
  static class Obj {
    @NotNull
    @Size(min = 1, max = 20)
    @Email
    String string = "pupupu@yandex.ru";
    @Range(min = 1, max = 2)
    int a = 2;
  }
  @Test
  public void validObject() {
    Obj obj = new Obj();
    ValidationResult result = Validator.validate(obj);
    assertTrue(result.isValid());
  }


  static class InvalidObj {
    @NotNull
    String string1 = null;
    @Size(min = 2, max = 5)
    String string2 = "pupupu";
    @Range(min = 1, max = 2)
    int number = 67;
    @Email
    String email = "pupupu";
  }
  @Test
  public void allAnnotationsErrors() {
    ValidationResult result = Validator.validate(new InvalidObj());
    assertFalse(result.isValid());
    assertEquals(4, result.getErrors().size());
  }

  static class Boundaries {
    @Size(min = 1, max = 2)
    String string1 = "a";
    @Size(min = 3, max = 5)
    String string2 = "aaaaa";
    @Range(min = 1, max = 2)
    int num1 = 1;
    @Range(min = 3, max = 5)
    int num2 = 5;
  }

  @Test
  public void boundariesValid() {
    assertTrue(Validator.validate(new Boundaries()).isValid());
  }

  static class BoundariesInvalid {
    @Size(min = 1, max = 2)
    String text = "aaa";
    @Range(min = 1, max = 2)
    int number = 67;
  }

  @Test
  public void boundariesInvalid() {
    assertEquals(2, Validator.validate(new BoundariesInvalid()).getErrors().size());
  }

  static class InvalidSize {
    @Size(min = 2, max = 1)
    String string = "a";
  }

  static class BadType {
    @Size(min = 1, max = 2)
    int num = 1;
  }
  @Test
  public void configurationErrors() {
    assertThrows(NullPointerException.class, () -> Validator.validate(null));
    assertThrows(ValidationConfigurationException.class, () -> Validator.validate(new InvalidSize()));
    assertThrows(ValidationConfigurationException.class, () -> Validator.validate(new BadType()));
  }


  static class Class1 {
    @NotNull
    private String string = null;
  }

  static class Class2 extends Class1 {
    @NotNull
    private String string1 = null;
    @NotNull
    static String string2 = null;
  }

  @Test
  public void privateAndStaticFields() {
    ValidationResult result = Validator.validate(new Class2());
    assertEquals(2, result.getErrors().size());
  }

  @Test
  public void errorsAreImmutable() {
    ValidationResult result = Validator.validate(new Obj());
    assertThrows(UnsupportedOperationException.class, () -> result.getErrors().add(new ValidationError("a", "b", "c")));
  }
}

package org.example;

public final class ValidationError {
  private final String fieldName;
  private final String annotationName;
  private final String message;

  public ValidationError(String fieldName, String annotationName, String message) {
    this.fieldName = fieldName;
    this.annotationName = annotationName;
    this.message = message;
  }
}

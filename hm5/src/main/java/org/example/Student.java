package org.example;

import java.util.*;

public final class Student {
  private final int id;
  private final String name;
  private final double grade;

  public Student(int id, String name, double grade) {
    if (id <= 0 || name == null || name.isEmpty() || grade > 10.0 || grade < 0.0) {
      throw new IllegalArgumentException();
    }
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  public int getId() {
    return id;
  }

  public double getGrade() {
    return grade;
  }

  public String getName() {
    return name;
  }
  @Override
  public boolean equals(Object student) {
    if (student == this) {
      return true;
    }
    if (!(student instanceof Student)) {
      return false;
    }
    return id == ((Student) student).getId() && name.equals(((Student) student).getName()) && ((Student) student).getGrade() == grade;
  }
  @Override
  public int hashCode() {
    return Objects.hash(id, name, grade);
  }
}

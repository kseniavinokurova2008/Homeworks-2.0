package org.example;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistryTest {
  Registry registry = new Registry();

  @Test
  void common() {
    List<Student> list = new ArrayList<>();
    list.add(new Student(1, "1", 1.0));
    list.add(new Student(2, "2", 2.0));
    list.add(new Student(3, "3", 3.0));
    Map<Integer, Student> hashMap = Registry.createHashMap(list);
    NavigableMap<Integer, Student> treeMap = Registry.createTreeMap(list);
    List<Student> studentsinGradeRange = registry.findStudentsByGradeRange(hashMap, 1, 2);
    assertTrue(studentsinGradeRange.contains(list.get(0)));
    assertTrue(studentsinGradeRange.contains(list.get(1)));
    List<Student> getTopNStudentsById = registry.getTopNStudentsById(treeMap, 2);
    assertTrue(getTopNStudentsById.contains(list.get(2)));
    assertTrue(getTopNStudentsById.contains(list.get(1)));
  }
  @Test
  void boundaries() {
    List<Student> list = new ArrayList<>();
    list.add(new Student(1, "1", 0.0));
    list.add(new Student(2, "2", 2.0));
    list.add(new Student(3, "3", 10.0));
    Map<Integer, Student> hashMap = Registry.createHashMap(list);
    NavigableMap<Integer, Student> treeMap = Registry.createTreeMap(list);
    List<Student> studentsinGradeRange = registry.findStudentsByGradeRange(hashMap, 0, 10.0);
    assertTrue(studentsinGradeRange.contains(list.get(0)));
    assertTrue(studentsinGradeRange.contains(list.get(1)));
    assertTrue(studentsinGradeRange.contains(list.get(2)));
    List<Student> getTopNStudentsById = registry.getTopNStudentsById(treeMap, 1);
    assertTrue(getTopNStudentsById.contains(list.get(2)));
  }
  @Test
  void OutOfBoundaries() {
    List<Student> list = new ArrayList<>();
    list.add(new Student(1, "1", 0.0));
    list.add(new Student(2, "2", 2.0));
    list.add(new Student(3, "3", 10.0));
    Map<Integer, Student> hashMap = Registry.createHashMap(list);
    NavigableMap<Integer, Student> treeMap = Registry.createTreeMap(list);
    assertThrows(IllegalArgumentException.class, () -> {
      registry.getTopNStudentsById(treeMap, -5);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      registry.findStudentsByGradeRange(hashMap, 6.0, 3.0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      registry.findStudentsByGradeRange(hashMap, -1.0, 3.0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      registry.findStudentsByGradeRange(hashMap, 2.0, 15.0);
    });
    List<Student> getTopNStudentsById = registry.getTopNStudentsById(treeMap, 5);
    assertTrue(getTopNStudentsById.contains(list.get(0)));
    assertTrue(getTopNStudentsById.contains(list.get(1)));
    assertTrue(getTopNStudentsById.contains(list.get(2)));
  }
  @Test
  void emptyMap() {
    List<Student> list = new ArrayList<>();
    Map<Integer, Student> hashMap = Registry.createHashMap(list);
    NavigableMap<Integer, Student> treeMap = Registry.createTreeMap(list);
    List<Student> studentsinGradeRange = registry.findStudentsByGradeRange(hashMap, 0, 10.0);
    List<Student> getTopNStudentsById = registry.getTopNStudentsById(treeMap, 5);
    assertTrue(studentsinGradeRange.isEmpty());
    assertTrue(getTopNStudentsById.isEmpty());
  }
}

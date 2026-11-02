package org.example;

import java.util.*;

public class Registry {
  public static HashMap<Integer, Student> createHashMap(List<Student> commonList) {
    HashMap<Integer, Student> hashMap = new HashMap<>();
    if (commonList.isEmpty()) {
      return hashMap;
    }
    for (Student student : commonList) {
      hashMap.put(student.getId(), student);
    }
    return hashMap;
  }
  public static TreeMap<Integer,Student> createTreeMap(List<Student> commonList) {
    TreeMap<Integer, Student> treeMap = new TreeMap<>(Comparator.reverseOrder());
    if (commonList.isEmpty()) {
      return treeMap;
    }
    for (Student student : commonList) {
      treeMap.put(student.getId(), student);
    }
    return treeMap;
  }

  public List<Student> findStudentsByGradeRange(Map<Integer, Student> students, double minGrade, double maxGrade) {
    if (minGrade > maxGrade || maxGrade > 10.0 || minGrade < 0.0) {
      throw new IllegalArgumentException();
    }
    List<Student> studentsinGradeRange = new ArrayList<>();
    for (Student student : students.values()) {
      if (student.getGrade() <= maxGrade && student.getGrade() >= minGrade) {
        studentsinGradeRange.add(student);
      }
    }
    return studentsinGradeRange;
  }


  public List<Student> getTopNStudentsById(NavigableMap<Integer, Student> students, int n) {
    List<Student> studentsTopN = new ArrayList<>();
    if (n == 0) {
      return studentsTopN;
    }
    if (n < 0) {
      throw new IllegalArgumentException();
    }
    int k = 0;
    for (Student student : students.values()) {
      studentsTopN.add(student);
      k += 1;
      if (k == n) {
        break;
      }
    }
    return studentsTopN;
  }

}

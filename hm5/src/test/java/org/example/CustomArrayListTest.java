package org.example;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {
  @Test
  void EmptyList() {
    CustomArrayList<Object> arraylist = new CustomArrayList<>();
    assertEquals(0, arraylist.size());
    assertTrue(arraylist.isEmpty());
  }

  @Test
  void orderOfAdding() {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    arraylist.add(1);
    arraylist.add(2);
    assertEquals(1, arraylist.get(0));
    assertEquals(2, arraylist.get(1));
  }
  @Test
  void rightIncrease() {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    for (int i = 0; i < 12; ++i) {
      arraylist.add(i);
    }
    assertEquals(12, arraylist.size());
    for (int i = 0; i < 12; ++i) {
      assertEquals(i, arraylist.get(i));
    }
  }
  @ParameterizedTest
  @ValueSource(ints = {0, 2, 4})
  void rightGet(int index) {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    for (int i = 0; i < 5; ++i) {
      arraylist.add(i);
    }
    assertEquals(index, arraylist.get(index));
  }
  @ParameterizedTest
  @ValueSource(ints = {-1, 5})
  void rejectionInvalidIndex(int index) {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    for (int i = 0; i < 5; ++i) {
      arraylist.add(i);
    }
    assertThrows(IndexOutOfBoundsException.class, () -> {
      arraylist.get(index);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      arraylist.remove(index);
    });
  }
  @ParameterizedTest
  @ValueSource(ints = {0, 2, 4})
  void rightRemove(int index) {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    for (int i = 0; i < 5; ++i) {
      arraylist.add(i);
    }
    assertEquals(index, arraylist.remove(index));
  }
  @Test
  void addingNull() {
    CustomArrayList<Object> arraylist = new CustomArrayList<>();
    assertThrows(NullPointerException.class, () -> {
      arraylist.add(null);
    });
  }
  @Test
  void rightIterator() {
    CustomArrayList<Integer> arraylist = new CustomArrayList<>();
    Iterator<Integer> iterator = arraylist.iterator();
    for (int i = 0; i < 3; ++i) {
      arraylist.add(i);
    }
    for (int i = 0; i < 3; ++i) {
      assertEquals(i, iterator.next());
    }
    assertThrows(NoSuchElementException.class, () -> {
      iterator.next();
    });
  }
}

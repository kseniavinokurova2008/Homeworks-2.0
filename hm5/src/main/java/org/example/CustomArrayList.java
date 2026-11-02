package org.example;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс, реализующий динамический массив
 * @param <E> тип элементов списка
 */
public class CustomArrayList<E> implements CustomList<E> {
  Object[] array = new Object[10];
  int pos = 0;

  /**
   * Увеличивает емкость массива, если это требуется
   */
  public Object[] increaselength() {
    if (pos == array.length) {
      Object[] array1 = new Object[array.length * 3 / 2];
      System.arraycopy(array, 0, array1, 0, array.length);
      return array1;
    }
    return array;
  }

  /**
   * Добавляет новый объект в массив
   *
   * @param object новый объект, который добавляем
   * @throws NullPointerException если object == null
   */
  @Override
  public void add(E object) {
    if (object == null) {
      throw new NullPointerException();
    }
    array = increaselength();
    array[pos] = object;
    pos += 1;
  }

  /**
   * Возвращает элемент по индеку
   *
   * @param index индекс, по которому нужно вернуть элемент
   * @return элемент индекса index
   * @throws IndexOutOfBoundsException если index выходит за границы
   */
  @Override
  public E get(int index) {
    if (index >= pos || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    return (E) array[index];
  }

  /**
   * Удаляет элемент по индексу
   *
   * @param index индекс, по которому нужно удалить элемент
   * @return удалённый элемент
   * @throws IndexOutOfBoundsException если index выходит за границы
   */
  @Override
  public E remove(int index) {
    if (index >= pos || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    Object returnValue = array[index];
    for (int i = index + 1; i < pos; ++i) {
      array[i - 1] = array[i];
    }
    array[pos - 1] = null;
    pos -= 1;
    return (E) returnValue;
  }

  /**
   * Возвращает размер массива
   *
   * @return размер массива
   */
  @Override
  public int size() {
    return pos;
  }

  /**
   * Проверка массива на пустоту
   *
   * @return true - массив пустой, false - иначе
   */
  @Override
  public boolean isEmpty() {
    return pos == 0;
  }

  /**
   * Возвращает итератор
   *
   * @return итератор
   */
  public Iterator<E> iterator() {
    Iterator<E> iterator = new Iterator<E>() {
      int index = 0;
      @Override
      public boolean hasNext() {
        return index < pos;
      }
      @Override
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return (E) array[index++];
      }
    };
    return iterator;
  }
}

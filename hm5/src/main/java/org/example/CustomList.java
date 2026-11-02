package org.example;

/**
 * Интерфейс списка
 *
 * @param <E> тип элементов списка
 */
public interface CustomList<E> extends Iterable<E> {
  /**
   * Добавляет элемент в список
   *
   * @param element объект, который надо добавить
   */
  void add(E element);


  /**
   * Возвращает элемент по индеку
   *
   * @param index индекс, по которому нужно вернуть элемент
   */
  E get(int index);


  /**
   * Удаляет элемент по индексу
   *
   * @param index индекс, по которому нужно удалить элемент
   */
  E remove(int index);


  /**
   * Возвращает количество элементов массива
   */
  int size();


  /**
   * Проверка массива на пустоту
   */
  boolean isEmpty();
}

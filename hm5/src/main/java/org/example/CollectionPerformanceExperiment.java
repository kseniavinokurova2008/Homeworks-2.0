
package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceExperiment {
  public static final int N = 10_000;
  public static long sum;

  public static void print(Result result) {
    System.out.println(result.operation + " " + result.numberOfElements + " "
        + result.timeArrayList + " " + result.timeLinkedList);
  }

  public static class Result {
    String operation;
    int numberOfElements;
    double timeArrayList;
    double timeLinkedList;

    Result(String operation, int numberOfElements,
           double timeArrayList, double timeLinkedList) {
      this.operation = operation;
      this.numberOfElements = numberOfElements;
      this.timeArrayList = timeArrayList;
      this.timeLinkedList = timeLinkedList;
    }
  }
  public static void start() {
    addToEndOfArray();
    addToBeginningOfArray();
    addToMiddleOfArray();
    gettingElements();
    removeFromBeginning();
    removeFromEnd();
  }
  public static void fillArrayOfResults() {
    arrayOfResults.add(addToEndOfArray());
    arrayOfResults.add(addToBeginningOfArray());
    arrayOfResults.add(addToMiddleOfArray());
    arrayOfResults.add(gettingElements());
    arrayOfResults.add(removeFromBeginning());
    arrayOfResults.add(removeFromEnd());
  }

  public static Result addToEndOfArray() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        arrayList.add(i);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        linkedList.add(i);
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Добавление элементов в конец", N, sumTimeArrayList, sumTimeLinkedList);
  }

  public static Result addToBeginningOfArray() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        arrayList.add(0, i);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        linkedList.addFirst(i);
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Добавление элементов в начало", N, sumTimeArrayList, sumTimeLinkedList);
  }

  public static Result addToMiddleOfArray() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      for (int i = 0; i < N; ++i) {
        arrayList.add(i);
        linkedList.add(i);
      }

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        arrayList.add(arrayList.size() / 2, 0);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        linkedList.add(linkedList.size() / 2, 0);
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Добавление элементов в середину", N, sumTimeArrayList, sumTimeLinkedList);
  }

  public static Result gettingElements() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      for (int i = 0; i < N; ++i) {
        arrayList.add(i);
        linkedList.add(i);
      }

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += arrayList.get(i);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += linkedList.get(i);
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Последовательный доступ к элементам", N, sumTimeArrayList, sumTimeLinkedList);
  }

  public static Result removeFromBeginning() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      for (int i = 0; i < N; ++i) {
        arrayList.add(i);
        linkedList.add(i);
      }

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += arrayList.remove(0);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += linkedList.removeFirst();
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Удаление с начала", N,
        sumTimeArrayList, sumTimeLinkedList);
  }

  public static Result removeFromEnd() {
    double sumTimeArrayList = 0;
    double sumTimeLinkedList = 0;

    for (int j = 0; j < 5; j++) {
      List<Integer> arrayList = new ArrayList<>();
      LinkedList<Integer> linkedList = new LinkedList<>();

      for (int i = 0; i < N; ++i) {
        arrayList.add(i);
        linkedList.add(i);
      }

      long start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += arrayList.remove(arrayList.size() - 1);
      }
      long finish = System.nanoTime();

      double timeArrayList = (finish - start) / 1_000_000.0;
      sumTimeArrayList += timeArrayList;

      start = System.nanoTime();
      for (int i = 0; i < N; ++i) {
        sum += linkedList.removeLast();
      }
      finish = System.nanoTime();

      double timeLinkedList = (finish - start) / 1_000_000.0;
      sumTimeLinkedList += timeLinkedList;
    }

    sumTimeArrayList = sumTimeArrayList / 5;
    sumTimeLinkedList = sumTimeLinkedList / 5;

    return new Result("Удаление с конца", N, sumTimeArrayList, sumTimeLinkedList);
  }

  static List<Result> arrayOfResults = new ArrayList<Result>();

  public static void main(String[] args) {
    start();
    start();
    sum = 0;
    fillArrayOfResults();
    System.out.println("Операция Количество элементов ArrayList LinkedList");
    for (int i = 0; i < arrayOfResults.size(); ++i) {
      print(arrayOfResults.get(i));
    }
    System.out.println("сумма: " + sum);
    System.out.println("Добавление элементов в конец и в ArrayList, и в LinkedList быстрое.\n" +
        "Добавление элементов в начало в ArrayList дольше, чем в LinkedList, из-за линейного сдвига элементов.\n" +
        "Добавление элементов в середину в LinkedList дольше, чем в ArrayList так как для добавления нужно добраться линейно до середины списка и изменить связи. В ArrayList нужно линейно сдвигать правые элементы.\n" +
        "Последовательный доступ к элементам в ArrayList быстрее, чем в LinkedList, так как в LinkedList нужно линейно добираться до конкретного индекса\n" +
        "Удаление с конца по времени в ArrayList и в LinkedList примерно одинаковое\n" +
        "Удаление с начала в ArrayList дольше, чем в LinkedList, из-за линейного сдвига всех правых элементов.\n");
  }
}

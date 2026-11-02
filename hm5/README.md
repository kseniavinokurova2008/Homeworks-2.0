# Java Collections Project

## Собственный динамический массив

### Файлы:
* src/main/java/org/example/CustomList.java — интерфейс списка
* src/main/java/org/example/CustomArrayList.java — реализация динамического массива
* src/test/java/org/example/CustomArrayListTest.java — unit-тесты

Для запуска тестов: mvn test

### Выводы, полученные в эксперименте производительности

* Добавление элементов (за исключением расширения размера массива), получение элемента, длины массива, проверка на пустоту - O(1)
* Удаление элемента - O(n)


## Эксперимент с ArrayList и LinkedList
### Файлы:
* src/main/java/org/example/CollectionPerformanceExperiment.java

### Выводы, полученные в эксперименте 

* Добавление элементов в конец и в ArrayList, и в LinkedList быстрое
* Добавление элементов в начало в ArrayList дольше, чем в LinkedList
* Добавление элементов в середину в LinkedList дольше, чем в ArrayList
* Последовательный доступ к элементам в ArrayList быстрее, чем в LinkedList
* Удаление с конца по времени в ArrayList и в LinkedList примерно одинаковое
* Удаление с начала в ArrayList дольше, чем в LinkedList

## Реестр студентов на HashMap и TreeMap 
### Файлы:
* src/main/java/org/example/Student.java - класс студента
* src/main/java/org/example/Registry.java - класс реестра студентов
* src/test/java/org/example/RegistryTest.java - unit-тесты

Для запуска тестов: mvn test

### Выводы
* HashMap позволяет хранить элементы по ключу и быстро получать по нему значение
* TreeMap позволяет хранить ключи в отсортированном порядке

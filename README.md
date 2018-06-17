# TODOApp

## Wprowadzenie

Projekt zaliczeniowy z przedmiotu Programowanie aplikacji mobilnych - 2018.
TODOApp - organizer przedstawiający listę zadań oraz ułatwiający planowanie na system Android.

## Używane oprogramowanie

* [Android Studio](https://developer.android.com/studio/index.html)
* [Git](https://git-scm.com/)

## Biblioteki
* [SQLite](https://www.sqlite.org/index.html)

## Changelog

### [0.1] - 2018-04-23
* Budowa MainActivity czyli list view.
* Przycisk do dodawania zadania i okienko je obsługujące.
* Dodanie layoutu "Zadania na dzisiaj".

### [0.2] - 2018-04-23
* Dodanie layoutu odpowiadającego za notatki.

### [0.3] - 2018-05-22
* Budowa TaskContract i TaskDbHelper czyli Wprowadzenie możliwości wpisywania i usuwania zadań.
* Implementacja i powiązanie z bazą danych.

### [0.4] - 2018-05-23
* Implementacja RecyclerView.

### [0.5] - 2018-06-11
* Implementacja PositionEditActivity.
* Poprawki layoutu.

### [0.6] - 2018-06-12
* Edycja Task.
* Działanie przycisku "zrobione" zmienione na przesunięcie pozycji w lewo, przycisku "edytuj" na przesuwanie pozycji w prawo.
* Implementacja otwarcia kalendarza po wykonaniu gestu przesunięcia ekranu w lewo w layoucie głównym. 

### [0.7] - 2018-06-16
* Dodanie odświeżania.
* Dodane animacji informujących o wykonywanej na pozycji akcji.
* Dodanie ikony aplikacji.

### [0.8] - 2018-06-17
* Implementacja możliwości sortowania według daty.
* Ulepszenie grafiki.
* Implementacja alarmu.
* Przypomnienie na ekranie głównym telefonu o nadchodzącym zadaniu.

## Autorzy

* **Laura Giza** - *Utworzenie layoutu odpowiadającego za dodanie notatek oraz możliwość edycji wprowadzonych danych*

* **Patryk Sikora** - *Utworzenie layoutu "Zadania na dzisiaj", interakcja z kolejnym layoutem", odpowiedzialny za przechowywanie danych*

* **Jolanta Walczak** - *Utworzenie okienka "Dodaj zadanie" wraz z możliwością ustawienia alarmu, przypomnienia, interakcja  z kolejnym layoutem*


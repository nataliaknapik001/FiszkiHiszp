# FiszkiHiszp

Aplikacja do nauki języka hiszpańskiego - fiszki hiszpańsko - polskie.

Projekt został wykonany w języku Java z wykorzystaniem Java Swing oraz Microsoft SQL Server. Aplikacja umożliwia zarządzanie słownictwem, naukę słówek oraz zapisywanie wyników nauki.

## Funkcje

- przeglądanie kategorii,
- przeglądanie słówek w wybranej kategorii,
- dodawanie nowych słówek,
- edycja istniejących słówek,
- usuwanie słówek,
- ćwiczenia z wykorzystaniem fiszek,
- zapisywanie wyników nauki,
- przeglądanie statystyk nauki,
- przechowywanie danych w Microsoft SQL Server.

## Technologie

- **Java**
- **Java Swing**
- **JDBC**
- **Microsoft SQL Server**
- **NetBeans / Apache Ant**


## Baza danych

Aplikacja korzysta z bazy danych **Microsoft SQL Server**.

Główne tabele:

- `Kategorie` – przechowuje kategorie słówek,
- `Slowka` – przechowuje polskie i hiszpańskie słówka,
- `HistoriaNauki` – przechowuje wyniki i statystyki nauki.

Połączenie z bazą danych realizowane jest za pomocą JDBC.

Dane logowania nie są przechowywane bezpośrednio w kodzie źródłowym. Aplikacja korzysta ze zmiennych środowiskowych:
```text
FISZKI_DB_USER
FISZKI_DB_PASSWORD
```

Przykład konfiguracji w systemie Windows:
```cmd
setx FISZKI_DB_USER "sa"
setx FISZKI_DB_PASSWORD "TWOJE_HASLO"
```

Po ustawieniu zmiennych środowiskowych należy ponownie uruchomić środowisko IDE.
Baza danych powinna być dostępna pod nazwą:
```text
FiszkiDB
```
## Struktura projektu

```text
FiszkiHiszp
├── src
│   ├── backend
│   │   └── BazaDanych.java
│   ├── frontend
│   │   ├── StartFrame.java
│   │   ├── SlowaFrame.java
│   │   └── ModyfikujFrame.java
│   └── fiszkihiszp
│       └── FiszkiHiszp.java
├── lib
│   └── mssql-jdbc
├── nbproject
├── .gitignore
└── README.md
```
## Cel projektu

Celem projektu było stworzenie aplikacji ułatwiającej naukę języka hiszpańskiego oraz praktyczne wykorzystanie:
- programowania obiektowego w Javie
- interfejsu graficznego Java Swing
- komunikacji z relacyjną bazą danych
- JDBC
- operacji CRUD
- obsługi wyjątków
- systemu kontroli wersji Git

## Autor
**Natalia Knapik** 

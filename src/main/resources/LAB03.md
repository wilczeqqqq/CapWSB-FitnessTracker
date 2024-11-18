# LABORATORIUM 03

## Kontynuacja LABORATORIUM 02
Termin upływa po 14 dniach od rozpoczęciu laboratorium.
## ZADANIE 1. Sieciowe API do operacji typu CRUD na Training (bez użycia rekordów)

### Potrzeba biznesowa

Jako użytkownik, chce mieć możliwość dostępu do panelu z treningami:

- tworzenie nowych,
- wyświetlanie swoich treningów,
- aktualizacji trenigów

### Wymagania funkcjonalne

Stworzone API powinno pozwalać na:

- [X] wyszukiwanie wszystkich treningów
- [X] wyszukiwanie treningów dla określonego Użytkownika:
- [X] wyszukiwanie wszystkich treningów zakończonych (po konkretnej zdefiniowanej dacie)
- [X] wyszukiwanie wszystkich treningów dla konkretnej aktywności (np. wszystkie treningi biegowe)
- [X] utworzenie nowego treningu
- [ ] aktualizacja treningu (dowolnie wybrane pole np. dystans)

### Wymagania techniczne

- [X] API sieciowe powinno wykorzystywać protokół HTTP oraz format JSON do transferu danych
- [ ] w repozytoriach rozwiązanie może wykorzystywać metody dostarczane przez interfejs JpaRepository oraz metody
  domyślne, pobierające dane za pomocą `findAll()` oraz przetwarzające je za pomocą strumieni (`Stream`). Przykład
  znaleźć można w `UserRepository`
- [X] rozwiązanie powinno spełniać zasady SOLID
- [ ] rozwiązanie powinno być pokryte testami jednostkowymi (>80%)
- [X] rozwiązanie powinno być odpowiednio zhermetyzowane (nie udostępniać funkcjonalności pozostałym pakietom programu)
- [ ] kod powinien być odpowiednio udokumentowany za pomocą JavaDoc
- [ ] do kodu powinna zostać dołączona wyeksportowana kolekcja zapytań z programu Postman, pozwalająca przetestować
  stworzone API
- [X] rozwiązanie powinno wykorzystywać zwykłe klasy Javowe do definicji obiektów transferu danych (DTO)

## ZADANIE 2. API sieciowe do generowania raportów treningowych

### Potrzeba biznesowa

Jako użytkownik systemu, chcę mieć możliwość otrzymywania wiadomości e-mailowych z podsumowaniem treningów w każdym
miesiącu. (warto to zrobić asynciem)
### Wymagania funkcjonalne

- [ ] w oparciu o ustalony zdefiniowany czas (co miesiąc) generowany jest raport z treningami dla każdego użytkownika z
  danego miesiąca
- [ ] raport wysyłany jest za pomocą e-mail z podsumowaniem do każdego użytkownika, ile treningów ma zarejestrowanych łącznie

## ZADANIE 3 (opcjonalne). Sieciowe API do operacji typu CRUD na Statistics (bez użycia rekordów)

### Potrzeba biznesowa

### Wymagania funkcjonalne

Stworzone API powinno pozwalać na:

- [ ] utworzenie nowych statystyk
- [ ] aktualizacja Statystyk Użytkownika implementacja funkcjonalności do aktualizacji istniejących statystyk dla
  użytkownika.
- [ ] pobranie szczegółów dotyczących statystyk dla danego użytkownika
- [ ] usunięcie statystyk
- [ ] wyszukiwanie wszystkich statystyk gdzie ilość kalorii jest większa niż zdefiniowana


### Wymagania techniczne

- [ ] przygotowanie danych wejściowych (uzupełnienie skryptu ładującego dane przy starcie aplikacji)
- [ ] API sieciowe powinno wykorzystywać protokół HTTP oraz format JSON do transferu danych
- [ ] w repozytoriach rozwiązanie może wykorzystywać metody dostarczane przez interfejs JpaRepository oraz metody
  domyślne, pobierające dane za pomocą `findAll()` oraz przetwarzające je za pomocą strumieni (`Stream`). Przykład
  znaleźć można w `UserRepository`
- [ ] rozwiązanie powinno spełniać zasady SOLID
- [ ] rozwiązanie powinno być pokryte testami jednostkowymi (>80%)
- [ ] rozwiązanie powinno być odpowiednio zhermetyzowane (nie udostępniać funkcjonalności pozostałym pakietom programu)
- [ ] kod powinien być odpowiednio udokumentowany za pomocą JavaDoc
- [ ] do kodu powinna zostać dołączona wyeksportowana kolekcja zapytań z programu Postman, pozwalająca przetestować
  stworzone API
- [ ] rozwiązanie powinno wykorzystywać zwykłe klasy Javowe do definicji obiektów transferu danych (DTO)
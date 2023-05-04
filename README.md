# PAM-Projekt
Projekt aplikacji mobilnej sklepu z elektroniką użytkową.
## Opis

  Jest to projekt aplikacji mobilnej zawierającej w sobie funkcjonalności wielu stron - inwentaryzacyjnej, sprzedaży, supportu, klienckiej. Każda ze stron w zależności od przydzielonej funkcji będzie miała dostęp do oddzielnych paneli, w całości tworzących wspólny ekosystem dostarczający produkty potencjalnemu Klientowi z inwentarza magazynu. 

Poniżej rozpisano podział zadań:

### Igor Domitrz: Panel inwentaryzacyjny 

* wylistowanie posiadanej w magazynie elektroniki użytkowej
* możliwość powiązania inwentarzu z wystawionymi ofertami
* możliwość zeskanowania urządzenia w celu wyświetlenia jego historii magazynowej/serwisowej skanując kod QR.
* gradacja urządzeń bazująca na przedstawionych parametrach (np. stan wizualy) i potencjalnych usterkach (np. pęknięcia, zadrapania, simlock, BER)

### Jakub Gęśla: Wylistowanie ofert, panel Clientside, koszyk zakupowy

* stworzenie bazy wystawionych ofert urządzeń elektronicznych
* możliwość zarejestrowania się do aplikacji oraz logowanie się do niej.
* koszyk zakupowy służący do zarządzania wybranymi produktami 
* formularz zakupowy zapewniający możliwość wypełnienia pól własnymi danymi, wybrania środka płatności, zakup produktu.

### Jarosław Smoliński: Filtrowanie i sortowanie przez użytkownika

* możliwość sortowania produktów według ceny, recenzji, popularności
* filtrowanie wyświetlanej zawartości zawartości według różnych kryteriów np. przedział cenowy, stan produktu, firma, model
* pasek wyszukiwania produktu po nazwie
* karta produktu wraz z opisem, specyfikacją, proponowanymi akcesoriami

### Marek Steciuk: Komunikacja Support-Sprzedawca-Klient

* Konto sprzedawcy/klienta określające historię sprzedaży/kupna, zawierające system oceny (opinii) wystawionej oferty
* Zintegrowana skrzynka pocztowa (relacja Sprzedawca-Klient) 
* Implementacja panelu informacyjnego (Kontakt/Regulamin) z możliwością komunikacji z działem Supportu
* System powiadomień powiązany z możliwością obserwowania sprzedającego

## Autorzy
* Igor Domitrz (igor.domitrz@student.wat.edu.pl)
* Jarosław Smoliński (jaroslaw.smolinski@student.wat.edu.pl)
* Jakub Gęśla (jakub.gesla@student.wat.edu.pl)
* Marek Steciuk (marek.steciuk@student.wat.edu.pl)
## Historia wersji
* 0.1
    * Manifest README

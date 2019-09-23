# Szkoła jazdy - bazy danych [projekt]

Aplikacja oparta o baze danych Oracle wykonana w ramach projektu z przedmiotu bazy danych podczas IV semestru studiów na kierunku Informatyka na PWr. 
Skład grupy: *Emilia Augustyn*, *Joanna Komorniczak*, *Mateusz Śliwka*

## Instalacja i używanie aplikacji

W celu uruchomienia i uzyskania pełnej funkcjonalności działania należy najpierw zaimportować plik **baza.sql** do istniejącej już bazy z użytkownikiem o nazwie *szkolajazdy* i haśle *admin*. W razie potrzeby zmiany należy edytować plik persistance.xml umieszczając tam prawdiłowe dane dostępowe. Po prawdiłowym zaimportowaniu bazy możemy otworzyć aplikację na dwa sposoby:
* niezależny plik **SzkolaJazdy-BD1.jar**, która zawiera w sobie wszystkie wymagane biblioteki
* otwierając powyższe repozytorium jako projekt i kompilując klasę **main.java**

**UWAGA!** W niektórych przypadkach, do pełnej zsynchronizowania bazy danych Oracle z aplikacją opartą o Hibernate wymagana jest zmiana limitów bazy. W tym celu należy wykonać poniższe zapytanie (z poziomu administratora bazy) i uruchomić serwer ponownie.
```
alter system set processes = 500 scope = spfile;
alter system set sessions = 400 scope = spfile;
```
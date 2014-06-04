Importer słownika do lematyzatora
===============

Słowem wstępu
---------------
Importer przesyła dane ze słownika do bazy Mongo, która dostępna jest na maszynce wirtualnej. Całość działa w obrębie samej maszyny, więc nie potrzebne jest udostępnianie bazy Mongo na zewnątrz. Słowa zapisywane są w kolekcjach, których nazwy są literami alfabetu.

Przed rozpoczęciem
---------------
Na starcie należy rozpakować plik słownika:

    tar -zxvf dict.tar.gz
    
Rozpakuje się on do pliku dict.txt.

Kompilacja
--------------
Następnie należy skompilować pliki javy:

    make
    
Zostaną utworzone pliki .class potrzebne do uruchmienia.

Uruchamianie
--------------
Aby uruchomić importer należy wykonać polecenie:

    make run [dict.txt] [dict]
    
Program może przyjąć dwa opcjonalne parametry.

Pierwszy (domyślnie dict.txt) - plik z którego importowane są słowa do bazy danych.

Drugi (domyślnie dict) - nazwa bazy danych w Mongo do której zapisywane są słowa.

Importerna uruchomić z tylko pierwszym parametrem (drugi jest domyślny), albo odrazu z dwoma. Niemożliwe jest wybranie nazwy bazy bez podanie pliku słownika.
    

## Uruchomienie modułów elektronicznych

Przed wgraniem oprogramowania w języku Lua, należy wgrać do modułu firmware, będący interpreterem języka Lua:
* [NodeMCU flashing the firmware](https://nodemcu.readthedocs.io/en/master/en/flash/)

By moduły łączyły się automatycznie z siecią WiFi i serwerem, należy skonfigurować następujące zmienne w pliku [connection.lua](https://github.com/chythe/MobileHomeControlSystem/blob/master/ModuleDriver/connection.lua):
```
SERVER_IP - Adres IP serwera
SERVER_PORT - Port serwera TCP
SSID - Nazwa sieci
PASSWORD - Hasło dostępu do sieci
```

Następnie należy wgrać pliki *.lua do pamięci flash modułu:
* [NodeMCU uploading code](https://nodemcu.readthedocs.io/en/master/en/upload/)

## Uruchomienie serwera

By uruchomić bazę danych na serwerze należy pobrać i zainstalować PostgreSQL:
* [PostgreSQL](https://www.postgresql.org/download/)

W folderze `bin`, gdzie zainstalowaliśmy postgresa znajduje się plik `psql.exe`.
Dodajemy ścieżkę do zmiennej środowiskowej Path lub wykonujemy w tym folderze z konsoli następujące polecenia:
```bash
psql.exe -U postgres
```

Zostaniemy poproszeni o podanie hasła. Jest ono identyczne jak dla lokalnego użytkownika w systemie. Kolejno wykonujemy:
```bash
create user admin with superuser password 'pass1234';
create database mobile_home_control_system;
grant all privileges on database "mobile_home_control_system" to admin;
\q
psql.exe -U admin mobile_home_control_system
```
Następnie w uruchomionej konsoli wykonujemy zawartość [skryptu DDL](https://github.com/chythe/MobileHomeControlSystem/blob/master/Database/MobileHomeControlSystem.ddl)

W tej samej konsoli, dodajemy użytkownika 'admin' z rolą administratora i zaszyfrowanym hasłem 'pass1234':
```sql
insert into users (username, password, role) 
values ('admin', 'bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f', 'ADMIN');
```

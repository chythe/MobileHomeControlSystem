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

from config.databaseconfig import database_connect
from tcp.tcpserver import tcp_server
from rest.restserver import rest_server
from interface.console import console


if __name__ == '__main__':
    database_connect()
    tcp_server.start()
    rest_server.start()
    console.start()

from pony.orm import *
from enums.enumconverter import EnumConverter
from enum import Enum


db = Database()


def database_connect():
    db.bind(provider='postgres', user='admin', password='pass1234',
            host='localhost', port="5432", database='mobile_home_control_system')
    db.provider.converter_classes.append((Enum, EnumConverter))
    sql_debug(True)
    db.generate_mapping()

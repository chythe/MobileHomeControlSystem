from pony.orm import *
from enums.enumconverter import EnumConverter
from enum import Enum


db = Database()


from model.room import Room
from model.user import User
from model.module import Module
from model.switchtype import SwitchType
from model.moduleconf import ModuleConfiguration


def database_connect():
    db.bind(provider='postgres', user='admin', password='pass1234',
            host='localhost', port="5432", database='mobile_home_control_system')
    db.provider.converter_classes.append((Enum, EnumConverter))
    sql_debug(True)
    db.generate_mapping()


def database_disconnect():
    db.disconnect()

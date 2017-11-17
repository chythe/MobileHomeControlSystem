from mongoengine import *
from model.room import Room
from model.switchtype import SwitchType


class Configuration(Document):
    userId = LongField(required=True)
    switchNo = IntField(required=True)
    room = ReferenceField(Room)
    type = ReferenceField(SwitchType)

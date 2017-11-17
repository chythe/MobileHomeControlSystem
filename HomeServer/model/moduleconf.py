from mongoengine import *
from model.room import Room
from model.switchtype import SwitchType


class ModuleConfiguration(Document):
    moduleId = LongField(required=True)
    switchNo = IntField(required=True)
    room = ReferenceField(Room)
    type = ReferenceField(SwitchType)

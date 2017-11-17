from mongoengine import *


class SwitchType(Document):
    typeId = LongField(required=True)
    name = StringField(required=True)

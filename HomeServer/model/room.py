from mongoengine import *


class Room(Document):
    roomId = LongField(required=True)
    name = StringField(required=True)

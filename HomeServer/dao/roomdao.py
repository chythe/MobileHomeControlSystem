from database.databaseconfig import *
from model.room import Room


class RoomDao(object):

    @db_session
    def create_room(self, name):
        return Room(name=name)

    @db_session
    def read_rooms(self):
        return Room.select()[:]

    @db_session
    def read_room(self, room_id):
        return Room[room_id]

    @db_session
    def update_room(self, room_id, name):
        r = Room[room_id]
        r.name = name
        return r

    @db_session
    def delete_room(self, room_id):
        Room[room_id].delete()

    @db_session
    def get_room_module_configurations(self, room_id):
        return list(Room[room_id].module_configurations)

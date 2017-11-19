from config.databaseconfig import *


class RoomDao:

    @db_session
    def create_room(self, name):
        Room(name=name)

    @db_session
    def read_rooms(self):
        return Room.select()[:]

    @db_session
    def read_room(self, room_id):
        return Room[room_id]

    @db_session
    def update_room(self, room_id, name):
        Room[room_id].name = name

    @db_session
    def delete_room(self, room_id):
        Room[room_id].delete()

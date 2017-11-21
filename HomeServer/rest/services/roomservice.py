from dao.roomdao import RoomDao


class RoomService(object):

    def __init__(self):
        self.__room_dao = RoomDao()

    def create_room(self, name):
        return self.__room_dao.create_room(name)

    def read_rooms(self):
        return self.__room_dao.read_rooms()

    def read_room(self, room_id):
        return self.__room_dao.read_room(room_id)

    def update_room(self, room_id, name):
        return self.__room_dao.update_room(room_id, name)

    def delete_room(self, room_id):
        self.__room_dao.delete_room(room_id)


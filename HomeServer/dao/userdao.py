from config.databaseconfig import *


class UserDao:

    # TODO TransactionError, OrmError

    @db_session
    def create_user(self, username, password, role):
        User(username=username, password=password, role=role)

    @db_session
    def read_users(self):
        return User.select()[:]

    @db_session
    def read_user(self, room_id):
        return User[room_id]

    @db_session
    def update_user(self, room):
        User[room.room_id] = room

    @db_session
    def delete_user(self, room_id):
        User[room_id].delete()

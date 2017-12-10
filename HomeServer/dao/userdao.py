from config.databaseconfig import *
from model.user import User


class UserDao(object):

    @db_session
    def create_user(self, username, password, role):
        return User(username=username, password=password, role=role)

    @db_session
    def read_users(self):
        return User.select()[:]

    @db_session
    def read_user(self, user_id):
        return User[user_id]

    @db_session
    def read_user_by_username(self, username):
        return list(User.select(lambda u: u.username == username))[0]

    @db_session
    def update_user(self, user_id, username, password, role):
        u = User[user_id]
        u.username = username
        u.password = password
        u.role = role
        return u

    @db_session
    def delete_user(self, room_id):
        User[room_id].delete()

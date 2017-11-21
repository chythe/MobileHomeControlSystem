from dao.userdao import UserDao


class UserService(object):

    def __init__(self):
        self.__user_dao = UserDao()

    def create_user(self, username, password, role):
        return self.__user_dao.create_user(username, password, role)

    def read_users(self):
        return self.__user_dao.read_users()

    def read_user(self, user_id):
        return self.__user_dao.read_user(user_id)

    def update_user(self, user_id, username, password, role):
        return self.__user_dao.update_user(user_id, username, password, role)

    def delete_user(self, user_id):
        self.__user_dao.delete_user(user_id)

    def login_user(self):
        # TODO
        pass

    def register_user(self):
        # TODO
        pass

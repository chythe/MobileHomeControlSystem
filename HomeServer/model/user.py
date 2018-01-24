from enums.role import Role
from database.databaseconfig import *
from tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class User(db.Entity):

    _table_ = "users"
    user_id = PrimaryKey(int, size=64, auto=True)
    username = Required(str, unique=True)
    password = Required(str)
    role = Required(Role)

    def to_dict(self):
        user_dict = change_dict_naming_convention(super(User, self).to_dict(), underscore_to_camel)
        user_dict.pop('password')
        return user_dict


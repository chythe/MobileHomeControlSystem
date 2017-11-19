from enums.role import Role
from config.databaseconfig import *
import json


class User(db.Entity):

    _table_ = "users"
    user_id = PrimaryKey(int, size=64, auto=True)
    username = Required(str, unique=True)
    password = Required(str)
    role = Required(Role)

    def to_json(self):
        return json.dumps(self.to_dict(), cls=EnumEncoder)

    def __dict__(self):
        return self.to_dict()

    def __str__(self):
        return self.to_json()

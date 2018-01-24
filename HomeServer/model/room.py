from database.databaseconfig import *
from tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class Room(db.Entity):

    _table_ = "rooms"
    room_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")

    def to_dict(self):
        return change_dict_naming_convention(
            super(Room, self).to_dict(), underscore_to_camel)

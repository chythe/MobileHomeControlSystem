from config.databaseconfig import *


class Room(db.Entity):

    _table_ = "rooms"
    room_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")

from config.databaseconfig import *


class ModuleConfiguration(db.Entity):

    _table_ = "module_configurations"
    mod = Required(Module, column="module_id")
    switch_no = Required(int, size=16)
    room = Required(Room, column="room_id")
    switch_type = Required(SwitchType, column="switch_type_id")
    name = Required(str, unique=True)
    PrimaryKey(mod, switch_no)

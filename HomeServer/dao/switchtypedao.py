from config.databaseconfig import *


class SwitchTypeDao(object):

    @db_session
    def create_switch_type(self, name):
        return SwitchType(name=name)

    @db_session
    def read_switch_types(self):
        return SwitchType.select()[:]

    @db_session
    def read_switch_type(self, switch_type_id):
        return SwitchType[switch_type_id]

    @db_session
    def update_switch_type(self, switch_type_id, name):
        st = SwitchType[switch_type_id]
        st.name = name
        return st

    @db_session
    def delete_switch_type(self, switch_type_id):
        SwitchType[switch_type_id].delete()

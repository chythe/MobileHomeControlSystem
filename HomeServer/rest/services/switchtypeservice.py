from dao.switchtypedao import SwitchTypeDao


class SwitchTypeService(object):

    def __init__(self):
        self.__switch_type_dao = SwitchTypeDao()

    def create_switch_type(self, name):
        return self.__switch_type_dao.create_switch_type(name)

    def read_switch_types(self):
        return self.__switch_type_dao.read_switch_types()

    def read_switch_type(self, switch_type_id):
        return self.__switch_type_dao.read_switch_type(switch_type_id)

    def update_switch_type(self, switch_type_id, name):
        return self.__switch_type_dao.update_switch_type(switch_type_id, name)

    def delete_switch_type(self, switch_type_id):
        self.__room_dao.delete_room(switch_type_id)

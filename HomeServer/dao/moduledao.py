from config.databaseconfig import *
from model.module import Module


class ModuleDao(object):

    @db_session
    def create_module(self, name, ip_address):
        try:
            return Module(name=name, ip_address=ip_address)
        except ValueError:
            rollback()

    @db_session
    def read_modules(self):
        return Module.select()[:]

    @db_session
    def read_module(self, module_id):
        return Module[module_id]

    @db_session
    def update_module(self, module_id, name, ip_address):
        m = Module[module_id]
        m.name = name
        m.ip_address = ip_address
        return m

    @db_session
    def delete_module(self, module_id):
        Module[module_id].delete()

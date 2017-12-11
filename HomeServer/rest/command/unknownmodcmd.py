from rest.tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class UnknownModuleCommand(object):

    ip_address = ""

    def __init__(self, ip_address):
        self.ip_address = ip_address

    def to_dict(self):
        unknown_module_command_dict = change_dict_naming_convention(self.__dict__, underscore_to_camel)
        return unknown_module_command_dict

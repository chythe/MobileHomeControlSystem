

class ModuleConnectionException(Exception):

    def __init__(self, message=""):
        super(ModuleConnectionException, self).__init__(message)



class ModuleConnectionException(Exception):

    def __init__(self, message, errors):
        super(ModuleConnectionException, self).__init__(message)
        self.errors = errors

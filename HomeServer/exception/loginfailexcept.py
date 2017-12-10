

class LoginFailedException(Exception):

    def __init__(self, message, errors):
        super(LoginFailedException, self).__init__(message)
        self.errors = errors



class LoginFailedException(Exception):

    def __init__(self, message=""):
        super(LoginFailedException, self).__init__(message)

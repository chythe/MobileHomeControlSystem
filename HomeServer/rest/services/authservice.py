import datetime
from itsdangerous import TimedJSONWebSignatureSerializer, BadSignature, SignatureExpired
from dao.userdao import UserDao
from exception.loginfailexcept import LoginFailedException


class AuthenticationService:

    def __init__(self):
        self.__user_dao = UserDao()

    def generate_auth_token(self, user, expiration=600):
        from rest.restserver import flask_server
        s = TimedJSONWebSignatureSerializer(flask_server.config['SECRET_KEY'], expires_in=expiration)
        return s.dumps({
            'user_id': user.user_id,
            "date_time": str(datetime.datetime.now())
        })

    def verify_auth_token(self, token):
        from rest.restserver import flask_server
        s = TimedJSONWebSignatureSerializer(flask_server.config['SECRET_KEY'])
        try:
            data = s.loads(token)
        except SignatureExpired:
            return None  # valid token, but expired
        except BadSignature:
            return None  # invalid token
        user = self.__user_dao.read_user(data['user_id'])
        return user

    def login_attempt(self, username, password):
        user = self.__user_dao.read_user_by_username(username)
        if not user or user.password != password:
            raise LoginFailedException("Login Failed")
        return user

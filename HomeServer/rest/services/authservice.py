import datetime
from itsdangerous import TimedJSONWebSignatureSerializer, BadSignature, SignatureExpired
from dao.userdao import UserDao
from exception.loginfailexcept import LoginFailedException
import os
import binascii
import pem


class AuthenticationService:

    HOUR_IN_SECONDS = 3600
    DAY_IN_SECONDS = 86400

    def __init__(self):
        self.__user_dao = UserDao()

    def generate_auth_token(self, user, expiration=DAY_IN_SECONDS):
        from rest.restserver import flask_server
        s = TimedJSONWebSignatureSerializer(
            flask_server.config['SECRET_KEY'], expires_in=expiration)
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

    def load_authentication_key(self):
        path = os.path.join(os.path.dirname(__file__), '..\key.pem')
        keys = pem.parse_file(path)
        key_string = str(keys[0]).replace('-----BEGIN PRIVATE KEY-----', '') \
            .replace('-----END PRIVATE KEY-----', '') \
            .replace('\r', '') \
            .replace('\n', '')
        return binascii.a2b_base64(key_string)

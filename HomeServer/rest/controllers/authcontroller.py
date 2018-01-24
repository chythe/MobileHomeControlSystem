from flask import Blueprint, jsonify, abort, g, request
from flask_httpauth import HTTPTokenAuth

from enums.role import Role
from exception.loginfailexcept import LoginFailedException
from rest.services.authservice import AuthenticationService
from pony.orm import OrmError

authentication_controller = Blueprint('authentication_controller', __name__)

authentication_service = AuthenticationService()

auth = HTTPTokenAuth('Bearer')


@auth.verify_token
def verify_token(token):
    if token:
        try:
            user = authentication_service.verify_auth_token(token)
            if user:
                g.current_user = user
                return True
        except TypeError:
            return False
    return False


@authentication_controller.route('/api/authentication/token', methods=['GET'])
@auth.login_required
def get_token():
    try:
        token = authentication_service.generate_auth_token(g.current_user)
        return jsonify({'token': token.decode('ascii')})
    except (OrmError, RuntimeError, ValueError):
        abort(404)


@authentication_controller.route('/api/authentication/login', methods=['POST'])
def login():
    try:
        username = request.json['username']
        password = request.json['password']
        user = authentication_service.login_attempt(username, password)
        response = jsonify(user.to_dict())
        token = authentication_service.generate_auth_token(user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except LoginFailedException:
        abort(401)
    except (OrmError, RuntimeError, ValueError):
        abort(404)


@authentication_controller.route('/api/authentication/register', methods=['POST'])
def register():
    from rest.controllers.usercontroller import user_service
    try:
        username = request.json['username']
        password = request.json['password']
        user = user_service.create_user(username, password, Role.USER)
        return jsonify(user.to_dict())
    except (OrmError, RuntimeError, ValueError):
        abort(404)

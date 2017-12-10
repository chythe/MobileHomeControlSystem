from flask import Blueprint, jsonify, request, abort, json, g

from rest.controllers.authcontroller import auth
from rest.services.authservice import AuthenticationService
from rest.services.userservice import UserService
from pony.orm import OrmError, IntegrityError
from enums.role import Role

user_controller = Blueprint('user_controller', __name__)

authentication_service = AuthenticationService()

user_service = UserService()


@user_controller.route('/api/user', methods=['GET'])
@auth.login_required
def get_users():
    try:
        users = user_service.read_users()
        users_dict = [u.to_dict() for u in users]
        response = jsonify(users_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@user_controller.route('/api/user/<int:user_id>', methods=['GET'])
@auth.login_required
def get_user(user_id):
    try:
        user = user_service.read_user(user_id)
        user_dict = user.to_dict()
        response = jsonify(user_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@user_controller.route('/api/user/current', methods=['GET'])
@auth.login_required
def get_current_user():
    try:
        user = g.current_user
        user_dict = user.to_dict()
        response = jsonify(user_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@user_controller.route('/api/user', methods=['POST'])
@auth.login_required
def create_user():
    try:
        username = request.json['username']
        password = request.json['password']
        role = Role[request.json['role']]
        user = user_service.create_user(username, password, role)
        user_dict = user.to_dict()
        response = jsonify(user_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@user_controller.route('/api/user', methods=['PUT'])
@auth.login_required
def update_user():
    try:
        user_id = request.json['userId']
        username = request.json['username']
        password = request.json['password']
        role = Role[request.json['role']]
        user = user_service.update_user(user_id, username, password, role)
        user_dict = user.to_dict()
        response = jsonify(user_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@user_controller.route('/api/user/<int:user_id>', methods=['DELETE'])
@auth.login_required
def delete_user(user_id):
    try:
        user_service.delete_user(user_id)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

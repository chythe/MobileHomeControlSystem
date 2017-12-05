from flask import Blueprint, jsonify, request, abort, json
from rest.services.userservice import UserService
from pony.orm import OrmError, IntegrityError
from enums.role import Role
from rest.tools.dictnameconv import *

user_controller = Blueprint('user_controller', __name__)

user_service = UserService()


@user_controller.route('/api/user', methods=['GET'])
def get_users():
    try:
        users = user_service.read_users()
        users_dict = [change_dict_naming_convention(u.to_dict(), underscore_to_camel) for u in users]
        return jsonify(users_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@user_controller.route('/api/user/<int:user_id>', methods=['GET'])
def get_user(user_id):
    try:
        return jsonify(user_service.read_user(user_id).to_dict())
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@user_controller.route('/api/user', methods=['POST'])
def create_user():
    try:
        username = request.json['username']
        password = request.json['password']
        role = Role[request.json['role']]
        user = user_service.create_user(username, password, role)
        user_dict = change_dict_naming_convention(user.to_dict(), underscore_to_camel)
        return jsonify(user_dict)
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@user_controller.route('/api/user', methods=['PUT'])
def update_user():
    try:
        user_id = request.json['userId']
        username = request.json['username']
        password = request.json['password']
        role = Role[request.json['role']]
        user = user_service.update_user(user_id, username, password, role)
        user_dict = change_dict_naming_convention(user.to_dict(), underscore_to_camel)
        return jsonify(user_dict)
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@user_controller.route('/api/user/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    try:
        user_service.delete_user(user_id)
        return jsonify({'result': True})
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

from flask import Blueprint, jsonify, request, abort, json
from rest.services.userservice import UserService
from pony.orm import OrmError, IntegrityError
from enums.role import Role

user_controller = Blueprint('user_controller', __name__)

user_service = UserService()


@user_controller.route('/api/user', methods=['GET'])
def get_users():
    try:
        return jsonify([u.to_dict() for u in user_service.read_users()])
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
        return jsonify(user_service.create_user(username, password, role).to_dict())
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@user_controller.route('/api/user', methods=['PUT'])
def update_user():
    try:
        user_id = request.json['user_id']
        username = request.json['username']
        password = request.json['password']
        role = Role[request.json['role']]
        return jsonify(user_service.update_user(user_id, username, password, role).to_dict())
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

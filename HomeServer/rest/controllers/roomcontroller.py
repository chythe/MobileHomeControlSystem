from flask import Blueprint, jsonify, request, abort, g
from rest.controllers.authcontroller import auth
from rest.services.authservice import AuthenticationService
from rest.services.roomservice import RoomService
from pony.orm import OrmError, IntegrityError

room_controller = Blueprint('room_controller', __name__)

authentication_service = AuthenticationService()

room_service = RoomService()


@room_controller.route('/api/room', methods=['GET'])
@auth.login_required
def get_rooms():
    try:
        rooms = room_service.read_rooms()
        rooms_dict = [r.to_dict() for r in rooms]
        response = jsonify(rooms_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room/<int:room_id>', methods=['GET'])
@auth.login_required
def get_room(room_id):
    try:
        room = room_service.read_room(room_id)
        room_dict = room.to_dict()
        response = jsonify(room_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room', methods=['POST'])
@auth.login_required
def create_room():
    try:
        name = request.json['name']
        room = room_service.create_room(name)
        room_dict = room.to_dict()
        response = jsonify(room_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@room_controller.route('/api/room', methods=['PUT'])
@auth.login_required
def update_room():
    try:
        room_id = request.json['roomId']
        name = request.json['name']
        room = room_service.update_room(room_id, name)
        room_dict = room.to_dict()
        response = jsonify(room_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@room_controller.route('/api/room/<int:room_id>', methods=['DELETE'])
@auth.login_required
def delete_room(room_id):
    try:
        room_service.delete_room(room_id)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room/module-configuration/<int:room_id>', methods=['GET'])
@auth.login_required
def get_room_module_configurations(room_id):
    try:
        module_configurations = room_service.get_room_module_configurations(room_id)
        module_configurations_dict = [mc.to_dict() for mc in module_configurations]
        response = jsonify(module_configurations_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

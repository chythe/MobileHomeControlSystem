from flask import Blueprint, jsonify, request, abort
from rest.services.roomservice import RoomService
from pony.orm import OrmError, IntegrityError
from rest.tools.dictnameconv import *

room_controller = Blueprint('room_controller', __name__)

room_service = RoomService()


@room_controller.route('/api/room', methods=['GET'])
def get_rooms():
    try:
        rooms = room_service.read_rooms()
        rooms_dict = [change_dict_naming_convention(r.to_dict(), underscore_to_camel) for r in rooms]
        return jsonify(rooms_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room/<int:room_id>', methods=['GET'])
def get_room(room_id):
    try:
        room = room_service.read_room(room_id)
        room_dict = change_dict_naming_convention(room.to_dict(), underscore_to_camel)
        return jsonify(room_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room', methods=['POST'])
def create_room():
    try:
        name = request.json['name']
        room = room_service.create_room(name)
        room_dict = change_dict_naming_convention(room.to_dict(), underscore_to_camel)
        return jsonify(room_dict)
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@room_controller.route('/api/room', methods=['PUT'])
def update_room():
    try:
        room_id = request.json['roomId']
        name = request.json['name']
        room = room_service.update_room(room_id, name)
        room_dict = change_dict_naming_convention(room.to_dict(), underscore_to_camel)
        return jsonify(room_dict)
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@room_controller.route('/api/room/<int:room_id>', methods=['DELETE'])
def delete_room(room_id):
    try:
        room_service.delete_room(room_id)
        return jsonify({'result': True})
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

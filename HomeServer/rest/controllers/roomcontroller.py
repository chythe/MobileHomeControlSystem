from flask import Blueprint, jsonify, request, abort
from rest.services.roomservice import RoomService
from pony.orm import OrmError, IntegrityError

room_controller = Blueprint('room_controller', __name__)

room_service = RoomService()


@room_controller.route('/api/room', methods=['GET'])
def get_rooms():
    try:
        return jsonify([r.to_dict() for r in room_service.read_rooms()])
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room/<int:room_id>', methods=['GET'])
def get_room(room_id):
    try:
        return jsonify(room_service.read_room(room_id).to_dict())
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@room_controller.route('/api/room', methods=['POST'])
def create_room():
    try:
        name = request.json['name']
        return jsonify(room_service.create_room(name).to_dict())
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@room_controller.route('/api/room', methods=['PUT'])
def update_room():
    try:
        room_id = request.json['room_id']
        name = request.json['name']
        return jsonify(room_service.update_room(room_id, name).to_dict())
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

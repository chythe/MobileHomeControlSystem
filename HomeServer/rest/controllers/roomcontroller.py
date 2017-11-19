from flask import Blueprint, jsonify
from dao.roomdao import RoomDao

room_controller = Blueprint('room_controller', __name__)

room_dao = RoomDao()


@room_controller.route('/api/room')
def room():
    return 'Room'

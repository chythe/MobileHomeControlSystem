from flask import Blueprint, jsonify, request, abort
from rest.services.switchtypeservice import SwitchTypeService
from pony.orm import OrmError, IntegrityError

switch_type_controller = Blueprint('switch_type_controller', __name__)

switch_type_service = SwitchTypeService()


@switch_type_controller.route('/api/switch-type', methods=['GET'])
def get_switch_types():
    try:
        return jsonify([st.to_dict() for st in switch_type_service.read_switch_types()])
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['GET'])
def get_switch_type(switch_type_id):
    try:
        return jsonify(switch_type_service.read_switch_type(switch_type_id).to_dict())
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type', methods=['POST'])
def create_switch_type():
    try:
        name = request.json['name']
        return jsonify(switch_type_service.create_switch_type(name).to_dict())
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@switch_type_controller.route('/api/switch-type', methods=['PUT'])
def update_switch_type():
    try:
        room_id = request.json['switch_type_id']
        name = request.json['name']
        return jsonify(switch_type_service.update_switch_type(room_id, name).to_dict())
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['DELETE'])
def delete_switch_type(switch_type_id):
    try:
        switch_type_service.delete_switch_type(switch_type_id)
        return jsonify({'result': True})
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

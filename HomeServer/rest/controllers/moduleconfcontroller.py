from flask import Blueprint, jsonify, request, abort
from rest.services.moduleconfservice import ModuleConfigurationService
from pony.orm import OrmError


module_configuration_controller = Blueprint('module_configuration_controller', __name__)

module_configuration_service = ModuleConfigurationService()


@module_configuration_controller.route('/api/module-configuration', methods=['GET'])
def get_module_configurations():
    try:
        return jsonify([mc.to_dict() for mc in module_configuration_service.read_module_configurations()])
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration/', methods=['GET'])
def get_module_configuration():
    try:
        module_id = request.args.get('module_id')
        switch_no = request.args.get('switch_no')
        return jsonify(module_configuration_service.read_module_configuration(module_id, switch_no).to_dict())
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration', methods=['POST'])
def create_module_configuration():
    try:
        module_id = request.json['module_id']
        switch_no = request.json['switch_no']
        room_id = request.json['room_id']
        switch_type_id = request.json['switch_type_id']
        name = request.json['name']
        return jsonify(module_configuration_service.create_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name).to_dict())
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration', methods=['PUT'])
def update_module_configuration():
    try:
        module_id = request.json['module_id']
        switch_no = request.json['switch_no']
        room_id = request.json['room_id']
        switch_type_id = request.json['switch_type_id']
        name = request.json['name']
        return jsonify(module_configuration_service.update_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name).to_dict())
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration/', methods=['DELETE'])
def delete_module_configuration():
    try:
        module_id = request.args.get('module_id')
        switch_no = request.args.get('switch_no')
        module_configuration_service.delete_module_configuration(module_id, switch_no)
        return jsonify({'result': True})
    except (OrmError, RuntimeError):
        abort(404)

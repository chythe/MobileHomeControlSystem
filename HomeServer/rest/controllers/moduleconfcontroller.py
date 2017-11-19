from flask import Blueprint, jsonify, request, abort
from rest.services.moduleconfservice import ModuleConfigurationService
from pony.orm import OrmError


module_configuration_controller = Blueprint('module_configuration_controller', __name__)

module_configuration_service = ModuleConfigurationService()


@module_configuration_controller.route('/api/module-configuration', methods=['GET'])
def get_module_configurations():
    try:
        return jsonify([mc.to_dict() for mc in module_configuration_service.read_modules()])
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration/', methods=['GET'])
def get_module_configuration():
    try:
        return jsonify(module_configuration_service.read_module_configuration(
            request.args.get('module_id'), request.args.get('switch_no')).to_dict())
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration', methods=['POST'])
def create_module_configuration():
    try:
        module_configuration_service.create_module_configuration(
            request.json['module_id'], request.json['switch_no'],
            request.json['room_id'], request.json['switch_type_id'],
            request.json['name'])
        return jsonify(module_configuration_service.read_module_configuration(
            request.json['module_id'], request.json['switch_no']).to_dict())
    except (OrmError, RuntimeError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration', methods=['PUT'])
def update_module_configuration():
    try:
        module_configuration_service.update_module_configuration(
            request.json['module_id'], request.json['switch_no'],
            request.json['room_id'], request.json['switch_type_id'],
            request.json['name'])
        return jsonify(module_configuration_service.read_module_configuration(
            request.json['module_id'], request.json['switch_no']).to_dict())
    except (OrmError, RuntimeError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration/', methods=['DELETE'])
def delete_module_configuration():
    try:
        module_configuration_service.delete_module_configuration(
            request.args.get('module_id'), request.args.get('switch_no'))
        return jsonify({'result': True})
    except (OrmError, RuntimeError):
        abort(404)

from flask import Blueprint, jsonify, request, abort
from rest.services.moduleconfservice import ModuleConfigurationService
from pony.orm import OrmError
from rest.tools.dictnameconv import *

module_configuration_controller = Blueprint('module_configuration_controller', __name__)

module_configuration_service = ModuleConfigurationService()


@module_configuration_controller.route('/api/module-configuration', methods=['GET'])
def get_module_configurations():
    try:
        module_configurations = module_configuration_service.read_module_configurations()
        module_configurations_dict = [change_dict_naming_convention(
            mc.to_dict(), underscore_to_camel) for mc in module_configurations]
        return jsonify(module_configurations_dict)
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration/', methods=['GET'])
def get_module_configuration():
    try:
        module_id = request.args.get('moduleId')
        switch_no = request.args.get('switchNo')
        module_configuration = module_configuration_service.read_module_configuration(module_id, switch_no)
        module_configuration_dict = recursive_to_dict(module_configuration, has_iterated=False, related_objects=True)
        module_configuration_dict = change_dict_naming_convention(module_configuration_dict, underscore_to_camel)
        return jsonify(module_configuration_dict)
    except (OrmError, RuntimeError, ValueError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration', methods=['POST'])
def create_module_configuration():
    try:
        module_id = request.json['moduleId']
        switch_no = request.json['switchNo']
        room_id = request.json['roomId']
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        module_configuration = module_configuration_service.create_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name)
        module_configuration_dict = change_dict_naming_convention(module_configuration.to_dict(), underscore_to_camel)
        return jsonify(module_configuration_dict)
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration', methods=['PUT'])
def update_module_configuration():
    try:
        module_id = request.json['moduleId']
        switch_no = request.json['switchNo']
        room_id = request.json['roomId']
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        module_configuration = module_configuration_service.update_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name)
        module_configuration_dict = change_dict_naming_convention(module_configuration.to_dict(), underscore_to_camel)
        return jsonify(module_configuration_dict)
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration/', methods=['DELETE'])
def delete_module_configuration():
    try:
        module_id = request.args.get('moduleId')
        switch_no = request.args.get('switchNo')
        module_configuration_service.delete_module_configuration(module_id, switch_no)
        return jsonify({'result': True})
    except (OrmError, RuntimeError):
        abort(404)

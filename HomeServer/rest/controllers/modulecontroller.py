from flask import Blueprint, jsonify, request, abort
from rest.services.moduleservice import ModuleService
from pony.orm import OrmError
from rest.tools.dictnameconv import *

module_controller = Blueprint('module_controller', __name__)

module_service = ModuleService()


@module_controller.route('/api/module', methods=['GET'])
def get_modules():
    try:
        modules = module_service.read_modules()
        modules_dict = [change_dict_naming_convention(mc.to_dict(), underscore_to_camel) for mc in modules]
        return jsonify(modules_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module/<int:module_id>', methods=['GET'])
def get_module(module_id):
    try:
        module = module_service.read_module(module_id)
        module_dict = change_dict_naming_convention(module.to_dict(), underscore_to_camel)
        return jsonify(module_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module', methods=['POST'])
def create_module():
    try:
        name = request.json['name']
        module = module_service.create_module(name)
        module_dict = change_dict_naming_convention(module.to_dict(), underscore_to_camel)
        return jsonify(module_dict)
    except (OrmError, KeyError, TypeError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@module_controller.route('/api/module', methods=['PUT'])
def update_module():
    try:
        module_id = request.json['moduleId']
        name = request.json['name']
        module = module_service.update_module(module_id, name)
        module_dict = change_dict_naming_convention(module.to_dict(), underscore_to_camel)
        return jsonify(module_dict)
    except (OrmError, TypeError) as e:
        print(str(e))
        abort(400)


@module_controller.route('/api/module/<int:module_id>', methods=['DELETE'])
def delete_module(module_id):
    try:
        module_service.delete_module(module_id)
        return jsonify({'result': True})
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)

from flask import Blueprint, jsonify, request, abort
from rest.services.moduleservice import ModuleService
from pony.orm import OrmError

module_controller = Blueprint('module_controller', __name__)

module_service = ModuleService()


@module_controller.route('/api/module', methods=['GET'])
def get_modules():
    try:
        return jsonify([m.to_dict() for m in module_service.read_modules()])
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module/<int:module_id>', methods=['GET'])
def get_module(module_id):
    try:
        return jsonify(module_service.read_module(module_id).to_dict())
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module', methods=['POST'])
def create_module():
    try:
        name = request.json['name']
        return jsonify(module_service.create_module(name).to_dict())
    except (OrmError, KeyError, TypeError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@module_controller.route('/api/module', methods=['PUT'])
def update_module():
    try:
        module_id = request.json['module_id']
        name = request.json['name']
        return jsonify(module_service.update_module(module_id, name).to_dict())
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

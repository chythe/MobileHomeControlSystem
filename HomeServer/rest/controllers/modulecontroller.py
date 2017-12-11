from flask import Blueprint, jsonify, request, abort, g

from rest.services.authservice import AuthenticationService
from rest.services.moduleservice import ModuleService
from rest.controllers.authcontroller import auth
from pony.orm import OrmError
from rest.tools.dictnameconv import *

module_controller = Blueprint('module_controller', __name__)

authentication_service = AuthenticationService()

module_service = ModuleService()


@module_controller.route('/api/module', methods=['GET'])
@auth.login_required
def get_modules():
    try:
        modules = module_service.read_modules()
        modules_dict = [m.to_dict() for m in modules]
        response = jsonify(modules_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module/<int:module_id>', methods=['GET'])
@auth.login_required
def get_module(module_id):
    try:
        module = module_service.read_module(module_id)
        module_dict = module.to_dict()
        response = jsonify(module_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, ValueError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module', methods=['POST'])
@auth.login_required
def create_module():
    try:
        name = request.json['name']
        ip_address = request.json['ipAddress']
        module = module_service.create_module(name, ip_address)
        module_dict = module.to_dict()
        response = jsonify(module_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, KeyError, TypeError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@module_controller.route('/api/module', methods=['PUT'])
@auth.login_required
def update_module():
    try:
        module_id = request.json['moduleId']
        name = request.json['name']
        ip_address = request.json['ipAddress']
        module = module_service.update_module(module_id, name, ip_address)
        module_dict = module.to_dict()
        response = jsonify(module_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, TypeError, ValueError) as e:
        print(str(e))
        abort(400)


@module_controller.route('/api/module/<int:module_id>', methods=['DELETE'])
@auth.login_required
def delete_module(module_id):
    try:
        module_service.delete_module(module_id)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, ValueError) as e:
        print(str(e))
        abort(404)


@module_controller.route('/api/module/search', methods=['GET'])
@auth.login_required
def search_unknown_modules():
    try:
        connected_unknown_modules = module_service.search_unknown_modules()
        connected_unknown_modules_dict = [mod.to_dict() for mod in connected_unknown_modules]
        response = jsonify(connected_unknown_modules_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, ValueError) as e:
        print(str(e))
        abort(404)

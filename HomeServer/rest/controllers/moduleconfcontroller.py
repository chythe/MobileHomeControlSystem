from flask import Blueprint, jsonify, request, abort, g
from rest.controllers.authcontroller import auth
from model.moduleconf import ModuleConfiguration
from rest.services.authservice import AuthenticationService
from rest.services.moduleconfservice import ModuleConfigurationService
from pony.orm import OrmError
from rest.tools.dictnameconv import *

module_configuration_controller = Blueprint('module_configuration_controller', __name__)

authentication_service = AuthenticationService()

module_configuration_service = ModuleConfigurationService()


@module_configuration_controller.route('/api/module-configuration', methods=['GET'])
@auth.login_required
def get_module_configurations():
    try:
        module_configurations = module_configuration_service.read_module_configurations()
        module_configurations_dict = [mc.to_dict() for mc in module_configurations]
        response = jsonify(module_configurations_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration/', methods=['GET'])
@auth.login_required
def get_module_configuration():
    try:
        module_id = request.args.get('moduleId')
        switch_no = request.args.get('switchNo')
        module_configuration = module_configuration_service.read_module_configuration(module_id, switch_no)
        module_configuration_dict = module_configuration.to_dict()
        response = jsonify(module_configuration_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, ValueError):
        abort(404)


@module_configuration_controller.route('/api/module-configuration', methods=['POST'])
@auth.login_required
def create_module_configuration():
    try:
        module_id = request.json['moduleId']
        switch_no = request.json['switchNo']
        room_id = request.json['roomId']
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        module_configuration = module_configuration_service.create_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name)
        module_configuration_dict = module_configuration.to_dict()
        response = jsonify(module_configuration_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration', methods=['PUT'])
@auth.login_required
def update_module_configuration():
    try:
        module_id = request.json['moduleId']
        switch_no = request.json['switchNo']
        room_id = request.json['roomId']
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        module_configuration = module_configuration_service.update_module_configuration(
            module_id, switch_no, room_id, switch_type_id, name)
        module_configuration_dict = module_configuration.to_dict()
        response = jsonify(module_configuration_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError, KeyError):
        abort(400)


@module_configuration_controller.route('/api/module-configuration/', methods=['DELETE'])
@auth.login_required
def delete_module_configuration():
    try:
        module_id = request.args.get('moduleId')
        switch_no = request.args.get('switchNo')
        module_configuration_service.delete_module_configuration(module_id, switch_no)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError):
        abort(404)

from flask import Blueprint, request, jsonify, abort
from rest.services.switchservice import SwitchService

switch_controller = Blueprint('switch_controller', __name__)

switch_service = SwitchService()


@switch_controller.route('/api/switch', methods=['GET'])
def get_state():
    # TODO
    pass


@switch_controller.route('/api/switch', methods=['POST'])
def switch():
    try:
        module_id = request.json['moduleId']
        switch_no = request.json['switchNo']
        state = request.json['state']
        switch_service.switch(module_id, switch_no, state)
        return jsonify({'result': True})
    except KeyError:
        abort(400)
    except AttributeError:
        abort(404)

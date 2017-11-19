from flask import Blueprint, make_response, jsonify

error_controller = Blueprint('error_controller', __name__)


# @error_controller.errorhandler(404)
# def not_found(error):
#     return make_response(jsonify({'error': 'Not found'}), 404)
#
#
# @error_controller.errorhandler(400)
# def bad_request(error):
#     return make_response(jsonify({'error': str(error)}), 400)
#
#
# @error_controller.error_handler(403)
# def unauthorized_access():
#     return make_response(jsonify({'error': 'Unauthorized access'}), 403)

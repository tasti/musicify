from flask import Blueprint, jsonify, request
import echonest

genres_api = Blueprint('genres_api', __name__)

@genres_api.route('', methods=['GET'])
def get_genres():
  genres = echonest.get_genres()
  if genres == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': genres })

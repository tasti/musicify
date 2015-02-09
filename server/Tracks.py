from flask import Blueprint, jsonify, request
import spotify

tracks_api = Blueprint('tracks_api', __name__)

@tracks_api.route('/artist', methods=['GET'])
def get_tracks_artist():
  if 'spotify_id' not in request.args:
    return jsonify({ 'success': False, 'message': 'spotify_id not specified.' })    

  tracks = spotify.get_tracks_by_artist_id(request.args['spotify_id'])

  if tracks == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': tracks })

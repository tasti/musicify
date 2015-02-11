from flask import Blueprint, jsonify, request
import spotify

tracks_api = Blueprint('tracks_api', __name__)

@tracks_api.route('/artist/<spotify_id>', methods=['GET'])
def get_tracks_artist(spotify_id):
  tracks = spotify.get_tracks_by_artist_id(spotify_id)
  if tracks == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': tracks })

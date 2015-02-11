from flask import Blueprint, jsonify, request
import echonest, spotify

artists_api = Blueprint('artists_api', __name__)

@artists_api.route('', methods=['GET'])
def get_artists():
  if 'name' not in request.args:
    return jsonify({ 'success': False, 'message': 'name not specified.' })    

  artists = spotify.get_artists_by_name(request.args['name'])
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': artists })

def get_artists_genre_helper(name):
  artists = echonest.get_artists_by_genre(name)
  if artists == None:
    return None

  # We want the spotify equivalent data of the artists
  return spotify.get_artists_by_id([artist['spotify_id'] for artist in artists])

@artists_api.route('/genre', methods=['GET'])
def get_artists_genre():
  if 'name' not in request.args:
    return jsonify({ 'success': False, 'message': 'name not specified.' })    

  artists = get_artists_genre_helper(request.args['name'])
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': artists })

@artists_api.route('/related/<spotify_id>', methods=['GET'])
def get_artists_similar(spotify_id):
  artists = spotify.get_artists_by_relation(spotify_id)
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': artists })

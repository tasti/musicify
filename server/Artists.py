from flask import Blueprint, request, jsonify
from google.appengine.ext import ndb
import spotify

artists_api = Blueprint('artists_api', __name__)

class Artist(ndb.Model):
  image_large_url = ndb.StringProperty(required=True)
  image_small_url = ndb.StringProperty(required=True)
  name = ndb.StringProperty(required=True)
  popularity = ndb.IntegerProperty(required=True)
  spotify_id = ndb.StringProperty(required=True)

@artists_api.route('/<name>', methods=['GET'])
def get_artist(name):
  artist = Artist.query(Artist.name == name).get()

  if artist != None:
    return jsonify({ 'success': True, 'artist': artist.name })

  return jsonify({ 'success': False, 'message': '%s is not an artist.' % name })

@artists_api.route('', methods=['GET'])
def get_artists():
  artists = Artist.query().fetch()

  return jsonify({ 'success': True, 'artists': [artist.to_dict() for artist in artists] })

@artists_api.route('', methods=['POST'])
def post_artist():
  if 'name' not in request.json:
    return jsonify({ 'success': False, 'message': 'name not specified.' })    

  artists = spotify.get_artists(request.json['name'])

  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })
  
  for a in artists:
    artist = Artist(image_large_url=a['image_large_url'],
                    image_small_url=a['image_small_url'],
                    name=a['name'],
                    popularity=a['popularity'],
                    spotify_id=a['spotify_id'])
    artist.put()

  return jsonify({ 'success': True, 'message': '%d artist(s) with the name, %s, have been added.' % (len(artists), request.json['name']) })

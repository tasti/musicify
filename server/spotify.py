import my_request

SPOTIFY_API = 'https://api.spotify.com/v1'

def _get(url, cleanup):
  data = my_request.get(url, True)
  if data == None:
    return None

  return cleanup(data)

def _cleanup_artist(artist):
  images = artist.pop('images', None)

  artist.pop('external_urls', None)
  artist.pop('followers', None)
  artist.pop('genres', None)
  artist.pop('href', None)
  
  artist['image_url_medium'] = ''
  artist['image_url_small'] = ''
  if len(images) >= 2:
    artist['image_url_medium'] = images[len(images) - 2]['url'] # Assuming image size always in correct position
    artist['image_url_small'] = images[len(images) - 1]['url'] # Assuming image size always in correct position
  
  artist.pop('type', None)
  artist.pop('uri', None)

  return artist

def _cleanup_track(track):
  album = track.pop('album', None)

  track['album_image_url_medium'] = ''
  track['album_image_url_small'] = ''
  if len(album['images']) >= 3:
    track['album_image_url_medium'] = album['images'][1]['url'] # Assuming image size always in correct position
    track['album_image_url_small'] = album['images'][2]['url'] # Assuming image size always in correct position
  
  track.pop('artists', None)
  track.pop('available_markets', None)
  track.pop('disc_number', None)
  track.pop('duration_ms', None)
  track.pop('explicit', None)
  track.pop('external_ids', None)
  track.pop('external_urls', None)
  track.pop('href', None)
  track.pop('track_number', None)
  track.pop('type', None)
  track.pop('uri', None)

  return track

def _cleanup_search(search):
  search = search['artists']['items']

  return _cleanup_artists({ 'artists': search })

def _cleanup_artists(artists):
  artists = artists['artists']
  artists = [_cleanup_artist(artist) for artist in artists]

  return artists

def _cleanup_tracks(tracks):
  tracks = tracks['tracks']
  tracks = [_cleanup_track(track) for track in tracks]

  return tracks

def get_artists_by_name(name):
  return _get('%s/search?q=%s&type=artist' % (SPOTIFY_API, name), _cleanup_search)

def get_artists_by_id(artist_ids):
  return _get('%s/artists?ids=%s' % (SPOTIFY_API, ','.join(artist_ids)), _cleanup_artists)

def get_artists_by_relation(artist_id):
  return _get('%s/artists/%s/related-artists' % (SPOTIFY_API, artist_id), _cleanup_artists)

def get_tracks_by_artist_id(artist_id):
  return _get('%s/artists/%s/top-tracks?country=US' % (SPOTIFY_API, artist_id), _cleanup_tracks)

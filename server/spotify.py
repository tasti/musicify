import request

# Gets rid of irrelevent data
def cleanup_artist(artist):
  artist.pop('external_urls', None)
  artist.pop('followers', None)
  artist.pop('href', None)
  artist['spotify_id'] = artist.pop('id', None)
  artist.pop('type', None)
  artist.pop('uri', None)

  # Assuming the images are always given in sorted order
  images = artist.pop('images', None)
  artist['image_small_url'] = images[len(images) - 1]['url']
  artist['image_large_url'] = images[0]['url']

  return artist

def filter_artists(artists):
  artists = artists['artists']['items']
  artists = [cleanup_artist(artist) for artist in artists]

  return artists

def get_artists(name):
  data = request.get('https://api.spotify.com/v1/search?q=%s&type=artist&limit=10' % name)

  if data == None:
    return None

  return filter_artists(data)

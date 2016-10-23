#!/usr/bin/python

from apiclient.discovery import build
from apiclient.errors import HttpError
from oauth2client.tools import argparser
import flask
import json
import requests

DEVELOPER_KEY = "AIzaSyBN22mEPpr9diCio9znkcSsPuBGnqeK7So"
YOUTUBE_API_SERVICE_NAME = "youtube"
YOUTUBE_API_VERSION = "v3"

#FLICKR_API_LAT_LON_KEY = 'c110ace9d671efab3e24e041e3de7796i'
FLICKR_API_LAT_LON_KEY='d76d2891b922ef7cfca0adf82a8a75c0'
#FLICKR_API_SIG='b750a13c55ef2191420c4b831b7edcae'
FLICKR_API_SIG='ad9359925ac94436d0f922a5d5e9b5ef'

def getLocationFromIPAPI():
	locationData = requests.get("http://ip-api.com/json").text
	locationJsonDic = json.loads(locationData)

	return {'lat': locationJsonDic['lat'] ,'lon':locationJsonDic['lon'], 'city' : locationJsonDic['city'].encode('ascii', 'ignore')}

def getNearestPlace(geoLocation):
	#print('GeoLocation', geoLocation)
	flickrRequestString = 'https://api.flickr.com/services/rest/?method=flickr.places.findByLatLon&api_key='+FLICKR_API_LAT_LON_KEY+'&lat='+str(geoLocation['lat'])+'&lon='+str(geoLocation['lon']) + '&format=json&nojsoncallback=1'
#	print(flickrRequestString)
	flickrTextJsonResponse = requests.get(flickrRequestString).text
#	print(type(flickrTextJsonResponse))
	print("Flick Response :", flickrTextJsonResponse)
	jsonData = json.loads(flickrTextJsonResponse)
	return (jsonData['places']['place'][0]['woe_name'])
#	return str(jsonData{'places'}{'place']['woe_name'])

def youtube_search(options):
	youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION,
    		developerKey=DEVELOPER_KEY)

	search_response = youtube.search().list(
		q=options.q,
		part="id,snippet",
		maxResults=options.max_results
	).execute()

	videos = []
	channels = []
	playlists = []

	for search_result in search_response.get("items", []):
		if search_result["id"]["kind"] == "youtube#video":
			videos.append("%s (%s)" % (search_result["snippet"]["title"],
                                 search_result["id"]["videoId"]))
		elif search_result["id"]["kind"] == "youtube#channel":
			channels.append("%s (%s)" % (search_result["snippet"]["title"],
                                   search_result["id"]["channelId"]))
		elif search_result["id"]["kind"] == "youtube#playlist":
			playlists.append("%s (%s)" % (search_result["snippet"]["title"],
                                    search_result["id"]["playlistId"]))

	print "Videos:\n", "\n".join(videos), "\n"
	print "Channels:\n", "\n".join(channels), "\n"
	print "Playlists:\n", "\n".join(playlists), "\n"


if __name__ == "__main__":
	
	locationInfo = getLocationFromIPAPI()
	print(locationInfo)
	localPlace = getNearestPlace(locationInfo)	
	print(localPlace)
	k_t_s = str(locationInfo['city']) + ', ' + str(localPlace)
	print 'Key Terms to Search : ', k_t_s
	
	argparser.add_argument("--q", help="Search term", default=k_t_s)
	argparser.add_argument("--max-results", help="Max results", default=25)
	argparser.add_argument("--location", help="GeoLocation", default= '(' + str(locationInfo['lat']) + ',' + str(locationInfo['lon']) + ')')
	args = argparser.parse_args()

	try:
		youtube_search(args)
	except HttpError, e:
		print "An HTTP error %d occurred:\n%s" % (e.resp.status, e.content)
#	print(getNearestPlace(getLocationFromIPAPI()))

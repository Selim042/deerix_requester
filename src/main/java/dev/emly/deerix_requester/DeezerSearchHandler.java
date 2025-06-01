package dev.emly.deerix_requester;

import api.deezer.DeezerApi;
import api.deezer.exceptions.DeezerException;
import api.deezer.objects.data.AlbumData;
import api.deezer.objects.data.ArtistData;
import api.deezer.objects.data.TrackData;

public class DeezerSearchHandler {

	final DeezerApi DEEZER_API;

	public static final DeezerSearchHandler SEARCH = new DeezerSearchHandler();

	public DeezerSearchHandler() {
		DEEZER_API = new DeezerApi();
	}

	public ArtistData searchArtist(String query) {
		try {
			return DEEZER_API.search().searchArtist(query).execute();
		} catch (DeezerException e) {
			return null;
		}
	}

	public AlbumData searchAlbum(String query) {
		try {
			return DEEZER_API.search().searchAlbum(query).execute();
		} catch (DeezerException e) {
			return null;
		}
	}

	public TrackData searchTrack(String query) {
		try {
			return DEEZER_API.search().searchTrack(query).execute();
		} catch (DeezerException e) {
			return null;
		}
	}

}

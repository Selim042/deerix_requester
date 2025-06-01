package dev.emly.deerix_requester;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.deezer.exceptions.DeezerException;
import api.deezer.objects.data.AlbumData;
import api.deezer.objects.data.ArtistData;
import api.deezer.objects.data.TrackData;

@RestController
class QueryController {

	@GetMapping("/api/search/artist")
	public ArtistData searchArtist(@RequestParam("q") String query, Model model) throws DeezerException {
		return DeezerSearchHandler.SEARCH.searchArtist(query);
	}

	@GetMapping("/api/search/album")
	public AlbumData searchAlbum(@RequestParam("q") String query, Model model) throws DeezerException {
		return DeezerSearchHandler.SEARCH.searchAlbum(query);
	}

	@GetMapping("/api/search/track")
	public TrackData searchTrack(@RequestParam("q") String query, Model model) throws DeezerException {
		return DeezerSearchHandler.SEARCH.searchTrack(query);
	}

	@PostMapping("/api/request/artist")
	public RequestStatus requestArtist(@RequestBody int id) {
		// TODO: actually request
		return new RequestStatus(RequestStatus.Status.NOT_REQUESTED, RequestStatus.Type.ARTIST, id);
	}

	@PostMapping("/api/request/album")
	public RequestStatus requestAlbum(@RequestBody int id) {
		// TODO: actually request
		return new RequestStatus(RequestStatus.Status.NOT_REQUESTED, RequestStatus.Type.ALBUM, id);
	}

	@PostMapping("/api/request/track")
	public RequestStatus requestTrack(@RequestBody int id) {
		// TODO: actually request
		return new RequestStatus(RequestStatus.Status.NOT_REQUESTED, RequestStatus.Type.TRACK, id);
	}

	public static class RequestStatus {

		public Status status;
		public Type type;
		public int id;

		public RequestStatus(Status status, Type type, int id) {
			super();
			this.status = status;
			this.type = type;
			this.id = id;
		}

		public static enum Status {
			REQUESTED, NOT_REQUESTED;
		}

		public static enum Type {
			ARTIST, ALBUM, TRACK;
		}
	}

}

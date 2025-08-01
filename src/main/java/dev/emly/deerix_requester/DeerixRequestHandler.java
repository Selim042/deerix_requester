package dev.emly.deerix_requester;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import api.deezer.DeezerApi;
import api.deezer.exceptions.DeezerException;
import api.deezer.objects.Album;
import api.deezer.objects.Artist;
import api.deezer.objects.Track;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

public class DeerixRequestHandler {

	final DeezerApi DEEZER_API;

	public static final DeerixRequestHandler REQUEST = new DeerixRequestHandler();
	public static final File DEERIX_CONFIG_PATH = new File("/config/deerix_settings.toml");
	
	private static final TomlWriter WRITER = new TomlWriter();

	public DeerixRequestHandler() {
		DEEZER_API = new DeezerApi();
	}

	private String getConfigContents() {
		StringBuilder builder = new StringBuilder();
		try {
			FileReader reader = new FileReader(DEERIX_CONFIG_PATH);
			while (reader.ready())
				builder.append((char) reader.read());
			reader.close();
		} catch (IOException e) {
//			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return builder.toString();
	}

	private Map<String, Object> getTomlFile() {
		Toml toml = new Toml().read(getConfigContents());
		return toml.toMap();
	}

	public boolean sendNotification(String art, String description) {
		DiscordWebhook webhook = new DiscordWebhook(System.getenv("DISCORD_WEBHOOK"));
		webhook.addEmbed(new DiscordWebhook.EmbedObject()
				.setTitle("New Music Requested")
				.setDescription(description)
				.setColor(Color.GREEN)
				// TODO: Add requested when we have auth
				// .addField("Requested By", "Emily", true)
				.setThumbnail(art)
				.setImage("https://kryptongta.com/images/kryptontitle2.png"));
        try {
            webhook.execute(); //Handle exception
			return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public boolean requestArtist(int id, String note) {
		Map<String, Object> tomlMap = getTomlFile();
		@SuppressWarnings("unchecked")
		Map<String, Object> artists = (Map<String, Object>) tomlMap.get("date");
		if (artists == null) {
			artists = new HashMap<String,Object>();
			tomlMap.put("date", artists);
		}
		artists.put(Integer.toString(id), note);

		try {
			WRITER.write(tomlMap, DEERIX_CONFIG_PATH);
			Artist artist = DEEZER_API.artist().getById(id).execute();
			sendNotification(artist.getPicture(), "Artist: " + artist.getName());
		} catch (IOException | DeezerException e) {
            return false;
        }

        return true;
	}

	public boolean requestAlbum(int id, String note) {
		Map<String, Object> tomlMap = getTomlFile();
		@SuppressWarnings("unchecked")
		Map<String, Object> albums = (Map<String, Object>) tomlMap.get("albums");
		if (albums == null) {
			albums = new HashMap<String,Object>();
			tomlMap.put("albums", albums);
		}
		albums.put(Integer.toString(id), note);

		try {
			WRITER.write(tomlMap, DEERIX_CONFIG_PATH);
			Album album = DEEZER_API.album().getById(id).execute();
			sendNotification(album.getCover(), "Album: " + album.getTitle());
		} catch (IOException | DeezerException e) {
			return false;
		}
		
		return true;
	}

	public boolean requestTrack(int id, String note) {
		Map<String, Object> tomlMap = getTomlFile();
		@SuppressWarnings("unchecked")
		Map<String, Object> tracks = (Map<String, Object>) tomlMap.get("tracks");
		if (tracks == null) {
			tracks = new HashMap<String,Object>();
			tomlMap.put("tracks", tracks);
		}
		tracks.put(Integer.toString(id), note);

		try {
			WRITER.write(tomlMap, DEERIX_CONFIG_PATH);
			Track track = DEEZER_API.track().getById(id).execute();
			sendNotification(track.getAlbum().getCover(), "Track: " + track.getTitle());
		} catch (IOException | DeezerException e) {
			return false;
		}
		
		return true;
	}

}

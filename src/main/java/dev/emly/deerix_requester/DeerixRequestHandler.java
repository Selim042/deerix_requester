package dev.emly.deerix_requester;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

public class DeerixRequestHandler {

	public static final DeerixRequestHandler REQUEST = new DeerixRequestHandler();
	public static final File DEERIX_CONFIG_PATH = new File("/deerix_settings.toml");
	
	private static final TomlWriter WRITER = new TomlWriter();

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}

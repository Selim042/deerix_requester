package dev.emly.deerix_requester;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.deezer.objects.data.DeezerData;

@Controller
public class FrontendController {

	@RequestMapping("/")
	public String index(@RequestParam(required = false) String q, @RequestParam(required = false) String st,
			Model model) {
		if (q != null && st != null) {
			DeezerData<?> searchResult = null;
			switch (st) {
			case "artist":
				searchResult = DeezerSearchHandler.SEARCH.searchArtist(q);
				break;
			case "album":
				searchResult = DeezerSearchHandler.SEARCH.searchAlbum(q);
				break;
			case "track":
				searchResult = DeezerSearchHandler.SEARCH.searchTrack(q);
				break;
			}
			if (searchResult != null) {
				model.addAttribute("q", q);
				model.addAttribute("type", st);
				model.addAttribute("results", searchResult.getData());
			}
		}
		return "index";
	}

	@RequestMapping("/request")
	public String request(@RequestParam(required = false) int id, @RequestParam(required = false) String q,
			@RequestParam(required = false) String st, Model model) {
		switch (st) {
		case "artist":
			DeerixRequestHandler.REQUEST.requestArtist(id, q);
			break;
		case "album":
			DeerixRequestHandler.REQUEST.requestAlbum(id, q);
			break;
		case "track":
			DeerixRequestHandler.REQUEST.requestTrack(id, q);
			break;
		}
		model.addAttribute("id", id);
		return index(q, st, model);
	}

}

package ipm;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

public class Cleaner {
	public String clean(String in) throws Exception {
		WikiModel wikiModel = new WikiModel(
				"http://www.mywiki.com/wiki/${image}",
				"http://www.mywiki.com/wiki/${title}");
		String out = wikiModel.render(new PlainTextConverter(), in);
		return out;
	}
}

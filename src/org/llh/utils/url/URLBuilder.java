package org.llh.utils.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class URLBuilder {

	private String site;

	private Map<String, Set<String>> param;

	public URLBuilder(String site) {
		this.site = site;
		param = new TreeMap<String, Set<String>>();
	}

	public URLBuilder add(String key, String... v) {
		Set<String> set = param.get(key);
		if (set == null) {
			set = new TreeSet<>();
			param.put(key, set);
		}
		for (String a : v) {
			set.add(a);
		}
		return this;
	}

	public String toUrl(boolean encode) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder(site);

		if (!param.isEmpty()) {
			if (site != null && !site.endsWith("?")) {
				builder.append("?");
			}

			for (String key : param.keySet()) {
				Set<String> values = param.get(key);
				for (String v : values) {
					builder.append(key).append("=").append(v).append("&");
				}
			}
			builder.setLength(builder.length() - 1);
		}

		return encode ? URLEncoder.encode(builder.toString(), "utf-8")
				: builder.toString();

	}
	 
}
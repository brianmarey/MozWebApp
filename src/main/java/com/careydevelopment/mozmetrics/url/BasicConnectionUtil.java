package com.careydevelopment.mozmetrics.url;

public class BasicConnectionUtil {

	public static String makeRequest(String urlToFetch) {
		String s = "";
		
		try {
			Link l = new Link("",urlToFetch);
			UrlReader reader = new UrlReader(l);
			PageInfo pageInfo = reader.getPageInfo();
			s = pageInfo.getContents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return s;
	}
}

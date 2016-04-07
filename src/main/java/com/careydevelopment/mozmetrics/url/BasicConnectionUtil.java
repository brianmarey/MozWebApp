package com.careydevelopment.mozmetrics.url;

public class BasicConnectionUtil {

	public static String makeRequest(String urlToFetch) throws UrlReaderException {
		String s = "";
		
		Link l = new Link("",urlToFetch);
		UrlReader reader = new UrlReader(l);
		PageInfo pageInfo = reader.getPageInfo();
		s = pageInfo.getContents();
		
		return s;
	}
}

package com.careydevelopment.mozmetrics.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlReader.class);

	private Link url;
	
	  public static void main(String[] args)
	  {
		try {
			//String output  = getUrlContents("http://brianmcarey.com");
			//System.out.println(output);
			//UrlReader r = new UrlReader("https://brianmcarey2.com");
			//r.getPageInfo();
		}catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public UrlReader(Link url) {
		  this.url = url;
	  }


	  private String getHttpLink (String link) {
		  if (link !=null) {
			  if (link.toLowerCase().startsWith("https")) {
				  link = "http" + link.substring(5, link.length());
				  LOGGER.info ("new link is " +link);
			  }
		  }

		  return link;
	  }


	  public PageInfo getPageInfo() throws UrlReaderException {
		  PageInfo pageInfo = new PageInfo();
		  URLConnection urlConnection = null;

		  boolean fail = false;
		  
		  try {
			  	String l = url.getToLink();
			  	//System.err.println(l);
			  	
			    // create a url object
			    URL urlCon = new URL(l);

			    // create a urlconnection object
				if (l.startsWith("https")) {
					urlConnection = (HttpsURLConnection) urlCon.openConnection();
					int rc = ((HttpsURLConnection) urlConnection).getResponseCode();
					//System.err.println(l + " - response coe " + rc );
					if (rc == -1) {
						l = getHttpLink(l);
						urlCon = new URL(l);
						urlConnection = (HttpURLConnection) urlCon.openConnection();
					}
				} else {
					urlConnection = (HttpURLConnection) urlCon.openConnection();
				}

				//HttpURLConnection urlConnection = (HttpURLConnection)urlConnection1;
			  	//get all headers
			  	Map<String, List<String>> map = urlConnection.getHeaderFields();

				/*for (String key : map.keySet()) {
					System.err.println(key + " = " + map.get(key));
				}*/

			  	pageInfo.setResponseCode(getResponseCode(map));
			  	pageInfo.setUrl(l);
			  	pageInfo.setFromUrl(url.getFromLink());

			  	//System.err.println(pageInfo.getResponseCode().getCode());
			  	
				//<meta http-equiv="refresh" content="0;url=http://www.dnsrsearch.com/index.php?origURL=http://brianmcarey2.com/&bc="/>
			  	if (pageInfo.getResponseCode() != null && pageInfo.getResponseCode().getCode() != null &&
						pageInfo.getResponseCode().getCode().startsWith("20")) {

					pageInfo.setContentType(getContentType(map));
					pageInfo.setContents(getContent(urlConnection));

					if (hasRefreshed(pageInfo.getContents())) {
						ResponseCode rs = new ResponseCode();
						rs.setMessage("Redirect - Not 301");
						pageInfo.setResponseCode(rs);
						pageInfo.setUrl(url.getToLink());
						pageInfo.setFromUrl(url.getFromLink());
					} else {
						pageInfo.setServer(getSingleValueFromResponse("Server", map));
						pageInfo.setLastModified(getSingleValueFromResponse("Last-Modified", map));
						pageInfo.setEtag(getSingleValueFromResponse("ETag", map));
						pageInfo.setCacheControl(getCacheControl(map));
						pageInfo.setExpires(getSingleValueFromResponse("Expires", map));
					}
			  	} else {
			  		LOGGER.warn("not getting anything!");
			  		fail = true;
			  	}
			} catch(Exception e) {
				e.printStackTrace();
				ResponseCode rs = new ResponseCode();
				rs.setMessage("Unknown");
				pageInfo.setResponseCode(rs);
				pageInfo.setUrl(url.getToLink());
			  	pageInfo.setFromUrl(url.getFromLink());
		    } finally {
			  if (urlConnection != null) {
				  if (urlConnection instanceof HttpURLConnection) {
					  ((HttpURLConnection)urlConnection).disconnect();
				  } else if (urlConnection instanceof HttpsURLConnection) {
					  ((HttpsURLConnection)urlConnection).disconnect();
				  }
			  }
		  }

		  if (fail) {
			  throw new UrlReaderException("Problem reading page!");
		  }
		  
		  return pageInfo;
	  }


	private boolean hasRefreshed(String contents) {
		boolean hasRefreshed = false;

		if (contents != null && contents.length() > 40) {
			if (contents.indexOf("<meta http-equiv=\"refresh\"") > -1) {
				hasRefreshed = true;
			}
		}

		return hasRefreshed;
	}


	private ContentType getContentType(Map<String,List<String>> map) {
		ContentType contentType = new ContentType();

		List<String> vals = map.get("Content-Type");
		if (vals != null && vals.size() > 0) {
			String type = vals.get(0).trim();
			String[] parts = type.split(";");
			if (parts != null && parts.length == 2) {
				String content = parts[0].trim();
				String encoding = parts[1].trim();

				String[] codes = encoding.split("=");
				if (codes != null && codes.length == 2) {
					contentType.setEncoding(codes[1]);
				}

				contentType.setContent(content);
			} else if (parts != null && parts.length == 1) {
				String ctype = parts[0];
				if (ctype.indexOf("/") > -1) {
					contentType.setContent(ctype);
				}
			}
		}

		return contentType;
	}

	  private ResponseCode getResponseCode(Map<String,List<String>> map) throws UrlReaderException {
			ResponseCode response = new ResponseCode();

			List<String> code = map.get(null);
		  	
		  	if (code == null) {
		  		response.setMessage("Unknoown");
				return response;
		  	}
		  	
		  	for (String s : code) {
		  		if (s != null && s.trim().length() > 3) {
		  			s = s.trim();
		  			int firstSpace = s.indexOf(" ");
		  			if (firstSpace == -1) throw new UrlReaderException("Can't parse response code!");
		  			String protocol = s.substring(0, firstSpace);
		  			
		  			int secondSpace = s.indexOf(" ", firstSpace+1);
		  			if (secondSpace == -1) throw new UrlReaderException("Can't parse response code!");
		  			String resCode = s.substring(firstSpace+1, secondSpace);
		  			
		  			String message = s.substring(secondSpace + 1, s.length());
		  			
		  			response.setCode(resCode);
		  			response.setMessage(message);
		  			response.setProtocol(protocol);
		  			break;
		  		}
		  	}

		  	return response;
	  }
	  
	  
	  private CacheControl getCacheControl(Map<String,List<String>> map) {
			CacheControl cacheControl = new CacheControl();

			List<String> rets = map.get("Cache-Control");
		  	
		  	if (rets != null) {
			  	for (String s : rets) {
			  		if (s != null && s.trim().length() > 2) {
			  			String val = s.trim();
			  			String[] parts = val.split(",");
			  			if (parts != null && parts.length > 0) {
							for (int i=0;i<parts.length;i++) {
								if (parts[i].trim().startsWith("max-age")) {
									String[] pieces = parts[i].split("=");
									if (pieces.length > 1) {
										try {
											cacheControl.setMaxAge(new Long(pieces[1]));
										} catch (NumberFormatException ne) {
											//problem reading expiry
										}
									}
								} else if (parts[i].trim().equals("no-cache")) {
									cacheControl.setCacheSpecification(parts[i].trim());
								} else if (parts[i].trim().equals("public") || parts[i].trim().equals("rivate")) {
									cacheControl.setDirective(parts[i].trim());
								}
							}
			  			}
			  			
			  			break;
			  		}
			  	}
		  	}	

		  	return cacheControl;		  
	  }
	  

	  private String getSingleValueFromResponse(String key, Map<String,List<String>> map) throws UrlReaderException {
			String val = "";

			List<String> rets = map.get(key);
		  	
		  	if (rets != null) {
			  	for (String s : rets) {
			  		if (s != null && s.trim().length() > 2) {
			  			val = s.trim();
			  			break;
			  		}
			  	}
		  	}	

		  return val;
	  }

	  
	  private String getContent (URLConnection urlConnection)  {
		  StringBuilder content = new StringBuilder();
		  BufferedReader bufferedReader = null;

		  //System.err.println("getting contents");
		  
		  try {
			  // wrap the urlconnection in a bufferedreader
			  bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			  String line;

			  // read from the urlconnection via the bufferedreader
			  while ((line = bufferedReader.readLine()) != null) {
				  content.append(line + "\n");
			  }
		  } catch (IOException e) {
			  e.printStackTrace();
		  } finally {
			  if (bufferedReader != null) {
				  try {
					  bufferedReader.close();
				  } catch (Exception e) {}
			  }
		  }

		  //System.err.println("conecnt is " + content.toString());
		  return content.toString();
	  }
}

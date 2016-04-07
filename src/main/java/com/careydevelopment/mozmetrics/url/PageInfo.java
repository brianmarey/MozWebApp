package com.careydevelopment.mozmetrics.url;

public class PageInfo {
	
	private String contents = "";
	private ResponseCode responseCode;
	private String server = "";
	private String lastModified = "";
	private CacheControl cacheControl;
	private String etag = "";

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	private String expires = "";
	private String fromUrl = "";



	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	private String url = "";
	private ContentType contentType;
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public CacheControl getCacheControl() {
		return cacheControl;
	}
	public void setCacheControl(CacheControl cacheControl) {
		this.cacheControl = cacheControl;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean equals(Object other) {
		boolean equals = false;
		
		if (other instanceof PageInfo) {
			PageInfo pi  = (PageInfo)other;
			if (pi.getUrl() != null && pi.getUrl().equals(url)) {
				equals = true;
			}
		}
		
		return equals;
	}

}

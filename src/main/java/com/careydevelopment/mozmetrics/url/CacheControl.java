package com.careydevelopment.mozmetrics.url;

public class CacheControl {
	private String directive;
	private Long maxAge;
	private String cacheSpecification;

	public String getCacheSpecification() {
		return cacheSpecification;
	}

	public void setCacheSpecification(String cacheSpecification) {
		this.cacheSpecification = cacheSpecification;
	}

	public String getDirective() {
		return directive;
	}
	public void setDirective(String directive) {
		this.directive = directive;
	}
	public Long getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Long maxAge) {
		this.maxAge = maxAge;
	}
	
	public boolean isCaching() {
		//System.err.println("cache spec is " + cacheSpecification + " and max age is " + maxAge);
		if (cacheSpecification != null && cacheSpecification.equals("no-cache")) {
			return false;
		} else if (maxAge == null || maxAge < 10) {
			return false;
		}

		return true;
	}
	
}

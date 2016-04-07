package com.careydevelopment.mozmetrics.url;

/**
 * Created by Brian on 9/12/2015.
 */
public class Link {
    private String fromLink = "";
    private String toLink = "";

    public Link(String fromLink, String toLink) {
        this.fromLink = fromLink;
        this.toLink = toLink;
    }

    public String getToLink() {
        return toLink;
    }

    public void setToLink(String toLink) {
        this.toLink = toLink;
    }

    public String getFromLink() {
        return fromLink;
    }

    public void setFromLink(String fromLink) {
        this.fromLink = fromLink;
    }

    public boolean equals (Object o) {
        boolean eq = false;

        if (o != null) {
            if (o instanceof Link) {
                if (((Link) o).getToLink().equals(toLink)) {
                    eq = true;
                }
            }
        }

        return eq;
    }
}

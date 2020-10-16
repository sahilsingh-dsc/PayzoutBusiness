package com.payzout.business.common.banner;

public class Banner {
    private String id;
    private String index;
    private String url;
    private String action;
    private String action_callback;

    public Banner() {
    }

    public Banner(String id, String index, String url, String action, String action_callback) {
        this.id = id;
        this.index = index;
        this.url = url;
        this.action = action;
        this.action_callback = action_callback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_callback() {
        return action_callback;
    }

    public void setAction_callback(String action_callback) {
        this.action_callback = action_callback;
    }

}

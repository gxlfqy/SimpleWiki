package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.model.Page;

public class PageInfo {
    public PageInfo() {
    }

    public PageInfo(Page page) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.entry_title = page.getEntry().getTitle();
    }

    private String id;
    private String title;
    private String entry_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntry_title() {
        return entry_title;
    }

    public void setEntry_title(String entry_title) {
        this.entry_title = entry_title;
    }
}

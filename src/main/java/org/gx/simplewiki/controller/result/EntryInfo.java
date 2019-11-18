package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.model.Entry;

import java.util.List;

public class EntryInfo {
    public EntryInfo() {
    }

    public EntryInfo(Entry entry) {
        this.id = entry.getId();
        this.title = entry.getTitle();
        this.create_id = entry.getCreater().getId();
    }

    private String id;
    private String title;
    private String create_id;

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

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

}

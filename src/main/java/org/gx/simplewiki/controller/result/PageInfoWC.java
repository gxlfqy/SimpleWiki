package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.model.Page;

public class PageInfoWC extends PageInfo {
    public PageInfoWC() {
    }

    public PageInfoWC(Page page) {
        super(page);
        this.content = page.getContent();
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

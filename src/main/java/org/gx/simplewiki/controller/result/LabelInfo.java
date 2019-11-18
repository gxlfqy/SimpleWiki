package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.model.Label;

public class LabelInfo {
    public LabelInfo() {
    }

    public LabelInfo(Label label) {
        this.title = label.getTitle();
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

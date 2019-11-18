package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.model.Entry;
import org.gx.simplewiki.model.Page;

import java.util.LinkedList;
import java.util.List;

public class EntryInfoWP extends EntryInfo {
    public EntryInfoWP() {
    }

    public EntryInfoWP(Entry entry) {
        super(entry);

        List<Page> list = entry.getPages();
        if (list != null) {
            this.pages = new LinkedList<PageInfo>();
            for (Page term : list) {
                this.pages.add(new PageInfo(term));
            }
        }
    }

    private List<PageInfo> pages;

    public List<PageInfo> getPage() {
        return pages;
    }

    public void setPage(List<PageInfo> page) {
        this.pages = page;
    }
}

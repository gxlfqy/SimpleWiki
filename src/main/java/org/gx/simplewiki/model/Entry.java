package org.gx.simplewiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.gx.simplewiki.exception.NonePermission;
import org.gx.simplewiki.exception.page.PageIndexOverflow;
import org.gx.simplewiki.exception.page.PageNoneFound;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/* 词条
* 1. 不能存在相同标题的文章
* 2. 词条ID为标题的哈希值
*
* */
@Entity
@Table(name = "entry", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class Entry {
    public Entry() {
        this.creater = new User();
        this.pages = new LinkedList<Page>();
        this.labels = new LinkedList<Label>();
    }

    public Entry(String title, User user) {
        this();
        setTitle(title);
        setCreater(user);
    }

    // 词条UUID
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    // 词条标题
    @Column(length = 255)
    private String title;
    // 多篇短文
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "entry_id")
    protected List<Page> pages;
    // 创建者
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creater_id")
    protected User creater;
    // 词条标签
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="r_entry_label",
            joinColumns={@JoinColumn(name="entry_id")},
            inverseJoinColumns={@JoinColumn(name="label_id")})
    @JsonIgnore
    protected List<Label> labels;

    /*******getter & setter***********/
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    /*********handle function*************/
    public void insertNewPage(int index, String page_title, String content, User user) throws PageIndexOverflow {
        Page page = new Page(0, page_title, user);
        page.setContent(content);
        int pageIndex = index;

        if (this.pages == null) {
            pageIndex = 0;
            this.pages = new LinkedList<Page>();
            this.pages.add(page);
        } else if (index < 0 && -index - 1 <= pages.size()) {
            pageIndex = pages.size() + 1 + index;
            this.pages.add(page);
        } else if (index >= 0 && index <= pages.size()) {
            for (Page term : this.pages) {
                if (term.getNumber() > index) {
                    term.setNumber(term.getNumber() + 1);
                }
            }
        } else {
            throw new PageIndexOverflow();
        }

        page.setNumber(pageIndex);
        this.pages.add(page);
    }

    public void changePageIndex(String page_id, int index) throws PageNoneFound, PageIndexOverflow {
        Page page = null;
        if (pages == null) {
            throw new PageNoneFound();
        } else {
            boolean found = false;
            for (Page term : pages) {
                if (term.getId().equals(page_id)) {
                    found = true;
                    page = term;
                    break;
                }
            }
            if (!found)
                throw new PageNoneFound();

            if (page.getNumber() == index)
                return;
        }

        if (index < 0) {
            if (-index > pages.size()) {
                throw new PageIndexOverflow();
            } else {
                index = pages.size() + 1 + index;
            }
        } else {
            if (index >= pages.size())
                throw new PageIndexOverflow();
        }

        int low, high;
        boolean indexLow;
        if (page.getNumber() == index) {
            return;
        } else if (page.getNumber() < index) {
            low = page.getNumber();
            high = index;
            indexLow = false;
        } else {
            low = index;
            high = page.getNumber();
            indexLow = true;
        }

        for (Page term : pages) {
            if (term.getNumber() >= low && term.getNumber() <= high) {
                if (indexLow) {
                    if (term.getNumber() != high)
                        term.setNumber(term.getNumber() + 1);
                } else {
                    if (term.getNumber() != low)
                        term.setNumber(term.getNumber() - 1);
                }
            }
        }

        page.setNumber(index);
    }

    public void addLabel(User user, Label label) throws NonePermission {
        if (user.getId().equals(creater.getId())) {
            throw new NonePermission();
        }

        if (this.labels == null) {
            this.labels = new LinkedList<Label>();
            this.labels.add(label);
        } else {
            for (Label term : this.labels) {
                if (term.getTitle().equals(label.getTitle())) {
                    return;
                }
            }
            this.labels.add(label);
        }
    }

    public void delLabel(Label label) {
        if (this.labels != null) {
            this.labels.remove(label);
        }
    }

}

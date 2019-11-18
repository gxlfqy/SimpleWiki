package org.gx.simplewiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.gx.simplewiki.exception.page.PageIndexOverflow;
import org.gx.simplewiki.exception.page.PageNoneCreater;
import org.gx.simplewiki.exception.page.PageNoneEntry;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "page", uniqueConstraints = @UniqueConstraint(columnNames = {"entry_id", "number"}))
public class Page {
    public Page() {
    }

    public Page(int number, String title, User user) {
        this();
        setNumber(number);
        setTitle(title);
        setCreater(user);
    }

    // 短文编号
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    // 词条中的编号
    @Column(name = "number", nullable = false)
    private int number;
    // 标题
    @Column(nullable = false)
    private String title;
    // 当前内容
    @Lob
    @Column(columnDefinition = "text")
    private String content;
    // 创建者
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creater_id")
    protected User creater;
    // 对应的词条
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id")
    @JsonIgnore
    protected Entry entry;
    // 用户建议
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "page_id")
    @JsonIgnore
    protected List<Revision> suggests;

    /*******getter & setter***********/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public List<Revision> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<Revision> suggests) {
        this.suggests = suggests;
    }

    /*********handle function*************/
    public void addRevision(Revision revision) {
        if (this.suggests == null) {
            this.suggests = new LinkedList<Revision>();
        }

        this.suggests.add(revision);
    }

}

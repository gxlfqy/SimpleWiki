package org.gx.simplewiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/* 词条标签
 * 1. 标签标题不得相同
 *
 * */
@Entity
@Table(name = "label", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class Label {
    public Label() {
    }

    public Label(String title) {
        setTitle(title);
    }

    // 标签ID
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @JsonIgnore
    private String id;
    // 标签标题
    @Column()
    private String title;

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

}

package org.gx.simplewiki.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/* 修改记录
* 1. 用户只能对短文的内容进行修改
*
* */
@Entity
@Table(name = "revision")
public class Revision {
	public Revision() {
	    this.user = new User();
	}

	public Revision(User user, String content) {
	    this();
		this.user = user;
		this.date = new Date();	// 获取当前时间
		this.content = content;
	}

	// 修改记录的ID
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	// 修改的人
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="user_id")
	protected User user;
	// 修改的时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	// 修改内容
	@Lob
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	@Transient
	@JsonIgnore
	static final public int NORMAL = 0;
	@Transient
	@JsonIgnore
	static final public int ACCEPT = 0;
	@Transient
	@JsonIgnore
	static final public int REFUSE = 0;
	// 修改状态
	@Column
	private int status;

	/*******getter & setter***********/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

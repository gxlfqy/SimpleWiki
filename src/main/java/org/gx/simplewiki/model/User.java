package org.gx.simplewiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "info_id"}), @UniqueConstraint(columnNames = {"name"})})
public class User {
	public User() {
		this.info = new UserInfo();
	}

	public User(String name, String password) {
		this();
		this.name = name;
		this.password = password;
	}

	// 用户ID
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	// 用户名
	@Column(length = 64)
	private String name;
	// 昵称
	@Column(length = 64)
	private String nickname;
	// 密码
	@Column(nullable = false, length = 256)
	@JsonIgnore
	private String password;
	// 许可证
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	public List<Access> accesses;
	// 发表的词条
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "creater_id")
	@JsonIgnore
	protected List<Entry> entrys = new LinkedList<Entry>();
	// 发表的修改
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	protected List<Revision> revisions;
	// 用户信息
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "info_id")
	@JsonIgnore
	protected UserInfo info;

	/*******getter & setter***********/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

	public List<Entry> getEntrys() {
		return entrys;
	}

	public void setEntrys(List<Entry> entrys) {
		this.entrys = entrys;
	}

	public List<Revision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	/*********handle function*************/
	// 创建词条
	public void createEntry(String title) {
		Entry entry = new Entry(title, this);

		this.entrys.add(entry);
	}

}
 
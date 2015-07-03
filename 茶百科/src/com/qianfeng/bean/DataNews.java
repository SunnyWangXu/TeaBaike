package com.qianfeng.bean;

public class DataNews {
	private String id;
	private String title;
	private String source;
	private String description;
	private String wap_thumb;
	private String create_time;
	private String nickname;

	@Override
	public String toString() {
		return "DataNews [id=" + id + ", title=" + title + ", source=" + source
				+ ", description=" + description + ", wap_thumb=" + wap_thumb
				+ ", create_time=" + create_time + ", nickname=" + nickname
				+ "]";
	}

	public DataNews() {
	}

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWap_thumb() {
		return wap_thumb;
	}

	public void setWap_thumb(String wap_thumb) {
		this.wap_thumb = wap_thumb;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}

package com.urlrest.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Url {

	@SequenceGenerator(name = "SQ_URL", sequenceName = "SQ_URL",   allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_URL")
	@Id
	private Long id;

	private String url;

	private String friendlyName;

	public Url() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	@Override
	public String toString() {
		return "Url{" +
				"id=" + id +
				", url='" + url + '\'' +
				", friendlyName='" + friendlyName + '\'' +
				'}';
	}
}

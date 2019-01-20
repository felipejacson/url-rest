package com.urlrest.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.urlrest.model.Url;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CustomResponse {

	private int status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Url> list;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Url data;

	public CustomResponse(HttpStatus status, List<Url> list) {
		this.status = status.value();
		this.list = list;
	}

	public CustomResponse(HttpStatus status, Url data) {
		this.status = status.value();
		this.data = data;
	}

	public CustomResponse(HttpStatus status, String message) {
		this.status = status.value();
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Url> getList() {
		return list;
	}

	public void setList(List<Url> list) {
		this.list = list;
	}

	public Url getData() {
		return data;
	}

	public void setData(Url data) {
		this.data = data;
	}
}

package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class ResponseModel {

	private byte kode;
	private String message;
	private List<DataItem> data;

	public ResponseModel(byte kode, String message, List<DataItem> data){
		this.kode = kode;
		this.message = message;
		this.data = data;
	}

	public byte getKode() {
		return kode;
	}

	public void setKode(byte kode) {
		this.kode = kode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}
}
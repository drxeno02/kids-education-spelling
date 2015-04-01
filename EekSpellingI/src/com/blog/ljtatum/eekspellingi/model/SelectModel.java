package com.blog.ljtatum.eekspellingi.model;

import java.util.ArrayList;

public class SelectModel {

	private String title;
	private String message;
	private static ArrayList<SelectModel> arrySelect = new ArrayList<SelectModel>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ArrayList<SelectModel> getArrySelect() {
		return arrySelect;
	}

	public static void setArrySelect(ArrayList<SelectModel> arry) {
		arrySelect = arry;
	}

	public static void addArrySelect(SelectModel selectModel) {
		arrySelect.add(selectModel);
	}

	public static int getCount() {
		return arrySelect.size();
	}

}

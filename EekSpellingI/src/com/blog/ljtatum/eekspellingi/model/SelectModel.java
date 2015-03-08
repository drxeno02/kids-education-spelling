package com.blog.ljtatum.eekspellingi.model;

import java.util.ArrayList;
import java.util.List;

public class SelectModel {
	
	private String title;
	private String message;
	private static List<SelectModel> arrySelect = new ArrayList<SelectModel>();
	
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
	
	public static List<SelectModel> getArrySelect() {
		return arrySelect;
	}
	
	public static void setArrySelect(List<SelectModel> arry) {
		arrySelect = arry;
	}
	
	public static void addArrySelect(SelectModel selectModel) {
		arrySelect.add(selectModel);
	}
	
	public static int getCount() {
		return arrySelect.size();
	}

}

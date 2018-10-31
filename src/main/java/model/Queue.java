package model;

import java.io.Serializable;

public class Queue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String comment;
	private String maxlimit;
	private String limitAt;
	private String name;
	private String target;
	private String time;
	private String parent;
	
	
	
	public Queue() {
		
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getMaxLimitInKbUp(){
		String[] arr = this.maxlimit.split("/");

		return Integer.parseInt(arr[0])/1000;
	}

	public int getMaxLimitInKbDown(){
		String[] arr = this.maxlimit.split("/");

		return Integer.parseInt(arr[1])/1000;
	}
	public String getMaxlimit() {
		return maxlimit;
	}
	public void setMaxlimit(String maxlimit) {
		this.maxlimit = maxlimit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getLimitAtInKbUp(){
		String[] arr = this.maxlimit.split("/");

		return Integer.parseInt(arr[0])/1000;
	}

	public int getLimitAtInKbDown(){
		String[] arr = this.maxlimit.split("/");

		return Integer.parseInt(arr[1])/1000;
	}
	public String getLimitAt() {
		return limitAt;
	}
	public void setLimitAt(String limitAt) {
		this.limitAt = limitAt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
	
	
}

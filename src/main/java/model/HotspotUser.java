package model;

import java.io.Serializable;

public class HotspotUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String password;
	private String profile;
	private String limitUptime;
	

	public HotspotUser() {
	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getLimitUptime() {
		return limitUptime;
	}
	public void setLimitUptime(String limitUptime) {
		this.limitUptime = limitUptime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}

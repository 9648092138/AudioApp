package com.op.main.payload;

import java.util.UUID;

import com.op.main.model.RoleName;

public class UserDetailRespose {

	private String name;

	private String username;

	private String email;

	private String mobileNo;
	
	private boolean enabled;
	private UUID userId;
	 private RoleName roleName;
	
	

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

	public UserDetailRespose() {
		super();
	}

	public UserDetailRespose(String name, String username, String email, String mobileNo, boolean enabled,
			UUID userId) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.mobileNo = mobileNo;
		this.enabled = enabled;
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserDetailRespose [name=" + name + ", username=" + username + ", email=" + email + ", mobileNo="
				+ mobileNo + ", enabled=" + enabled + ", userId=" + userId + "]";
	}

}

package com.op.main.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminSignUpRequest {
	@NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    
     @Size(min=0,max=10)
	 @Pattern(regexp="(^$|[0-9]{10})")
	 private String mobileNo;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public AdminSignUpRequest() {
		super();
	}

	public AdminSignUpRequest(@NotBlank @Size(min = 4, max = 40) String name,
			@NotBlank @Size(min = 3, max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(min = 6, max = 20) String password,
			@Size(min = 0, max = 10) @Pattern(regexp = "(^$|[0-9]{10})") String mobileNo) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "AdminSignUpRequest [name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", mobileNo=" + mobileNo + "]";
	}
     
}

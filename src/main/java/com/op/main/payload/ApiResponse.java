package com.op.main.payload;

import java.util.UUID;

/**
 * Created by op
 */
public class ApiResponse {
    private Boolean active;
    private String message;
    private UUID userId;
    private long reg_Id;

    public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public long getReg_Id() {
		return reg_Id;
	}

	public void setReg_Id(long reg_Id) {
		this.reg_Id = reg_Id;
	}

	

	public ApiResponse(Boolean active, String message, UUID userId, long reg_Id) {
		super();
		this.active = active;
		this.message = message;
		this.userId = userId;
		this.reg_Id = reg_Id;
	}

	

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	
}

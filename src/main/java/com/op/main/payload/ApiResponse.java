package com.op.main.payload;

import java.util.UUID;

/**
 * Created by op
 */
public class ApiResponse {
    private Boolean success;
    private String message;
    private UUID userId;
   

    public ApiResponse(Boolean success, String message, UUID userId) {
		super();
		this.success = success;
		this.message = message;
		this.userId = userId;
	}

	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

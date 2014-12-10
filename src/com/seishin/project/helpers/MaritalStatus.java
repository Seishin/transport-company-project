package com.seishin.project.helpers;

public enum MaritalStatus {
	SINGLE("Single"), MARRIED("Married");
	
	private String status;
	
	MaritalStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}
}

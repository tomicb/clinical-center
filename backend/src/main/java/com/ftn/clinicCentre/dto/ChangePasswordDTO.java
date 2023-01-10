package com.ftn.clinicCentre.dto;

public class ChangePasswordDTO {
	
	private String oldPassword;
	private String newPassword;
	private String repeatedPassword;
	
	public ChangePasswordDTO() {}

	public ChangePasswordDTO(String oldPassword, String newPassword, String repeatedPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.repeatedPassword = repeatedPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordDTO [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", repeatedPassword="
				+ repeatedPassword + "]";
	}

}

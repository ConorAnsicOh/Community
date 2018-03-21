package com.ktds.community.member.vo;

import javax.validation.constraints.NotEmpty;

public class MemberVO {

	private int id;
	@NotEmpty(message = "Email은 필수입력 값입니다.")
	private String email;
	@NotEmpty(message = "Nickname은 필수 입력 값입니다.")
	private String nickname;
	@NotEmpty(message = "Password는 필수 입력 값입니다.")
	private String password;

	private String registDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

}

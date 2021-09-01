package com.example.demo.app.inquiry;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InquiryForm {
	@NotNull(message="お名前を入力してください")
	@Size(min =1,max=20,message="２０文字以下で入力してください")
	private String name;
	
	@NotNull(message="メールアドレスを入力してください")
	@Email(message="メールアドレスのフォーマットで入力してください")
	private String email;
	@NotNull(message="問い合わせ内容を入力してください")
	private String contents;
	
	public InquiryForm() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
	
}

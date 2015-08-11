package ua.com.benlinus92.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users_auth")
public class Login {
	public Login() { }
	public Login(String login, String password) {
		this.login = login;
		this.password = password;
	}
	@Id
	@Column(nullable = false)
	private String login;
	@Column(nullable = false)
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	public String getLogin() {
		return this.login;
	}
}

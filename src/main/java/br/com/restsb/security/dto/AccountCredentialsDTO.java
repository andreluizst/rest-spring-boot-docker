package br.com.restsb.security.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountCredentialsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

}

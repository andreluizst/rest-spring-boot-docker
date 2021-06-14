package br.com.restsb.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="first_name", nullable = false, length=50)
	private String firstName;
	@Column(name="last_name", nullable = false, length=80)
	private String lastName;
	@Column(nullable=false, length=100)
	private String address;
	@Column(nullable=false, length=10)
	private String gender;
	@Column(nullable = false)
	private Boolean enabled;
	
	
}

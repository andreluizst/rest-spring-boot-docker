package br.com.restsb.data.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data //annotation is the combination of @ToString, @EqualsAndHashCode, @Getter, @Setter.
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder("id")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

	private static final long serialVersionUID = -1090387767145879342L;
	
	//Como a classe RepresentationModel do spring hateoas utiliza uma definição própria de id. por isso a classe que a extend não pode ter um campo com esse nome 
	//por isso o uso do Mapping do Dozer para mapear o nome do campo key para id
	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;	
	private Boolean enabled;
}

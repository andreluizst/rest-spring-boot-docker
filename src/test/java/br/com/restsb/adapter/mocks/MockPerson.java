package br.com.restsb.adapter.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.restsb.data.dto.PersonDTO;
import br.com.restsb.data.model.Person;

public class MockPerson {

	public Person mockEntity() {
		return mockEntity(0);
	}
	
	public PersonDTO mockVO() {
		return mockVO(0);
	}
	
	public Person mockEntity(Integer number) {
		Person person = new Person();
		person.setAddress("Address test");
		person.setFirstName("First name " + number);
		person.setLastName("Last name " + number);
		person.setGender(number % 2 == 0 ? "Male" : "Female");
		person.setId(number.longValue());
		return person;
	}
	
	public PersonDTO mockVO(Integer number) {
		PersonDTO person = new PersonDTO();
		person.setAddress("Address test");
		person.setFirstName("First name " + number);
		person.setLastName("Last name " + number);
		person.setGender(number % 2 == 0 ? "Male" : "Female");
		person.setKey(number.longValue());
		return person;
	}
	
	public List<Person> mockEntityList(Integer maxSize){
		List<Person> persons = new ArrayList<>();
		if (maxSize == null || maxSize <= 1)
			maxSize = 2;
		for (Integer i=1;i<maxSize;i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}
	
	public List<PersonDTO> mockVoList(Integer maxSize){
		List<PersonDTO> persons = new ArrayList<>();
		if (maxSize == null || maxSize <= 1)
			maxSize = 2;
		for (Integer i=1;i<maxSize;i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}
}

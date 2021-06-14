package br.com.restsb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.restsb.adapter.DozerAdapter;
import br.com.restsb.data.dto.PersonDTO;
import br.com.restsb.data.model.Person;
import br.com.restsb.exception.ResourceNotFoundException;
import br.com.restsb.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;

//	private final AtomicLong atomicLong = new AtomicLong();
//	private List<Person> persons = new ArrayList<>();

	public PersonServices() {
		super();
		// initPersons();
	}

	public PersonDTO create(PersonDTO personVo) {
		var person = DozerAdapter.ParseObject(personVo, Person.class);
		var vo = DozerAdapter.ParseObject(repository.save(person), PersonDTO.class);
		return vo;
	}

	public PersonDTO update(PersonDTO person) {
		Person entity = DozerAdapter.ParseObject(this.findById(person.getKey()), Person.class);
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return DozerAdapter.ParseObject(repository.save(entity), PersonDTO.class);
	}

	public boolean delete(Long id) {
//		try {
//			Person person = findById(id);
//			persons.remove(person);
//			return true;
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return false;
		Person entity = DozerAdapter.ParseObject(this.findById(id), Person.class);
		repository.delete(entity);
		return true;
		
	}

	public PersonDTO findById(Long id) {
//		Person person = null;
//		person = persons.stream().filter(p -> p.getId() == Long.valueOf(id)).findFirst().get();
//		return person;
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found (id = " + id + ")"));
		return DozerAdapter.ParseObject(entity, PersonDTO.class);
	}

	public Page<PersonDTO> findAll(Pageable pageable) {
		Page<Person> page = repository.findAll(pageable);
		return page.map(this::convertToPersonDTO);
	}
	
	@Transactional
	public void disablePerson(Long id) {
		repository.disablePerson(id);
	}

	private PersonDTO convertToPersonDTO(Person entity) {
		return DozerAdapter.ParseObject(entity, PersonDTO.class);
	}
	
	public Page<PersonDTO> findPersonByName(String firstName, Pageable pageable){
		Page<Person> page = repository.findByPersonByName(firstName, pageable);
		return page.map(this::convertToPersonDTO);
	}

}

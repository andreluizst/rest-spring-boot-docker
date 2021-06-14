package br.com.restsb.controller.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.restsb.config.WebConfig;
import br.com.restsb.data.dto.PersonDTO;
import br.com.restsb.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = "http://localhost:8080")//CORS local
@Api(value = "Person Endpoint v1", description = "Available actions for person", tags = { "PersonEndpoint", "person" })
@RestController
@RequestMapping("api/person/v1")
public class PersonController {

	@Autowired
	private PersonServices service;

	@ApiOperation(value = "Find a person by id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public PersonDTO findById(@PathVariable("id") Long id) {
		PersonDTO person = service.findById(id);
		Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(id))
				.withSelfRel();
		person.add(link);
		return person;
	}

//	@CrossOrigin(origins = "http://localhost:8080")//CORS local
	@ApiOperation(value = "Find all persons recorded")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "15") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			PagedResourcesAssembler<PersonDTO> assembler) {

		Direction sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		Page<PersonDTO> persons = service.findAll(pageable);
		persons.forEach(p -> p.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return new ResponseEntity<>(assembler.toModel(persons), HttpStatus.OK);
	}

	@ApiOperation(value = "Create a new person")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			"application/x-yaml" }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
					"application/x-yaml" })
	public PersonDTO create(@RequestBody PersonDTO person) {
		PersonDTO personDto = service.create(person);
		Link lnk = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel();
		personDto.add(lnk);
		return personDto;
	}

	@ApiOperation(value = "Update the data of a person")
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			"application/x-yaml" }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
					"application/x-yaml" })
	public PersonDTO update(@RequestBody PersonDTO person) {
		PersonDTO personDto = service.update(person);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel();
		personDto.add(link);
		return personDto;
	}

	@ApiOperation(value = "Remove a person of the records")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);
	}

	@ApiOperation(value = "Disable a person by id")
	@PatchMapping("/disable/{id}")
	public void disablePerson(@PathVariable("id") Long id) {
		service.disablePerson(id);
	}

	@ApiOperation(value = "Find person by firstname or part of it")
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, WebConfig.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findPersonByName(
			@PathVariable("firstName") String firstName, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "15") int size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			PagedResourcesAssembler<PersonDTO> assembler) {

		Direction sortdirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortdirection, "firstName"));
		Page<PersonDTO> pagePersons = service.findPersonByName(firstName, pageable);
		pagePersons.forEach(p -> p.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return new ResponseEntity<>(assembler.toModel(pagePersons), HttpStatus.OK);
	}
}

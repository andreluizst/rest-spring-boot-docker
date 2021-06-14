package br.com.restsb.adapter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import br.com.restsb.adapter.mocks.MockPerson;
import br.com.restsb.data.dto.PersonDTO;
import br.com.restsb.data.model.Person;


public class DozerAdapterTest {
	
	
	MockPerson inputObject;
	
	public DozerAdapterTest() {
		inputObject = new MockPerson();
	}
	
	@BeforeTestClass
	public void setUP() {
		inputObject = new MockPerson();
	}
	
	@Test
	public void parseEntityToVO() {
		PersonDTO input = DozerAdapter.ParseObject(inputObject.mockEntity(), PersonDTO.class);
		Assert.assertEquals(Long.valueOf(0L), input.getKey());
		Assert.assertEquals("First name 0", input.getFirstName());
		Assert.assertEquals("Last name 0", input.getLastName());
		Assert.assertEquals("Address test", input.getAddress());
		Assert.assertEquals("Male", input.getGender());
	}
	
	@Test
	public void parseVOToEntity() {
		Person input = DozerAdapter.ParseObject(inputObject.mockVO(), Person.class);
		Assert.assertEquals(Long.valueOf(0L), input.getId());
		Assert.assertEquals("First name 0", input.getFirstName());
		Assert.assertEquals("Last name 0", input.getLastName());
		Assert.assertEquals("Address test", input.getAddress());
		Assert.assertEquals("Male", input.getGender());
	}

}

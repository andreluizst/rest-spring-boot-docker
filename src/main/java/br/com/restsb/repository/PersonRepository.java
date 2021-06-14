package br.com.restsb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.restsb.data.model.Person;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>{

	@Modifying
	@Query("update Person p set p.enabled = false where p.id = :id ")
	void disablePerson(@Param("id") Long id);
	
	@Query("select p from Person p where p.firstName like lower(concat('%', :firstName, '%'))")
	Page<Person> findByPersonByName(@Param("firstName") String firstName, Pageable pageable);
}

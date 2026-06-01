package org.example.Ecommerce2.Person.PersonRepository;

import org.example.Ecommerce2.Person.PersonModels.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


}

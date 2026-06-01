package org.example.Ecommerce2.Person.PersonService;

import org.example.Ecommerce2.Person.PersonModels.Person;
import org.example.Ecommerce2.Person.PersonRepository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {


    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findPersonById(Long id) {

        return personRepository.findById(id);
    }


}

package org.example.Ecommerce2.Person.PersonController;

import org.example.Ecommerce2.Person.PersonModels.Person;
import org.example.Ecommerce2.Person.PersonService.PersonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService  personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/{id}")
    public Optional<Person> getPersonById(@PathVariable Long id){

        return personService.findPersonById(id);
    }




}

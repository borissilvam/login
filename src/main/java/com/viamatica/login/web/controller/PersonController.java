package com.viamatica.login.web.controller;

import com.viamatica.login.domain.Person;
import com.viamatica.login.domain.service.PersonService;
import com.viamatica.login.web.error.PersonNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping("/all")
    @Operation(summary = "Obtiene todas las personas registradas")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Person> > getAll(){
        try {
            List<Person> personList = personService.getAll();
            if (personList.isEmpty()){
                throw new PersonNotFound();
            }else {
                return new ResponseEntity<>(personList, HttpStatus.OK);
            }
        }catch (PersonNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }

    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una Persona por su id ")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id){

        try {
            return personService.getPerson(id)
                    .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                    .orElseThrow(()-> new PersonNotFound(id));
        }catch (PersonNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }


    }

    @PostMapping("/save")
    @Operation(summary = "Guarda una Persona en la base de dato")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<?> save(@RequestBody Person person){
        try {
            if (!person.getIdentification().matches("^\\d{10}$")){
                String mensaje = "El numero de identificación debe contener 10 dijitos";

                throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

            } else if (!person.getIdentification().matches("^\\d+$")) {
                String mensaje = "El numero de identificación solo debe contener numeros";

                throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

            } else if (!person.getIdentification().matches("^(?!.*(\\d)\\1{3})\\d{10}$")) {
                String mensaje = "El numero de identificación  no puede contener 4 numeros consecutivos";

                throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

            } else {
                return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
            }
        }catch (Exception ex){
           throw  new ResponseStatusException(
                    HttpStatus.CONFLICT, ex.getMessage()
            );
        }

    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina una persona de la base de datos")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity delete(@PathVariable("id") int idPerson){
        try {
            if (personService.getPerson(idPerson).isEmpty()){
                throw new PersonNotFound(idPerson);
            }else {
                personService.delete(idPerson);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (PersonNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }

    }
}

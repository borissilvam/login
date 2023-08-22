package com.viamatica.login.web.controller;

import com.viamatica.login.domain.User;
import com.viamatica.login.domain.service.UserService;
import com.viamatica.login.web.error.UserNotFound;
import com.viamatica.login.web.validation.PasswordValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    PasswordValidator validator = new PasswordValidator();
    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los usuarios registrados en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<User>>  getAll(){
        try {
            List<User> users = userService.getAll();
            if (users.isEmpty()){
                throw new UserNotFound();
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (UserNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }

    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un usuario por su id")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        try {
            return userService.getUser(id)
                    .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                    .orElseThrow(()-> new UserNotFound(id));
        }catch (UserNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }

    }
    @PostMapping("/save")
    @Operation(summary = "Guarda un usuario en la base de datos")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<?> save(@RequestBody User user){

       try {

           String mensaje;
           if (!user.getUserName().matches("^[\\w]+$")){
               mensaje = "En nombre de usuario contiene signos";

               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);
           }
           else if (userService.getUserByUserName(user.getUserName()).isPresent()) {
                   mensaje = "El nombre de usuario ya esta en uso";
               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);
           }
           else if (!user.getUserName().matches("^(?=.*\\d).+$")){
               mensaje = "El nombre de usuario debe contener al menos un numero";
               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);
           }
           else if (!user.getUserName().matches("^(?=.*[A-Z]).+$")) {
               mensaje = "El nombre de usuario debe contener al menos una mayúscula";
               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

           } else if (!user.getUserName().matches("^.{8,20}$")) {
               mensaje = "El nombre de usuario debe contener entre 8 y 20 caracteres";
               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

           } else if (!userService.getUserByIdPerson(user.getIdPerson()).isEmpty()) {
               mensaje = "Este Persona ya tiene una cuenta registrada";
               throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);

           } else {
               return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
           }
       }catch (Exception e){
           throw new ResponseStatusException(
                   HttpStatus.CONFLICT, e.getMessage()
           );
       }

    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un usuario en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity delete(@PathVariable("id") int idUser){

        try {
            if (userService.getUser(idUser).isEmpty()){
                throw new UserNotFound(idUser);
            }else {
                userService.delete(idUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (UserNotFound ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }

    }
}

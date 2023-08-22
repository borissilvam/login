package com.viamatica.login.web.controller;

import com.viamatica.login.domain.Session;
import com.viamatica.login.domain.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    @GetMapping("/user/{id}")
    @Operation(summary = "Obtiene todas las sessiones de un usuario por medio de su id")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Session>> getSessionByIdUser(@PathVariable("id") int idUser){
        return new ResponseEntity<>(sessionService.getSessionByIdUser(idUser), HttpStatus.OK) ;
    }
    @GetMapping("/active")
    public String sesionActive(){
        return "Tiene una sesiòn activa";
    }


}


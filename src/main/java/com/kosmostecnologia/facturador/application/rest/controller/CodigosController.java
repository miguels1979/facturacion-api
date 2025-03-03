package com.kosmostecnologia.facturador.application.rest.controller;

import com.kosmostecnologia.facturador.domain.service.CuisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;

@RestController
@RequestMapping("/codigos")
public class CodigosController {

    private final CuisService cuisService;

    public CodigosController(CuisService cuisService) {
        this.cuisService = cuisService;
    }

    @PostMapping("/obtener-cuis/{idPuntoVenta}")
    public ResponseEntity<Object> obtenerCuis(@PathVariable("idPuntoVenta") Integer idPuntoVenta) throws RemoteException {
        this.cuisService.obtenerCuis(idPuntoVenta);
        return ResponseEntity.ok().build();
    }
}

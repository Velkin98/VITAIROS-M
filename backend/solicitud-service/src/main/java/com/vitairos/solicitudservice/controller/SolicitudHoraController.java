package com.vitairos.solicitudservice.controller;

import com.vitairos.solicitudservice.model.SolicitudHora;
import com.vitairos.solicitudservice.service.SolicitudHoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudHoraController {

    private final SolicitudHoraService service;

    public SolicitudHoraController(
            SolicitudHoraService service) {
        this.service = service;
    }

    // GET: Listar solicitudes
    @GetMapping
    public List<SolicitudHora> listar() {
        return service.listar();
    }

    // POST: Registrar solicitud
    @PostMapping
    public ResponseEntity<SolicitudHora> guardar(
            @Valid @RequestBody SolicitudHora solicitud) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(solicitud));
    }

    // PUT: Actualizar solicitud
    @PutMapping("/{id}")
    public SolicitudHora actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SolicitudHora solicitud) {

        return service.actualizar(id, solicitud);
    }

    // DELETE: Eliminar solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

package com.vitairos.emocionservice.controller;

import com.vitairos.emocionservice.model.Emocion;
import com.vitairos.emocionservice.service.EmocionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/emociones")
public class EmocionController {

    private final EmocionService service;

    public EmocionController(EmocionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Emocion> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emocion guardar(@Valid @RequestBody Emocion emocion) {
        return service.guardar(emocion);
    }

    @PutMapping("/{id}")
    public Emocion actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Emocion emocion) {
        return service.actualizar(id, emocion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

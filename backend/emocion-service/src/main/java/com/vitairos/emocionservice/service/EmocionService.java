
package com.vitairos.emocionservice.service;

import com.vitairos.emocionservice.model.Emocion;
import com.vitairos.emocionservice.repository.EmocionRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class EmocionService {

    private final EmocionRepository repository;

    public EmocionService(EmocionRepository repository) {
        this.repository = repository;
    }

    public List<Emocion> listar() {
        return repository.findAll();
    }

    public Emocion guardar(Emocion emocion) {
        emocion.setId(null);
        return repository.save(emocion);
    }

    public Emocion actualizar(Integer id, Emocion emocion) {
        Emocion existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Emoción no encontrada"));

        existente.setNombre(emocion.getNombre());
        existente.setCategoria(emocion.getCategoria());
        existente.setEnergia(emocion.getEnergia());
        existente.setNota(emocion.getNota());

        return repository.save(existente);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}

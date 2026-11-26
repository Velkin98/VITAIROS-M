package com.vitairos.solicitudservice.service;

import com.vitairos.solicitudservice.model.SolicitudHora;
import com.vitairos.solicitudservice.repository.SolicitudHoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SolicitudHoraService {

    private final SolicitudHoraRepository repository;

    public SolicitudHoraService(
            SolicitudHoraRepository repository) {
        this.repository = repository;
    }

    public List<SolicitudHora> listar() {
        return repository.findAll();
    }

    public SolicitudHora guardar(SolicitudHora solicitud) {

        solicitud.setId(null);
        solicitud.setFechaSolicitud(System.currentTimeMillis());

        return repository.save(solicitud);
    }

    public SolicitudHora actualizar(
            Integer id, SolicitudHora solicitud) {

        SolicitudHora existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitud no encontrada"
                ));

        existente.setProfesional(solicitud.getProfesional());
        existente.setFecha(solicitud.getFecha());
        existente.setHora(solicitud.getHora());

        return repository.save(existente);
    }

    public void eliminar(Integer id) {

        SolicitudHora existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitud no encontrada"
                ));

        repository.delete(existente);
    }
}
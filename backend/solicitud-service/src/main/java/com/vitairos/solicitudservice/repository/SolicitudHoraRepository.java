package com.vitairos.solicitudservice.repository;

import com.vitairos.solicitudservice.model.SolicitudHora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudHoraRepository
        extends JpaRepository<SolicitudHora, Integer> {
}
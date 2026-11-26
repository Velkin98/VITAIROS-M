package com.vitairos.solicitudservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "solicitudes_hora")
public class SolicitudHora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String profesional;

    @NotBlank
    private String fecha;

    @NotBlank
    private String hora;

    private Long fechaSolicitud = System.currentTimeMillis();

    public SolicitudHora() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Long getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Long fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
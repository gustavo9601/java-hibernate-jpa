package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// @Embeddable // Se podra utilizar en otras clases, similar a un trait en PHP

/*
* Es util para separar logica de Entidades que se va a replicar en otras entidades
* */
@Embeddable
public class Auditoria {
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }



    /*
     * Eventos que se ejecutan en el ciclo de vida de la entidad
     * Antes o despues de persistirce el objeto
     * */

    @PrePersist
    public void prePersist() {
        System.out.println("Inicializar algo justo antes del persist");
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }


    @PreUpdate
    public void preUpdate() {
        System.out.println("Inicializar algo justo antes de actualizar");
        this.actualizadoEn = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Inicializar algo justo antes de eliminar");
    }

}

package org.gmarquezp.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private Integer numero;

    @Embedded
    private Auditoria auditoria = new Auditoria();

    public Direccion() {
    }

    public Direccion(String calle, Integer numero) {
        this.calle = calle;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", creadoEn=" + ((this.auditoria != null) ? this.auditoria.getCreadoEn() : null) +
                ", actualizadoEn=" + ((this.auditoria != null) ? this.auditoria.getActualizadoEn() : null) +
                '}';
    }
}

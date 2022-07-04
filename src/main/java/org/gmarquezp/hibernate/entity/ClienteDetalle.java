package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes_detalle")
public class ClienteDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean esPrime;

    @Column(name = "puntos_acumulados")
    private Long puntosAcumulados;

    @Embedded
    private Auditoria auditoria = new Auditoria();


    public ClienteDetalle() {
    }

    public ClienteDetalle(boolean esPrime, Long puntosAcumulados) {
        this.esPrime = esPrime;
        this.puntosAcumulados = puntosAcumulados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEsPrime() {
        return esPrime;
    }

    public void setEsPrime(boolean esPrime) {
        this.esPrime = esPrime;
    }

    public Long getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(Long puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }


    @Override
    public String toString() {
        return "ClienteDetalle{" +
                "id=" + id +
                ", esPrime=" + esPrime +
                ", puntosAcumulados=" + puntosAcumulados +
                ", creadoEn=" + ((this.auditoria != null) ? this.auditoria.getCreadoEn() : null) +
                ", actualizadoEn=" + ((this.auditoria != null) ? this.auditoria.getActualizadoEn() : null) +
                '}';
    }
}

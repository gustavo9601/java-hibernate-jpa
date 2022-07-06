package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private Long total;

    @Embedded // Ahora se extienden todos los atributo de la clase @Embeddable en esta
    private Auditoria auditoria = new Auditoria();


    // Muchas facturas para un cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id") // indica el nombre de la columna en la tabla
    private Cliente cliente; // si no se especifica el column, tomara atributo_id // cliente_id

    public Factura() {
    }

    public Factura(String descripcion, Long total) {
        this.descripcion = descripcion;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", total=" + total +
                ", creadoEn=" + ((this.auditoria != null) ? this.auditoria.getCreadoEn() : null) +
                ", actualizadoEn=" + ((this.auditoria != null) ? this.auditoria.getActualizadoEn() : null) +
                '}';
    }
}

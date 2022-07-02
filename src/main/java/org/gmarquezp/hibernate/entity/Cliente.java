package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;


@Entity // indica que es una entidada mapear con la tabla cliente
@Table(name = "clientes") // epecifica el nombre de la tabla en la base de datos
public class Cliente {
    @Id // indica que es el identificador de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que el valor del identificador es auto incremental
    private Long id;

    private String nombre;
    private String apellido;

    @Column(name = "forma_pago") // indica el nombre de la columna en la tabla
    private String formaPago;

    @Embedded // Ahora se extienden todos los atributo de la clase @Embeddable en esta
    private Auditoria auditoria = new Auditoria();



    // Necesita de un constructor vacio si existe uno con parametros, ya que JPA se encargara de realizar el modelamiento de los datos a objeto
    public Cliente() {
    }

    // Constructor para usarclo con HQL
    public Cliente(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }



    /*
     * Eventos que se ejecutan en el ciclo de vida de la entidad
     * Antes o despues de persistirce el objeto
     * */


    @PostPersist
    public void postPersist() {
        System.out.println("Inicializar algo justo despues del persist");
        System.out.println("Cliente creado con id: " + this.getId());
    }

    @PostUpdate
    public void postUpdate() {
        System.out.println("Inicializar algo justo despues de actualizar");
        System.out.println("Cliente actualizado con id: " + this.getId());
    }

    @Override
    public String toString() {


        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", creadoEn=" + ((this.auditoria != null) ? this.auditoria.getCreadoEn() : null) +
                ", actualizadoEn=" + ((this.auditoria != null) ? this.auditoria.getActualizadoEn() : null) +
                ", formaPago='" + formaPago + '\'' +
                '}';
    }
}

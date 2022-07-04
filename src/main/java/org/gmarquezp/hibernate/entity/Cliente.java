package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


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

    // Un cliente a muchas direcciones
    // Hibernate creara una tabla intermadia entre clientes y direcciones donde se almacena la relacion de uno a muchos
    // cascade = CascadeType.ALL // persistira el registro en cada insersion de cliente
    // orphanRemoval = true // eliminara los registros en tablas intermedias, para que no queden registros huerfanos

    /*
     * Alternativas en la relacion
     *
     * // Si especificamos el id de relacion, no creara la tabla intermedia, sino solo un campo en la tabla Direccion cliente_id para relacionarse con Clientes
     *  @JoinTable(name = "cliente_id")
     *
     * // Permite especificar por nuestra cuenta el nombre de la tabla pivote, y las llaves de relacion entre cliente y direccion
     * @JoinTable(name = "tbl_clientes_direcciones", joinColumns = @JoinColumn(name="cliente_id"), inverseJoinColumns = @JoinColumn(name="direccion_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"direccion_id"}))
     * */


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;


    // Relacion inverza, un cliente a muchas facturas
    // mappedBy = "cliente" // indica que la relacion es inversa, es decir, que la direccion es la clave foranea de la tabla intermedia
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private List<Factura> facturas;



    // Relacion Uno a uno
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_detalle_id") // indica el nombre de la columna en la tabla
    private ClienteDetalle clienteDetalle;


    @Embedded // Ahora se extienden todos los atributo de la clase @Embeddable en esta
    private Auditoria auditoria = new Auditoria();


    // Necesita de un constructor vacio si existe uno con parametros, ya que JPA se encargara de realizar el modelamiento de los datos a objeto
    public Cliente() {
        this.direcciones = new ArrayList<>();
        this.facturas = new ArrayList<>();
    }

    // Constructor para usarclo con HQL
    public Cliente(String nombre, String apellido) {
        this(); // llama al constructor vacio
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

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Cliente addFacturas(Factura factura) {
        this.facturas.add(factura);
        factura.setCliente(this); // seteamos el cliente en la factura, como relacion inversa
        return this;
    }

    public ClienteDetalle getClienteDetalle() {
        return clienteDetalle;
    }

    public void setClienteDetalle(ClienteDetalle clienteDetalle) {
        this.clienteDetalle = clienteDetalle;
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
                ", direcciones='" + this.getDirecciones() + '\'' +
                ", facturas='" + this.getFacturas() + '\'' +
                ", clienteDetalle='" + this.getClienteDetalle() + '\'' +
                '}';
    }
}

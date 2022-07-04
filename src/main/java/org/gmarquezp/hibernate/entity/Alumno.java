package org.gmarquezp.hibernate.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    // Una relacion de muchos a muchos, tendra una tabla intermedia
    // Cuando se cree un alumno creara todos sus cursos asociados
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // PERSIST, MERGE se tendra en cuenta al crear o actualizar NO al eliminar
    @JoinTable(name = "alumnos_cursos",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursos;

    public Alumno() {
        this.cursos = new ArrayList<>();
    }

    public Alumno(String nombre, String apellido) {
        this();
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

    public List<Curso> getCursos() {
        return cursos;
    }

    public Alumno addCursos(Curso curso){
        this.getCursos().add(curso);
        return this;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cursos=" + cursos +
                '}';
    }

    public void removeCursos(Curso curso3) {
        this.getCursos().remove(curso3);
    }
}

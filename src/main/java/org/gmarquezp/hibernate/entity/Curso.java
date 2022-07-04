package org.gmarquezp.hibernate.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String profesor;

    public Curso() {
    }

    public Curso(String titulo, String profesor) {
        this.titulo = titulo;
        this.profesor = profesor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}

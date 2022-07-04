package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Alumno;
import org.gmarquezp.hibernate.entity.Curso;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesManyToMany {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        try{
            entityManager.getTransaction().begin();

            // Crear alumnos
            Alumno alumno1 = new Alumno("Juanito", "Perez");
            Alumno alumno2 = new Alumno("Gustavillo", "Marquez");

            // Creando cursos
            Curso curso1 = new Curso("Java Fulll Stack", "Juan");
            Curso curso2 = new Curso("Python IA", "ALejandro");

            // Añadiendo los cursos
            alumno1.addCursos(curso1)
                    .addCursos(curso2);

            alumno2.addCursos(curso1);

            // Guardando
            entityManager.persist(alumno1);
            entityManager.persist(alumno2);


            entityManager.getTransaction().commit();

            System.out.println("alumno1 = " + alumno1);
            System.out.println("alumno2 = " + alumno2);


            // Eliimando un curso
            entityManager.getTransaction().begin();
            Curso curso3 = entityManager.find(Curso.class, 1L);
            alumno2.removeCursos(curso3);
            entityManager.merge(alumno2);
            entityManager.getTransaction().commit();
            System.out.println("alumno2 eliminando curso= " + alumno2);

        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            entityManager.close();
        }


    }
}

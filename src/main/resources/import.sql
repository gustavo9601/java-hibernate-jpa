/*Alumnos*/
INSERT INTO alumnos
    ( apellido, nombre)
VALUES ( 'Perez', 'Juan');
INSERT INTO alumnos
    ( apellido, nombre)
VALUES (, 'Marquez', 'Gustavo');
INSERT INTO alumnos
    ( apellido, nombre)
VALUES ( 'Perez', 'Juan');
INSERT INTO alumnos
    ( apellido, nombre)
VALUES ( 'Marquez', 'Gustavo');


/*Cursos*/
INSERT INTO cursos
    ( profesor, titulo)
VALUES ( 'Juan', 'Java');
INSERT INTO cursos
    ( profesor, titulo)
VALUES ( 'ALejandro', 'Python');


/*Asociacion alumnos con cursos*/
INSERT INTO alumnos_cursos
    (alumno_id, curso_id)
VALUES (3, 1);
INSERT INTO alumnos_cursos
    (alumno_id, curso_id)
VALUES (3, 2);
INSERT INTO alumnos_cursos
    (alumno_id, curso_id)
VALUES (4, 1);

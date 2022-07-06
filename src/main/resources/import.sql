/*Alumnos
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
*/

INSERT INTO clientes ( apellido, actualizado_en, creado_en, forma_pago, nombre, cliente_detalle_id) VALUES( 'Perez', '2022-07-05 21:08:22.665', '2022-07-05 21:08:22.665', 'Efectivo', 'Oscar', NULL);

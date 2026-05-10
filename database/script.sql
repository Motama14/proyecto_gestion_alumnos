
CREATE TABLE alumnos (
    dni TEXT PRIMARY KEY,
    nombre TEXT NOT NULL,
    edad INTEGER NOT NULL
);

CREATE TABLE alumno_fp (
    dni TEXT PRIMARY KEY,
    ciclo TEXT NOT NULL,
    FOREIGN KEY (dni) REFERENCES alumnos(dni)
);

CREATE TABLE alumno_bach (
    dni TEXT PRIMARY KEY,
    modalidad TEXT NOT NULL,
    FOREIGN KEY (dni) REFERENCES alumnos(dni)
);

CREATE TABLE notas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    dni TEXT,
    asignatura TEXT,
    nota FLOAT NOT NULL,
    FOREIGN KEY (dni) REFERENCES alumnos(dni)
);

-- Todos los INSERT se han generado con IA
INSERT INTO alumnos (dni, nombre, edad) VALUES ('66666666F', 'Pedro Romero', 20);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('77777777G', 'Lucía Fernández', 19);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('88888888H', 'Sergio Molina', 21);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('99999999I', 'Elena Ruiz', 18);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('10101010J', 'Marcos Jiménez', 22);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('11111111K', 'Sofía Torres', 20);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('12121212L', 'Diego Morales', 19);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('13131313M', 'Valentina Castro', 21);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('14141414N', 'Adrián Ortega', 18);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('15151515O', 'Natalia Vega', 23);

INSERT INTO alumno_fp (dni, ciclo) VALUES ('66666666F', 'DAM');
INSERT INTO alumno_fp (dni, ciclo) VALUES ('77777777G', 'ASIR');
INSERT INTO alumno_fp (dni, ciclo) VALUES ('88888888H', 'DAW');
INSERT INTO alumno_fp (dni, ciclo) VALUES ('10101010J', 'DAM');
INSERT INTO alumno_fp (dni, ciclo) VALUES ('12121212L', 'ASIR');

INSERT INTO alumno_bach (dni, modalidad) VALUES ('99999999I', 'Ciencias');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('11111111K', 'Humanidades');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('13131313M', 'Artes');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('14141414N', 'Ciencias');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('15151515O', 'Humanidades');

INSERT INTO notas (dni, asignatura, nota) VALUES ('66666666F', 'Programación', 9.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('66666666F', 'Bases de Datos', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('66666666F', 'Entornos de Desarrollo', 7.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('77777777G', 'Redes', 6.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('77777777G', 'Sistemas Operativos', 7.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('77777777G', 'Seguridad Informática', 8.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('88888888H', 'Diseño Web', 9.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('88888888H', 'Programación', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('88888888H', 'Bases de Datos', 6.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('10101010J', 'Programación', 5.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('10101010J', 'Bases de Datos', 4.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('10101010J', 'Entornos de Desarrollo', 6.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('12121212L', 'Redes', 9.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('12121212L', 'Sistemas Operativos', 8.5);

INSERT INTO notas (dni, asignatura, nota) VALUES ('99999999I', 'Matemáticas', 9.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('99999999I', 'Física', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('99999999I', 'Química', 7.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111K', 'Historia', 6.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111K', 'Filosofía', 7.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111K', 'Literatura', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('13131313M', 'Historia del Arte', 9.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('13131313M', 'Dibujo Artístico', 9.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('13131313M', 'Filosofía', 7.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('14141414N', 'Matemáticas', 5.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('14141414N', 'Física', 6.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('14141414N', 'Biología', 7.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('15151515O', 'Historia', 8.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('15151515O', 'Filosofía', 9.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('15151515O', 'Literatura', 6.5);

INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111A', 'Entornos de Desarrollo', 6.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111A', 'Programación de Servicios', 7.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('22222222B', 'Física', 5.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('22222222B', 'Química', 6.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('33333333C', 'Bases de Datos', 6.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('33333333C', 'Diseño Web', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('44444444D', 'Filosofía', 7.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('44444444D', 'Literatura', 5.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('55555555E', 'Matemáticas', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('55555555E', 'Química', 6.5);
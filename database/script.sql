
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
    nota REAL NOT NULL,
    FOREIGN KEY (dni) REFERENCES alumnos(dni)
);

INSERT INTO alumnos (dni, nombre, edad) VALUES ('11111111A', 'Ana García', 20);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('22222222B', 'Juan Pérez', 18);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('33333333C', 'Laura Martínez', 21);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('44444444D', 'Carlos López', 19);
INSERT INTO alumnos (dni, nombre, edad) VALUES ('55555555E', 'María Sánchez', 22);

INSERT INTO alumno_fp (dni, ciclo) VALUES ('11111111A', 'DAM');
INSERT INTO alumno_fp (dni, ciclo) VALUES ('33333333C', 'DAW');

INSERT INTO alumno_bach (dni, modalidad) VALUES ('22222222B', 'Ciencias');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('44444444D', 'Humanidades');
INSERT INTO alumno_bach (dni, modalidad) VALUES ('55555555E', 'Ciencias');

INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111A', 'Programación', 8.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('11111111A', 'Bases de Datos', 7.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('22222222B', 'Matemáticas', 6.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('22222222B', 'Filosofía', 9.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('33333333C', 'Programación', 5.5);
INSERT INTO notas (dni, asignatura, nota) VALUES ('44444444D', 'Historia', 8.0);
INSERT INTO notas (dni, asignatura, nota) VALUES ('55555555E', 'Física', 7.5);
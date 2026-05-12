package fxml;

import DAO.alumno.AlumnoDAO;
import DAO.alumno.AlumnoDAOImpl;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objetos.Alumno;
import objetos.AlumnoBachillerato;
import objetos.AlumnoFP;
import objetos.controlExcepciones.Check;
import objetos.controlExcepciones.EdadInvalidaExcepcion;
import objetos.controlExcepciones.StringInvalidoExcepcion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorIndex {
    @FXML
    private TableView<Map.Entry<String, Alumno>> tablaAlumnos;
    @FXML
    private TableColumn<Map.Entry<String, Alumno>, String> colDni;
    @FXML
    private TableColumn<Map.Entry<String, Alumno>, String> colNombre;
    @FXML
    private TableColumn<Map.Entry<String, Alumno>, Integer> colEdad;
    @FXML
    private TableColumn<Map.Entry<String, Alumno>, Double> colMedia;


    @FXML
    private TextField inputNombre;
    @FXML
    private TextField inputDni;
    @FXML
    private TextField inputEdad;
    @FXML
    private ComboBox<String> inputCurso;
    @FXML
    private TextField inputExtra;


    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAct;
    @FXML
    private Button btnAbrir;


    @FXML
    private Button btnInforme;
    @FXML
    private TextArea Informe;
    @FXML
    private Button btnCopiar;
    @FXML
    private Button btnExportar;


    @FXML
    private Label errorLabel;


    private final AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
    private ObservableList<Map.Entry<String, Alumno>> listaAlumnos = FXCollections.observableArrayList();

    // Global a la clase para que todos los metodos que funcionan alrededor del Informe puedan acceder directamente a el
    private final StringBuilder sb = new StringBuilder();

    public void initialize() {
        colDni.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getKey()));
        colNombre.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getNombre()));
        colEdad.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getValue().getEdad()).asObject());
        colMedia.setCellValueFactory(e -> {
            Alumno alumno = e.getValue().getValue();
            return new SimpleDoubleProperty(alumno.calcularMedia()).asObject();
        });

        inputCurso.getItems().addAll("Bachillerato", "FP");


        btnAgregar.setOnAction(e -> agregarAlumno());
        btnEliminar.setOnAction(e -> eliminarAlumno());
        btnAct.setOnAction(e -> actualizarAlumno());
        btnAbrir.setOnAction(e -> abrirPerfil());
        btnInforme.setOnAction(e -> generarInforme());

        btnCopiar.setOnAction(e -> copiarInforme());
        btnExportar.setOnAction(e -> exportarTxt());

        cargarDatos();


        // Esto hace que al seleccionar una fila se rellenen automáticamente todos los campos del formulario que se usa para agregar nuevos alumnos,
        // en este caso para poder actualizar el alumno seleccionado
        tablaAlumnos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                btnAct.setDisable(false);
                btnAbrir.setDisable(false);
                Alumno a = newVal.getValue();

                inputDni.setText(a.getDni());
                inputDni.setDisable(true);
                inputNombre.setText(a.getNombre());
                inputEdad.setText(String.valueOf(a.getEdad()));

                if(a instanceof AlumnoFP) {
                    inputCurso.setValue("FP");
                    inputExtra.setText(((AlumnoFP) a).getCiclo());
                } else {
                    inputCurso.setValue("Bachillerato");
                    inputExtra.setText(((AlumnoBachillerato) a).getModalidad());
                }
            } else {
                inputDni.clear();
                inputNombre.clear();
                inputEdad.clear();
                inputExtra.clear();
                inputCurso.setValue("Curso");
                inputDni.setDisable(false);
                btnAct.setDisable(true);
                btnAbrir.setDisable(true);
            }
        });


        // Actualiza las celdas de las notas a color verde o rojo según si la media está aprobada o no
        colMedia.setCellFactory(col -> new TableCell<Map.Entry<String, Alumno>, Double>() {
            @Override
            protected void updateItem(Double media, boolean empty) {
                super.updateItem(media,empty);
                if (empty || media == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(media));
                    if (media >= 5) {
                        setStyle("-fx-background-color: #b6fcb6;");
                    } else {
                        setStyle("-fx-background-color: #fcc5c5;");
                    }
                }
            }
        });
    }

    private void cargarDatos() {
        listaAlumnos.clear();
        Map<String, Alumno> mapa = alumnoDAO.obtenerTodo().stream().collect(Collectors.toMap(Alumno::getDni, e -> e));
        listaAlumnos = FXCollections.observableArrayList(mapa.entrySet());
        tablaAlumnos.setItems(listaAlumnos);
    }

    private void agregarAlumno() {
        String inDni = inputDni.getText();
        String inNombre = inputNombre.getText();
        String inEdad = inputEdad.getText();
        String inCurso = inputCurso.getValue();
        String inExtra = inputExtra.getText();

        if(!inDni.isEmpty() && !inNombre.isEmpty() && !inEdad.isEmpty() && !inCurso.isEmpty() && !inExtra.isEmpty() && (inCurso.equals("Bachillerato") || inCurso.equals("FP"))) {
            if(!listaAlumnos.stream().map(Map.Entry::getKey).collect(Collectors.toList()).contains(inDni)) {
                try {
                    Check.checkDni(inDni);
                    Check.checkNombre(inNombre);
                    Check.checkExtra(inExtra);
                    int edad = Check.parseInt(inEdad);
                    errorLabel.setVisible(false);

                    Alumno a;

                    if (inCurso.equals("Bachillerato")) {
                        a = new AlumnoBachillerato(
                                inDni, inNombre, edad, inExtra
                        );
                    } else {
                        a = new AlumnoFP(
                                inDni, inNombre, edad, inExtra
                        );
                    }

                    alumnoDAO.agregarAlumno(a);

                    cargarDatos();
                    inputDni.clear();
                    inputNombre.clear();
                    inputEdad.clear();
                    inputExtra.clear();
                } catch (EdadInvalidaExcepcion | StringInvalidoExcepcion e) {
                    errorLabel.setText(e.getMessage());
                    errorLabel.setVisible(true);
                }
            }
        } else {
            errorLabel.setText("Debes rellenar todos los campos");
            errorLabel.setVisible(true);
        }
    }

    private void eliminarAlumno() {
        // Usando este tipo de variable evito que lance un NullPointerException si se pulsa el boton mientras que no hay ningún alumno seleccionado
        Optional<Map.Entry<String, Alumno>> select = Optional.ofNullable(tablaAlumnos.getSelectionModel().getSelectedItem());

        if(select.isPresent()) {
            errorLabel.setVisible(false);
            alumnoDAO.eliminarAlumno(select.get().getValue());
            cargarDatos();
        } else {
            errorLabel.setText("Debes seleccionar un alumno");
            errorLabel.setVisible(true);
        }
    }

    private void actualizarAlumno() {
        String inDni = inputDni.getText();
        String inNombre = inputNombre.getText();
        String inEdad = inputEdad.getText();
        String inCurso = inputCurso.getValue();
        String inExtra = inputExtra.getText();

        if(!inNombre.isEmpty() && !inEdad.isEmpty() && !inCurso.isEmpty() && !inExtra.isEmpty() && (inCurso.equals("Bachillerato") || inCurso.equals("FP"))) {
            try {
                // No se comprueba el DNI porque se inhabilita el campo del DNI cuando se selecciona un alumno para actualizar
                Check.checkNombre(inNombre);
                Check.checkExtra(inExtra);
                int edad = Check.parseInt(inEdad);
                errorLabel.setVisible(false);
                alumnoDAO.actualizarAlumno(inDni, inNombre, edad, inCurso, inExtra);
                cargarDatos();
            } catch (EdadInvalidaExcepcion | StringInvalidoExcepcion e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Uno o varios campos están vacíos, rellene todos");
            errorLabel.setVisible(true);
        }
    }

    // Metodo para generar el informe: a partir de la lista observable se mapea para obtener solo los valores (la lista de objetos Alumno)
    // para ordenarlos estando los alumnos de FP primero y luego Bachillerato y a través de un forEach usar .append para agregar los toString() de cada
    // Alumno al StringBuilder
    private void generarInforme() {
        sb.delete(0, sb.length());
        btnCopiar.setDisable(false);
        btnExportar.setDisable(false);

        listaAlumnos.stream().map(Map.Entry::getValue).sorted(Comparator.comparing(e -> e instanceof AlumnoFP ? 0 : 1))
                .forEach(a -> sb.append(a.toString()).append("\n"));
        if(sb.length() == 0) {
            Informe.setText("No hay alumnos suficientes para generar un informe");
        } else {
            Informe.setText(String.valueOf(sb));
        }
    }

    // Metodo para copiar el Informe en el portapapeles del usuario
    private void copiarInforme() {
        Clipboard clipBoard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(String.valueOf(sb));
        clipBoard.setContent(content);

        // Reinicia el TextArea y el StringBuilder una vez que se ha copiado el informe:
        sb.delete(0, sb.length());
        btnCopiar.setDisable(true);
        btnExportar.setDisable(true);
        Informe.setText("");
    }

    // Metodo para exportar el Informe en un archivo TXT
    private void exportarTxt() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("alumnos.txt"))) {
            bw.write(String.valueOf(sb));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        sb.delete(0, sb.length());
        btnCopiar.setDisable(true);
        btnExportar.setDisable(true);
        Informe.setText("");
    }


    // Crea un nuevo Stage donde se muestra los datos del alumno seleccionado junto a las asignaturas y notas que dicho alumno tiene asignado
    private void abrirPerfil() {
        try {
            Alumno select = tablaAlumnos.getSelectionModel().getSelectedItem().getValue();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil.fxml"));
            Parent root = loader.load();

            ControladorPerfil popupController = loader.getController();
            popupController.setAlumno(select);

            Scene popupScene = new Scene(root, 600, 320);
            popupScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/perfil.css")).toExternalForm());

            Stage popupStage = new Stage();
            popupStage.setTitle("Perfil " + select.getNombre());
            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);

            popupStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        cargarDatos();
    }




}

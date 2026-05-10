package fxml;

import DAO.NotaDAO;
import DAO.NotaDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import objetos.Alumno;
import objetos.AlumnoBachillerato;
import objetos.AlumnoFP;
import objetos.nota.Nota;
import objetos.nota.NotaInvalidaExcepcion;


public class ControladorPerfil {
    private Alumno alumno;
    // Este metodo se usa para poder traer desde el controlador de la vista principal el alumno que se ha seleccionado para abrir su perfil
    // y poder cargar sus datos, los metodos de carga se ejecutan dentro de este metodo ya que si se ejecutaran dentro del initialize
    // que se ejecuta antes de este metodo, la variable alumno sería null y lanzaría una excepción
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        cargarDatos();
        cargarDatosAlumno();
    }

    @FXML
    private Label putNombre;
    @FXML
    private Label putDni;
    @FXML
    private Label putEdad;
    @FXML
    private Label putCurso;
    @FXML
    private Label putExtra;


    @FXML
    private TextField inputAsignatura;
    @FXML
    private TextField inputNota;


    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;


    @FXML
    private TableView<Nota> tablaNotas;
    @FXML
    private TableColumn<Nota, String> colAsignatura;
    @FXML
    private TableColumn<Nota, Double> colNota;

    @FXML
    private Label errorLabel;


    NotaDAO notaDAO = new NotaDAOImpl();
    ObservableList<Nota> listaNotas = FXCollections.observableArrayList();

    public void initialize() {
        colAsignatura.setCellValueFactory(new PropertyValueFactory<>("asignatura"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        // Se cargan los datos de la fila seleccionada para que se facilite el actualizar dicha fila
        tablaNotas.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if(newVal != null) {
                inputAsignatura.setText(newVal.getAsignatura());
                inputNota.setText(String.valueOf(newVal.getNota()));
                btnActualizar.setDisable(false);
                inputAsignatura.setDisable(true);
            } else {
                inputAsignatura.clear();
                inputNota.clear();
                btnActualizar.setDisable(true);
                inputAsignatura.setDisable(false);
            }
        });

        btnAgregar.setOnAction(e -> agregarNota());
        btnEliminar.setOnAction(e -> eliminarNota());
        btnActualizar.setOnAction(e -> actualizarNota());
    }

    private void cargarDatosAlumno() {
        // Se cargan los datos del alumno que se ha seleccionado para abrir la nueva ventana con su perfil
        putNombre.setText(alumno.getNombre());
        putDni.setText(alumno.getDni());
        putEdad.setText(String.valueOf(alumno.getEdad()));
        if(alumno instanceof AlumnoFP) {
            putCurso.setText("FP");
            putExtra.setText(((AlumnoFP) alumno).getCiclo());
        } else if (alumno instanceof AlumnoBachillerato) {
            putCurso.setText("Bachillerato");
            putExtra.setText(((AlumnoBachillerato) alumno).getModalidad());
        }
    }

    private void cargarDatos() {
        listaNotas.clear();
        listaNotas = FXCollections.observableArrayList(notaDAO.obtenerTodo(alumno.getDni()));
        tablaNotas.setItems(listaNotas);
    }

    private void agregarNota() {
        String inAsignatura = inputAsignatura.getText();
        String inNota = inputNota.getText();

        if(!inAsignatura.isEmpty() && !inNota.isEmpty()) {
            try {
                double nota = Nota.parseDouble(inNota);

                errorLabel.setVisible(false);
                Nota n = new Nota(inAsignatura, nota);
                notaDAO.agregarNota(n, alumno.getDni());
                cargarDatos();

                inputNota.clear();
                inputAsignatura.clear();
            } catch (NotaInvalidaExcepcion e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Debes rellenar todos los campos");
            errorLabel.setVisible(true);
        }
    }

    private void actualizarNota() {
        Nota select = tablaNotas.getSelectionModel().getSelectedItem();
        String inAsignatura = inputAsignatura.getText();
        String inNota = inputNota.getText();

        if(select != null && !inAsignatura.isEmpty() && !inNota.isEmpty()) {
            try {
                double nota = Double.parseDouble(inNota);

                errorLabel.setVisible(false);
                notaDAO.actualizarNota(inAsignatura, nota, select.getId());
                cargarDatos();

                inputNota.clear();
                inputAsignatura.clear();
            } catch (NotaInvalidaExcepcion e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Debes rellenar todos los campos");
            errorLabel.setVisible(true);
        }
    }

    private void eliminarNota() {
        Nota select = tablaNotas.getSelectionModel().getSelectedItem();

        if(select != null) {
            errorLabel.setVisible(false);
            notaDAO.eliminarNota(select);
            cargarDatos();
        } else {
            errorLabel.setText("Debes seleccionar una fila");
            errorLabel.setVisible(true);
        }
    }

}

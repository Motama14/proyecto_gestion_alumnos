

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));

        Scene scene = new Scene(loader.load(), 700, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("index.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Aplicación Gestión Alumnos");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

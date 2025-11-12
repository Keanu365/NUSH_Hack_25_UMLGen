package org.UML.UMLGen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/splash-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load()/*, 400, 600*/);
        stage.setTitle("ClassMate");
        stage.setScene(scene);
        stage.getIcons().add(new javafx.scene.image.Image("file:" + System.getProperty("user.dir") + "/src/main/resources/com/example/classmate/View/icon.png"));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
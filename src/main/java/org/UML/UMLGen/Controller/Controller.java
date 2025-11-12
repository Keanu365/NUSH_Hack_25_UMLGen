package org.UML.UMLGen.Controller;

import org.UML.UMLGen.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    @FXML
    void menu(MouseEvent event) {
        //TODO: Cool animation
        Node source = (Node)event.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage)scene.getWindow();
        stage.close();
    }

    static void showScene(Stage stage, String title, String path) throws IOException {
        showScene(stage, title, path, stage.getWidth(), stage.getHeight());
    }

    static void showScene(Stage stage, String title, String path, boolean fullScreen) throws IOException {
        showScene(stage, title, path, stage.getWidth(), stage.getHeight(), fullScreen);
    }



    static void showScene(Stage stage, String title, String path, double w, double h, boolean fullScreen) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(path));
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.getIcons().add(new javafx.scene.image.Image("file:" + System.getProperty("user.dir") + "/src/main/resources/com/example/classmate/View/icon.png"));
        stage.setResizable(false);
        stage.setFullScreen(fullScreen);
        stage.setWidth(w);
        stage.setHeight(h);
        stage.show();
    }

    static void showScene(Stage stage, String title, String path, double w, double h) throws IOException {
        showScene(stage, title, path, w, h, true);
    }

    static boolean showAlert(Alert.AlertType at, String title, String header, String content, boolean reqConfirm) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        if (reqConfirm) {
            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");
            alert.getButtonTypes().setAll(yesBtn, noBtn);
            ButtonType bt = alert.showAndWait().orElse(noBtn);
            return bt.equals(yesBtn);
        } else {
            alert.showAndWait();
            return true;
        }
    }

    abstract String generateAI(String prompt);
}

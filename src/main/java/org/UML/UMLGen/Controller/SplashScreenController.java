package org.UML.UMLGen.Controller;

import org.UML.UMLGen.HelloApplication;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenController {
    public static String fxmlToShow = "View/main-menu.fxml";
    public static String title = "ClassMate";
    @FXML private ImageView logo;
    @FXML private ProgressBar progressBar;

    @FXML
    public void initialize() {
        Thread thread = new Thread(() -> {
            RotateTransition ft = new RotateTransition(Duration.millis(1000), logo);
            ft.setFromAngle(0);
            ft.setToAngle(360);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.play();
            Timeline progressTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                    new KeyFrame(Duration.millis(5000), new KeyValue(progressBar.progressProperty(), 1, Interpolator.EASE_BOTH))
            );
            progressTimeline.setCycleCount(1);
            progressTimeline.play();
            try {
                Thread.sleep(5000);
                if (!fxmlToShow.isEmpty()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlToShow));
                    Platform.runLater(() -> {
                        Stage stage = new Stage();
                        stage.setTitle(title);
                        try {
                            Parent root = fxmlLoader.load();
                            stage.setScene(new Scene(root));
                        } catch (IOException e) {
                            System.err.println("Could not load FXML.");
                        }
                        try {
                            UMLEditorController controller = fxmlLoader.getController();
                            stage.getScene().setUserData(controller);
                        } catch (Exception _){}
                        stage.setFullScreen(!fxmlToShow.equals("View/main-menu.fxml"));
                        stage.setFullScreenExitHint("");
//                        stage.setResizable(false);
                        stage.getIcons().add(new javafx.scene.image.Image("file:" + System.getProperty("user.dir") + "/src/main/resources/com/example/classmate/View/icon.png"));
                        stage.show();
                    });
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted.");
            } finally {
                Platform.runLater(() -> ((Stage) logo.getScene().getWindow()).close());
            }
        });
        thread.start();
    }
}

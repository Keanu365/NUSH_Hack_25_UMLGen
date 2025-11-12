package org.UML.UMLGen.Controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainController extends Controller{
    @FXML private Button umlGenBtn;

    @FXML
    public void initialize(){
        Platform.runLater(()->{
            ((Stage) umlGenBtn.getScene().getWindow()).setResizable(false);
            ((Stage) umlGenBtn.getScene().getWindow()).setFullScreen(false);
        });
    }


    @FXML
    void loadUMLScene(MouseEvent ignore) throws IOException {
        showScene(new Stage(), "UML Diagram Generator", "View/uml-view-new.fxml", false);
    }

    @FXML
    void btnHover(MouseEvent event) {
        Button sourceBtn = (Button) event.getSource();
        Parent p = sourceBtn.getParent();
        if (p.getChildrenUnmodifiable().size() > 1) {
            for (Node n : p.getChildrenUnmodifiable()) {
                if (n != sourceBtn) {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(100), n);
                    tt.setToY(n.getLayoutY() > sourceBtn.getLayoutY() ? 10f : -10f);
                    tt.play();
                }
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(100), sourceBtn);
        st.setToX(1.25f);
        st.setToY(1.25f);
        st.play();
    }

    @FXML
    void btnUnhover(MouseEvent event) {
        Button sourceBtn = (Button) event.getSource();
        Parent p = sourceBtn.getParent();
        if (p.getChildrenUnmodifiable().size() > 1) {
            for (Node n : p.getChildrenUnmodifiable()) {
                if (n != sourceBtn) {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(100), n);
                    tt.setToY(0f);
                    tt.play();
                }
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(100), sourceBtn);
        st.setToX(1f);
        st.setToY(1f);
        st.play();
    }

    @Override
    String generateAI(String prompt) {
        return "";
    }
}

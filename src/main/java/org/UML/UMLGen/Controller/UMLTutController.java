package org.UML.UMLGen.Controller;

import org.UML.UMLGen.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class UMLTutController extends Controller{
    @FXML private GridPane gridPane;
    @FXML private Label exitBtn;
    @FXML private Label descLbl;
    @FXML private ImageView gifView;
    @FXML private Label nextBtn;
    @FXML private Label prevBtn;

    private int tutCounter = 0;
    private final String[] tutMessages = {
            "First, compile your project and find the directory where all the .class files are stored. The example above shows how to do this in IntelliJ.",
            "Upload the directory containing the .class files into the programme. In the example shown above, it is the 'production/ClassMateTests' directory.",
            "Another example with JavaFX in IntelliJ.",
            "Upload the directory containing the .class files; in this case, it is the 'classes' directory."
    };
    private final Image[] images = new Image[4];

    @FXML
    void initialize(){
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(HelloApplication.class.getResource("View/TutGifs/GIF" + i + ".gif").toExternalForm());
        }
        gifView.setImage(images[0]);
        descLbl.setText(tutMessages[0]);
    }

    @FXML
    void exit(MouseEvent event) {
        exitBtn.getScene().getWindow().hide();
    }

    @FXML
    void next(InputEvent event) {
        if (event instanceof KeyEvent keyEvent) {
            if (keyEvent.getCode() != KeyCode.RIGHT) return;
        }
        tutCounter++;
        if (tutCounter >= tutMessages.length) {
            tutCounter = 0;
        }
        gifView.setImage(images[tutCounter]);
        descLbl.setText(tutMessages[tutCounter]);
    }

    @FXML
    void previous(InputEvent event) {
        if (event instanceof KeyEvent keyEvent) {
            if (keyEvent.getCode() != KeyCode.LEFT) return;
        }
        tutCounter--;
        if (tutCounter < 0) {
            tutCounter = tutMessages.length - 1;
        }
        gifView.setImage(images[tutCounter]);
        descLbl.setText(tutMessages[tutCounter]);
    }

    @Override
    String generateAI(String prompt) {
        return "";
    }
}

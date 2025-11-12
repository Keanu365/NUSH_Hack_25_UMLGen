package org.UML.UMLGen.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
    @FXML private Button closeBtn;

    @FXML
    void handleClose(ActionEvent event) {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }

}

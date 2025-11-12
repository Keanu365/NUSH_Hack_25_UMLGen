package org.UML.UMLGen.Controller;

import org.UML.UMLGen.HelloApplication;
import org.UML.UMLGen.Model.UMLClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UMLMenuController extends Controller{
    @FXML private Label backBtn;
    @FXML private ColorPicker colorPicker;
    @FXML private Button generateBtn;
    @FXML private Label selectedFolderLbl;
    @FXML private CheckBox simpleNamesCheckbox;
    @FXML private Label simpleNamesLbl;
    @FXML private Button uploadBtn;
    @FXML private Button instructionsBtn;
    @FXML private Button loadCMUDBtn;

    static UMLClass[] umlClasses;
    static File cmudFile;

    @FXML
    public void initialize(){umlClasses = null; cmudFile = null;}

    @FXML
    void generate(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("UML Diagram Editor");
        stage.getIcons().add(new javafx.scene.image.Image("file:" + System.getProperty("user.dir") + "/src/main/resources/com/example/classmate/View/icon.png"));
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("View/splash-screen.fxml"));
        SplashScreenController.fxmlToShow = "View/uml-diagram.fxml";
        SplashScreenController.title = "UML Diagram Editor";
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
        ((Stage) generateBtn.getScene().getWindow()).close();
    }

    @FXML
    void upload(MouseEvent event) {
        DirectoryChooser dc  = new DirectoryChooser();
        dc.setTitle("Select Folder");
        File folder = dc.showDialog(uploadBtn.getScene().getWindow());
        if (folder != null){
            try{
                selectedFolderLbl.setText("Selected folder: " + folder.getAbsolutePath());
                File[] files = folder.listFiles();
                if (files == null || files.length == 0) throw new Exception("Provided folder is empty.");
                Class<?>[] classes = UMLClass.loadFolder(folder);
                umlClasses = new UMLClass[classes.length];
                for (int i = 0; i < classes.length; i++) {
                    umlClasses[i] = new UMLClass(classes[i]);
                }
            }catch (Exception e){
                System.err.println("An error occurred.");
            }
        }
    }

    @FXML
    void loadCMUD(MouseEvent event) {
        FileChooser fc  = new FileChooser();
        fc.setTitle("Select Folder");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ClassMate UML Diagram", "*.cmud")
        );
        File file = fc.showOpenDialog(uploadBtn.getScene().getWindow());
        if (file != null){
            try{
                cmudFile = file;
                generate(event);
            }catch (Exception e){
                System.err.println("An error occurred.");
            }
        }
    }

    @Override
    String generateAI(String prompt) {
        return "";
    }
}

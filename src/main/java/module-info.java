module org.UML.UMLGen {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.genai;
    requires javafx.graphics;
    requires java.compiler;
    requires javafx.base;
    requires java.desktop;
    requires javafx.swing;
    requires org.fxmisc.richtext;


    opens org.UML.UMLGen to javafx.fxml;
    exports org.UML.UMLGen;
    exports org.UML.UMLGen.Model;
    opens org.UML.UMLGen.Model to javafx.fxml;
    exports org.UML.UMLGen.Controller;
    opens org.UML.UMLGen.Controller to javafx.fxml;
}
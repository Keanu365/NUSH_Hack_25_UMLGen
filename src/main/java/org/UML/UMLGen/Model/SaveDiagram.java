package org.UML.UMLGen.Model;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.io.*;

public class SaveDiagram{
    public static Pane contentPane = new Pane();

    public static void saveAsPNG(File outputFile) throws Exception {saveAsPNG(contentPane, outputFile);}
    public static void saveAsPNG(Pane contentPane, File outputFile) throws Exception {
        if (contentPane.getChildren().isEmpty()) throw new IllegalArgumentException();
        Bounds bounds = getContentBounds(contentPane);
        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new Rectangle2D(
                bounds.getMinX()-10.0,
                bounds.getMinY()-10.0,
                bounds.getWidth()+20.0,
                bounds.getHeight()+20.0
        ));
        WritableImage image = contentPane.snapshot(params, null);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
    }

    public static void saveAsCMUD(File outputFile) throws Exception {saveAsCMUD(contentPane, outputFile);}
    public static void saveAsCMUD(Pane contentPane, File outputFile) throws Exception {
        if (contentPane.getChildren().isEmpty()) throw new IllegalArgumentException();
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(contentPane.getChildren().size() + "\n");
        for (Node node : contentPane.getChildren()) {
            if (node instanceof Formattable fNode) bw.write(fNode.getFormat() + "\n");
        }
        bw.close(); //ALWAYS keep this line at the end
    }

    public static void loadCMUD(File file){

    }

    private static Bounds getContentBounds(Pane contentPane) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Node node : contentPane.getChildren()) {
            Bounds b = node.getBoundsInParent();
            minX = Math.min(minX, b.getMinX());
            minY = Math.min(minY, b.getMinY());
            maxX = Math.max(maxX, b.getMaxX());
            maxY = Math.max(maxY, b.getMaxY());
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
}

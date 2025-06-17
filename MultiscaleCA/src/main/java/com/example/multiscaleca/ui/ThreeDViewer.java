package com.example.multiscaleca.ui;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class ThreeDViewer {
    public static void show(Grid3D grid, int cellSize) {
        int W = grid.getWidth(), H = grid.getHeight(), D = grid.getDepth();
        Group root3D = new Group();
        for (int z = 0; z < D; z++) {
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    Cell c = grid.getCell(x, y, z);
                    if (c != null && !c.isEmpty()) {
                        Box b = new Box(cellSize, cellSize, cellSize);
                        b.setTranslateX((x - W/2.0) * cellSize);
                        b.setTranslateY((y - H/2.0) * cellSize);
                        b.setTranslateZ((z - D/2.0) * cellSize);
                        PhongMaterial mat = new PhongMaterial();
                        var col = c.getColor();
                        mat.setDiffuseColor(Color.color(col.getRed(), col.getGreen(), col.getBlue()));
                        b.setMaterial(mat);
                        root3D.getChildren().add(b);
                    }
                }
            }
        }

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(10000);
        camera.setTranslateZ(-Math.max(W, H) * cellSize);

        Rotate rx = new Rotate(-30, Rotate.X_AXIS);
        Rotate ry = new Rotate(-45, Rotate.Y_AXIS);
        root3D.getTransforms().addAll(rx, ry);

        SubScene sub = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
        sub.setCamera(camera);
        sub.setFill(Color.gray(0.1));

        // Zoom
        sub.setOnScroll(e -> camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY()));
        // Obrót myszką
        final Delta drag = new Delta();
        sub.setOnMousePressed((MouseEvent e) -> { drag.x = e.getSceneX(); drag.y = e.getSceneY(); });
        sub.setOnMouseDragged((MouseEvent e) -> {
            double dx = e.getSceneX() - drag.x;
            double dy = e.getSceneY() - drag.y;
            ry.setAngle(ry.getAngle() + dx * 0.5);
            rx.setAngle(rx.getAngle() - dy * 0.5);
            drag.x = e.getSceneX(); drag.y = e.getSceneY();
        });

        Stage stage = new Stage();
        Group root = new Group(sub);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("3D Viewer");
        stage.show();
    }

    private static class Delta { double x, y; }
}

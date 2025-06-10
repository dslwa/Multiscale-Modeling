package com.example.multiscaleca;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private final int cellSize = 3;
    private final int gridWidth = 220;
    private final int gridHeight = 220;

    private Grid grid;
    private Canvas canvas;

    private TextField numOfCells;
    private ComboBox<String> neighbourhoodField;
    private ComboBox<String> seedingModeBox;
    private ComboBox<String> boundaryBox;

    @Override
    public void start(Stage stage) {
        // Inicjalizacja kontrolek
        numOfCells = new TextField();
        numOfCells.setPromptText("Liczba zarodków");
        numOfCells.getStyleClass().add("input-field");

        neighbourhoodField = new ComboBox<>();
        neighbourhoodField.getItems().addAll("Von Neumann", "Pentagonalne losowe");
        neighbourhoodField.getSelectionModel().selectFirst();
        neighbourhoodField.getStyleClass().add("combo-box");

        seedingModeBox = new ComboBox<>();
        seedingModeBox.getItems().addAll("Losowe", "Jednorodne", "Ręczne");
        seedingModeBox.getSelectionModel().selectFirst();
        seedingModeBox.getStyleClass().add("combo-box");

        boundaryBox = new ComboBox<>();
        boundaryBox.getItems().addAll("Periodyczne", "Absorbujące");
        boundaryBox.getSelectionModel().selectFirst();
        boundaryBox.getStyleClass().add("combo-box");

        Button startButton = new Button("Start symulacji");
        startButton.getStyleClass().add("button-primary");
        startButton.setOnAction(e -> onStart());

        Button growButton = new Button("Iteracja");
        growButton.getStyleClass().add("button-secondary");
        growButton.setOnAction(e -> onIterate());

        canvas = new Canvas(cellSize * gridWidth, cellSize * gridHeight);
        canvas.getStyleClass().add("canvas-grid");
        setupCanvasClick();

        VBox controls = new VBox(10,
                numOfCells,
                neighbourhoodField,
                seedingModeBox,
                boundaryBox,
                startButton,
                growButton
        );
        controls.getStyleClass().add("controls-pane");

        HBox root = new HBox(20,
                canvas,
                controls
        );
        root.getStyleClass().add("root-pane");

        Scene scene = new Scene(root);
        // Załaduj plik CSS
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        stage.setTitle("Rozrost ziaren CA");
        stage.setScene(scene);
        stage.show();
    }

    private void onStart() {
        String mode = seedingModeBox.getValue();
        grid = new Grid(gridWidth, gridHeight);
        if ("Periodyczne".equals(boundaryBox.getValue())) {
            grid.setBoundaryType(Grid.BoundaryType.PERIODIC);
        } else {
            grid.setBoundaryType(Grid.BoundaryType.ABSORBING);
        }
        if (!"Ręczne".equals(mode)) {
            try {
                int grains = Integer.parseInt(numOfCells.getText());
                if (mode.equals("Losowe")) grid.seedRandomGrains(grains);
                else if (mode.equals("Jednorodne")) grid.seedUniformGrains(grains);
            } catch (NumberFormatException ex) {
                System.out.println("Niepoprawna liczba zarodków!");
            }
        }
        renderGrid();
    }

    private void onIterate() {
        try {
            grid.growOneIteration(neighbourhoodField.getValue());
        } catch (Exception ex) {
            System.out.println("Nie można wykonać iteracji.");
        }
        renderGrid();
    }

    private void setupCanvasClick() {
        canvas.setOnMouseClicked(e -> {
            if (!"Ręczne".equals(seedingModeBox.getValue()) || grid == null) return;
            int x = (int) (e.getX() / cellSize);
            int y = (int) (e.getY() / cellSize);
            if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
                Cell cell = grid.getCell(x, y);
                if (cell.isEmpty()) {
                    int nextId = getNextAvailableGrainId();
                    cell.setGrainId(nextId);
                    double r = 0.3 + 0.6 * Math.random();
                    double g = 0.3 + 0.6 * Math.random();
                    double b = 0.3 + 0.6 * Math.random();
                    cell.setColor(Color.color(r, g, b));
                    renderGrid();
                }
            }
        });
    }

    private int getNextAvailableGrainId() {
        int maxId = 0;
        for (int y = 0; y < grid.getHeight(); y++)
            for (int x = 0; x < grid.getWidth(); x++)
                maxId = Math.max(maxId, grid.getCell(x, y).getGrainId());
        return maxId + 1;
    }

    private void renderGrid() {
        if (grid == null) return;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Color c = grid.getCell(x, y).getColor();
                gc.setFill(c);
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
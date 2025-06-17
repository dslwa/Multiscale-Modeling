package com.example.multiscaleca.ui;

import com.example.multiscaleca.algorithm.MonteCarloSimulator;
import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid;
import com.example.multiscaleca.model.GrainStats;
import com.example.multiscaleca.model.util.BoundaryType;
import com.example.multiscaleca.neighborhood.*;
import com.example.multiscaleca.seeding.*;
import com.example.multiscaleca.ui.event.CanvasClickHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.*;

public class MainApp extends Application {
    private final int cellSize   = 4;
    private final int gridWidth  = 160;
    private final int gridHeight = 160;

    private Grid grid;
    private GridCanvas canvas;
    private ControlPanel controlPanel;
    private StatsPanel statsPanel;
    private final MonteCarloSimulator monteCarloSimulator = new MonteCarloSimulator();
    private int mcStepCounter = 0;

    @Override
    public void start(Stage stage) {
        controlPanel = new ControlPanel();
        statsPanel   = new StatsPanel();
        canvas       = new GridCanvas(gridWidth, gridHeight, cellSize);

        HBox mainContent = new HBox(20, canvas, controlPanel);
        BorderPane root  = new BorderPane();
        root.setTop(statsPanel);
        root.setCenter(mainContent);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );

        stage.setTitle("Rozrost ziaren CA");
        stage.setScene(scene);
        stage.show();

        setupActions();
    }

    private void setupActions() {
        controlPanel.getStartButton().setOnAction(e -> {
            onStart();
            updateStats();
            setClickHandler();
        });

        controlPanel.getGrowButton().setOnAction(e -> {
            String text = controlPanel.getDensityTargetField().getText().trim();
            double threshold;
            try {
                threshold = Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                onIterate();
                updateStats();
                return;
            }
            applyGrowthWithThreshold(threshold);
        });

        controlPanel.getRemoveRandomGrainsButton().setOnAction(e -> {
            try {
                int count = Integer.parseInt(controlPanel.getGrainsToRemoveField().getText());
                removeRandomGrains(count);
                canvas.render(grid);
                updateStats();
            } catch (NumberFormatException ex) {
                System.out.println("Niepoprawna liczba!");
            }
        });

        controlPanel.getMcStepButton().setOnAction(e -> runMonteCarloIteration());
    }

    private void onStart() {
        String mode = controlPanel.getSeedingModeBox().getValue();
        GrainSeeder seeder = switch (mode) {
            case "Jednorodne" -> new UniformSeeder();
            case "Losowe"     -> new RandomSeeder();
            default             -> new ManualSeeder();
        };

        String neigh = controlPanel.getNeighbourhoodField().getValue();
        NeighborhoodStrategy strategy = switch (neigh) {
            case "Pentagonalne losowe" -> new PentagonalStrategy();
            case "Moore"               -> new MooreStrategy();
            case "Heksagonalne losowe" -> new RandomHexagonalStrategy();
            default                      -> new VonNeumannStrategy();
        };

        grid = new Grid(gridWidth, gridHeight, seeder, strategy);
        if ("Periodyczne".equals(controlPanel.getBoundaryBox().getValue())) {
            grid.setBoundaryType(BoundaryType.PERIODIC);
        } else {
            grid.setBoundaryType(BoundaryType.ABSORBING);
        }

        if (!"Ręczne".equals(mode)) {
            try {
                int grains = Integer.parseInt(controlPanel.getNumOfCellsField().getText());
                grid.seed(grains);
            } catch (NumberFormatException ex) {
                System.out.println("Niepoprawna liczba zarodków!");
            }
        }

        mcStepCounter = 0;
        canvas.render(grid);
    }

    private void onIterate() {
        grid.growOneIteration();
        canvas.render(grid);
    }

    private void applyGrowthWithThreshold(double threshold) {
        int H = grid.getHeight(), W = grid.getWidth();
        int[][] backupIds = new int[H][W];
        javafx.scene.paint.Color[][] backupColors = new javafx.scene.paint.Color[H][W];
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                Cell cell = grid.getCell(x, y);
                backupIds[y][x]    = cell.getGrainId();
                backupColors[y][x] = cell.getColor();
            }
        }

        grid.growOneIteration();
        var stats = GrainStats.calculate(grid.getGrid());
        if (stats.density > threshold) {
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    Cell cell = grid.getCell(x, y);
                    cell.setGrainId(backupIds[y][x]);
                    cell.setColor(backupColors[y][x]);
                }
            }
            System.out.println("Próg " + threshold + "% przekroczony – iteracja anulowana.");
        } else {
            canvas.render(grid);
            statsPanel.update(stats);
        }
    }

    private void runMonteCarloIteration() {
        if (grid == null) return;
        try {
            double kt = Double.parseDouble(controlPanel.getKtField().getText());
            monteCarloSimulator.setKt(kt);
        } catch (NumberFormatException ex) {
            monteCarloSimulator.setKt(0.1);
        }
        monteCarloSimulator.fullIteration(grid);
        mcStepCounter++;
        canvas.render(grid);
        updateStats();
    }

    private void removeRandomGrains(int count) {
        Set<Integer> ids = new HashSet<>();
        for (var row : grid.getGrid()) {
            for (var cell : row) {
                if (cell.getGrainId() != 0) ids.add(cell.getGrainId());
            }
        }
        List<Integer> list = new ArrayList<>(ids);
        Collections.shuffle(list);
        Set<Integer> toRemove = new HashSet<>(list.subList(0, Math.min(count, list.size())));
        for (var row : grid.getGrid()) {
            for (var cell : row) {
                if (toRemove.contains(cell.getGrainId())) {
                    cell.setGrainId(0);
                    cell.setColor(javafx.scene.paint.Color.WHITE);
                }
            }
        }
        canvas.render(grid);
    }

    private void setClickHandler() {
        canvas.setOnMouseClicked(new CanvasClickHandler(grid, canvas, controlPanel, statsPanel, cellSize));
    }

    private void updateStats() {
        statsPanel.update(GrainStats.calculate(grid.getGrid()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
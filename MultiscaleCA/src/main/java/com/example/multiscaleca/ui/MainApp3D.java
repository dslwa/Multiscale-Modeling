package com.example.multiscaleca.ui;

import com.example.multiscaleca.algorithm.MonteCarloSimulator3D;
import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.GrainStats3D;
import com.example.multiscaleca.model.util.BoundaryType;
import com.example.multiscaleca.neighborhood.Neighborhood3DStrategy;
import com.example.multiscaleca.neighborhood.VonNeumann3DStrategy;
import com.example.multiscaleca.neighborhood.Pentagonal3DStrategy;
import com.example.multiscaleca.neighborhood.Moore3DStrategy;
import com.example.multiscaleca.neighborhood.RandomHexagonal3DStrategy;
import com.example.multiscaleca.seeding.GrainSeeder3D;
import com.example.multiscaleca.seeding.RandomSeeder3D;
import com.example.multiscaleca.seeding.UniformSeeder3D;
import com.example.multiscaleca.seeding.ManualSeeder3D;
import com.example.multiscaleca.ui.event.CanvasClickHandler3D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;

public class MainApp3D extends Application {
    private final int cellSize = 6;
    private final int W = 100, H = 100, D = 100;

    private Grid3D grid3D;
    private GridCanvas3D canvas3D;
    private ControlPanel controlPanel;
    private StatsPanel3D statsPanel;
    private final MonteCarloSimulator3D mc3D = new MonteCarloSimulator3D();
    private Neighborhood3DStrategy strategy3D;
    @Override
    public void start(Stage stage) {
        controlPanel = new ControlPanel();
        statsPanel   = new StatsPanel3D();
        canvas3D     = new GridCanvas3D(W, H, D, cellSize);

        HBox content = new HBox(20, canvas3D, controlPanel);
        BorderPane root = new BorderPane();
        root.setTop(statsPanel);
        root.setCenter(content);

        setup3D();

        Scene scene = new Scene(root);
        stage.setTitle("3D Rozrost Ziaren");
        stage.setScene(scene);
        stage.show();
    }

    private void setup3D() {
        grid3D = new Grid3D(W, H, D);
        BoundaryType bt = controlPanel.getBoundaryBox().getValue().equals("Periodyczne")
                ? BoundaryType.PERIODIC
                : BoundaryType.ABSORBING;
        grid3D.setBoundaryType(bt);

        String seedMode = controlPanel.getSeedingModeBox().getValue();
        GrainSeeder3D seeder3D = switch (seedMode) {
            case "Jednorodne" -> new UniformSeeder3D();
            case "Losowe"     -> new RandomSeeder3D();
            default            -> new ManualSeeder3D();
        };
        int seeds = 0;
        try {
            seeds = Integer.parseInt(controlPanel.getNumOfCellsField().getText());
        } catch (NumberFormatException ignored) { }
        seeder3D.seed(grid3D, seeds);

        String neigh = controlPanel.getNeighbourhoodField().getValue();
        strategy3D = switch (neigh) {
            case "Von Neumann"         -> new VonNeumann3DStrategy();
            case "Pentagonalne losowe" -> new Pentagonal3DStrategy();
            case "Moore"               -> new Moore3DStrategy();
            default                     -> new RandomHexagonal3DStrategy();
        };
        mc3D.setNeighborhoodStrategy(strategy3D);

        canvas3D.render(grid3D);
        statsPanel.update(GrainStats3D.calculate(grid3D.getGrid()));

        canvas3D.setOnMouseClicked(
                new CanvasClickHandler3D(grid3D, canvas3D, controlPanel, statsPanel, cellSize)
        );

        controlPanel.setMaxSlice(grid3D.getDepth());
        controlPanel.getDepthSlider().setValue(1);
        controlPanel.getDepthSlider().valueProperty().addListener((obs, o, n) -> {
            int z = controlPanel.getSelectedSlice();
            canvas3D.setCurrentZ(z);
            canvas3D.render(grid3D);
        });

        controlPanel.getStartButton().setOnAction(e -> setup3D());

        controlPanel.getGrowButton().setOnAction(e -> {
            double threshold;
            try {
                threshold = Double.parseDouble(controlPanel.getDensityTargetField().getText());
            } catch (NumberFormatException ex) {
                threshold = Double.POSITIVE_INFINITY;
            }
            // backup
            int Dp = grid3D.getDepth(), Hp = grid3D.getHeight(), Wp = grid3D.getWidth();
            int[][][] bIds = new int[Dp][Hp][Wp];
            javafx.scene.paint.Color[][][] bCols =
                    new javafx.scene.paint.Color[Dp][Hp][Wp];
            for (int z = 0; z < Dp; z++)
                for (int y = 0; y < Hp; y++)
                    for (int x = 0; x < Wp; x++) {
                        Cell c = grid3D.getCell(x,y,z);
                        bIds[z][y][x]   = c.getGrainId();
                        bCols[z][y][x]  = c.getColor();
                    }

            grid3D.growOneIteration(strategy3D);
            var stats = GrainStats3D.calculate(grid3D.getGrid());
            if (stats.density > threshold) {
                for (int z = 0; z < Dp; z++)
                    for (int y = 0; y < Hp; y++)
                        for (int x = 0; x < Wp; x++) {
                            Cell c = grid3D.getCell(x,y,z);
                            c.setGrainId(bIds[z][y][x]);
                            c.setColor(bCols[z][y][x]);
                        }
                System.out.println("Próg " + threshold + "% przekroczony – iteracja anulowana.");
            } else {
                canvas3D.render(grid3D);
                statsPanel.update(stats);
            }
        });

        controlPanel.getMcStepButton().setOnAction(e -> {
            double ktVal = 0.1;
            try {
                ktVal = Double.parseDouble(controlPanel.getKtField().getText());
            } catch (NumberFormatException ignored) { }
            mc3D.setKt(ktVal);
            mc3D.fullIteration(grid3D);
            canvas3D.render(grid3D);
            statsPanel.update(GrainStats3D.calculate(grid3D.getGrid()));
        });

        controlPanel.getRemoveRandomGrainsButton().setOnAction(e -> {
            try {
                int count = Integer.parseInt(controlPanel.getGrainsToRemoveField().getText());
                removeRandomGrains3D(count);
                canvas3D.render(grid3D);
                statsPanel.update(GrainStats3D.calculate(grid3D.getGrid()));
            } catch (NumberFormatException ex) {
                System.out.println("Niepoprawna liczba ziaren do usunięcia!");
            }
        });

        controlPanel.getExportButton().setOnAction(e ->
                Exporter3D.export(grid3D, cellSize)
        );

        controlPanel.getView3DButton().setOnAction(e ->
                ThreeDViewer.show(grid3D, cellSize)
        );
    }

    private void removeRandomGrains3D(int count) {
        Set<Integer> ids = new HashSet<>();
        for (int z = 0; z < grid3D.getDepth(); z++)
            for (int y = 0; y < grid3D.getHeight(); y++)
                for (int x = 0; x < grid3D.getWidth(); x++) {
                    Cell c = grid3D.getCell(x,y,z);
                    if (c != null && c.getGrainId() != 0) ids.add(c.getGrainId());
                }
        List<Integer> list = new ArrayList<>(ids);
        Collections.shuffle(list);
        Set<Integer> toRemove = new HashSet<>(
                list.subList(0, Math.min(count, list.size()))
        );
        for (int z = 0; z < grid3D.getDepth(); z++)
            for (int y = 0; y < grid3D.getHeight(); y++)
                for (int x = 0; x < grid3D.getWidth(); x++) {
                    Cell c = grid3D.getCell(x,y,z);
                    if (c != null && toRemove.contains(c.getGrainId())) {
                        c.setGrainId(0);
                        c.setColor(javafx.scene.paint.Color.WHITE);
                    }
                }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

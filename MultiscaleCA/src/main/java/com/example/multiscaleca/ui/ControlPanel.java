package com.example.multiscaleca.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    private final TextField numOfCells;
    private final ComboBox<String> neighbourhoodField;
    private final ComboBox<String> seedingModeBox;
    private final ComboBox<String> boundaryBox;
    private final ComboBox<String> modeBox;
    private final TextField grainsToRemoveField;
    private final Button removeRandomGrainsButton;
    private final TextField densityTargetField;
    private final Button startButton;
    private final Button growButton;
    private final Button mcStepButton;
    private final Button exportButton;
    private final TextField ktField;
    private final TextField depthField;

    public ControlPanel() {
        setSpacing(14);
        setStyle("-fx-background-color: linear-gradient(to bottom, #1f1f1f, #2c2c2c); " +
                "-fx-padding: 20; -fx-border-radius: 16; -fx-background-radius: 16; " +
                "-fx-border-color: #555; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,255,255,0.2), 10, 0.3, 0, 4);");

        numOfCells = createField("Liczba zarodków");
        neighbourhoodField = createComboBox("Von Neumann", "Pentagonalne losowe", "Moore", "Heksagonalne losowe");
        seedingModeBox = createComboBox("Losowe", "Jednorodne", "Ręczne");
        boundaryBox = createComboBox("Periodyczne", "Absorbujące");
        modeBox = createComboBox("Dodawanie", "Usuwanie");

        grainsToRemoveField = createField("Ile ziaren usunąć");
        removeRandomGrainsButton = createButton("Usuń losowe ziarna");
        densityTargetField = createField("Max zagęszczenie (%)");
        ktField = createField("Wartość kt");
        depthField = createField("Głębokość (Z)");

        startButton = createButton("Start symulacji");
        growButton  = createButton("Iteracja");
        mcStepButton = createButton("MC Iteracja");
        exportButton = createButton("Eksportuj do OVITO");

        getChildren().addAll(
                numOfCells,
                neighbourhoodField,
                seedingModeBox,
                boundaryBox,
                modeBox,
                grainsToRemoveField,
                removeRandomGrainsButton,
                densityTargetField,
                ktField,
                depthField,
                startButton,
                growButton,
                mcStepButton,
                exportButton
        );
    }

    private TextField createField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        return tf;
    }

    private ComboBox<String> createComboBox(String... options) {
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll(options);
        cb.getSelectionModel().selectFirst();
        return cb;
    }

    private Button createButton(String label) {
        Button btn = new Button(label);
        return btn;
    }

    public TextField getNumOfCellsField()             { return numOfCells; }
    public ComboBox<String> getNeighbourhoodField()   { return neighbourhoodField; }
    public ComboBox<String> getSeedingModeBox()       { return seedingModeBox; }
    public ComboBox<String> getBoundaryBox()          { return boundaryBox; }
    public ComboBox<String> getModeBox()              { return modeBox; }
    public TextField getGrainsToRemoveField()         { return grainsToRemoveField; }
    public Button getRemoveRandomGrainsButton()       { return removeRandomGrainsButton; }
    public TextField getDensityTargetField()          { return densityTargetField; }
    public Button getStartButton()                    { return startButton; }
    public Button getGrowButton()                     { return growButton; }
    public Button getMcStepButton()                   { return mcStepButton; }
    public Button getExportButton()                   { return exportButton; }
    public TextField getKtField()                     { return ktField; }
    public TextField getDepthField()                  { return depthField; }
}
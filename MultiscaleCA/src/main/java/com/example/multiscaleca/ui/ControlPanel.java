package com.example.multiscaleca.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    private final TextField numOfCellsField;
    private final ComboBox<String> neighbourhoodField;
    private final ComboBox<String> seedingModeBox;
    private final ComboBox<String> boundaryBox;
    private final ComboBox<String> modeBox;
    private final TextField grainsToRemoveField;
    private final Button removeRandomGrainsButton;
    private final TextField densityTargetField;
    private final TextField ktField;

    private final Slider depthSlider;
    private final Label depthLabel;

    private final Button startButton;
    private final Button growButton;
    private final Button mcStepButton;
    private final Button exportButton;
    private final Button view3DButton;

    public ControlPanel() {
        setSpacing(14);
        setStyle("-fx-background-color: linear-gradient(to bottom, #1f1f1f, #2c2c2c); " +
                "-fx-padding: 20; -fx-border-radius: 16; -fx-background-radius: 16; " +
                "-fx-border-color: #555; -fx-border-width: 2; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,255,255,0.2), 10, 0.3, 0, 4);");

        numOfCellsField = createField("Liczba zarodków");
        neighbourhoodField = createComboBox("Von Neumann", "Pentagonalne losowe", "Moore", "Heksagonalne losowe");
        seedingModeBox = createComboBox("Losowe", "Jednorodne", "Ręczne");
        boundaryBox = createComboBox("Periodyczne", "Absorbujące");
        modeBox = createComboBox("Dodawanie", "Usuwanie");

        grainsToRemoveField = createField("Ile ziaren usunąć");
        removeRandomGrainsButton = createButton("Usuń losowe ziarna");
        densityTargetField = createField("Max zagęszczenie (%)");
        ktField = createField("Wartość kt");

        // Zmieniony suwak - wybór warstwy Z dla 3D
        depthSlider = new Slider(1, 1, 1);
        depthSlider.setMajorTickUnit(1);
        depthSlider.setMinorTickCount(0);
        depthSlider.setSnapToTicks(true);
        depthSlider.setShowTickLabels(true);
        depthSlider.setShowTickMarks(true);
        depthLabel = new Label("Warstwa Z: 1");
        depthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int z = newVal.intValue();
            depthLabel.setText("Warstwa Z: " + z);
        });

        startButton = createButton("Start symulacji");
        growButton = createButton("Iteracja");
        mcStepButton = createButton("MC Iteracja");
        exportButton = createButton("Eksportuj do OVITO");
        view3DButton = createButton("Pokaż 3D");

        getChildren().addAll(
                numOfCellsField,
                neighbourhoodField,
                seedingModeBox,
                boundaryBox,
                modeBox,
                grainsToRemoveField,
                removeRandomGrainsButton,
                densityTargetField,
                ktField,
                depthLabel,
                depthSlider,
                startButton,
                growButton,
                mcStepButton,
                exportButton,
                view3DButton
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
        return new Button(label);
    }

    public TextField getNumOfCellsField() {
        return numOfCellsField;
    }

    public ComboBox<String> getNeighbourhoodField() {
        return neighbourhoodField;
    }

    public ComboBox<String> getSeedingModeBox() {
        return seedingModeBox;
    }

    public ComboBox<String> getBoundaryBox() {
        return boundaryBox;
    }

    public ComboBox<String> getModeBox() {
        return modeBox;
    }

    public TextField getGrainsToRemoveField() {
        return grainsToRemoveField;
    }

    public Button getRemoveRandomGrainsButton() {
        return removeRandomGrainsButton;
    }

    public TextField getDensityTargetField() {
        return densityTargetField;
    }

    public TextField getKtField() {
        return ktField;
    }

    public Slider getDepthSlider() {
        return depthSlider;
    }

    public int getSelectedSlice() {
        return (int) depthSlider.getValue() - 1;
    }

    public void setMaxSlice(int max) {
        depthSlider.setMax(max);
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getGrowButton() {
        return growButton;
    }

    public Button getMcStepButton() {
        return mcStepButton;
    }

    public Button getExportButton() {
        return exportButton;
    }

    public Button getView3DButton() {
        return view3DButton;
    }
}
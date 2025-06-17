

// --- StatsPanel3D.java ---
package com.example.multiscaleca.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.example.multiscaleca.model.GrainStats3D;

public class StatsPanel3D extends HBox {
    private final Label countLabel = new Label("Liczba ziaren: ");
    private final Label avgLabel   = new Label("Średni rozmiar: ");
    private final Label minLabel   = new Label("Min: ");
    private final Label maxLabel   = new Label("Max: ");
    private final Label densLabel  = new Label("Zagęszczenie: ");

    public StatsPanel3D() {
        setSpacing(20);
        getChildren().addAll(countLabel, avgLabel, minLabel, maxLabel, densLabel);
    }

    public void update(GrainStats3D.Stats stats) {
        countLabel.setText("Liczba ziaren: " + stats.count);
        avgLabel.setText("Średni rozmiar: " + String.format("%.2f", stats.average));
        minLabel.setText("Min: " + stats.min);
        maxLabel.setText("Max: " + stats.max);
        densLabel.setText("Zagęszczenie: " + String.format("%.2f", stats.density) + "%");
    }
}
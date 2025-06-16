package com.example.multiscaleca.ui;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.example.multiscaleca.model.GrainStats;

public class StatsPanel extends HBox {
    private Label grainCountLabel = new Label("Liczba ziaren: ");
    private Label avgSizeLabel = new Label("Średni rozmiar: ");
    private Label minSizeLabel = new Label("Min rozmiar: ");
    private Label maxSizeLabel = new Label("Max rozmiar: ");
    private Label densityLabel = new Label("Zagęszczenie: ");

    public StatsPanel() {
        setSpacing(30);
        getStyleClass().add("stats-pane");
        getChildren().addAll(
                grainCountLabel, avgSizeLabel,
                minSizeLabel, maxSizeLabel, densityLabel
        );
    }

    public void update(GrainStats.Stats stats) {
        grainCountLabel.setText("Liczba ziaren: " + stats.count);
        avgSizeLabel.setText("Średni rozmiar: " + String.format("%.2f", stats.average));
        minSizeLabel.setText("Min rozmiar: " + stats.min);
        maxSizeLabel.setText("Max rozmiar: " + stats.max);
        densityLabel.setText("Zagęszczenie: " + String.format("%.2f", stats.density) + "%");
    }
}

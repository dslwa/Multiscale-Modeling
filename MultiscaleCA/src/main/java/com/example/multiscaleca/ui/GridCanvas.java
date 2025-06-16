package com.example.multiscaleca.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.example.multiscaleca.model.Grid;

public class GridCanvas extends Canvas {
    private final int cellSize;
    public GridCanvas(int width, int height, int cellSize) {
        super(width * cellSize, height * cellSize);
        this.cellSize = cellSize;
    }
    public void render(Grid grid) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        for (int y=0; y<grid.getHeight(); y++) {
            for (int x=0; x<grid.getWidth(); x++) {
                gc.setFill(grid.getCell(x,y).getColor());
                gc.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
            }
        }
    }
}
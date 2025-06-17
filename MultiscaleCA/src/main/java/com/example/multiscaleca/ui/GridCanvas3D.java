package com.example.multiscaleca.ui;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridCanvas3D extends Canvas {
    private final int width, height, depth, cellSize;
    private int currentZ = 0;

    public GridCanvas3D(int width, int height, int depth, int cellSize) {
        super(width * cellSize, height * cellSize);
        this.width    = width;
        this.height   = height;
        this.depth    = depth;
        this.cellSize = cellSize;
    }

    public void setCurrentZ(int z) {
        currentZ = Math.max(0, Math.min(z, depth - 1));
    }

    public void render(Grid3D grid) {
        render(grid, currentZ);
    }

    public void render(Grid3D grid, int zSlice) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell c = grid.getCell(x, y, zSlice);
                if (c != null && c.getGrainId() != 0) {
                    var col = c.getColor();
                    gc.setFill(Color.color(col.getRed(), col.getGreen(), col.getBlue()));
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }
}

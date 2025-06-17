// --- CanvasClickHandler3D.java ---
package com.example.multiscaleca.ui.event;

import com.example.multiscaleca.model.GrainStats3D;
import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.ui.GridCanvas3D;
import com.example.multiscaleca.ui.ControlPanel;
import com.example.multiscaleca.ui.StatsPanel3D;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasClickHandler3D implements EventHandler<MouseEvent> {
    private final Grid3D grid;
    private final GridCanvas3D canvas;
    private final ControlPanel cp;
    private final StatsPanel3D sp;
    private final int cellSize;
    private int currentZ = 0;

    public CanvasClickHandler3D(Grid3D grid, GridCanvas3D canvas, ControlPanel cp, StatsPanel3D sp, int cellSize) {
        this.grid = grid;
        this.canvas = canvas;
        this.cp = cp;
        this.sp = sp;
        this.cellSize = cellSize;
    }

    @Override
    public void handle(MouseEvent e) {

        int x = (int)(e.getX() / cellSize);
        int y = (int)(e.getY() / cellSize);

        if (x<0 || x>=grid.getWidth() || y<0 || y>=grid.getHeight()) return;
        String mode = cp.getModeBox().getValue();
        if ("Dodawanie".equals(mode) && cp.getSeedingModeBox().getValue().equals("Ręczne")) {
            Cell c = grid.getCell(x, y, currentZ);
            if (c.isEmpty()) {
                int id = grid.getNextAvailableGrainId();
                c.setGrainId(id);
                c.setColor(Color.color(Math.random(), Math.random(), Math.random()));
            }
        } else if ("Usuwanie".equals(mode)) {
            Cell c = grid.getCell(x, y, currentZ);
            int id = c.getGrainId();
            if (id!=0) {
                for (int z=0; z<grid.getDepth(); z++) {
                    for (int yy=0; yy<grid.getHeight(); yy++) {
                        for (int xx=0; xx<grid.getWidth(); xx++) {
                            Cell cc = grid.getCell(xx, yy, z);
                            if (cc.getGrainId()==id) {
                                cc.setGrainId(0);
                                cc.setColor(Color.WHITE);
                            }
                        }
                    }
                }
            }
        }
        canvas.render(grid);
        sp.update(GrainStats3D.calculate(grid.getGrid()));
    }
}

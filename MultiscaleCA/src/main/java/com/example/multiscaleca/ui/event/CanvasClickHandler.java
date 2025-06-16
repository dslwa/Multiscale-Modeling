package com.example.multiscaleca.ui.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import com.example.multiscaleca.model.Grid;
import com.example.multiscaleca.ui.GridCanvas;
import com.example.multiscaleca.ui.ControlPanel;
import com.example.multiscaleca.ui.StatsPanel;
import com.example.multiscaleca.model.GrainStats;
import javafx.scene.paint.Color;

public class CanvasClickHandler implements EventHandler<MouseEvent> {
    private Grid grid;
    private GridCanvas canvas;
    private ControlPanel cp;
    private StatsPanel sp;
    private int cellSize;
    public CanvasClickHandler(Grid grid, GridCanvas canvas, ControlPanel cp, StatsPanel sp, int cellSize) {
        this.grid = grid; this.canvas = canvas; this.cp = cp; this.sp = sp; this.cellSize = cellSize;
    }
    @Override public void handle(MouseEvent e) {
        int x = (int)(e.getX()/cellSize);
        int y = (int)(e.getY()/cellSize);
        if (x<0||x>=grid.getWidth()||y<0||y>=grid.getHeight()) return;
        String mode = cp.getModeBox().getValue();
        String seedMode = cp.getSeedingModeBox().getValue();
        if ("Dodawanie".equals(mode) && "Ręczne".equals(seedMode)) {
            if (grid.getCell(x,y).isEmpty()) {
                int id = grid.getNextAvailableGrainId();
                grid.getCell(x,y).setGrainId(id);
                grid.getCell(x,y).setColor(Color.color(Math.random(),Math.random(),Math.random()));
            }
        } else if ("Usuwanie".equals(mode)) {
            int clickedId = grid.getCell(x,y).getGrainId();
            if (clickedId!=0) {
                for (int i=0;i<grid.getHeight();i++)
                    for (int j=0;j<grid.getWidth();j++)
                        if (grid.getCell(j,i).getGrainId()==clickedId) {
                            grid.getCell(j,i).setGrainId(0);
                            grid.getCell(j,i).setColor(Color.WHITE);
                        }
            }
        }
        canvas.render(grid);
        sp.update(GrainStats.calculate(grid.getGrid()));
    }
}

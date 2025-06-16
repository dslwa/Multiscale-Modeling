package com.example.multiscaleca.neighborhood;
import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid;

import java.util.ArrayList;
import java.util.List;

public class MooreStrategy implements NeighborhoodStrategy {
    private static final int[][] OFFSETS = {
            {-1,-1},{0,-1},{1,-1},
            {-1, 0},       {1, 0},
            {-1, 1},{0, 1},{1, 1}
    };

    @Override
    public List<Cell> getNeighbors(Grid grid, int x, int y) {

        List<Cell> out = new ArrayList<>();

        for (int[] o : OFFSETS) {
            Cell c = grid.getCell(x + o[0], y + o[1]);
            if (c != null && !c.isEmpty()) {
                out.add(c);
            }
        }
        return out;
    }
}
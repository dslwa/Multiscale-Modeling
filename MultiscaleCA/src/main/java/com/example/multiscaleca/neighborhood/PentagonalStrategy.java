package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Grid;
import com.example.multiscaleca.model.Cell;
import java.util.*;

public class PentagonalStrategy implements NeighborhoodStrategy {
    private final Random random = new Random();
    private static final int[][][] VARIANTS = {
            {{0,-1},{-1,0},{1,0},{0,1}},
            {{-1,-1},{0,-1},{1,-1},{0,1}},
            {{-1,1},{0,1},{1,1},{0,-1}},
            {{-1,0},{0,-1},{0,1},{-1,1}},
            {{1,0},{0,-1},{0,1},{1,-1}}
    };
    @Override
    public List<Cell> getNeighbors(Grid grid, int x, int y) {
        List<Cell> out = new ArrayList<>();
        int[][] chosen = VARIANTS[random.nextInt(VARIANTS.length)];
        for (int[] o : chosen) {
            Cell c = grid.getCell(x+o[0], y+o[1]);
            if (c!=null && !c.isEmpty()) out.add(c);
        }
        return out;
    }
}

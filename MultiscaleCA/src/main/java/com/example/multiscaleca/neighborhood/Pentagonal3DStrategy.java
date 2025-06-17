package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pentagonal3DStrategy implements Neighborhood3DStrategy {
    private static final Random rnd = new Random();
    private static final int[][][] VARIANTS = {
            // płaszczyzna XY bez sąsiadów w +Z i -Z
            {{-1,-1, 0},{0,-1, 0},{1,-1, 0},{-1, 0, 0},{1, 0, 0},{-1, 1, 0},{0, 1, 0},{1, 1, 0},
                    {-1,-1, 1},{0,-1, 1},{1,-1, 1},{-1, 0, 1}},
            // płaszczyzna XY inne kierunki
            {{-1, 0, 0},{0, 0, 0},{1, 0, 0},{-1, 1, 0},{0, 1, 0},{1, 1, 0},{-1, 0, 1},{0, 0, 1},{1, 0, 1},{0, 1, 1},{-1, 1, 1},{1, 1, 1}},
            // płaszczyzna YZ
            {{0,-1,-1},{0,0,-1},{0,1,-1},{0,-1,0},{0,1,0},{0,-1,1},{0,0,1},{0,1,1},
                    {1,-1,-1},{1,0,-1},{1,-1,0},{1,0,0}},
            // płaszczyzna YZ inna
            {{-1,-1,0},{-1,0,0},{-1,1,0},{0,-1,0},{0,1,0},{1,-1,0},{1,0,0},{1,1,0},
                    {0,-1,-1},{0,0,-1},{0,1,-1},{0,-1,1}},
            // płaszczyzna XZ
            {{-1,0,-1},{0,0,-1},{1,0,-1},{-1,0,0},{1,0,0},{-1,1,-1},{0,1,-1},{1,1,-1},
                    {-1,1,0},{0,1,0},{1,1,0},{-1,1,1}}
    };

    @Override
    public List<Cell> getNeighbors(Grid3D grid, int x, int y, int z) {
        int idx = rnd.nextInt(VARIANTS.length);
        int[][] variant = VARIANTS[idx];
        List<Cell> neighbors = new ArrayList<>();
        for (int[] off : variant) {
            Cell c = grid.getCell(x + off[0], y + off[1], z + off[2]);
            if (c != null && !c.isEmpty()) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }
}

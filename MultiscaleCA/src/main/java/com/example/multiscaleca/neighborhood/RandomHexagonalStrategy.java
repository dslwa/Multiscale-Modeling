package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomHexagonalStrategy implements NeighborhoodStrategy {
    private final Random rnd = new Random();
    private static final int[][] FLAT_TOP = {
            { -1,  0}, {1,  0},   // lewe i prawe
            { 0, -1}, { 1, -1},  // górne dwa
            { 0,  1}, { 1,  1}   // dolne dwa
    };
    private static final int[][] FLAT_SIDE = {
            { 0, -1}, { 0,  1},  // góra i dół
            {-1,  0}, {-1,  1},  // lewy dwa
            { 1,  0}, { 1, -1}   // prawy dwa
    };

    @Override
    public List<Cell> getNeighbors(Grid grid, int x, int y) {
        List<Cell> out = new ArrayList<>();
        int[][] variant = rnd.nextBoolean() ? FLAT_TOP : FLAT_SIDE;
        for (int[] o : variant) {
            Cell c = grid.getCell(x + o[0], y + o[1]);
            if (c != null && !c.isEmpty()) {
                out.add(c);
            }
        }
        return out;
    }
}
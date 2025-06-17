package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomHexagonal3DStrategy implements Neighborhood3DStrategy {
    private static final Random rnd = new Random();

    private static final int[][] XY_FLAT_TOP = {
            {-1,  0,  0}, {1,  0,  0},
            { 0, -1,  0}, {1, -1,  0},
            { 0,  1,  0}, {1,  1,  0}
    };
    private static final int[][] XY_FLAT_SIDE = {
            { 0, -1,  0}, {0,  1,  0},
            {-1, 0,   0}, {-1, 1,   0},
            { 1, 0,   0}, { 1,-1,   0}
    };
    private static final int[][] XZ_FLAT_TOP = {
            {-1,  0,  0}, {1,  0,  0},
            { 0,  0, -1}, {1,  0, -1},
            { 0,  0,  1}, {1,  0,  1}
    };
    private static final int[][] XZ_FLAT_SIDE = {
            { 0,  0, -1}, {0,  0,  1},
            {-1, 0,  0}, {-1,0,  1},
            { 1, 0,  0}, {1, 0, -1}
    };
    private static final int[][] YZ_FLAT_TOP = {
            {0, -1,  0}, {0,  1,  0},
            {0,  0, -1}, {0,  1, -1},
            {0,  0,  1}, {0,  1,  1}
    };
    private static final int[][] YZ_FLAT_SIDE = {
            {0,  0, -1}, {0,  0,  1},
            {0, -1,  0}, {0, -1,  1},
            {0,  1,  0}, {0,  1, -1}
    };

    @Override
    public List<Cell> getNeighbors(Grid3D grid, int x, int y, int z) {
        int plane = rnd.nextInt(3);
        boolean flatTop = rnd.nextBoolean();
        int[][] offsets;
        switch (plane) {
            case 0 -> offsets = flatTop ? XY_FLAT_TOP : XY_FLAT_SIDE;
            case 1 -> offsets = flatTop ? XZ_FLAT_TOP : XZ_FLAT_SIDE;
            default -> offsets = flatTop ? YZ_FLAT_TOP : YZ_FLAT_SIDE;
        }
        List<Cell> neighbors = new ArrayList<>();
        for (int[] o : offsets) {
            Cell c = grid.getCell(x + o[0], y + o[1], z + o[2]);
            if (c != null && !c.isEmpty()) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }
}


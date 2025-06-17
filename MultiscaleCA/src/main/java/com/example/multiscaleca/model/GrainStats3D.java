package com.example.multiscaleca.model;

import java.util.HashMap;
import java.util.Map;

public class GrainStats3D {
    public static Stats calculate(Cell[][][] grid3d) {
        Stats stats = new Stats();
        int filled = 0;
        Map<Integer,Integer> sizes = new HashMap<>();

        for (Cell[][] slice : grid3d) {
            for (Cell[] row : slice) {
                for (Cell c : row) {
                    if (c.getGrainId() != 0) {
                        sizes.merge(c.getGrainId(), 1, Integer::sum);
                        filled++;
                    }
                }
            }
        }

        stats.count   = sizes.size();
        int sum       = sizes.values().stream().mapToInt(i -> i).sum();
        stats.average = stats.count > 0 ? (double) sum / stats.count : 0;
        stats.min     = sizes.values().stream().mapToInt(i -> i).min().orElse(0);
        stats.max     = sizes.values().stream().mapToInt(i -> i).max().orElse(0);

        int totalVoxels = grid3d.length * grid3d[0].length * grid3d[0][0].length;
        stats.density = totalVoxels > 0
                ? 100.0 * filled / totalVoxels
                : 0;

        return stats;
    }

    public static class Stats {
        public int count;
        public double average;
        public int min;
        public int max;
        public double density;
    }
}

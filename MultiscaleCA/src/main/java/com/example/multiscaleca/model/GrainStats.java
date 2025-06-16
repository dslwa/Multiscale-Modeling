package com.example.multiscaleca.model;

public class GrainStats {
    public static Stats calculate(Cell[][] grid) {
        Stats stats = new Stats();
        int filled = 0;
        java.util.Map<Integer,Integer> sizes = new java.util.HashMap<>();
        for (Cell[] row : grid) {
            for (Cell c : row) {
                if (c.getGrainId() != 0) {
                    sizes.merge(c.getGrainId(), 1, Integer::sum);
                    filled++;
                }
            }
        }
        stats.count = sizes.size();
        int sum = sizes.values().stream().mapToInt(i->i).sum();
        stats.average = stats.count>0 ? (double)sum / stats.count : 0;
        stats.min = sizes.values().stream().mapToInt(i->i).min().orElse(0);
        stats.max = sizes.values().stream().mapToInt(i->i).max().orElse(0);
        int total = grid.length * grid[0].length;
        stats.density = total>0 ? 100.0 * filled / total : 0;
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
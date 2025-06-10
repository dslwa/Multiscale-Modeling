package com.example.multiscaleca;

import javafx.scene.paint.Color;
import java.util.*;

public class Grid {
    public enum BoundaryType { ABSORBING, PERIODIC }

    private final Cell[][] grid;
    private final int width;
    private final int height;
    private final Random random = new Random();
    private BoundaryType boundaryType = BoundaryType.ABSORBING;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        initialize();
    }

    public Grid(int width, int height, long randomSeed) {
        this(width, height);
        this.random.setSeed(randomSeed);
    }

    private void initialize() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell(x, y);
            }
        }
    }

    public void setBoundaryType(BoundaryType type) {
        this.boundaryType = type;
    }

    public Cell getCell(int x, int y) {
        if (boundaryType == BoundaryType.PERIODIC) {
            int px = (x + width) % width;
            int py = (y + height) % height;
            return grid[py][px];
        }
        // ABSORBING: poza granicami zwracamy null
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[y][x];
        }
        return null;
    }

    public void seedRandomGrains(int count) {
        for (int i = 1; i <= count; i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (!grid[y][x].isEmpty());

            grid[y][x].setGrainId(i);
            double r = 0.3 + 0.6 * random.nextDouble();
            double g = 0.3 + 0.6 * random.nextDouble();
            double b = 0.3 + 0.6 * random.nextDouble();
            grid[y][x].setColor(Color.color(r, g, b));
        }
    }

    public void reset() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].setGrainId(0);
                grid[y][x].setColor(Color.WHITE);
            }
        }
    }

    public void growOneIteration(String neighborhoodType) {
        Cell[][] newCells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell current = grid[y][x];
                if (!current.isEmpty()) {
                    newCells[y][x] = new Cell(x, y);
                    newCells[y][x].setGrainId(current.getGrainId());
                    newCells[y][x].setColor(current.getColor());
                    continue;
                }

                List<Cell> neighbors;
                if ("Von Neumann".equals(neighborhoodType)) {
                    neighbors = getVonNeumannNeighbors(x, y);
                } else if ("Pentagonalne losowe".equals(neighborhoodType)) {
                    neighbors = getPentagonalRandomNeighbors(x, y);
                } else {
                    neighbors = getVonNeumannNeighbors(x, y);
                }

                if (!neighbors.isEmpty()) {
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (Cell n : neighbors) {
                        freq.put(n.getGrainId(), freq.getOrDefault(n.getGrainId(), 0) + 1);
                    }
                    int mostCommonId = Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();
                    Cell sample = neighbors.stream()
                            .filter(n -> n.getGrainId() == mostCommonId)
                            .findFirst()
                            .orElse(null);

                    newCells[y][x] = new Cell(x, y);
                    newCells[y][x].setGrainId(mostCommonId);
                    if (sample != null) {
                        newCells[y][x].setColor(sample.getColor());
                    }
                } else {
                    newCells[y][x] = new Cell(x, y);
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = newCells[y][x];
            }
        }
    }

    public List<Cell> getVonNeumannNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] offsets = {{0,-1},{-1,0},{1,0},{0,1}};
        for (int[] o : offsets) {
            Cell n = getCell(x + o[0], y + o[1]);
            if (n != null && !n.isEmpty()) neighbors.add(n);
        }
        return neighbors;
    }

    public List<Cell> getPentagonalRandomNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        int[][][] variants = {
                {{0,-1},{-1,0},{1,0},{0,1}},
                {{-1,-1},{0,-1},{1,-1},{0,1}},
                {{-1,1},{0,1},{1,1},{0,-1}},
                {{-1,0},{0,-1},{0,1},{-1,1}},
                {{1,0},{0,-1},{0,1},{1,-1}}
        };
        int[][] chosen = variants[random.nextInt(variants.length)];
        for (int[] o : chosen) {
            Cell n = getCell(x + o[0], y + o[1]);
            if (n != null && !n.isEmpty()) neighbors.add(n);
        }
        return neighbors;
    }

    public void seedUniformGrains(int count) {
        int sqrt = (int) Math.sqrt(count);
        if (sqrt*sqrt < count) sqrt++;
        int spacingX = width/sqrt;
        int spacingY = height/sqrt;
        int id = 1;
        for (int y = spacingY/2; y < height && id <= count; y += spacingY) {
            for (int x = spacingX/2; x < width && id <= count; x += spacingX) {
                if (grid[y][x].isEmpty()) {
                    grid[y][x].setGrainId(id++);
                    double r = 0.3+0.6*random.nextDouble();
                    double g = 0.3+0.6*random.nextDouble();
                    double b = 0.3+0.6*random.nextDouble();
                    grid[y][x].setColor(Color.color(r,g,b));
                }
            }
        }
    }

    public Cell[][] getGrid() { return grid; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

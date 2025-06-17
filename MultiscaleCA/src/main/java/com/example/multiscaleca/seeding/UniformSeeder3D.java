package com.example.multiscaleca.seeding;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import javafx.scene.paint.Color;

public class UniformSeeder3D implements GrainSeeder3D {
    @Override
    public void seed(Grid3D grid, int count) {
        int W = grid.getWidth();
        int H = grid.getHeight();
        int D = grid.getDepth();
        int sqrt = (int)Math.cbrt(count);
        if (sqrt * sqrt * sqrt < count) sqrt++;
        int stepX = W / sqrt;
        int stepY = H / sqrt;
        int stepZ = D / sqrt;
        int id = 1;
        for (int z = stepZ/2; z < D && id <= count; z += stepZ) {
            for (int y = stepY/2; y < H && id <= count; y += stepY) {
                for (int x = stepX/2; x < W && id <= count; x += stepX) {
                    Cell c = grid.getCell(x,y,z);
                    if (c.isEmpty()) {
                        c.setGrainId(id++);
                        double r = Math.random();
                        double g = Math.random();
                        double b = Math.random();
                        c.setColor(Color.color(r, g, b));
                    }
                }
            }
        }
    }
}

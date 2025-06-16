package com.example.multiscaleca.seeding;

import com.example.multiscaleca.model.Grid;
import javafx.scene.paint.Color;
import java.util.Random;

public class UniformSeeder implements GrainSeeder {
    private final Random random = new Random();
    @Override
    public void seed(Grid grid, int count) {
        int sqrt = (int)Math.sqrt(count); if (sqrt*sqrt < count) sqrt++;
        int spacingX = grid.getWidth() / sqrt;
        int spacingY = grid.getHeight() / sqrt;
        int id = 1;
        for (int y = spacingY/2; y < grid.getHeight() && id <= count; y += spacingY) {
            for (int x = spacingX/2; x < grid.getWidth() && id <= count; x += spacingX) {
                if (grid.getCell(x,y).isEmpty()) {
                    grid.getCell(x,y).setGrainId(id++);
                    double r = 0.3 + 0.6 * random.nextDouble();
                    double g = 0.3 + 0.6 * random.nextDouble();
                    double b = 0.3 + 0.6 * random.nextDouble();
                    grid.getCell(x,y).setColor(Color.color(r,g,b));
                }
            }
        }
    }
}
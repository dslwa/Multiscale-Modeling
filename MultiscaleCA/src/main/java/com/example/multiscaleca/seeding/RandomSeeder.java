package com.example.multiscaleca.seeding;

import com.example.multiscaleca.model.Grid;
import javafx.scene.paint.Color;
import java.util.Random;

public class RandomSeeder implements GrainSeeder {
    private final Random random = new Random();
    @Override
    public void seed(Grid grid, int count) {
        for (int i = 1; i <= count; i++) {
            int x, y;
            do {
                x = random.nextInt(grid.getWidth());
                y = random.nextInt(grid.getHeight());
            } while (!grid.getCell(x,y).isEmpty());
            grid.getCell(x,y).setGrainId(i);
            double r = 0.3 + 0.6 * random.nextDouble();
            double g = 0.3 + 0.6 * random.nextDouble();
            double b = 0.3 + 0.6 * random.nextDouble();
            grid.getCell(x,y).setColor(Color.color(r,g,b));
        }
    }
}
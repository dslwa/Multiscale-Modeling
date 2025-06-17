package com.example.multiscaleca.seeding;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import javafx.scene.paint.Color;
import java.util.Random;

public class RandomSeeder3D implements GrainSeeder3D {
    private final Random rnd = new Random();

    @Override
    public void seed(Grid3D grid, int count) {
        int W = grid.getWidth(), H = grid.getHeight(), D = grid.getDepth();
        for (int id = 1; id <= count; id++) {
            int x, y, z;
            do {
                x = rnd.nextInt(W);
                y = rnd.nextInt(H);
                z = rnd.nextInt(D);
            } while (!grid.getCell(x,y,z).isEmpty());
            Cell c = grid.getCell(x,y,z);
            c.setGrainId(id);
            c.setColor(Color.color(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()));
        }
    }
}

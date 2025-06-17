package com.example.multiscaleca.ui;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Exporter3D {

    public static void export(Grid3D grid, int cellSize) {
        String filename = "output3d.xyz";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            int count = 0;
            for (int z = 0; z < grid.getDepth(); z++)
                for (int y = 0; y < grid.getHeight(); y++)
                    for (int x = 0; x < grid.getWidth(); x++)
                        if (!grid.getCell(x,y,z).isEmpty()) count++;

            writer.write(count + "\n");
            writer.write("Properties=species:I:1:pos:R:3:color:R:3:radius:R:1\n");

            double radius = 0.5 * cellSize;

            for (int z = 0; z < grid.getDepth(); z++) {
                for (int y = 0; y < grid.getHeight(); y++) {
                    for (int x = 0; x < grid.getWidth(); x++) {
                        Cell c = grid.getCell(x,y,z);
                        if (!c.isEmpty()) {
                            Color col = c.getColor();
                            double px = x * cellSize;
                            double py = y * cellSize;
                            double pz = z * cellSize;
                            writer.write(String.format(Locale.US,
                                    "%d %.3f %.3f %.3f %.3f %.3f %.3f %.3f\n",
                                    c.getGrainId(),
                                    px, py, pz,
                                    col.getRed(), col.getGreen(), col.getBlue(),
                                    radius
                            ));
                        }
                    }
                }
            }

            System.out.println("Eksport zapisany do " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

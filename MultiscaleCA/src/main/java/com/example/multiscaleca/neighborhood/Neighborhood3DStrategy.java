package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid3D;
import java.util.List;

public interface Neighborhood3DStrategy {
    List<Cell> getNeighbors(Grid3D grid, int x, int y, int z);
}
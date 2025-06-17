package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Grid;
import com.example.multiscaleca.model.Cell;
import java.util.List;

public interface NeighborhoodStrategy {
    List<Cell> getNeighbors(Grid grid, int x, int y);
}
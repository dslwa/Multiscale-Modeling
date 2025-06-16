package com.example.multiscaleca.seeding;
import com.example.multiscaleca.model.Grid;

public interface GrainSeeder {
    void seed(Grid grid, int count);
}
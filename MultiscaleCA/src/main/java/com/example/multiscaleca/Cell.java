package com.example.multiscaleca;

import javafx.scene.paint.Color;

public class Cell {
    private final int x;
    private final int y;
    private int grainId;
    private Color color;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.grainId = 0;
        this.color = Color.WHITE;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getGrainId(){ return grainId; }
    public void setGrainId(int grainId){ this.grainId = grainId; }
    public Color getColor(){ return color; }
    public void setColor(Color color){ this.color = color; }
    public boolean isEmpty(){ return grainId == 0; }
}

package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid3D;
import java.util.*;

public class Moore3DStrategy implements Neighborhood3DStrategy {
    private static final List<int[]> OFFSETS;
    static {
        OFFSETS = new ArrayList<>();
        for(int dz=-1; dz<=1; dz++)
            for(int dy=-1; dy<=1; dy++)
                for(int dx=-1; dx<=1; dx++)
                    if (!(dx==0&&dy==0&&dz==0)) OFFSETS.add(new int[]{dx,dy,dz});
    }

    @Override
    public List<Cell> getNeighbors(Grid3D grid, int x, int y, int z) {
        List<Cell> out = new ArrayList<>();
        for (int[] o: OFFSETS) {
            Cell c = grid.getCell(x+o[0], y+o[1], z+o[2]);
            if (c!=null && !c.isEmpty()) out.add(c);
        }
        return out;
    }
}

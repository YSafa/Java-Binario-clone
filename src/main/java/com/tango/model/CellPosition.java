package com.tango.model;

public record CellPosition(int row, int col) {

    public CellPosition {
        if (row < 0 || row > 5 || col < 0 || col > 5) {
            throw new IllegalArgumentException(
                    "Pozisyon 6x6 board dışında: row=" + row + ", col=" + col
            );
        }
    }
}
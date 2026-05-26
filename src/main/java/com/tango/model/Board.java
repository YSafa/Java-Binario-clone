package com.tango.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int SIZE = 6;
    private final Cell[][] cells;
    private final List<Constraint> constraints;

    public Board() {
        this.cells = new Cell[SIZE][SIZE];
        this.constraints = new ArrayList<>();
        initializeEmpty();
    }

    private void initializeEmpty() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = Cell.empty();
            }
        }
    }

    public Symbol getSymbol(CellPosition position) {
        return cells[position.row()][position.col()].getSymbol();
    }

    public Cell getCell(CellPosition position) {
        return cells[position.row()][position.col()];
    }

    public void setSymbol(CellPosition position, Symbol symbol) {
        cells[position.row()][position.col()].setSymbol(symbol);
    }

    public void placeLockedCell(CellPosition position, Symbol symbol) {
        cells[position.row()][position.col()] = Cell.locked(symbol);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public List<Constraint> getConstraints() {
        return List.copyOf(constraints);
    }

    public int getSize() {
        return SIZE;
    }
}
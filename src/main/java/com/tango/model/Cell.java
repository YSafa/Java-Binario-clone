package com.tango.model;

public class Cell {

    private Symbol symbol;
    private final boolean locked;

    public Cell(Symbol symbol, boolean locked) {
        this.symbol = symbol;
        this.locked = locked;
    }

    public static Cell empty() {
        return new Cell(Symbol.EMPTY, false);
    }

    public static Cell locked(Symbol symbol) {
        return new Cell(symbol, true);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        if (locked) {
            throw new IllegalStateException("Kilitli hücre değiştirilemez.");
        }
        this.symbol = symbol;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isEmpty() {
        return symbol == Symbol.EMPTY;
    }
}
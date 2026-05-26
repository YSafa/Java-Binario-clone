package com.tango.model;

public class Constraint {

    private final CellPosition first;
    private final CellPosition second;
    private final ConstraintType type;

    public Constraint(CellPosition first, CellPosition second, ConstraintType type) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    public boolean isSatisfied(Board board) {
        Symbol s1 = board.getSymbol(first);
        Symbol s2 = board.getSymbol(second);

        if (s1 == Symbol.EMPTY || s2 == Symbol.EMPTY) {
            return true;
        }

        return switch (type) {
            case SAME -> s1 == s2;
            case DIFFERENT -> s1 != s2;
            case NONE -> true;
        };
    }

    public CellPosition getFirst() { return first; }
    public CellPosition getSecond() { return second; }
    public ConstraintType getType() { return type; }
}
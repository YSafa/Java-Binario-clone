package com.tango.validator;

import com.tango.model.Board;
import com.tango.model.CellPosition;
import com.tango.model.Symbol;

public class BoardValidator {

    private final RowColumnRule rowColumnRule = new RowColumnRule();
    private final ConstraintRule constraintRule = new ConstraintRule();

    public boolean isValid(Board board) {
        return rowColumnRule.isValid(board) &&
                constraintRule.isValid(board);
    }

    public boolean isComplete(Board board) {
        if (!isValid(board)) return false;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getSymbol(new CellPosition(row, col)) == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
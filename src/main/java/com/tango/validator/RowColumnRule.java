package com.tango.validator;

import com.tango.model.Board;
import com.tango.model.CellPosition;
import com.tango.model.Symbol;

public class RowColumnRule {

    public boolean isValid(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            if (!isRowValid(board, i)) return false;
            if (!isColumnValid(board, i)) return false;
        }
        return true;
    }

    private boolean isRowValid(Board board, int row) {
        return isLineValid(board, row, true);
    }

    private boolean isColumnValid(Board board, int col) {
        return isLineValid(board, col, false);
    }

    private boolean isLineValid(Board board, int index, boolean isRow) {
        int starCount = 0;
        int moonCount = 0;
        int consecutiveSame = 1;
        Symbol previous = null;

        for (int i = 0; i < board.getSize(); i++) {
            CellPosition position = isRow
                    ? new CellPosition(index, i)
                    : new CellPosition(i, index);

            Symbol current = board.getSymbol(position);

            if (current == Symbol.EMPTY) {
                previous = null;
                consecutiveSame = 1;
                continue;
            }

            if (current == Symbol.STAR) starCount++;
            if (current == Symbol.MOON) moonCount++;

            if (current == previous) {
                consecutiveSame++;
                if (consecutiveSame > 2) return false;
            } else {
                consecutiveSame = 1;
            }

            previous = current;
        }

        if (starCount > 3 || moonCount > 3) return false;

        return true;
    }
}
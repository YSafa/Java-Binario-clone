package com.tango.validator;

import com.tango.model.Board;
import com.tango.model.Constraint;

public class ConstraintRule {

    public boolean isValid(Board board) {
        for (Constraint constraint : board.getConstraints()) {
            if (!constraint.isSatisfied(board)) {
                return false;
            }
        }
        return true;
    }
}
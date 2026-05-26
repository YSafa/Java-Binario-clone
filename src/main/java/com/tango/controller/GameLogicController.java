package com.tango.controller;

import com.tango.model.Board;
import com.tango.model.CellPosition;
import com.tango.model.Symbol;
import com.tango.validator.BoardValidator;

public class GameLogicController {

    private Board board;
    private final BoardValidator validator;

    public GameLogicController() {
        this.validator = new BoardValidator();
    }

    public void startGame(Board board) {
        this.board = board;
    }

    public MoveResult makeMove(CellPosition position, boolean isRightClick) {
        if (board == null) {
            throw new IllegalStateException("Oyun henüz başlatılmadı.");
        }

        if (board.getCell(position).isLocked()) {
            return MoveResult.LOCKED_CELL;
        }

        Symbol current = board.getSymbol(position);
        Symbol next = getNextSymbol(current, isRightClick);

        board.setSymbol(position, next);

        if (!validator.isValid(board)) {
            board.setSymbol(position, current);
            return MoveResult.INVALID_MOVE;
        }

        if (validator.isComplete(board)) {
            return MoveResult.GAME_COMPLETE;
        }

        return MoveResult.SUCCESS;
    }

    private Symbol getNextSymbol(Symbol current, boolean isRightClick) {
        if (isRightClick) {
            return switch (current) {
                case EMPTY -> Symbol.MOON;
                case MOON  -> Symbol.STAR;
                case STAR  -> Symbol.EMPTY;
            };
        } else {
            return switch (current) {
                case EMPTY -> Symbol.STAR;
                case STAR  -> Symbol.MOON;
                case MOON  -> Symbol.EMPTY;
            };
        }
    }

    public void clearCell(CellPosition position) {
        if (board.getCell(position).isLocked()) return;
        board.setSymbol(position, Symbol.EMPTY);
    }

    public Board getBoard() {
        return board;
    }

    public enum MoveResult {
        SUCCESS,
        INVALID_MOVE,
        LOCKED_CELL,
        GAME_COMPLETE
    }
}
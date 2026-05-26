package com.tango.service;

import com.tango.model.*;

import java.util.ArrayList;
import java.util.List;

public class PuzzleLoader {

    public Board loadPuzzle(int puzzleIndex) {
        List<Board> puzzles = buildPuzzles();

        if (puzzleIndex < 0 || puzzleIndex >= puzzles.size()) {
            throw new IllegalArgumentException("Geçersiz bulmaca indeksi: " + puzzleIndex);
        }

        return puzzles.get(puzzleIndex);
    }

    public int getPuzzleCount() {
        return buildPuzzles().size();
    }

    private List<Board> buildPuzzles() {
        List<Board> puzzles = new ArrayList<>();
        puzzles.add(buildPuzzle1());
        return puzzles;
    }

    private Board buildPuzzle1() {
        Board board = new Board();

        // Kilitli hücreler (ipuçları)
        board.placeLockedCell(new CellPosition(0, 0), Symbol.STAR);
        board.placeLockedCell(new CellPosition(0, 3), Symbol.MOON);
        board.placeLockedCell(new CellPosition(2, 2), Symbol.STAR);
        board.placeLockedCell(new CellPosition(3, 3), Symbol.MOON);
        board.placeLockedCell(new CellPosition(5, 1), Symbol.STAR);
        board.placeLockedCell(new CellPosition(5, 4), Symbol.MOON);

        // Kısıtlar
        board.addConstraint(new Constraint(
                new CellPosition(0, 0),
                new CellPosition(0, 1),
                ConstraintType.DIFFERENT
        ));
        board.addConstraint(new Constraint(
                new CellPosition(1, 2),
                new CellPosition(1, 3),
                ConstraintType.SAME
        ));
        board.addConstraint(new Constraint(
                new CellPosition(3, 3),
                new CellPosition(4, 3),
                ConstraintType.DIFFERENT
        ));

        return board;
    }
}
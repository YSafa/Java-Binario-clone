package com.tango.controller;

import com.tango.model.Board;
import com.tango.model.CellPosition;
import com.tango.model.Constraint;
import com.tango.model.ConstraintType;
import com.tango.model.Symbol;
import com.tango.service.PuzzleLoader;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private GridPane boardGrid;

    private final GameLogicController logicController = new GameLogicController();
    private final PuzzleLoader puzzleLoader = new PuzzleLoader();
    private StackPane[][] cellPanes;

    @FXML
    public void initialize() {
        loadPuzzle(0);
    }

    private void loadPuzzle(int index) {
        Board board = puzzleLoader.loadPuzzle(index);
        logicController.startGame(board);
        cellPanes = new StackPane[board.getSize()][board.getSize()];
        buildBoardUI(board);
    }

    private void buildBoardUI(Board board) {
        boardGrid.getChildren().clear();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                StackPane cellPane = createCellPane(row, col, board);
                cellPanes[row][col] = cellPane;
                boardGrid.add(cellPane, col, row);
            }
        }

        renderConstraints(board);
    }

    private StackPane createCellPane(int row, int col, Board board) {
        StackPane pane = new StackPane();
        pane.getStyleClass().add("cell");

        if (board.getCell(new CellPosition(row, col)).isLocked()) {
            pane.getStyleClass().add("cell-locked");
        }

        updateCellLabel(pane, board.getSymbol(new CellPosition(row, col)));

        final int r = row;
        final int c = col;
        pane.setOnMouseClicked(event -> handleCellClick(event, r, c));

        return pane;
    }

    private void handleCellClick(MouseEvent event, int row, int col) {
        boolean isRightClick = event.getButton() == MouseButton.SECONDARY;
        CellPosition position = new CellPosition(row, col);

        GameLogicController.MoveResult result =
                logicController.makeMove(position, isRightClick);

        switch (result) {
            case SUCCESS, GAME_COMPLETE -> {
                updateCellLabel(
                        cellPanes[row][col],
                        logicController.getBoard().getSymbol(position)
                );
                if (result == GameLogicController.MoveResult.GAME_COMPLETE) {
                    showGameComplete();
                }
            }
            case INVALID_MOVE -> flashInvalid(cellPanes[row][col]);
            case LOCKED_CELL -> { /* sessizce yoksay */ }
        }
    }

    private void updateCellLabel(StackPane pane, Symbol symbol) {
        pane.getChildren().clear();
        String text = switch (symbol) {
            case STAR  -> "⭐";
            case MOON  -> "🌙";
            case EMPTY -> "";
        };
        if (!text.isEmpty()) {
            Label label = new Label(text);
            label.getStyleClass().add("cell-label");
            pane.getChildren().add(label);
        }
    }

    private void renderConstraints(Board board) {
        for (Constraint constraint : board.getConstraints()) {
            if (constraint.getType() == ConstraintType.NONE) continue;

            CellPosition first = constraint.getFirst();
            CellPosition second = constraint.getSecond();

            Label constraintLabel = new Label(
                    constraint.getType() == ConstraintType.SAME ? "=" : "✕"
            );
            constraintLabel.getStyleClass().add("constraint-label");

            boolean isHorizontal = first.row() == second.row();

            if (isHorizontal) {
                boardGrid.add(constraintLabel, first.col(), first.row());
            } else {
                boardGrid.add(constraintLabel, first.col(), first.row());
            }
        }
    }

    private void flashInvalid(StackPane pane) {
        pane.getStyleClass().add("cell-invalid");
        new javafx.animation.PauseTransition(
                javafx.util.Duration.millis(400)
        ).play();
        pane.getStyleClass().remove("cell-invalid");
    }

    private void showGameComplete() {
        System.out.println("Tebrikler, oyunu tamamladınız!");
        // İleride bir dialog veya ekran geçişi eklenecek
    }
}
package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFour {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayfourinput.txt"));
    List<String> list = lines.collect(Collectors.toCollection(ArrayList::new));
    partTwo(list);

  }

  private static void partOne(List<String> lines) {
    List<Integer> turns = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

    List<Board> boards = new ArrayList<>();

    createBoards(lines, boards);

    for (Integer turn : turns) {
      for (Board board : boards) {
        boolean win = board.mark(turn);
        if (win) {
          calculateScore(turn, board);
          return;
        }
      }
    }
  }

  private static void partTwo(List<String> lines) {
    List<Integer> turns = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

    List<Board> boards = new ArrayList<>();
    Board lastWon = null;
    int lastWonTurn = 0;
    createBoards(lines, boards);

    for (Integer turn : turns) {
      List<Board> nextTurnBoards = new ArrayList<>();
      for (Board board : boards) {
        boolean win = board.mark(turn);
        if (win) {
          lastWon = board;
          lastWonTurn = turn;
        } else {
          nextTurnBoards.add(board);
        }
      }
      if (nextTurnBoards.isEmpty()) {
        break;
      }
      boards = nextTurnBoards;
    }

    calculateScore(lastWonTurn, lastWon);
  }

  private static void calculateScore(Integer turn, Board board) {
    long value = 0;
    for (Square square : board.map.values()) {
      if (!square.marked) {
        value += square.number;
      }
    }
    System.out.println(value * turn);
  }

  private static void createBoards(List<String> lines, List<Board> boards) {
    Board currentBoard = new Board();
    int row = 0;
    for (int i = 1; i < lines.size(); i++) {
      if (lines.get(i).isBlank()) {
        currentBoard = new Board();
        boards.add(currentBoard);
        row = 0;
        continue;
      }

      Board finalCurrentBoard = currentBoard;
      int finalRow = row;
      Arrays.stream(lines.get(i).split(" "))
          .filter(s -> !s.isBlank())
          .map(Integer::parseInt)
          .map(Square::new)
          .forEachOrdered(s -> finalCurrentBoard.add(s, finalRow));

      row++;
    }
  }

  static class Board {
    Map<Integer, Square> map = new HashMap<>();
    List<List<Square>> grid = new ArrayList<>();

    public Board() {
      for (int i = 0; i < 5; i++) {
        grid.add(new ArrayList<>());
      }
    }

    public void add(Square square, int row) {
      map.put(square.number, square);
      grid.get(row).add(square);
      square.setPosition(row, grid.get(row).size() - 1);
    }

    public boolean mark(Integer turn) {
      if (!map.containsKey(turn)) {
        return false;
      }
      Square square = map.get(turn);
      square.marked = true;
      boolean columnWin = true;
      boolean rowWin = true;
      for (int i = 0; i < 5; i++) {
        if (!grid.get(square.row).get(i).marked) {
          rowWin = false;
        }
        if (!grid.get(i).get(square.column).marked) {
          columnWin = false;
        }
      }
      return rowWin || columnWin;
    }
  }


  static class Square {
    boolean marked;
    int number;
    int column;
    int row;

    public Square(int number) {
      this.number = number;
      marked = false;
    }

    public void setPosition(int row, int column) {
      this.row = row;
      this.column = column;
    }
  }
}

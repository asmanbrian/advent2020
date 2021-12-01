package twenty.dayfive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFive {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/dayfiveinput.txt"));
    System.out.println(getMissingSeat(lines));
  }

  static int getMissingSeat(Stream<String> lines) {
    List<char[]> input = lines.map(String::toCharArray).collect(Collectors.toList());

    Set<Integer> seats = new HashSet<>();
    int minSeat = Integer.MAX_VALUE;
    int maxSeat = Integer.MIN_VALUE;
    for (char[] line : input) {
      int seatNumber = getSeatNumber(line);
      minSeat = Math.min(minSeat, seatNumber);
      maxSeat = Math.max(maxSeat, seatNumber);
      seats.add(seatNumber);
    }

    for (int i = minSeat + 1; i < maxSeat; i++) {
      if (!seats.contains(i) && seats.contains(i - 1) && seats.contains(i + 1)) {
        return i;
      }
    }
    return -1;
  }


  private static int getSeatNumber(char[] line) {
    int minRow = 0;
    int maxRow = 127;
    for (int i = 0; i < 7; i++) {
      if (line[i] == 'B') {
        minRow = maxRow - ((maxRow - minRow) / 2);
      } else {
        maxRow = minRow + ((maxRow - minRow) / 2);
      }
    }
    int minColumn = 0;
    int maxColumn = 7;
    for (int i = 7; i < 10; i++) {
      if (line[i] == 'R') {
        minColumn = maxColumn - ((maxColumn - minColumn) / 2);
      } else {
        maxColumn = minColumn + ((maxColumn - minColumn) / 2);
      }
    }

    return new Seat(minRow, minColumn).seatNumber;
  }


  public static class Seat {
    int row;
    int column;
    int seatNumber;

    public Seat(int row, int column) {
      this.row = row;
      this.column = column;
      this.seatNumber = row * 8 + column;
    }

  }


}

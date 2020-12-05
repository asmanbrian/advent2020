package dayfive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFive {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/dayfiveinput.txt"));
    partTwo(lines);
  }

  static void partTwo(Stream<String> lines) {
    Set<Integer> seats = lines.map(String::toCharArray)
        .map(DayFive::getSeat).map(seat -> seat.seatNumber).collect(Collectors.toSet());

    int maxSeat = 127 * 8 + 7;
    for (int i = 1; i <= maxSeat; i++) {
      if (!seats.contains(i) && seats.contains(i - 1) && seats.contains(i + 1)) {
        System.out.println(i);
        return;
      }
    }
  }

  static void partOne(Stream<String> lines) {
    OptionalInt max = lines.map(String::toCharArray).
        map(DayFive::getSeat).mapToInt(seat -> seat.seatNumber).max();

    max.ifPresent(System.out::println);
  }

  private static Seat getSeat(char[] line) {
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

    return new Seat(minRow, minColumn);
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

package twenty.DayTwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class DayTwo {

  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/daytwoinput.txt"));
    AtomicInteger valid = new AtomicInteger();
    lines.forEach(line -> {
      partTwo(valid, line);
    });

    System.out.println(valid);
  }

  private static void partTwo(AtomicInteger valid, String line) {
    String[] split = line.split("-| |:");

    int firstPos = Integer.parseInt(split[0]);
    int secondPos = Integer.parseInt(split[1]);
    char c = split[2].charAt(0);
    String password = split[4];
    boolean firstPosFound = firstPos <= password.length() && password.charAt(firstPos - 1) == c;
    boolean secondPosFound = secondPos <= password.length() && password.charAt(secondPos - 1) == c;
    if (((firstPosFound && !secondPosFound) ||
        (!firstPosFound && secondPosFound))) {
      valid.getAndIncrement();
    }
  }

  private static void partOne(AtomicInteger valid, String line) {
    String[] split = line.split("-| |:");
    int min = Integer.parseInt(split[0]);
    int max = Integer.parseInt(split[1]);
    char c = split[2].charAt(0);
    String password = split[4];

    int occurrences = 0;
    for (int i = 0; i < password.length(); i++) {
      if (password.charAt(i) == c) {
        occurrences++;
      }
    }

    if (occurrences >= min && occurrences <= max) {
      valid.getAndIncrement();
    }
  }
}

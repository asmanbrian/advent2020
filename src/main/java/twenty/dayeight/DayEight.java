package twenty.dayeight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DayEight {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/dayeightinput.txt")).collect(Collectors.toList());

    Set<Integer> executed = new HashSet<>();
    long acc = 0;
    int i = 0;
    while (i < lines.size()) {
      String[] line = lines.get(i).split(" ");
      String command = line[0];
      int offset = Integer.parseInt(line[1]);
      if (command.equals("nop") || command.equals("jmp")) {
        try {
          acc = attemptToExecute(lines, executed, acc, i);
          break;
        } catch (IllegalStateException e) {
          //nothing
        }
      } else {
      }
      executed.add(i);
      switch (command) {
        case "acc":
          acc += offset;
          i++;
          break;
        case "jmp":
          i += offset;
          break;
        default:
          i++;
      }
    }
    System.out.println(acc);
  }

  private static long attemptToExecute(List<String> lines, Set<Integer> executed, long acc, int i) {
    int toFlip = i;
    Set<Integer> localExecuted = new HashSet<>();
    localExecuted.addAll(executed);
    while (i < lines.size()) {
      if (localExecuted.contains(i)) {
        throw new IllegalStateException();
      }
      localExecuted.add(i);
      String[] line = lines.get(i).split(" ");
      String command = line[0];
      int offset = Integer.parseInt(line[1]);
      if (toFlip == i) {
        if (command.equals("jmp")) {
          command = "nop";
        } else if (command.equals("nop")) {
          command = "jmp";
        }
      }
      switch (command) {
        case "acc":
          acc += offset;
          i++;
          break;
        case "jmp":
          i += offset;
          break;
        default:
          i++;
      }
    }
    return acc;
  }

  private static void partOne(List<String> lines) {
    Set<Integer> executed = new HashSet<>();
    long acc = 0;
    int i = 0;
    while (!executed.contains(i)) {
      executed.add(i);
      String[] line = lines.get(i).split(" ");
      String command = line[0];
      int offset = Integer.parseInt(line[1]);
      switch (command) {
        case "acc":
          acc += offset;
          i++;
          break;
        case "jmp":
          i += offset;
          break;
        default:
          i++;
      }
    }
    System.out.println(acc);
  }
}

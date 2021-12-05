package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayThree {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/daythreeinput.txt"));
    List<String> list = lines.collect(Collectors.toCollection(ArrayList::new));
    partTwo(list);

  }

  private static void partOne(List<String> lines) {
    int lineLength = lines.get(0).length();
    int[] ones = new int[lineLength];
    for (String line : lines) {
      for (int i = 0; i < lineLength; i++) {
        if (line.charAt(i) == '1') {
          ones[i]++;
        }
      }
    }
    StringBuilder gammaBuilder = new StringBuilder();
    StringBuilder epsilonBuilder = new StringBuilder();
    for (int i = 0; i < lineLength; i++) {
      if (ones[i] > lines.size() / 2) {
        gammaBuilder.append("1");
        epsilonBuilder.append("0");
      } else {
        gammaBuilder.append("0");
        epsilonBuilder.append("1");
      }
    }
    int gamma = Integer.parseInt(gammaBuilder.toString(), 2);
    int epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);

    long power = ((long) gamma * epsilon);
    System.out.println(power);
  }

  private static void partTwo(List<String> lines) {
    int oxygen = findValue(lines, true);
    int scrubber = findValue(lines, false);
    System.out.println((long) oxygen * scrubber);
  }

  private static int findValue(List<String> lines, boolean mostCommonSearch) {
    for (int i = 0; i < lines.get(0).length(); i++) {
      int ones = 0;
      int zeros = 0;
      for (String line : lines) {
        if (line.charAt(i) == '1') {
          ones++;
        } else {
          zeros++;
        }
      }
      char mostCommon;
      char leastCommon;
      if (ones >= zeros) {
        mostCommon = '1';
        leastCommon = '0';
      } else {
        mostCommon = '0';
        leastCommon = '1';
      }
      char search = mostCommonSearch ? mostCommon : leastCommon;
      int finalI = i;
      lines = lines.stream().filter(l -> l.charAt(finalI) == search).collect(Collectors.toList());
      if (lines.size() == 1) {
        break;
      }
    }
    return Integer.parseInt(lines.get(0), 2);
  }

}

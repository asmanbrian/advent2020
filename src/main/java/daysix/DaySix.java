package daysix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DaySix {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/daysixinput.txt")).collect(Collectors.toList());
    long sum = partTwo(lines);


    System.out.println(sum);
  }

  private static long partTwo(List<String> lines) {
    long sum = 0;
    HashMap<Character, Integer> answersForGroup = new HashMap<>();
    int groupSize = 0;
    for (String line : lines) {
      if (line == null || line.isBlank()) {
        int finalGroupSize = groupSize;
        sum += answersForGroup.values().stream().filter(i -> i == finalGroupSize).count();
        answersForGroup = new HashMap<>();
        groupSize = 0;
        continue;
      }

      groupSize++;
      for (char c : line.toCharArray()) {
        if (answersForGroup.containsKey(c)) {
          answersForGroup.put(c, answersForGroup.get(c) + 1);
        } else {
          answersForGroup.put(c, 1);
        }
      }
    }
    int finalGroupSize1 = groupSize;
    sum += answersForGroup.values().stream().filter(i -> i == finalGroupSize1).count();
    return sum;
  }


  private static long partOne(List<String> lines) {
    long sum = 0;
    Set<Character> currentAnswers = new HashSet<>();

    for (String line : lines) {
      if (line == null || line.isBlank()) {
        sum += currentAnswers.size();
        currentAnswers = new HashSet<>();
        continue;
      }

      for (char c : line.toCharArray()) {
        currentAnswers.add(c);
      }
    }
    sum += currentAnswers.size();
    return sum;
  }
}

package dayten;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayTen {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/dayteninput.txt"));
    partTwo(lines);
  }

  private static void partTwo(Stream<String> lines) {
    List<Integer> numberList = lines.map(Integer::parseInt).collect(Collectors.toList());
    int max = Integer.MIN_VALUE;
    Set<Integer> numbers = new HashSet<>();
    for (Integer i : numberList) {
      max = Math.max(max, i);
      numbers.add(i);
    }
    Map<Integer, Long> memo = new HashMap<>();
    long paths = findPaths(0, numbers, max, memo);
    System.out.println(paths);
  }

  private static long findPaths(int start, Set<Integer> numbers, int max,
      Map<Integer, Long> memo) {
    if (memo.containsKey(start)) {
      return memo.get(start);
    }
    if (start == max) {
      memo.put(start, 1L);
      return 1;
    }
    long paths = 0;
    for (int i = 1; i < 4; i++) {
      if (numbers.contains(start + i)) {
        paths += findPaths(start + i, numbers, max, memo);
      }
    }
    memo.put(start, paths);
    return paths;
  }

  private static void partOne(Stream<String> lines) {
    List<Integer> numbers = lines.map(Integer::parseInt).sorted().collect(Collectors.toList());

    numbers.add(0, 0);
    numbers.add(numbers.get(numbers.size() - 1) + 3);

    int[] diffs = new int[4];
    for (int i = 1; i < numbers.size(); i++) {
      diffs[numbers.get(i) - numbers.get(i - 1)]++;
    }
    System.out.println(diffs[1] * diffs[3]);
  }
}

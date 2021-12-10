package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayTen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayteninput.txt"))
        .collect(Collectors.toCollection(ArrayList::new));
    partTwo(lines);
  }

  private static void partOne(List<String> lines) {
    int sum = 0;
    Map<Character, Integer> values = new HashMap<>();
    values.put(')', 3);
    values.put(']', 57);
    values.put('}', 1197);
    values.put('>', 25137);
    for (String line : lines) {
      Deque<Character> targets = new LinkedList<>();
      for (char c : line.toCharArray()) {
        if (isOpenChar(c)) {
          addTarget(c, targets);
          continue;
        }
        char target = targets.pop();
        if (target != c) {
          sum += values.get(c);
          break;
        }
      }
    }
    System.out.println(sum);
  }

  private static void addTarget(char c, Deque<Character> targets) {
    switch (c) {
      case '(':
        targets.push(')');
        break;
      case '[':
        targets.push(']');
        break;
      case '<':
        targets.push('>');
        break;
      case '{':
        targets.push('}');
        break;
    }
  }

  private static boolean isOpenChar(char c) {
    return c == '(' || c == '[' || c == '<' || c == '{';
  }

  private static void partTwo(List<String> lines) {
    List<Long> scores = new ArrayList<>();
    Map<Character, Integer> values = new HashMap<>();
    values.put(')', 1);
    values.put(']', 2);
    values.put('}', 3);
    values.put('>', 4);
    for (String line : lines) {
      boolean error = false;
      Deque<Character> targets = new LinkedList<>();
      for (char c : line.toCharArray()) {
        if (isOpenChar(c)) {
          addTarget(c, targets);
          continue;
        }
        char target = targets.pop();
        if (target != c) {
          error = true;
          break;
        }
      }
      if (error) {
        continue;
      }
      long score = 0L;
      for (Character c : targets) {
        score *= 5;
        score += values.get(c);
      }
      if (score != 0) {
        scores.add(score);
      }
    }
    scores.sort(Long::compareTo);
    System.out.println(scores.get(scores.size() / 2));
  }

}

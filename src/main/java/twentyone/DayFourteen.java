package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayFourteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayfourteeninput.txt"))
        .collect(Collectors.toCollection(ArrayList::new));
    partTwo(lines);

  }

  private static void partOne(List<String> lines) {
    String current = lines.get(0);
    Map<String, String> insertions = new HashMap<>();
    for (int i = 2; i < lines.size(); i++) {
      String[] split = lines.get(i).split(" -> ");
      insertions.put(split[0], split[1]);
    }

    for (int i = 0; i < 1; i++) {
      StringBuilder next = new StringBuilder();
      next.append(current.charAt(0));
      for (int j = 0; j < current.length() - 1; j++) {
        String pair = current.substring(j, j + 2);
        next.append(insertions.get(pair));
        next.append(pair.charAt(1));
      }
      current = next.toString();
      System.out.println("Step " + i);
    }
    Map<Character, Long> counts = new HashMap<>();
    for (char c = 'A'; c <= 'Z'; c++) {
      counts.put(c, 0L);
    }
    for (char c : current.toCharArray()) {
      counts.put(c, counts.get(c) + 1);
    }
    List<Long> sortedCounts = counts.values().stream().filter(i -> i > 0).sorted().collect(Collectors.toList());
    System.out.println(sortedCounts.get(sortedCounts.size() - 1) - sortedCounts.get(0));
  }

  private static void partTwo(List<String> lines) {
    String current = lines.get(0);
    Map<String, String> insertions = new HashMap<>();
    Map<String, Long> pairCounts = new HashMap<>();
    Map<String, Long> emptyPairs = new HashMap<>();
    for (int i = 2; i < lines.size(); i++) {
      String[] split = lines.get(i).split(" -> ");
      insertions.put(split[0], split[1]);
      pairCounts.put(split[0], 0L);
      emptyPairs.put(split[0], 0L);

    }
    for (int i = 0; i < current.length() - 1; i++) {
      String pair = current.substring(i, i + 2);
      pairCounts.put(pair, pairCounts.get(pair) + 1);
    }

    for (int i = 0; i < 40; i++) {
      Map<String, Long> nextCounts = new HashMap<>(emptyPairs);

      for (Map.Entry<String, Long> count : pairCounts.entrySet()) {
        if (count.getValue() == 0) {
          continue;
        }
        String pair = count.getKey();
        String insert = insertions.get(pair);
        String firstNewPair = "" + pair.charAt(0) + insert;
        String secondNewPair = insert + pair.charAt(1);
        nextCounts.put(firstNewPair, nextCounts.get(firstNewPair) + count.getValue());
        nextCounts.put(secondNewPair, nextCounts.get(secondNewPair) + count.getValue());
        //        nextCounts.put(pair, pairCounts.get(pair) - count.getValue());
      }
      pairCounts = nextCounts;
    }

    Map<Character, Long> elementCounts = new HashMap<>();
    for (char c = 'A'; c <= 'Z'; c++) {
      elementCounts.put(c, 0L);
    }
    for (Map.Entry<String, Long> pairCount : pairCounts.entrySet()) {
      char elementOne = pairCount.getKey().charAt(0);
      char elementTwo = pairCount.getKey().charAt(1);
      elementCounts.put(elementOne, elementCounts.get(elementOne) + pairCount.getValue());
      //      elementCounts.put(elementTwo, elementCounts.get(elementTwo) + pairCount.getValue());
    }
    char lastChar = current.charAt(current.length() - 1);
    elementCounts.put(lastChar, elementCounts.get(lastChar) + 1);
    List<Long> sortedCounts = elementCounts.values().stream().filter(i -> i > 0).sorted().collect(Collectors.toList());
    System.out.println(sortedCounts.get(sortedCounts.size() - 1) - sortedCounts.get(0));
  }


}

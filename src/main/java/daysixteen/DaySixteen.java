package daysixteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DaySixteen {
  public static void main(String[] args) throws IOException {
    List<String> lines =
        Files.lines(Paths.get("src/main/resources/daysixteeninput.txt"))
            .collect(Collectors.toList());

    Map<String, List<Pair<Integer, Integer>>> rules = new HashMap<>();
    List<int[]> validTickets = new ArrayList<>();
    int[] myTicket = new int[15];
    int index = 0;
    System.out.println("adding rules");
    for (; !lines.get(index).isBlank(); index++) {
      addRule(lines, rules, index);
    }
    System.out.println("validating tickets");
    for (; index < lines.size(); index++) {
      if (lines.get(index).isBlank()) {
        continue;
      }

      if (lines.get(index).equals("your ticket:")) {
        myTicket = Arrays.stream(lines.get(index + 1).split(",")).mapToInt(Integer::parseInt).toArray();
        index += 4;
      }

      int[] values = Arrays.stream(lines.get(index).split(",")).mapToInt(Integer::parseInt).toArray();
      boolean validTicket = true;
      for (int v : values) {
        boolean validValue = false;
        for (List<Pair<Integer, Integer>> rule1 : rules.values()) {
          for (Pair<Integer, Integer> range1 : rule1) {
            if (isValid(v, range1)) {
              validValue = true;
              break;
            }
          }
          if (validValue) {
            break;
          }
        }
        validTicket = validTicket && validValue;
      }
      if (validTicket) {
        validTickets.add(values);
      }
    }


    String[][] possibleFieldPositions = new String[rules.size()][rules.size()];
    for (int i = 0; i < rules.size(); i++) {
      possibleFieldPositions[i] = rules.keySet().toArray(new String[0]);
    }
    System.out.println("finding possible positions");
    for (int[] ticket : validTickets) {
      for (int i = 0; i < ticket.length; i++) {
        Set<String> newPossibilities = new HashSet<>();
        for (String rule : possibleFieldPositions[i]) {
          for (Pair<Integer, Integer> range : rules.get(rule)) {
            if (isValid(ticket[i], range)) {
              newPossibilities.add(rule);
              break;
            }
          }
        }
        possibleFieldPositions[i] = newPossibilities.toArray(new String[0]);
      }
    }
    System.out.println("finding correct positions");
    Map<String, Integer> positions = new HashMap<>();
    positions = findFieldOrder(possibleFieldPositions, 0, positions);


    List<String> departureFields =
        positions.keySet().stream().filter(k -> k.startsWith("departure")).collect(Collectors.toList());
    long mult = 1L;
    for (String field : departureFields) {
      mult *= myTicket[positions.get(field)];
    }
    System.out.println(mult);
  }

  private static Map<String, Integer> findFieldOrder(String[][] possibleFieldPositions, int i,
      Map<String, Integer> positions) {
    if (i >= possibleFieldPositions.length) {
      return positions;
    }
    for (String field : possibleFieldPositions[i]) {
      if (positions.containsKey(field)) {
        continue;
      }
      Map<String, Integer> copy = new HashMap<>(positions);
      copy.put(field, i);
      Map<String, Integer> ret = findFieldOrder(possibleFieldPositions, i + 1, copy);
      if (ret != null) {
        return ret;
      }
    }
    if (i < 3) {
      System.out.println("failed on position " + i);
    }
    return null;
  }

  private static void partOne(List<String> lines, Map<String, List<Pair<Integer, Integer>>> rules, int sum, int i) {
    for (; i < lines.size(); i++) {
      if (lines.get(i).isBlank()) {
        continue;
      }
      if (lines.get(i).equals("your ticket:")) {
        //your ticket logic
        i += 4;
      }

      int[] values = Arrays.stream(lines.get(i).split(",")).mapToInt(Integer::parseInt).toArray();

      for (int v : values) {
        boolean valid = false;
        for (List<Pair<Integer, Integer>> rule : rules.values()) {
          for (Pair<Integer, Integer> range : rule) {
            if (isValid(v, range)) {
              valid = true;
              break;
            }
          }
          if (valid) {
            break;
          }
        }
        if (!valid) {
          sum += v;
        }
      }
    }
    System.out.println(sum);
  }

  private static boolean isValid(int v, Pair<Integer, Integer> range) {
    return v >= range.getLeft() && v <= range.getRight();
  }

  private static void addRule(List<String> lines, Map<String, List<Pair<Integer, Integer>>> rules, int i) {
    String[] split = lines.get(i).split(":");
    String key = split[0];
    split = split[1].split("[ |\\-]");
    List<Pair<Integer, Integer>> value = new ArrayList<>();
    value.add(new ImmutablePair<>(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
    value.add(new ImmutablePair<>(Integer.parseInt(split[4]), Integer.parseInt(split[5])));
    rules.put(key, value);
  }
}

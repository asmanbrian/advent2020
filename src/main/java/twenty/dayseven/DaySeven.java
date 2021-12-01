package twenty.dayseven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DaySeven {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/dayseveninput.txt"));
    partTwo(lines);
  }

  private static void partTwo(Stream<String> lines) {
    Map<String, Set<Pair<Integer, String>>> containersToBags = new HashMap<>();
    lines
        .forEach(line -> {

          String[] split = line.split(" ");
          String container = split[0] + split[1];
          if (line.endsWith("no other bags.")) {
            containersToBags.put(container, new HashSet<>());
          }
          for (int i = 4; i < split.length; i++) {
            if (StringUtils.isNumeric(split[i])) {
              int numBags = Integer.parseInt(split[i]);
              String bag = split[i + 1] + split[i + 2];
              if (containersToBags.containsKey(container)) {
                containersToBags.get(container).add(new ImmutablePair<>(numBags, bag));
              } else {
                Set<Pair<Integer, String>> bags = new HashSet<>();
                bags.add(new ImmutablePair<>(numBags, bag));
                containersToBags.put(container, bags);
              }
            }
          }
        });

    System.out.println(getNumberOfBagsWithin("shinygold", containersToBags) - 1);
  }

  public static long getNumberOfBagsWithin(String container, Map<String, Set<Pair<Integer, String>>> containersToBags) {
    long numBags = 1;
    if (containersToBags.containsKey(container)) {
      for (Pair p : containersToBags.get(container)) {
        numBags += (Integer) p.getLeft() * getNumberOfBagsWithin((String) p.getRight(), containersToBags);
      }
    }
    return numBags;
  }

  private static void partOne(Stream<String> lines) {
    Map<String, Set<String>> bagToContainers = new HashMap<>();
    lines
        .filter(line -> !line.endsWith("no other bags."))
        .forEach(line -> {
          String[] split = line.split(" ");
          String container = split[0] + split[1];
          for (int i = 4; i < split.length; i++) {
            if (StringUtils.isNumeric(split[i])) {
              String bag = split[i + 1] + split[i + 2];
              if (bagToContainers.containsKey(bag)) {
                bagToContainers.get(bag).add(container);
              } else {
                Set<String> containers = new HashSet<>();
                containers.add(container);
                bagToContainers.put(bag, containers);
              }
            }
          }
        });

    Set<String> goldContainers = containersThatHold("shinygold", bagToContainers);
    System.out.println(goldContainers.size());
  }

  public static Set<String> containersThatHold(String bag, Map<String, Set<String>> bagToContainers) {
    if (!bagToContainers.containsKey(bag)) {
      return new HashSet<>();
    }

    Set<String> oneLevelContainers = bagToContainers.get(bag);
    Set<String> returnContainers = new HashSet<>();
    for (String s : oneLevelContainers) {
      returnContainers.add(s);
      returnContainers.addAll(containersThatHold(s, bagToContainers));
    }
    return returnContainers;
  }
}
//0 light
//1 red
//2 bags
//3 contain
//4 1
//5 bright
//6 white
//7 bag,
//8 2
//9 muted
//10 yellow
//11 bags.
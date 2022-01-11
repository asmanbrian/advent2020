package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DayTwelve {
  public static void main(String[] args) throws IOException {
    List<String> lines =
        Files.lines(Paths.get("src/main/resources/twentyone/daytwelveinput.txt")).collect(Collectors.toList());
    partTwo(lines);

  }

  private static void partOne(List<String> lines) {
    Map<String, Cave> caves = new HashMap<>();
    Cave start = new Cave("start");
    Cave end = new Cave("end");
    caves.put("start", start);
    caves.put("end", end);

    for (String line : lines) {
      String[] parts = line.split("-");
      Cave first;
      Cave second;
      if (caves.containsKey(parts[0])) {
        first = caves.get(parts[0]);
      } else {
        first = new Cave(parts[0]);
        caves.put(parts[0], first);
      }
      if (caves.containsKey(parts[1])) {
        second = caves.get(parts[1]);
      } else {
        second = new Cave(parts[1]);
        caves.put(parts[1], second);
      }
      first.connections.add(second);
      second.connections.add(first);
    }

    int paths = getPathsOne(start, end, caves, new HashSet<>());
    System.out.println(paths);
  }

  private static int getPathsOne(Cave start, Cave end, Map<String, Cave> caves, Set<Cave> visited) {
    Set<Cave> visitedCopy = new HashSet<>(visited);
    int paths = 0;
    if (start == end) {
      return 1;
    }
    visitedCopy.add(start);
    for (Cave c : start.connections) {
      if (c.big || !visitedCopy.contains(c)) {

        paths += getPathsOne(c, end, caves, visitedCopy);
      }
    }
    return paths;
  }

  private static void partTwo(List<String> lines) {
    Map<String, Cave> caves = new HashMap<>();
    Cave start = new Cave("start");
    Cave end = new Cave("end");
    caves.put("start", start);
    caves.put("end", end);

    for (String line : lines) {
      String[] parts = line.split("-");
      Cave first;
      Cave second;
      if (caves.containsKey(parts[0])) {
        first = caves.get(parts[0]);
      } else {
        first = new Cave(parts[0]);
        caves.put(parts[0], first);
      }
      if (caves.containsKey(parts[1])) {
        second = caves.get(parts[1]);
      } else {
        second = new Cave(parts[1]);
        caves.put(parts[1], second);
      }
      first.connections.add(second);
      second.connections.add(first);
    }

    int paths = getPathsTwo(start, end, caves, new HashSet<>(), false);
    System.out.println(paths);
  }

  private static int getPathsTwo(Cave start, Cave end, Map<String, Cave> caves, Set<Cave> visited, boolean repeated) {
    Set<Cave> visitedCopy = new HashSet<>(visited);
    int paths = 0;
    if (start == end) {
      return 1;
    }
    if (visitedCopy.contains(start)) {
      if (!start.big) {
        repeated = true;
      }
    } else {
      visitedCopy.add(start);
    }
    for (Cave c : start.connections) {
      if (c.name.equals("start")) {
        continue;
      }
      if (c.big || !visitedCopy.contains(c) || !repeated) {

        paths += getPathsTwo(c, end, caves, visitedCopy, repeated);
      }
    }
    return paths;
  }


  private static class Cave {
    String name;
    boolean big = false;
    List<Cave> connections = new ArrayList<>();

    public Cave(String name) {
      this.name = name;
      if (name.toUpperCase().equals(name)) {
        big = true;
      }
    }

    public String toString() {
      return name;
    }
  }
}

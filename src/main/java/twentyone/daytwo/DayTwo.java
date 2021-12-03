package twentyone.daytwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayTwo {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/daytwoinput.txt"));
    //    List<Integer> numbers = lines.map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
    partTwo(lines);

  }

  private static void partOne(Stream<String> lines) {
    Map<String, Integer> map = new HashMap<>();
    map.put("forward", 0);
    map.put("down", 0);
    map.put("up", 0);
    lines.map(l -> l.split(" ")).forEach(a -> map.put(a[0], map.get(a[0]) + Integer.parseInt(a[1])));
    System.out.println((long) map.get("forward") * (map.get("down") - map.get("up")));


  }

  private static void partTwo(Stream<String> lines) {
    List<String[]> parsed = lines.map(l -> l.split(" ")).collect(Collectors.toCollection(ArrayList::new));

    int aim = 0;
    int h = 0;
    int d = 0;
    for (String[] p : parsed) {
      int distance = Integer.parseInt(p[1]);
      if (p[0].equals("down")) {
        aim += distance;
        continue;
      }
      if (p[0].equals("up")) {
        aim -= distance;
        continue;
      }
      h += distance;
      d += aim * distance;

    }
    System.out.println((long) h * d);
  }
}

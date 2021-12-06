package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DaySix {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/daysixinput.txt"));
    List<Integer> numbers =
        Arrays.stream(lines.findFirst().get().split(",")).map(Integer::parseInt).collect(Collectors.toList());
    partTwo(numbers);

  }

  private static void partOne(List<Integer> numbers) {
    for (int i = 0; i < 80; i++) {
      List<Integer> next = new ArrayList<>();
      for (Integer fish : numbers) {
        if (fish == 0) {
          next.add(8);
          next.add(6);
        } else {
          next.add(fish - 1);
        }
      }
      numbers = next;
    }
    System.out.println(numbers.size());
  }

  private static void partTwo(List<Integer> numbers) {
    System.out.println("Day -1" + ": " + numbers.size() + " fish ");
    numbers.stream().sorted().forEachOrdered(n -> System.out.print(n + ","));
    System.out.println();
    System.out.println();

    Map<Integer, Long> map = new HashMap<>();
    for (int i = 0; i < 9; i++) {
      map.put(i, 0L);
    }
    for (Integer num : numbers) {
      map.put(num, map.get(num) + 1);
    }
    for (int i = 0; i < 256; i++) {
      Map<Integer, Long> next = new HashMap<>();
      for (int j = 0; j < 9; j++) {
        next.put(j, 0L);
      }
      for (Map.Entry<Integer, Long> entry : map.entrySet()) {
        if (entry.getKey() == 0) {
          next.put(8, next.get(8) + entry.getValue());
          next.put(6, next.get(6) + entry.getValue());
        } else {
          next.put(entry.getKey() - 1, next.get(entry.getKey() - 1) + entry.getValue());
        }
      }

      map = next;
      long size = map.values().stream().mapToLong(Long::longValue).sum();
      System.out.println("Day " + i + ": " + size + " fish ");
      //      numbers.stream().sorted().forEachOrdered(n -> System.out.print(n + ","));
      System.out.println();
      System.out.println();
    }
    //count added before loop starts
    //count added per loop
    //add per number of loops
  }

}

package twenty.dayone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayOne {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/dayoneinput.txt"));
    Set<Integer> numbers = lines.map(Integer::parseInt).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    partTwo(numbers);

  }

  private static void partOne(Set<Integer> numbers) {
    numbers.forEach(i -> {
      int search = 2020 - i;
      if (numbers.contains(search)) {
        System.out.println(i * search);
        return;
      }
    });
  }

  private static void partTwo(Set<Integer> numbers) {
    Integer[] array = numbers.toArray(new Integer[0]);
    for (int i = 0; i < array.length; i++) {
      for (int j = 1; j < array.length; j++) {
        int search = 2020 - array[i] - array[j];
        if (numbers.contains(search)) {
          System.out.println(array[i] * array[j] * search);
          return;
        }
      }
    }
  }

}

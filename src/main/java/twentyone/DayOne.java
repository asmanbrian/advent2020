package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayOne {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayoneinput.txt"));
    List<Integer> numbers = lines.map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
    partTwo(numbers);

  }

  private static void partOne(List<Integer> numbers) {
    int increases = 0;
    for (int i = 1; i < numbers.size(); i++) {
      if (numbers.get(i) > numbers.get(i - 1)) {
        increases++;
      }
    }
    System.out.println(increases);
  }

  private static void partTwo(List<Integer> numbers) {
    int increases = 0;
    for (int i = 3; i < numbers.size(); i++) {
      if (numbers.get(i) > numbers.get(i - 3)) {
        increases++;
      }
    }
    System.out.println(increases);
  }

}

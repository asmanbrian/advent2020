package daynine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DayNine {
  public static void main(String[] args) throws IOException {
    List<Long> numbers = Files.lines(Paths.get("src/main/resources/daynineinput.txt"))
        .map(Long::parseLong).collect(Collectors.toList());
    long invalid = partOne(numbers);
    for (int i = 0; i < numbers.size(); i++) {
      long sum = numbers.get(i);
      long min = sum;
      long max = sum;
      for (int j = i + 1; j < numbers.size(); j++) {
        sum += numbers.get(j);
        min = Math.min(min, numbers.get(j));
        max = Math.max(max, numbers.get(j));
        if (sum > invalid) {
          break;
        }
        if (sum == invalid) {
          System.out.println(min + max);
          return;
        }
      }
    }
  }

  private static long partOne(List<Long> numbers) {
    LinkedHashSet<Long> set = new LinkedHashSet<>();

    for (Long in : numbers) {
      if (set.size() < 25) {
        set.add(in);
        continue;
      }
      boolean valid = false;
      for (Long i : set) {
        long search = in - i;
        if (search != in && set.contains(search)) {
          valid = true;
          break;
        }
      }
      if (!valid) {
        return in;
      }

      Long first = set.iterator().next();
      set.remove(first);
      set.add(in);
    }
    return -1;
  }
}

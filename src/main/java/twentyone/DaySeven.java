package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DaySeven {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayseveninput.txt"));
    List<Integer> numbers =
        Arrays.stream(lines.findFirst().get().split(",")).map(Integer::parseInt).collect(Collectors.toList());

    int max = numbers.stream().mapToInt(Integer::intValue).max().getAsInt();
    int[] sorted = new int[max + 1];
    for (Integer num : numbers) {
      sorted[num]++;
    }
    partTwo(numbers, sorted);

  }

  private static void partOne(List<Integer> numbers, int[] sorted) {

    int numLeft = 0;
    int numRight = numbers.size() - sorted[0];
    int bestTarget = 0;
    int bestTargetFuel = Integer.MAX_VALUE;
    int targetFuel = 0;

    for (int i = 0; i < sorted.length; i++) {
      targetFuel += i * sorted[i];
    }
    for (int i = 1; i < sorted.length; i++) {
      numLeft += sorted[i - 1];
      targetFuel = targetFuel + numLeft - numRight;
      numRight -= sorted[i];
      if (targetFuel < bestTargetFuel) {
        bestTarget = i;
        bestTargetFuel = targetFuel;
      }
    }
    System.out.println("Target " + bestTarget + " with fuel " + bestTargetFuel);
  }

  private static void partTwo(List<Integer> numbers, int[] sorted) {
    int bestTarget = 0;

    int bestTargetFuel = Integer.MAX_VALUE;

    for (int i = 0; i < sorted.length; i++) {
      int targetFuel = 0;
      for (int j = 0; j < i; j++) {
        targetFuel += (i - j) * (i - j + 1) / 2 * sorted[j];
      }
      for (int j = i + 1; j < sorted.length; j++) {
        targetFuel += (j - i) * (j - i + 1) / 2 * sorted[j];
      }
      if (targetFuel < bestTargetFuel) {
        bestTarget = i;
        bestTargetFuel = targetFuel;
      }
      System.out.println("Target " + i + " with fuel " + targetFuel);

    }
    System.out.println("Best target " + bestTarget + " with fuel " + bestTargetFuel);


  }
}

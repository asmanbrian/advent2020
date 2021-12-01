package twenty.dayfifteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DayFifteen {
  //parts one and two are the same, just with a different number of turns
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("src/main/resources/dayfifteeninput.txt"));
    Map<Integer, int[]> spoken = new HashMap<>();

    int[] startingNumbers = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    int i = 0;
    for (; i < startingNumbers.length; i++) {
      spoken.put(startingNumbers[i], new int[] {i, -1});
    }
    int lastNumber = startingNumbers[startingNumbers.length - 1];
    for (; i < 30000000; i++) {
      int currentNumber;
      if (spoken.containsKey(lastNumber)) {
        int[] prevSpoken = spoken.get(lastNumber);
        if (prevSpoken[1] < 0) {
          currentNumber = 0;
        } else {
          currentNumber = prevSpoken[0] - prevSpoken[1];
        }
      } else {
        throw new RuntimeException("not spoken yet");
      }
      if (!spoken.containsKey(currentNumber)) {
        spoken.put(currentNumber, new int[] {i, -1});
      } else {
        int[] currentSpoken = spoken.get(currentNumber);
        currentSpoken[1] = currentSpoken[0];
        currentSpoken[0] = i;
      }
      lastNumber = currentNumber;
    }
    System.out.println(lastNumber);
  }
}

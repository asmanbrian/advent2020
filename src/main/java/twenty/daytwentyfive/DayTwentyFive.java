package twenty.daytwentyfive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayTwentyFive {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentyfiveinput.txt"));

    long cardPublic = Long.parseLong(lines.get(0));
    long doorPublic = Long.parseLong(lines.get(1));

    long value = 1;
    int loopSize = 0;
    while (value != cardPublic) {
      loopSize++;
      value = transform(value, 7);
    }

    System.out.println("Card loop size is " + loopSize);

    long encryptionKey = 1L;
    for (int i = 0; i < loopSize; i++) {
      encryptionKey = transform(encryptionKey, doorPublic);
    }
    System.out.println(encryptionKey);

  }

  public static long transform(long value, long subjectValue) {
    value *= subjectValue;
    value %= 20201227;
    return value;
  }
}

package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DaySeventeen {
  public static void main(String[] args) throws IOException {
    String line = Files.lines(Paths.get("src/main/resources/twentyone/dayseventeeninput.txt")).findFirst().get();
    partOne(line);

  }

  private static void partOne(String line) {
    line = line.substring(15);
    String[] split = line.replace(", y=", "..").split("\\.\\.");
    int minX = Integer.parseInt(split[0]);
    int maxX = Integer.parseInt(split[1]);
    int minY = Integer.parseInt(split[2]);
    int maxY = Integer.parseInt(split[3]);
    int count = 0;
    int result = Integer.MIN_VALUE;
    int vX = maxX;
    int vY = maxY;
    for (int i = maxX; i > 0; i--) {
      for (int j = minY; j < maxX; j++) {
        vX = i;
        vY = j;
        int posX = 0;
        int posY = 0;
        int maybePeak = Integer.MIN_VALUE;
        while (true) {
          posX += vX;
          posY += vY;
          maybePeak = Math.max(maybePeak, posY);
          if (vX > 0) {
            vX--;
          }
          vY--;

          if ((posX >= minX && posX <= maxX) && (posY >= minY && posY <= maxY)) {
            System.out.println("in target. X: " + i + " Y: " + j);
            result = Math.max(result, maybePeak);
            count++;
            break;
          }
          if (posX > maxX || posY < minY) {
            System.out.println("Past target. X: " + i + " Y: " + j);
            break;
          }
        }
      }
    }

    System.out.println("peak: " + result);
    System.out.println("initial count " + count);


  }

  private static void partTwo(String line) {
  }


}

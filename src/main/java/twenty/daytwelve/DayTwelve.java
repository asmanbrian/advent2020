package twenty.daytwelve;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayTwelve {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/daytwelveinput.txt"))
        .collect(Collectors.toList());
    partTwo(lines);
  }

  private static void partTwo(List<String> lines) {
    int x = 0;
    int y = 0;
    int waypointX = 10;
    int waypointY = 1;
    for (String line : lines) {
      char dir = line.charAt(0);
      int distance = Integer.parseInt(line.substring(1));
      switch (dir) {
        case 'N':
          waypointY += distance;
          break;
        case 'S':
          waypointY -= distance;
          break;
        case 'E':
          waypointX += distance;
          break;
        case 'W':
          waypointX -= distance;
          break;
        case 'R':
          for (int i = 0; i < distance / 90; i++) {
            int temp = waypointX;
            waypointX = waypointY;
            waypointY = -1 * temp;
          }
          break;
        case 'L':
          for (int i = 0; i < distance / 90; i++) {
            int temp = waypointX;
            waypointX = -1 * waypointY;
            waypointY = temp;
          }
          break;
        case 'F':
          x += distance * waypointX;
          y += distance * waypointY;
          break;
      }
    }
    System.out.println(Math.abs(x) + Math.abs(y));
  }

  private static void partOne(List<String> lines) {
    int x = 0;
    int y = 0;
    Map<Integer, Character> facings = new HashMap<>();
    facings.put(0, 'E');
    facings.put(90, 'S');
    facings.put(180, 'W');
    facings.put(270, 'N');
    int facing = 0;
    for (String line : lines) {
      char dir = line.charAt(0);
      int distance = Integer.parseInt(line.substring(1));
      if (dir == 'F') {
        dir = facings.get(facing);
      }
      switch (dir) {
        case 'N':
          y -= distance;
          break;
        case 'S':
          y += distance;
          break;
        case 'E':
          x += distance;
          break;
        case 'W':
          x -= distance;
          break;
        case 'R':
          facing = (facing + distance) % 360;
          break;
        case 'L':
          facing = (facing - distance + 360) % 360;
          break;
      }
    }
    System.out.println(Math.abs(x) + Math.abs(y));
  }
}

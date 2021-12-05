package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFive {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayfiveinput.txt"));
    List<String> list = lines.collect(Collectors.toCollection(ArrayList::new));
    run(list, false);

  }

  private static void run(List<String> lines, boolean partOne) {
    int intersections = 0;
    int[][] grid = new int[1000][1000];
    for (String line : lines) {
      String[] split = line.split(" -> ");
      Point start = new Point(split[0].split(","));
      Point end = new Point(split[1].split(","));

      if (start.x == end.x) {
        for (int i = start.y; ; ) {
          grid[i][start.x]++;
          if (grid[i][start.x] == 2) {
            intersections++;
          }
          if (i < end.y) {
            i++;
          } else if (i > end.y) {
            i--;
          } else {
            break;
          }
        }
        continue;
      }

      if (start.y == end.y) {
        for (int i = start.x; ; ) {
          grid[start.y][i]++;
          if (grid[start.y][i] == 2) {
            intersections++;
          }
          if (i < end.x) {
            i++;
          } else if (i > end.x) {
            i--;
          } else {
            break;
          }
        }
        continue;
      }

      //diagonal
      if (!partOne) {
        if (end.x < start.x) {
          Point temp = start;
          start = end;
          end = temp;
        }
      }

      for (int i = 0; i <= end.x - start.x; i++) {
        int y;
        if (start.y < end.y) {
          y = start.y + i;
        } else {
          y = start.y - i;
        }
        grid[y][start.x + i]++;
        if (grid[y][start.x + i] == 2) {
          intersections++;
        }
      }
    }

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        System.out.print(grid[i][j]);
      }
      System.out.println();
    }
    System.out.println(intersections);
  }

  static class Point {
    int x;
    int y;

    public Point(String[] input) {
      x = Integer.parseInt(input[0]);
      y = Integer.parseInt(input[1]);
    }
  }
}

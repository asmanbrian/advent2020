package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DayThirteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/daythirteeninput.txt"))
        .collect(Collectors.toCollection(ArrayList::new));
    partOne(lines);

  }

  private static void partOne(List<String> lines) {
    int maxX = -1;
    int maxY = -1;
    int points = 0;
    for (String line : lines) {
      if (line.isBlank()) {
        break;
      }
      String[] split = line.split(",");
      maxX = Math.max(Integer.parseInt(split[0]), maxX);
      maxY = Math.max(Integer.parseInt(split[1]), maxY);
      points++;
    }
    boolean[][] grid = new boolean[maxY + 1][maxX + 1];
    for (int i = 0; i < points; i++) {
      String[] split = lines.get(i).split(",");
      int x = Integer.parseInt(split[0]);
      int y = Integer.parseInt(split[1]);
      grid[y][x] = true;
    }
    for (int i = points + 1; i < lines.size(); i++) {
      boolean up = false;
      String[] split = lines.get(i).split("=");
      if (split[0].endsWith("y")) {
        up = true;
      }
      int fold = Integer.parseInt(split[1]);

      if (up) {
        foldUp(fold, maxY, maxX, grid);
        maxY = fold;
      } else {
        foldLeft(fold, maxY, maxX, grid);
        maxX = fold;
      }
    }
    int count = 0;
    for (int i = 0; i <= maxY; i++) {
      for (int j = 0; j <= maxX; j++) {
        if (grid[i][j]) {
          System.out.print("x");
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    System.out.println(count);

  }

  private static void foldUp(int fold, int maxY, int maxX, boolean[][] grid) {
    for (int i = fold + 1; i <= maxY; i++) {
      for (int j = 0; j <= maxX; j++) {
        if (grid[i][j]) {
          grid[fold - (i - fold)][j] = true;
          grid[i][j] = false;
        }
      }
    }
  }

  private static void foldLeft(int fold, int maxY, int maxX, boolean[][] grid) {
    for (int i = 0; i <= maxY; i++) {
      for (int j = fold + 1; j <= maxX; j++) {
        if (grid[i][j]) {
          grid[i][fold - (j - fold)] = true;
          grid[i][j] = false;
        }
      }
    }
  }

  private static void partTwo(List<String> lines) {
  }


}

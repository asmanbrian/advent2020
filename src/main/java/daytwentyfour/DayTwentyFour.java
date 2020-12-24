package daytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayTwentyFour {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentyfourinput.txt"));

    boolean[][] grid = new boolean[200][200];

    int start = 100;

    partOne(lines, grid, start);

    for (int day = 0; day < 100; day++) {
      boolean[][] newGrid = new boolean[200][200];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          int adj = countAdjacent(grid, i, j);
          if (grid[i][j]) {
            newGrid[i][j] = adj != 0 && adj <= 2;
          } else {
            newGrid[i][j] = adj == 2;
          }
        }
      }
      grid = newGrid;
      System.out.println("Day " + (day + 1) + ": " + getBlackCount(grid));
    }
  }

  private static int countAdjacent(boolean[][] grid, int i, int j) {
    int adj = 0;
    try {
      for (int yOff = -1; yOff < 2; yOff++) {
        if (yOff == 0) {
          if (grid[i][j - 1]) {
            adj++;
          }

          if (grid[i][j + 1]) {
            adj++;
          }
          continue;
        }
        int y = i + yOff;
        if (grid[y][j + i % 2]) {
          adj++;
        }
        if (grid[y][j + (i % 2) - 1]) {
          adj++;
        }
      }
    } catch (Exception e) {
      if (grid[i][j] || adj > 0) {
        throw new RuntimeException("need to expand");
      }
    }
    return adj;
  }

  public static void partOne(List<String> lines, boolean[][] grid, int start) {
    for (String line : lines) {
      int xPos = start;
      int yPos = start;
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == 'e') {
          xPos += 1;
          continue;
        }
        if (line.charAt(i) == 'w') {
          xPos -= 1;
          continue;
        }

        if (line.charAt(i + 1) == 'e') {
          xPos += yPos % 2;
        } else {
          xPos += (yPos % 2) - 1;
        }
        if (line.charAt(i) == 's') {
          yPos += 1;
        } else {
          yPos -= 1;
        }
        i++;
      }
      System.out.println("y: " + yPos + " x: " + xPos);
      grid[yPos][xPos] = !grid[yPos][xPos];
    }
    int count = getBlackCount(grid);

    System.out.println("\n" + count);
  }

  public static int getBlackCount(boolean[][] grid) {
    int count = 0;
    for (boolean[] row : grid) {
      for (boolean cell : row) {
        if (cell) {
          count++;
        }
      }
    }
    return count;
  }
}

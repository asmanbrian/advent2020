package daythree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DayThree {

  public static void main(String[] args) throws IOException {
    Object[] lines = Files.lines(Paths.get("src/main/resources/daythreeinput.txt")).toArray();
    char[][] grid = new char[lines.length][((String) lines[0]).length()];
    for (int i = 0; i < lines.length; i++) {
      String line = (String) lines[i];
      System.out.println();
      for (int j = 0; j < line.length(); j++) {
        grid[i][j] = line.charAt(j);
        System.out.print(line.charAt(j));
      }

    }
    long trees = getTreesWithSlope(grid, 1, 1) *
        getTreesWithSlope(grid, 3, 1) *
        getTreesWithSlope(grid, 5, 1) *
        getTreesWithSlope(grid, 7, 1) *
        getTreesWithSlope(grid, 1, 2);
    System.out.println(trees);
  }

  private static long getTreesWithSlope(char[][] grid, int xSlope, int ySlope) {
    long trees = 0;
    int x = 0;
    int y = 0;
    while (y < grid.length) {
      if (grid[y][x] == '#') {
        trees++;
      }
      x = (x + xSlope) % grid[y].length;
      y += ySlope;
    }
    return trees;
  }
}

package twenty.dayeleven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DayEleven {
  public static void main(String[] args) throws IOException {
    List<char[]> lines = Files.lines(Paths.get("src/main/resources/dayeleveninput.txt"))
        .map(String::toCharArray).collect(Collectors.toList());
    int adjacencyRule = 5; // change for part one or two
    int rowLength = lines.get(0).length;
    char[][] grid = new char[lines.size()][rowLength];
    for (int i = 0; i < lines.size(); i++) {
      grid[i] = lines.get(i);
    }

    while (true) {


      char[][] nextGrid = new char[lines.size()][rowLength];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < rowLength; j++) {
          if (grid[i][j] == '.') {
            nextGrid[i][j] = '.';
            continue;
          }
          int adj = countOccupiedAdjacentSeatsPartTwo(i, j, grid, rowLength);
          if (grid[i][j] == 'L') {
            if (adj == 0) {
              nextGrid[i][j] = '#';
            } else {
              nextGrid[i][j] = 'L';
            }
            continue;
          }
          if (adj >= adjacencyRule) {
            nextGrid[i][j] = 'L';
          } else {
            nextGrid[i][j] = '#';
          }
        }
      }

      int occupied = 0;
      boolean same = true;
      for (int i = 0; i < grid.length; i++) {
        if (!same) {
          break;
        }
        for (int j = 0; j < rowLength; j++) {
          if (grid[i][j] != nextGrid[i][j]) {
            same = false;
            break;
          }
          if (grid[i][j] == '#') {
            occupied++;
          }
        }
      }
      if (same) {
        System.out.println("occupied " + occupied);
        break;
      }
      grid = nextGrid;
    }
  }

  private static int countOccupiedAdjacentSeatsPartOne(int y, int x, char[][] grid, int rowLength) {
    int count = 0;
    for (int i = y - 1; i < y + 2; i++) {
      if (i < 0 || i >= grid.length) {
        continue;
      }
      for (int j = x - 1; j < x + 2; j++) {
        if (j < 0 || j >= rowLength || (i == y && j == x)) {
          continue;
        }
        if (grid[i][j] == '#') {
          count++;
        }
      }
    }
    return count;
  }

  private static int countOccupiedAdjacentSeatsPartTwo(int y, int x, char[][] grid, int rowLength) {
    int count = 0;

    for (int u = y - 1; u < y + 2; u++) {
      for (int o = x - 1; o < x + 2; o++) {
        if (u == y && o == x) {
          continue;
        }
        int i = u;
        int j = o;
        while (i >= 0 && j >= 0 && i < grid.length && j < rowLength) {
          if (i == y && j == x) {
            continue;
          }
          if (grid[i][j] == '#') {
            count++;
          }
          if (grid[i][j] != '.') {
            break;
          }
          i += Integer.compare(i, y);
          j += Integer.compare(j, x);
        }
      }
    }

    return count;
  }
}

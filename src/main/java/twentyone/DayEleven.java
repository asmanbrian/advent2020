package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DayEleven {
  public static void main(String[] args) throws IOException {
    List<String> lines =
        Files.lines(Paths.get("src/main/resources/twentyone/dayeleveninput.txt")).collect(Collectors.toList());
    partTwo(lines);

  }

  private static void partOne(List<String> lines) {
    long allFlashes = 0L;
    Octopus[][] grid = createGrid(lines);

    for (int step = 1; step < 101; step++) {
      incrementGrid(grid);
      flashGrid(grid);
      allFlashes += countFlashesAndReset(grid);
    }
    //    for (int i = 0; i < grid.length; i++) {
    //      for (int j = 0; j < grid[i].length; j++) {
    //        System.out.print(grid[i][j]);
    //      }
    //      System.out.println();
    //    }
    System.out.println(allFlashes);
  }

  private static void partTwo(List<String> lines) {
    long allFlashes = 0L;
    Octopus[][] grid = createGrid(lines);
    int size = grid.length * grid[0].length;
    for (int step = 1; ; step++) {
      incrementGrid(grid);
      flashGrid(grid);
      int flashes = countFlashesAndReset(grid);
      if (flashes == size) {
        System.out.println(step);
        return;
      }
    }
    //    for (int i = 0; i < grid.length; i++) {
    //      for (int j = 0; j < grid[i].length; j++) {
    //        System.out.print(grid[i][j]);
    //      }
    //      System.out.println();
    //    }
  }

  private static int countFlashesAndReset(Octopus[][] grid) {
    int flashes = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].power > 9) {
          flashes++;
          grid[i][j].power = 0;
          grid[i][j].flashed = false;
        }
      }
    }
    return flashes;
  }

  private static void flashGrid(Octopus[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].power > 9 && !grid[i][j].flashed) {
          flash(j, i, grid);
        }
      }
    }
  }

  private static void incrementGrid(Octopus[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j].power++;
      }
    }
  }

  private static Octopus[][] createGrid(List<String> lines) {
    Octopus[][] grid = new Octopus[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).length(); j++) {
        grid[i][j] = new Octopus(Integer.parseInt("" + lines.get(i).charAt(j)));
      }
    }
    return grid;
  }

  private static void flash(int x, int y, Octopus[][] grid) {
    grid[y][x].flashed = true;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (y + i >= 0 && x + j >= 0 && y + i < grid.length && x + j < grid[0].length) {
          if (i == 0 && j == 0) {
            continue;
          }
          grid[y + i][x + j].power++;
          if (grid[y + i][x + j].power > 9 && !grid[y + i][x + j].flashed) {
            flash(x + j, y + i, grid);
          }
        }
      }
    }
  }


  private static class Octopus {
    boolean flashed = false;
    int power;

    public Octopus(int power) {
      this.power = power;
    }

    @Override
    public String toString() {
      return "" + power;
    }
  }
}

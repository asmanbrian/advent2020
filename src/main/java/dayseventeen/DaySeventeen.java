package dayseventeen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DaySeventeen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/dayseventeeninput.txt")).collect(
        Collectors.toList());
    int gridSize = 30;
    int xStart = gridSize / 2 - lines.get(0).length() / 2;
    int yStart = gridSize / 2 - lines.size() / 2;

    //w,z,y,x
    int[][][][] grid = new int[gridSize][gridSize][gridSize][gridSize];
    Set<Coordinate> coordsToCheck = new HashSet<>();
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).length(); j++) {
        if (lines.get(i).charAt(j) == '#') {
          grid[gridSize / 2 - 1][gridSize / 2 - 1][i + yStart][j + xStart] = 1;
          for (int w = -1; w < 2; w++) {
            for (int z = -1; z < 2; z++) {
              for (int y = -1; y < 2; y++) {
                for (int x = -1; x < 2; x++) {
                  coordsToCheck
                      .add(new Coordinate(gridSize / 2 - 1 + w, gridSize / 2 - 1 + z, i + yStart + y, j + xStart + x));
                }
              }
            }
          }
        }
      }
    }
    for (int turn = 0; turn < 6; turn++) {
      int[][][][] newGrid = new int[gridSize][gridSize][gridSize][gridSize];
      Set<Coordinate> nextCoordsToCheck = new HashSet<>();
      for (Coordinate coord : coordsToCheck) {
        int newValue = findNewValue(coord.w, coord.z, coord.y, coord.x, grid, nextCoordsToCheck);
        newGrid[coord.w][coord.z][coord.y][coord.x] = newValue;
      }

      grid = newGrid;
      coordsToCheck = nextCoordsToCheck;
      System.out.println("turn " + turn);
      int active = 0;
      for (int b = 0; b < grid.length; b++) {
        for (int i = 0; i < grid.length; i++) {
          for (int j = 0; j < grid[i].length; j++) {
            for (int k = 0; k < grid[i][j].length; k++) {
              if (grid[b][i][j][k] == 1) {
                active++;
              }
            }
          }
        }
      }
      System.out.println(active);
    }
  }

  private static int findNewValue(int w, int z, int y, int x, int[][][][] grid,
      Set<Coordinate> nextCoordsToCheck) {
    if (w < 2 || z < 2 || y < 2 || x < 2 || w >= grid.length - 2 || z >= grid.length - 2 || y >= grid.length - 2
        || x >= grid.length - 2) {
      throw new RuntimeException("grid needs to expand");
    }
    
    int activeNeighbor = countNeighbors(w, z, y, x, grid, nextCoordsToCheck);
    if (grid[w][z][y][x] == 0) {
      if (activeNeighbor == 3) {
        return 1;
      } else {
        return 0;
      }
    } else {
      if (activeNeighbor == 2 || activeNeighbor == 3) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  private static int countNeighbors(int b, int i, int j, int k, int[][][][] grid, Set<Coordinate> nextCoordsToCheck) {
    int activeNeighbor = 0;
    for (int w = -1; w < 2; w++) {
      for (int z = -1; z < 2; z++) {
        for (int y = -1; y < 2; y++) {
          for (int x = -1; x < 2; x++) {
            if (w == 0 && z == 0 && y == 0 && x == 0) {
              continue;
            }
            nextCoordsToCheck.add(new Coordinate(w + b, z + i, y + j, x + k));
            if (grid[w + b][z + i][y + j][x + k] == 1) {
              activeNeighbor++;
              if (activeNeighbor == 4) {
                return activeNeighbor;
              }
            }
          }
        }
      }

    }
    return activeNeighbor;
  }


  public static class Coordinate {

    int w;
    int x;
    int y;
    int z;

    public Coordinate(int w, int z, int y, int x) {
      this.w = w;
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Coordinate that = (Coordinate) o;
      return w == that.w &&
          x == that.x &&
          y == that.y &&
          z == that.z;
    }

    @Override
    public int hashCode() {
      return Objects.hash(w, x, y, z);
    }
  }

}

package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DayNine {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/daynineinput.txt"))
        .collect(Collectors.toCollection(ArrayList::new));
    partTwo(lines);

  }

  private static void partOne(List<String> lines) {
    int sum = 0;
    Point[][] grid = createGrid(lines);


    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        List<Point> adjacents = getAdjacent(j, i, grid);
        boolean lowPoint = true;
        for (Point adj : adjacents) {
          if (grid[i][j].value >= adj.value) {
            lowPoint = false;
          }
        }
        if (lowPoint) {
          sum += 1 + grid[i][j].value;
        }
      }
    }

    System.out.println(sum);
  }

  private static List<Point> getAdjacent(int x, int y, Point[][] grid) {
    List<Point> ret = new ArrayList<>();
    if (y > 0) {
      ret.add(grid[y - 1][x]);
    }
    if (y < grid.length - 1) {
      ret.add(grid[y + 1][x]);
    }
    if (x > 0) {
      ret.add(grid[y][x - 1]);
    }
    if (x < grid[0].length - 1) {
      ret.add(grid[y][x + 1]);
    }
    return ret;
  }

  private static void partTwo(List<String> lines) {
    Point[][] grid = createGrid(lines);
    List<Integer> basinSizes = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        List<Point> adjacents = getAdjacent(j, i, grid);
        boolean lowPoint = true;
        for (Point adj : adjacents) {
          if (grid[i][j].value >= adj.value) {
            lowPoint = false;
          }
        }
        if (lowPoint) {
          basinSizes.add(traverseBasin(grid[i][j], grid));
        }
      }
    }
    basinSizes = basinSizes.stream().sorted().collect(Collectors.toList());
    int ret = 1;
    for (int i = basinSizes.size() - 1; i > basinSizes.size() - 4; i--) {
      ret *= basinSizes.get(i);
    }
    System.out.println(ret);
  }

  private static int traverseBasin(Point point, Point[][] grid) {
    if (point.traversed || point.value == 9) {
      return 0;
    }
    int size = 1;
    point.traversed = true;
    for (Point adj : getAdjacent(point.x, point.y, grid)) {
      size += traverseBasin(adj, grid);
    }
    return size;

  }

  private static Point[][] createGrid(List<String> lines) {
    Point[][] grid = new Point[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      char[] line = lines.get(i).toCharArray();
      for (int j = 0; j < line.length; j++) {
        grid[i][j] = new Point(Integer.parseInt(String.valueOf(line[j])), j, i);
      }
    }
    return grid;
  }

  static class Point {
    int value;
    boolean traversed;
    int x;
    int y;

    public Point(int value, int x, int y) {
      this.value = value;
      traversed = false;
      this.x = x;
      this.y = y;
    }
  }

}

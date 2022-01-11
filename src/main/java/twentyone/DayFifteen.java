package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class DayFifteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayfifteeninput.txt"))
        .collect(Collectors.toCollection(ArrayList::new));
    partTwo(lines);

  }

  private static void partOne(List<String> lines) {
    Position[][] grid = new Position[lines.size()][lines.get(0).length()];
    PriorityQueue<Position> unvisited = new PriorityQueue<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < lines.get(0).length(); j++) {
        Position position = new Position(Integer.parseInt("" + lines.get(i).charAt(j)));
        unvisited.add(position);
        grid[i][j] = position;
        addNeighbors(grid, j, i, position);
      }
    }
    grid[0][0].riskFromStart = 0;
    Position end = grid[grid.length - 1][grid[0].length - 1];
    Set<Position> visited = new HashSet<>();
    while (!visited.contains(end)) {
      Position current = unvisited.poll();
      for (Position p : current.neighbors) {
        if (!visited.contains(p)) {
          if (p.riskFromStart > p.risk + current.riskFromStart) {
            unvisited.remove(p);
            p.riskFromStart = p.risk + current.riskFromStart;
            unvisited.add(p);
          }
        }
      }
      visited.add(current);
    }

    System.out.println(end.riskFromStart);
  }



  private static void partTwo(List<String> lines) {
    int baseLength = lines.size();
    int baseWidth = lines.get(0).length();
    Position[][] grid = new Position[baseLength * 5][baseWidth * 5];
    PriorityQueue<Position> unvisited = new PriorityQueue<>();
    for (int i = 0; i < baseLength; i++) {
      for (int j = 0; j < baseWidth; j++) {
        for (int k = 0; k < 5; k++) {
          //          int y = i + (baseLength * k);
          int x = j + (baseWidth * k);
          int risk = (Integer.parseInt("" + lines.get(i).charAt(j)) + k);
          if (risk > 9) {
            risk -= 9;
          }
          Position position = new Position(risk);
          unvisited.add(position);
          grid[i][x] = position;
          addNeighbors(grid, x, i, position);

          for (int f = 1; f < 5; f++) {
            int y = i + (baseLength * f);
            risk = grid[y - baseLength][x].risk + 1;
            if (risk > 9) {
              risk -= 9;
            }
            position = new Position(risk);
            unvisited.add(position);
            grid[y][x] = position;
            addNeighbors(grid, x, y, position);
          }
          //          position = new Position(risk);
          //          unvisited.add(position);
          //          grid[i][x] = position;
          //          addNeighbors(grid, x, i, position);
        }

      }
    }

    for (int f = 0; f < grid.length; f++) {
      System.out.println();
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[f][j] != null) {
          System.out.print(grid[f][j].risk);
        } else {
          System.out.print(0);
        }
      }
    }
    grid[0][0].riskFromStart = 0;
    Position end = grid[grid.length - 1][grid[0].length - 1];
    Set<Position> visited = new HashSet<>();
    while (!visited.contains(end)) {
      Position current = unvisited.poll();
      for (Position p : current.neighbors) {
        if (!visited.contains(p)) {
          if (p.riskFromStart > p.risk + current.riskFromStart) {
            unvisited.remove(p);
            p.riskFromStart = p.risk + current.riskFromStart;
            unvisited.add(p);
          }
        }
      }
      visited.add(current);
    }
    System.out.println();
    System.out.println(end.riskFromStart);
  }

  private static void addNeighbors(Position[][] grid, int x, int y, Position position) {
    if (y > 0) {
      Position top = grid[y - 1][x];
      if (top != null) {
        top.neighbors.add(position);
        position.neighbors.add(top);
      }
    }
    if (x > 0) {
      Position left = grid[y][x - 1];
      if (left != null) {
        left.neighbors.add(position);
        position.neighbors.add(left);
      }
    }
    if (y < grid.length - 1) {
      Position down = grid[y + 1][x];
      if (down != null) {
        down.neighbors.add(position);
        position.neighbors.add(down);
      }
    }
    if (x < grid[0].length - 1) {
      Position right = grid[y][x + 1];
      if (right != null) {
        right.neighbors.add(position);
        position.neighbors.add(right);
      }
    }
  }

  public static class Position implements Comparable<Position> {
    int risk;
    int riskFromStart = Integer.MAX_VALUE;
    Set<Position> neighbors = new HashSet<>();

    public Position(int risk) {
      this.risk = risk;
    }

    @Override
    public int compareTo(Position o) {
      return Integer.compare(this.riskFromStart, o.riskFromStart);
    }
  }
}

package daytwenty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayTwenty {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentyinput.txt"));
    List<Tile> tiles = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).startsWith("Tile")) {
        int id = Integer.parseInt(lines.get(i).replace(":", "").split(" ")[1]);
        char[][] grid = new char[10][10];
        for (int j = 0; j < 10; j++) {
          for (int k = 0; k < 10; k++) {
            grid[j][k] = lines.get(j + i + 1).charAt(k);
          }
        }
        Tile tile = new Tile(id, grid);
        tiles.add(tile);
      }
      i += 11;
    }

    long result = 1L;
    for (Tile tile : tiles) {
      int matches = 0;
      for (String edge : tile.getEdges()) {
        boolean match = false;
        for (Tile otherTile : tiles) {
          if (tile.id == otherTile.id) {
            continue;
          }
          for (int i = 0; i < 4; i++) {
            otherTile.rotate();
            if (edge.equals(otherTile.getEdges()[0])) {
              match = true;
              matches++;
              break;
            }
          }
          if (!match) {
            otherTile.flip();
            for (int i = 0; i < 4; i++) {
              otherTile.rotate();
              if (edge.equals(otherTile.getEdges()[0])) {
                match = true;
                matches++;
                break;
              }
            }
          }
          if (match) {
            break;
          }
        }
      }
      if (matches == 2) {
        result *= tile.id;
        System.out.println(tile.id);
      }
    }
    System.out.println(result);

  }


  public static class Tile {
    int id;
    char[][] grid;

    public Tile(int id, char[][] grid) {
      this.id = id;
      this.grid = grid;
    }

    public void rotate() {
      char[][] transpose = new char[10][10];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          transpose[j][i] = grid[i][j];
        }
      }
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          grid[i][j] = transpose[i][grid.length - 1 - j];
        }
      }
    }

    public void flip() {
      char[][] flip = new char[10][10];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          flip[i][j] = grid[grid.length - 1 - i][j];
        }
      }
      grid = flip;
    }

    public String[] getEdges() {
      StringBuilder leftEdgeBuilder = new StringBuilder();
      StringBuilder rightEdgeBuilder = new StringBuilder();
      for (int i = 0; i < 10; i++) {
        leftEdgeBuilder.append(grid[i][0]);
        rightEdgeBuilder.append(grid[i][9]);
      }
      String topEdge = new String(grid[0]);
      String bottomEdge = new String(grid[9]);
      String leftEdge = leftEdgeBuilder.toString();
      String rightEdge = rightEdgeBuilder.toString();
      return new String[] {topEdge, bottomEdge, leftEdge, rightEdge};
    }
  }

}

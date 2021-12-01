package twenty.daytwenty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayTwenty {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentyinput.txt"));
    List<Tile> unplacedTiles = new ArrayList<>();
    Tile start = null;
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
        //found during part one
        if (tile.id != 1327) {
          unplacedTiles.add(tile);
        } else {
          start = tile;
        }
      }
      i += 11;
    }
    Tile startOfLine = start;
    boolean found;
    found = rotateAndCreateGrid(unplacedTiles, startOfLine);
    if (!found) {
      startOfLine.flip();
      found = rotateAndCreateGrid(unplacedTiles, startOfLine);
    }
    if (!found) {
      System.out.println("Invalid top left corner");
    }

    List<List<Character>> image = new ArrayList<>();
    image.add(new ArrayList<>());
    Tile current = start;
    startOfLine = start;
    int offset = 0;
    while (current != null) {
      for (int j = 0; j < current.image.size(); j++) {
        if (j + offset >= image.size()) {
          image.add(new ArrayList<>());
        }
        image.get(j + offset).addAll(current.image.get(j));
      }
      current = current.neighbors[1];
      if (current == null) {
        offset += 8;
        current = startOfLine.neighbors[2];
        startOfLine = current;
      }
    }

    //                  #
    //#    ##    ##    ###
    // #  #  #  #  #  #
    int dragons = 0;
    for (int t = 0; t < 4; t++) {
      image = rotate(image);

      dragons = findDragons(image);
      if (dragons > 0) {
        break;
      }
    }

    if (dragons == 0) {
      image = flip(image);
    }
    for (int t = 0; t < 4; t++) {
      image = rotate(image);

      dragons = findDragons(image);
      if (dragons > 0) {
        break;
      }
    }
    System.out.println("\n" + dragons);

    long sum = image.stream().mapToLong(l -> l.stream().filter(c -> c == '#').count()).sum();
    sum = sum - 14 * dragons;
    System.out.println(sum);
  }

  private static int findDragons(List<List<Character>> image) {
    int dragons = 0;
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        try {
          //could probably do this with a bitmask but oh well

          //                  #
          //#    ##    ##    ###
          // #  #  #  #  #  #
          if (image.get(i).get(j) == '#' &&
              image.get(i + 1).get(j - 18) == '#' &&
              image.get(i + 1).get(j - 13) == '#' &&
              image.get(i + 1).get(j - 12) == '#' &&
              image.get(i + 1).get(j - 7) == '#' &&
              image.get(i + 1).get(j - 6) == '#' &&
              image.get(i + 1).get(j - 1) == '#' &&
              image.get(i + 1).get(j) == '#' &&
              image.get(i + 1).get(j + 1) == '#' &&
              image.get(i + 2).get(j - 17) == '#' &&
              image.get(i + 2).get(j - 14) == '#' &&
              image.get(i + 2).get(j - 11) == '#' &&
              image.get(i + 2).get(j - 8) == '#' &&
              image.get(i + 2).get(j - 5) == '#' &&
              image.get(i + 2).get(j - 2) == '#') {
            dragons++;
            image.get(i).set(j, 'O');
          }
        } catch (Exception e) {
        }
      }
    }
    return dragons;
  }

  private static List<List<Character>> rotate(List<List<Character>> image) {
    List<List<Character>> copy = new ArrayList<>(image.size());
    while (copy.size() < image.size()) {
      copy.add(new ArrayList<>(image.size()));
    }
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.size(); j++) {
        copy.get(j).add(image.get(i).get(j));
      }
    }

    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.size(); j++) {
        image.get(i).set(j, copy.get(i).get(image.size() - 1 - j));
      }
    }
    return image;
  }

  private static List<List<Character>> flip(List<List<Character>> image) {
    List<List<Character>> copy = new ArrayList<>(image.size());
    for (int i = 0; i < image.size(); i++) {
      copy.add(image.get(image.size() - 1 - i));
    }
    return copy;
  }
/*
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
    }*/

  private static boolean rotateAndCreateGrid(List<Tile> unplacedTiles, Tile startOfLine) {
    for (int i = 0; i < 4; i++) {
      for (Tile tile : unplacedTiles) {
        tile.neighbors = new Tile[4];
      }
      startOfLine.neighbors = new Tile[4];
      startOfLine.rotate();
      List<Tile> tilesCopy = new ArrayList<>();
      tilesCopy.addAll(unplacedTiles);
      if (createTileGrid(tilesCopy, startOfLine)) {
        return true;
      }
    }
    return false;
  }

  private static boolean createTileGrid(List<Tile> unplacedTiles, Tile startOfLine) {
    Tile current = startOfLine;
    while (!unplacedTiles.isEmpty()) {
      while (current != null && !unplacedTiles.isEmpty()) {
        current = findNeighbor(current, 1, unplacedTiles);
      }
      Tile nextLineStart = findNeighbor(startOfLine, 2, unplacedTiles);
      if (nextLineStart == null && unplacedTiles.size() > 2) {
        return false;
      }
      current = nextLineStart;
      startOfLine = current;
    }
    return true;
  }

  private static Tile findNeighbor(Tile current, int orien, List<Tile> unplacedTiles) {
    if (current == null) {
      return null;
    }
    String edge = current.getEdges()[orien];

    for (Tile otherTile : unplacedTiles) {
      if (current.id == otherTile.id) {
        continue;
      }
      int oppositeOrien = (orien + 2) % 4;
      if (findNeighbor(orien, current, edge, otherTile, oppositeOrien)) {
        unplacedTiles.remove(otherTile);
        return otherTile;
      }
      otherTile.flip();
      if (findNeighbor(orien, current, edge, otherTile, oppositeOrien)) {
        unplacedTiles.remove(otherTile);
        return otherTile;
      }
    }

    return null;
  }

  private static boolean findNeighbor(int orien, Tile tile, String edge, Tile otherTile,
      int oppositeOrien) {
    for (int i = 0; i < 4; i++) {
      otherTile.rotate();
      if (edge.equals(otherTile.getEdges()[oppositeOrien])) {
        tile.neighbors[orien] = otherTile;
        otherTile.neighbors[oppositeOrien] = tile;
        return true;
      }
    }
    return false;
  }


  public static class Tile {
    int id;
    char[][] grid;
    Tile[] neighbors = new Tile[4];
    List<List<Character>> image;

    public Tile(int id, char[][] grid) {
      this.id = id;
      this.grid = grid;
      image = getImage();
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
      image = getImage();
    }

    public void flip() {
      char[][] flip = new char[10][10];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          flip[i][j] = grid[grid.length - 1 - i][j];
        }
      }
      grid = flip;
      image = getImage();
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
      return new String[] {topEdge, rightEdge, bottomEdge, leftEdge};
    }

    private List<List<Character>> getImage() {
      List<List<Character>> image = new ArrayList<>();
      for (int i = 0; i < 8; i++) {
        image.add(new ArrayList<>());
        for (int j = 0; j < 8; j++) {
          image.get(i).add(grid[i + 1][j + 1]);
        }
      }
      return image;
    }

  }


}

package twenty.daytwentytwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DayTwentyTwo {
  static int gameNumber = 1;

  public static void main(String[] args) throws IOException {

    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentytwoinput.txt"));
    boolean addingPlayerOne = true;
    List<Integer> playerOneDeck = new LinkedList<>();
    List<Integer> playerTwoDeck = new LinkedList<>();
    for (String line : lines) {
      if (line.equals("Player 1:") || line.isEmpty()) {
        continue;
      }
      if (line.equals("Player 2:")) {
        addingPlayerOne = false;
        continue;
      }
      if (addingPlayerOne) {
        playerOneDeck.add(Integer.parseInt(line));
      } else {
        playerTwoDeck.add(Integer.parseInt(line));
      }
    }

    int winner = playRecursiveGame(playerOneDeck, playerTwoDeck);
    List<Integer> winningDeck = winner == 1 ? playerOneDeck : playerTwoDeck;
    System.out.println(winner);

    long score = 0L;
    for (int i = 0; i < winningDeck.size(); i++) {
      score += winningDeck.get(i) * (winningDeck.size() - i);
    }
    System.out.println("\n" + score);
  }

  public static int playRecursiveGame(List<Integer> playerOneDeck, List<Integer> playerTwoDeck) {
    Set<DeckPair> deckHistory = new HashSet<>();
    while (!playerOneDeck.isEmpty() && !playerTwoDeck.isEmpty()) {
      DeckPair current = new DeckPair(playerOneDeck, playerTwoDeck);
      if (deckHistory.contains(current)) {
        return 1;
      }
      deckHistory.add(current);
      int one = playerOneDeck.remove(0);
      int two = playerTwoDeck.remove(0);
      int roundWinner;
      if (playerOneDeck.size() >= one && playerTwoDeck.size() >= two) {
        List<Integer> oneCopy = new LinkedList<>();
        List<Integer> twoCopy = new LinkedList<>();
        for (int i = 0; i < one; i++) {
          oneCopy.add(playerOneDeck.get(i));
        }
        for (int i = 0; i < two; i++) {
          twoCopy.add(playerTwoDeck.get(i));
        }
        roundWinner = playRecursiveGame(oneCopy, twoCopy);
      } else {
        if (one > two) {
          roundWinner = 1;
        } else {
          roundWinner = 2;
        }
      }
      if (roundWinner == 1) {
        playerOneDeck.add(one);
        playerOneDeck.add(two);
      } else {
        playerTwoDeck.add(two);
        playerTwoDeck.add(one);
      }
    }
    int winner;
    if (playerOneDeck.isEmpty()) {
      winner = 2;
    } else {
      winner = 1;
    }
    return winner;
  }

  public static void partOne(List<Integer> playerOneDeck, List<Integer> playerTwoDeck) {
    while (!playerOneDeck.isEmpty() && !playerTwoDeck.isEmpty()) {
      int one = playerOneDeck.remove(0);
      int two = playerTwoDeck.remove(0);
      if (one > two) {
        playerOneDeck.add(one);
        playerOneDeck.add(two);
      } else {
        playerTwoDeck.add(two);
        playerTwoDeck.add(one);
      }
    }
    List<Integer> winner;
    if (playerOneDeck.isEmpty()) {
      winner = playerTwoDeck;
    } else {
      winner = playerOneDeck;
    }
    long score = 0L;
    for (int i = 0; i < winner.size(); i++) {
      score += winner.get(i) * (winner.size() - i);
    }
    System.out.println(score);
  }

  public static class DeckPair {
    List<Integer> playerOneDeck;
    List<Integer> playerTwoDeck;

    public DeckPair(List<Integer> playerOneDeck, List<Integer> playerTwoDeck) {
      this.playerOneDeck = List.copyOf(playerOneDeck);
      this.playerTwoDeck = List.copyOf(playerTwoDeck);
    }

    @Override
    public boolean equals(Object o) {

      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      DeckPair deckPair = (DeckPair) o;

      return Arrays.equals(playerOneDeck.toArray(), deckPair.playerOneDeck.toArray()) &&
          Arrays.equals(playerTwoDeck.toArray(), deckPair.playerTwoDeck.toArray());
    }

    @Override
    public int hashCode() {
      return Objects.hash(Arrays.hashCode(playerOneDeck.toArray()), Arrays.hashCode(playerTwoDeck.toArray()));
    }
  }
}

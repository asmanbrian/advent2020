package twenty.daytwentythree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DayTwentyThree {
  public static void main(String[] args) throws IOException {
    String line = Files.readString(Paths.get("src/main/resources/daytwentythreeinput.txt"));
    Node first = new Node(Character.getNumericValue(line.charAt(0)));
    Node current = first;
    int min = first.num;
    int max = first.num;
    Map<Integer, Node> map = new HashMap<>();
    Set<Integer> pickedUp = new HashSet<>();
    map.put(current.num, current);
    for (int i = 1; i < line.length(); i++) {
      current.next = new Node(Character.getNumericValue(line.charAt(i)));
      current = current.next;
      min = Math.min(min, current.num);
      max = Math.max(max, current.num);
      map.put(current.num, current);
    }
    for (int i = max + 1; i <= 1000000; i++) {
      current.next = new Node(i);
      current = current.next;
      map.put(current.num, current);
    }
    max = 1000000;


    current.next = first;
    current = first;
    for (int i = 0; i < 10000000; i++) {
      pickedUp = new HashSet<>();
      Node formerNext = current.next;
      Node next = formerNext;
      Node lastPickUp = null;
      for (int j = 0; j < 3; j++) {
        pickedUp.add(next.num);
        lastPickUp = next;
        next = next.next;
      }
      current.next = next;
      int dest = current.num - 1;
      if (dest < min) {
        dest = max;
      }
      while (pickedUp.contains(dest)) {
        dest--;
        if (dest < min) {
          dest = max;
        }
      }

      Node destNode = map.get(dest);
      lastPickUp.next = destNode.next;
      destNode.next = formerNext;
      current = current.next;
    }
    current = map.get(1).next;
    System.out.println((long) current.num * current.next.num);
    for (int i = 0; i < 2; i++) {
      System.out.println(current);
      current = current.next;
    }
  }

  public static class Node {
    Node next;
    int num;

    public Node(int num) {
      this.num = num;
    }

    public String toString() {
      return num + "";
    }
  }
}

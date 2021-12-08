package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

public class DayEight {
  public static void main(String[] args) throws IOException {
    Stream<String> lines = Files.lines(Paths.get("src/main/resources/twentyone/dayeightinput.txt"));
    List<String> list = lines.collect(Collectors.toCollection(ArrayList::new));
    partTwo(list);

  }

  private static void partOne(List<String> lines) {
    int count = 0;
    for (String line : lines) {
      String output = line.split(" \\| ")[1];
      for (String number : output.split(" ")) {
        if (number.length() == 2 || number.length() == 4 || number.length() == 3 || number.length() == 7) {
          count++;
        }
      }
    }
    System.out.println(count);
  }

  private static void partTwo(List<String> lines) {
    int sum = 0;
    for (String line : lines) {
      String[] split = line.split(" \\| ");
      String[] inputNums = split[0].split(" ");
      String[] outputNums = split[1].split(" ");

      Set<Character> possibleTop = new HashSet<>();
      Set<Character> possibleTopRight = new HashSet<>();
      Set<Character> possibleMiddle = new HashSet<>();
      Set<Character> possibleBottomRight = new HashSet<>();
      Set<Character> possibleBottom = new HashSet<>();
      Set<Character> possibleBottomLeft = new HashSet<>();
      Set<Character> possibleTopLeft = new HashSet<>();

      List<Set<Character>> possibilities = new ArrayList<>();
      possibilities.add(possibleTop);
      possibilities.add(possibleTopRight);
      possibilities.add(possibleMiddle);
      possibilities.add(possibleBottomRight);
      possibilities.add(possibleBottom);
      possibilities.add(possibleBottomLeft);
      possibilities.add(possibleTopLeft);

      char top = 0;
      char topRight = 0;
      char bottomRight = 0;
      char middle = 0;
      char bottom = 0;
      char bottomLeft = 0;
      char topLeft = 0;
      Set<Character> notSeen =
          Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g').collect(Collectors.toCollection(HashSet::new));
      String one = Arrays.stream(inputNums).filter(s -> s.length() == 2).findFirst().get();
      String four = Arrays.stream(inputNums).filter(s -> s.length() == 4).findFirst().get();
      String seven = Arrays.stream(inputNums).filter(s -> s.length() == 3).findFirst().get();
      String eight = Arrays.stream(inputNums).filter(s -> s.length() == 7).findFirst().get();
      //5 digits = 2, 3, 5,
      //6 digits = 0, 6, 9
      for (char c : one.toCharArray()) {
        notSeen.remove(c);
        possibleTopRight.add(c);
        possibleBottomRight.add(c);
      }

      for (char c : seven.toCharArray()) {
        notSeen.remove(c);
        if (!possibleTopRight.contains(c)) {
          top = c;
          break;
        }
      }

      for (char c : four.toCharArray()) {
        notSeen.remove(c);
        if (!possibleTopRight.contains(c)) {
          possibleTopLeft.add(c);
          possibleMiddle.add(c);
        }
      }

      List<String> unknownFiveDigit =
          Arrays.stream(inputNums).filter(s -> s.length() == 5).collect(Collectors.toList());
      String three;
      for (String s : unknownFiveDigit) {
        boolean hasAll = true;
        for (char c : possibleTopRight) {
          if (!s.contains("" + c)) {
            hasAll = false;
          }
        }
        if (hasAll) {
          three = s;
          for (char c : three.toCharArray()) {
            if (top != c && !possibleTopRight.contains(c)) {
              notSeen.remove(c);
              possibleMiddle.add(c);
              possibleBottom.add(c);
            }
          }
          for (char c : four.toCharArray()) {
            if (!three.contains("" + c)) {
              topLeft = c;
              for (Set<Character> possible : possibilities) {
                possible.remove(c);
              }
              break;
            }
          }
          unknownFiveDigit.remove(s);
          break;
        }
      }

      for (String number : unknownFiveDigit) {
        for (char c : number.toCharArray()) {
          if (notSeen.contains(c)) {
            notSeen.remove(c);
            bottomLeft = c;
            for (Set<Character> possible : possibilities) {
              possible.remove(c);
            }
          }
        }
      }

      List<String> unknownSixDigit =
          Arrays.stream(inputNums).filter(s -> s.length() == 6).collect(Collectors.toList());

      for (String number : unknownSixDigit) {
        Set<Character> numberSet = number.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
        for (char c : possibleTopRight) {
          if (!numberSet.contains(c)) {
            topRight = c;
            for (Set<Character> possibility : possibilities) {
              possibility.remove(c);
            }
            bottomRight = possibleBottomRight.stream().findFirst().get();
            for (Set<Character> possibility : possibilities) {
              possibility.remove(bottomRight);
            }
            break;
          }
        }
      }
      for (String number : unknownSixDigit) {
        Set<Character> numberSet = number.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
        boolean isZero = false;
        for (char c : possibleMiddle) {
          if (!numberSet.contains(c)) {
            isZero = true;
            middle = c;
            for (Set<Character> possibility : possibilities) {
              possibility.remove(c);
            }
            bottom = possibleBottom.stream().findFirst().get();
            for (Set<Character> possibility : possibilities) {
              possibility.remove(bottomRight);
            }
            break;
          }
        }
        if (isZero) {
          break;
        }
      }

      Set<Character> zeroSet = Set.of(top, topRight, bottomRight, bottom, bottomLeft, topLeft);
      Set<Character> oneSet = Set.of(topRight, bottomRight);
      Set<Character> twoSet = Set.of(top, topRight, middle, bottomLeft, bottom);
      Set<Character> threeSet = Set.of(top, topRight, middle, bottomRight, bottom);
      Set<Character> fourSet = Set.of(topLeft, topRight, middle, bottomRight);
      Set<Character> fiveSet = Set.of(top, topLeft, middle, bottomRight, bottom);
      Set<Character> sixSet = Set.of(top, topLeft, middle, bottomLeft, bottomRight, bottom);
      Set<Character> sevenSet = Set.of(top, topRight, bottomRight);
      Set<Character> eightSet = Set.of(top, topRight, bottomRight, bottom, bottomLeft, middle, topLeft);
      Set<Character> nineSet = Set.of(top, topRight, middle, topLeft, bottomRight, bottom);
      Map<Set<Character>, Character> map = new HashMap<>();
      map.put(zeroSet, '0');
      map.put(oneSet, '1');
      map.put(twoSet, '2');
      map.put(threeSet, '3');
      map.put(fourSet, '4');
      map.put(fiveSet, '5');
      map.put(sixSet, '6');
      map.put(sevenSet, '7');
      map.put(eightSet, '8');
      map.put(nineSet, '9');

      StringBuilder outputConverted = new StringBuilder();
      for (String num : outputNums) {
        Set<Character> numberSet = num.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
        for (Map.Entry<Set<Character>, Character> entry : map.entrySet()) {
          if (numberSet.size() == entry.getKey().size()
              && Sets.intersection(numberSet, entry.getKey()).size() == entry.getKey().size()) {
            outputConverted.append(entry.getValue());
            break;
          }
        }
      }
      sum += Integer.parseInt(outputConverted.toString());
    }
    System.out.println(sum);
  }
}


package dayeighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DayEighteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/dayeighteeninput.txt")).collect(Collectors.toList());
    long result = 0L;
    for (String line : lines) {
      result += compute(line);
    }
    System.out.println(result);
  }

  private static long compute(String line) {
    while (line.contains("(")) {
      int lastParenIndex = -1;
      for (int i = line.indexOf('('); i < line.length(); i++) {
        if (line.charAt(i) == '(') {
          lastParenIndex = i;
        } else if (line.charAt(i) == ')') {
          long comp = compute(line.substring(lastParenIndex + 1, i));
          line = line.substring(0, lastParenIndex) + comp + line.substring(i + 1);
          break;
        }
      }
    }

    while (line.contains("+")) {
      int i = line.indexOf("+");
      String left = line.substring(0, i - 1);
      String right = line.substring(i + 2);
      int leftSpaceIndex = left.lastIndexOf(" ");
      int rightSpaceIndex = right.indexOf(" ");
      if (rightSpaceIndex < 0) {
        rightSpaceIndex = right.length();
      }
      String leftNumber = left.substring(leftSpaceIndex + 1);
      String rightNumber = right.substring(0, rightSpaceIndex);
      long result = Long.parseLong(leftNumber) + Long
          .parseLong(rightNumber);
      line =
          line.substring(0, leftSpaceIndex + 1) + result + right.substring(Math.max(1, rightSpaceIndex));
    }

    return computeSinglePrecendence(line);

  }

  private static void partOne(List<String> lines) {
    long result = 0L;
    for (String line : lines) {
      while (line.contains("(")) {
        int lastParenIndex = -1;
        for (int i = line.indexOf('('); i < line.length(); i++) {
          if (line.charAt(i) == '(') {
            lastParenIndex = i;
          } else if (line.charAt(i) == ')') {
            long comp = computeSinglePrecendence(line.substring(lastParenIndex + 1, i));
            line = line.substring(0, lastParenIndex) + comp + line.substring(i + 1);
            break;
          }
        }
      }

      result += computeSinglePrecendence(line);
    }
    System.out.println(result);
  }

  private static long computeSinglePrecendence(String s) {
    String[] split = s.split(" ");
    long left = Long.parseLong(split[0]);
    for (int i = 1; i < split.length; i += 2) {
      if (split[i].equals("+")) {
        left = left + Integer.parseInt(split[i + 1]);
      } else if (split[i].equals("*")) {
        left = left * Integer.parseInt(split[i + 1]);
      } else {
        throw new RuntimeException("expected operator");
      }
    }
    return left;
  }
}

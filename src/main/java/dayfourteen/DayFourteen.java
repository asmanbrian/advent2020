package dayfourteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayFourteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/dayfourteeninput.txt"))
        .collect(Collectors.toList());
    String bitmask = "";
    Map<Long, Long> memory = new HashMap<>();
    for (String line : lines) {
      String[] split = line.split(" ");
      if (split[0].equals("mask")) {
        bitmask = split[2];
        continue;
      }
      Long address = Long.parseLong(split[0].split("\\[|\\]")[1]);
      Long value = Long.parseLong(split[2]);
      StringBuilder bitValue = new StringBuilder();
      addToMemory(0, address, new StringBuilder(), value, bitmask, memory);

    }
    Long sum = 0L;
    for (Long value : memory.values()) {
      sum += value;
    }
    System.out.println(sum);

  }

  private static void addToMemory(int i, long address, StringBuilder addressBuilder, Long value, String bitmask,
      Map<Long, Long> memory) {
    for (; i < 36; i++) {
      long binaryValue = (long) Math.pow(2, 35 - i);
      if (address >= binaryValue) {
        addressBuilder.append("1");
        address -= binaryValue;
      } else {
        addressBuilder.append("0");
      }
      if (bitmask.charAt(i) == '1') {
        addressBuilder.replace(addressBuilder.length() - 1, addressBuilder.length(), "" + bitmask.charAt(i));
      } else if (bitmask.charAt(i) == 'X') {
        StringBuilder addressBuilderCopy = new StringBuilder(addressBuilder.toString());
        addressBuilderCopy.replace(addressBuilderCopy.length() - 1, addressBuilderCopy.length(), "1");
        addToMemory(i + 1, address, addressBuilderCopy, value, bitmask, memory);
        addressBuilder.replace(addressBuilder.length() - 1, addressBuilder.length(), "0");
      }
    }
    memory.put(Long.parseUnsignedLong(addressBuilder.toString(), 2), value);
  }

  private static void partOne(List<String> lines, String bitmask) {
    Map<Integer, Long> memory = new HashMap<>();
    for (String line : lines) {
      String[] split = line.split(" ");
      if (split[0].equals("mask")) {
        bitmask = split[2];
        continue;
      }
      int address = Integer.parseInt(split[0].split("\\[|\\]")[1]);
      Long value = Long.parseLong(split[2]);
      StringBuilder bitValue = new StringBuilder();
      for (int i = 0; i < 36; i++) {
        long binaryValue = (long) Math.pow(2, 35 - i);
        if (value >= binaryValue) {
          bitValue.append("1");
          value -= binaryValue;
        } else {
          bitValue.append("0");
        }
        if (bitmask.charAt(i) != 'X') {
          bitValue.replace(bitValue.length() - 1, bitValue.length(), "" + bitmask.charAt(i));
        }
      }
      memory.put(address, Long.parseUnsignedLong(bitValue.toString(), 2));
    }
    Long sum = 0L;
    for (Long value : memory.values()) {
      sum += value;
    }
    System.out.println(sum);
  }
}

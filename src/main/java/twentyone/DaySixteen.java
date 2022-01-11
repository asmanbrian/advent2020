package twentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DaySixteen {
  public static void main(String[] args) throws IOException {
    String line = Files.lines(Paths.get("src/main/resources/twentyone/daysixteeninput.txt")).findFirst().get();

    Map<Character, String> conversions = new HashMap<>();
    conversions.put('0', "0000");
    conversions.put('1', "0001");
    conversions.put('2', "0010");
    conversions.put('3', "0011");
    conversions.put('4', "0100");
    conversions.put('5', "0101");
    conversions.put('6', "0110");
    conversions.put('7', "0111");
    conversions.put('8', "1000");
    conversions.put('9', "1001");
    conversions.put('A', "1010");
    conversions.put('B', "1011");
    conversions.put('C', "1100");
    conversions.put('D', "1101");
    conversions.put('E', "1110");
    conversions.put('F', "1111");
    StringBuilder binaryLine = new StringBuilder();
    for (char c : line.toCharArray()) {
      binaryLine.append(conversions.get(c));
    }
    partOne(binaryLine.toString());

  }

  private static void partOne(String line) {
    int sum = 0;
    int pos = 0;
    while (pos < line.length()) {
      if (!line.substring(pos).contains("1")) {
        break;
      }
      Packet packet = processPacket(line, pos, line.length());
      sum += packet.version;
      pos += packet.length;
    }
    System.out.println(sum);
  }

  private static Packet processPacket(String line, int start, int limit) {
    System.out.println(line.substring(start, limit));
    int pos = start;
    int version = Integer.parseInt(line.substring(pos, pos + 3), 2);
    StringBuilder value = new StringBuilder();
    long valueLong = 0L;
    pos += 3;

    int type = Integer.parseInt(line.substring(pos, pos + 3), 2);
    pos += 3;
    if (type == 4) {
      while (line.charAt(pos) == '1') {
        value.append(line, pos + 1, pos + 5);
        pos += 5;
      }
      value.append(line, pos + 1, pos + 5);
      pos += 5;
      //      while (pos < limit && line.charAt(pos) == '0') {
      //        System.out.println("skipping zero");
      //        pos++;
      //      }
      valueLong = Long.parseLong(value.toString(), 2);
    } else {
      if (line.charAt(pos) == '0') {
        pos++;
        int bitLength = Integer.parseInt(line.substring(pos, pos + 15), 2);
        pos += 15;
        int newLimit = pos + bitLength;
        while (pos < newLimit) {
          Packet packet = processPacket(line, pos, newLimit);
          version += packet.version;
          pos += packet.length;
        }
      } else {
        pos++;
        int packetLength = Integer.parseInt(line.substring(pos, pos + 11), 2);
        pos += 11;
        for (int i = 0; i < packetLength; i++) {
          Packet packet = processPacket(line, pos, limit);
          version += packet.version;
          pos += packet.length;

        }
      }
    }
    //    System.out.println(line.substring(start, pos));
    System.out.println("value: " + valueLong);
    return new Packet(version, type, valueLong, pos - start);
  }

  private static void partTwo(String line) {
  }

  private static class Packet {
    int version;
    int type;
    long value;
    int length;

    public Packet(int version, int type, long value, int length) {
      this.version = version;
      this.type = type;
      this.value = value;
      this.length = length;
    }
  }
}

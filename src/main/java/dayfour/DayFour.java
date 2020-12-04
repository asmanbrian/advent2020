package dayfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayFour {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/dayfourinput.txt")).collect(Collectors.toList());
    Passport currentPassport = new Passport();
    int validPassports = 0;
    for (String line : lines) {
      if (line == null || line.isBlank()) {
        if (currentPassport.validate()) {
          validPassports++;
        }
        currentPassport = new Passport();
        continue;
      }

      String[] data = line.split(" ");
      Arrays.stream(data).forEach(currentPassport::addData);
    }
    if (currentPassport.validate()) {
      validPassports++;
    }
    
    System.out.println(validPassports);
  }

  public static class Passport {
    int birthYear;
    int issueYear;
    int expirationYear;
    String height;
    String hairColor;
    String eyeColor;
    String passportId;
    String countryId;

    public void addData(String data) {
      String[] keyValue = data.split(":");
      if (keyValue[0] == null || keyValue[1] == null) {
        return;
      }
      switch (keyValue[0]) {
        case ("byr"):
          birthYear = Integer.parseInt(keyValue[1]);
          break;
        case ("iyr"):
          issueYear = Integer.parseInt(keyValue[1]);
          break;
        case ("eyr"):
          expirationYear = Integer.parseInt(keyValue[1]);
          break;
        case ("hgt"):
          height = keyValue[1];
          break;
        case ("hcl"):
          hairColor = keyValue[1];
          break;
        case ("ecl"):
          eyeColor = keyValue[1];
          break;
        case ("pid"):
          passportId = keyValue[1];
          break;
        case ("cid"):
          countryId = keyValue[1];
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + keyValue[0]);
      }
    }

    public boolean validate() {
      boolean validHeight = false;
      if (height != null && !height.isEmpty()) {
        if (height.endsWith("cm")) {
          int heightValue = Integer.parseInt(height.substring(0, height.length() - 2));
          validHeight = heightValue >= 150 && heightValue <= 193;
        } else if (height.endsWith("in")) {
          int heightValue = Integer.parseInt(height.substring(0, height.length() - 2));
          validHeight = heightValue >= 59 && heightValue <= 76;
        }
      }

      boolean validHair = hairColor != null && hairColor.matches("#[0-9a-f]{6}");

      boolean validEye = eyeColor != null && eyeColor.matches("amb|blu|brn|gry|grn|hzl|oth");

      boolean validPassport = passportId != null && passportId.matches("[0-9]{9}");
      boolean validBirth = birthYear >= 1920 && birthYear <= 2002;
      boolean validIssue = issueYear >= 2010 && issueYear <= 2020;
      boolean validExpiration = expirationYear >= 2020 && expirationYear <= 2030;
      return validBirth &&
          validIssue &&
          validExpiration &&
          validHeight &&
          validHair &&
          validEye &&
          validPassport;
    }
  }
}


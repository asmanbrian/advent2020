package twenty.daytwentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DayTwentyOne {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daytwentyoneinput.txt"));
    Map<String, Set<String>> allergenMap = new HashMap<>();
    Map<String, Integer> ingredientMap = new HashMap<>();

    for (String line : lines) {
      String[] split = line.replace(")", "")
          .split(" \\(contains ");

      Set<String> ingredients = Arrays.stream(split[0].split(" ")).collect(Collectors.toSet());
      String[] allergens = split[1].split(", ");
      for (String i : ingredients) {
        if (ingredientMap.containsKey(i)) {
          ingredientMap.put(i, ingredientMap.get(i) + 1);
        } else {

          ingredientMap.put(i, 1);
        }
      }
      for (String allergen : allergens) {
        if (allergenMap.containsKey(allergen)) {
          Set<String> existingList = allergenMap.get(allergen);
          existingList.retainAll(ingredients);
        } else {
          Set<String> newSet = new HashSet<>();
          newSet.addAll(ingredients);
          allergenMap.put(allergen, newSet);
        }
      }
    }
    Set<String> nonAllergens = new HashSet<>();
    nonAllergens.addAll(ingredientMap.keySet());
    for (String i : ingredientMap.keySet()) {
      for (Set<String> allergenSet : allergenMap.values()) {
        if (!nonAllergens.contains(i)) {
          break;
        }
        for (String j : allergenSet) {
          if (i.equals(j)) {
            nonAllergens.remove(i);
            break;
          }
        }
      }
    }
    //    partOne(ingredientMap, nonAllergens);

    Map<String, String> foundAllergens = new HashMap<>();

    while (!allergenMap.isEmpty()) {
      for (Map.Entry<String, Set<String>> entry : allergenMap.entrySet()) {
        if (entry.getValue().size() == 1) {
          String allergen = entry.getValue().stream().findFirst().get();
          allergenMap.remove(entry.getKey());
          allergenMap.values().forEach(i -> i.removeIf(allergen::equals));
          foundAllergens.put(entry.getKey(), allergen);
          break;
        }
      }
    }
    foundAllergens.entrySet().stream().sorted(
        Comparator.comparing(Map.Entry::getKey)).forEach(e -> System.out.print(e.getValue() + ","));

  }

  public static void partOne(Map<String, Integer> ingredientMap, Set<String> nonAllergens) {
    int count = 0;
    for (String i : nonAllergens) {

      count += ingredientMap.get(i);
    }
    System.out.println(count);
  }
}

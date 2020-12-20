package daynineteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayNineteen {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/daynineteeninput.txt"));
    Map<Integer, Rule> rules = new HashMap<>();
    int valid = 0;
    for (String line : lines) {
      if (line.isEmpty()) {
        continue;
      }
      if (line.contains(":")) {
        addRule(line, rules);
        continue;
      }

      Result result = rules.get(0).isValid(line);
      if (result.valid && result.length == line.length()) {
        valid++;
      }
    }
    System.out.println(valid);
  }


  private static void addRule(String s, Map<Integer, Rule> rules) {
    String[] keyAndRule = s.split(":");
    String ruleString = keyAndRule[1];
    int key = Integer.parseInt(keyAndRule[0]);
    if (key == 8) {
      rules.put(key, new Rule8(rules));
      return;
    }
    if (key == 11) {
      rules.put(key, new Rule11(rules));
      return;
    }
    if (key == 0) {
      rules.put(key, new Rule0(rules));
      return;
    }
    if (ruleString.charAt(1) == '"') {
      rules.put(key, new CharRule(ruleString.charAt(2)));
      return;
    }
    String[] options = ruleString.split("\\|");
    List<List<Integer>> optionList = new ArrayList<>();
    for (String option : options) {
      List<Integer> ruleList = new ArrayList<>();
      for (String subRule : option.trim().split(" ")) {
        Integer subRuleKey = Integer.parseInt(subRule);
        ruleList.add(subRuleKey);
      }
      optionList.add(ruleList);
    }
    rules.put(key, new OptionRule(optionList, rules));
  }


  public interface Rule {
    Result isValid(String s);
  }


  public static class OptionRule implements Rule {
    List<List<Integer>> options;
    private final Map<Integer, Rule> ruleMap;

    public OptionRule(List<List<Integer>> options, Map<Integer, Rule> ruleMap) {
      this.options = options;
      this.ruleMap = ruleMap;
    }

    @Override
    public Result isValid(String s) {
      for (List<Integer> list : options) {
        int length = 0;
        String sCopy = s;
        boolean valid = true;
        for (Integer key : list) {
          Rule rule = ruleMap.get(key);
          Result result = rule.isValid(sCopy);
          if (!result.valid) {
            valid = false;
            break;
          }
          length += result.length;
          sCopy = sCopy.substring(result.length);
        }
        if (valid) {
          return new Result(true, length);
        }
      }
      return new Result(false, 0);
    }
  }


  public static class CharRule implements Rule {
    char c;

    public CharRule(char c) {
      this.c = c;
    }

    @Override
    public Result isValid(String s) {
      if (s.length() > 0 && s.charAt(0) == c) {
        return new Result(true, 1);
      } else {
        return new Result(false, 0);
      }
    }

  }


  //0: 8 11
  public static class Rule0 implements Rule {

    private final Map<Integer, Rule> ruleMap;

    public Rule0(Map<Integer, Rule> ruleMap) {
      this.ruleMap = ruleMap;
    }

    @Override
    public Result isValid(String s) {
      Rule rule8 = ruleMap.get(8);
      Rule rule11 = ruleMap.get(11);

      for (int i = 1; i < s.length() - 1; i++) {
        Result eightResult = rule8.isValid(s.substring(0, i));
        Result elevenResult = rule11.isValid(s.substring(i));
        if (eightResult.valid && elevenResult.valid && eightResult.length + elevenResult.length == s.length()) {
          return new Result(true, s.length());
        }
      }
      return new Result(false, 0);
    }
  }


  //8: 42 | 42 8
  public static class Rule8 implements Rule {

    private final Map<Integer, Rule> ruleMap;

    public Rule8(Map<Integer, Rule> ruleMap) {
      this.ruleMap = ruleMap;
    }

    @Override
    public Result isValid(String s) {
      int length = 0;
      int start = 0;
      Rule rule42 = ruleMap.get(42);
      for (int i = 1; i <= s.length(); i++) {
        Result result = rule42.isValid(s.substring(start, i));
        if (result.valid) {
          length += result.length;
          start = i;
        }
      }
      return new Result(length > 0 && length == s.length(), length);
    }


  }


  //11: 42 31 | 42 11 31
  public static class Rule11 implements Rule {

    private final Map<Integer, Rule> rules;

    public Rule11(Map<Integer, Rule> rules) {

      this.rules = rules;
    }

    @Override
    public Result isValid(String s) {
      if (s.length() < 1) {
        return new Result(true, 0);
      }
      Rule rule42 = rules.get(42);
      Rule rule31 = rules.get(31);
      int elevenStart = -1;
      int elevenEnd = -1;
      for (int i = 1; i <= s.length(); i++) {
        Result result = rule42.isValid(s.substring(0, i));
        if (result.valid) {
          elevenStart = i;
          break;
        }
      }
      if (elevenStart == -1) {
        return new Result(false, 0);
      }

      for (int i = s.length() - 1; i >= elevenStart; i--) {
        Result result = rule31.isValid(s.substring(i));
        if (result.valid) {
          elevenEnd = i;
          break;
        }
      }
      if (elevenEnd == -1) {
        return new Result(false, 0);
      }

      int length = 0;
      int start = elevenStart;
      for (int i = elevenStart + 1; i <= elevenEnd; i++) {
        Result result = isValid(s.substring(start, i));
        if (result.valid) {
          length += result.length;
          start = i;
        }
      }
      return new Result(length == elevenEnd - elevenStart, s.length());
    }
  }


  public static class Result {


    boolean valid;
    int length;

    public Result(boolean valid, int length) {
      this.valid = valid;
      this.length = length;
    }

    @Override
    public String toString() {
      return "Result{" +
          "valid=" + valid +
          ", length=" + length +
          '}';
    }
  }
}

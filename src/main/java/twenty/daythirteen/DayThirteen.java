package twenty.daythirteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DayThirteen {
  public static void main(String[] args) throws IOException, InterruptedException {
    List<String> lines = Files.lines(Paths.get("src/main/resources/daythirteeninput.txt")).collect(Collectors.toList());
    String[] busses = lines.get(1).split(",");
    int maxBus = Integer.MIN_VALUE;
    int maxPosition = 0;
    for (int i = 0; i < busses.length; i++) {
      if (busses[i].equals("x")) {
        continue;
      }
      int numericBus = Integer.parseInt(busses[i]);
      if (numericBus > maxBus) {
        maxBus = numericBus;
        maxPosition = i;
      }
    }
    ExecutorService executor = Executors.newFixedThreadPool(8);
    for (int y = 1; y < 9; y++) {
      int finalMaxBus = maxBus;
      int finalMaxPosition = maxPosition;
      int finalY = y;
      executor.execute(() -> {
        try {
          //closest to 100000000000000 that's a multiple of my max bus
          for (long i = 99999999999762L + finalMaxBus * finalY; ; i += (finalMaxBus * 8)) {
            boolean valid = true;
            for (int j = 0; j < busses.length; j++) {
              if (busses[j].equals("x")) {
                continue;
              }
              long time = i - (finalMaxPosition - j);
              if (time % Integer.parseInt(busses[j]) != 0) {
                valid = false;
                break;
              }
            }
            if (valid) {
              System.out.println("found " + (i - finalMaxPosition));
              //this doesn't seem to actually work to shut down everything, oh well
              executor.shutdownNow();
              break;
            }
            if (i % 100000000 == 0) {
              System.out.println(Thread.currentThread().getName() + " " + i + " Start time " + (i - finalMaxPosition));
            }
          }
        } catch (Exception e) {
          System.out.println(e);
        }
      });
    }
    executor.shutdown();
    executor.awaitTermination(333333333, TimeUnit.MINUTES);
  }

  private static void partOne(List<String> lines) {
    int startTime = Integer.parseInt(lines.get(0));
    int[] busses = Arrays.stream(lines.get(1).split(",")).filter(s -> !s.equals("x")).mapToInt(Integer::parseInt)
        .toArray();
    int firstBus = -1;
    int timeWaiting = -1;
    for (int i = startTime; firstBus < 0; i++) {
      for (int j : busses) {
        if (i % j == 0) {
          firstBus = j;
          timeWaiting = i - startTime;
          break;
        }
      }
    }
    System.out.println(firstBus * timeWaiting);
  }
}

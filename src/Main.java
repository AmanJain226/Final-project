import java.util.ArrayList;
import java.util.Scanner;
public class Main {
  private static final Scanner scanner = new Scanner(System.in);
  private static final ArrayList<Player> players = new ArrayList<>();
  public static void main(String[] args) {
    boolean running = true;
    while (running) {
      printMenu();
      String choice = scanner.nextLine().trim();
      switch (choice) {
        case "1" -> 
          addPlayer();
        case "2" -> 
          listPlayers();
        case "3", "4", "5", "6" -> 
          System.out.println("Coming soon(needs Tournament class).");
        case "7" -> {
          System.out.println("Goodbye!");
          running = false;
        }
        case "8" -> 
          testMatch();
        default -> 
          System.out.println("Invalid choice. Enter 1-7.");
      }
    }
  }
  private static void printMenu() {
    System.out.println();
    System.out.println("Ping Pong Tournament Manager");
    System.out.println("1. Add player");
    System.out.println("2. List players");
    System.out.println("3. Start single-elimination tournament");
    System.out.println("4. Start round-robin tournament");
    System.out.println("5. Enter match score");
    System.out.println("6. View bracket/standings");
    System.out.println("7. Save and quit");
    System.out.print("Choose an option: ");
  }
  private static void addPlayer() {
    System.out.println("Player name: ");
    String name = scanner.nextLine().trim();
    if (name.isEmpty()) {
      System.out.println("Name can't be empty.");
      return;
    }
    for (Player p : players) {
      if (p.getName().equalsIgnoreCase(name)) {
        System.out.println("That player already exists");
        return;
      }
    }
    players.add(new Player(name));
    System.out.println("Added " + name + ".");
  }
  private static void listPlayers() {
    if (players.isEmpty()) {
      System.out.println("No players yet.");
      return;
    }
    for (int i = 0; i < players.size(); i++) {
      System.out.println((i+1) + ". " + players.get(i));
    }
  }
  //Temporary method until later to test if Match.java works
  private static void testMatch() {
    if (players.size() < 2) {
      System.out.println("Add at least 2 players first.");
      return;
    }
    Player a = players.get(0);
    Player b = players.get(1);
    int bestOf = readInt("Best of (3 or 5): ");
    Match match;
    try {
      match = new Match(a, b, bestOf);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }
    while (!match.isPlayed()) {
      int g1 = readInt("Games won by " + a.getName() + ": ");
      int g2 = readInt("Games won by " + b.getName() + ": ");
      try {
        match.recordResult(g1, g2);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    Player winner = match.getWinner();
    Player loser;
    if (winner == a) {
      loser = b;
    } else {
      loser = a;
    }
    winner.recordWin();
    loser.recordLoss();
    System.out.println("Winner: " + winner.getName());
  }
  private static int readInt(String prompt) {
    while (true) {
      System.out.println(prompt);
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Please enter a whole number.");
      }
    }
  }
}

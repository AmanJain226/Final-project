import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Main {
  private static final Scanner scanner = new Scanner(System.in);
  private static final ArrayList<Player> players = new ArrayList<>();
  private static Tournament tournament;
  private static RoundRobin roundRobin;
  public static void main(String[] args) {
    boolean running = true;
    loadTournament();
    while (running) {
      printMenu();
      String choice = scanner.nextLine().trim();
      switch (choice) {
        case "1" -> 
          addPlayer();
        case "2" -> 
          listPlayers();
        case "3" -> 
          startSingleElimination();
        case "4" -> 
          startRoundRobin();
        case "5" -> 
          enterScore();
        case "6" ->
          viewBracket();
        case "7" -> {
          System.out.println("Goodbye!");
          running = false;
        }
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
    if (name.contains(",")) {
      System.out.println("Names can't contain commas.");
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
  private static void startSingleElimination() {
    if (players.size() < 2) {
      System.out.println("Add at least 2 players first.");
      return;
    }
    int bestOf = readInt("Best of (3 or 5): ");
    try {
      tournament = new Tournament(players, bestOf);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }
    System.out.println("Bracket generated!");
    roundRobin = null;
    viewBracket();
  }
  private static void enterScore() {
    if (roundRobin != null) {
      enterRoundRobinScore();
      return;
    }
    if (tournament == null) {
      System.out.println("No tournament started yet.");
      return;
    }
    if (tournament.isFinished()) {
      System.out.println("Tournament is over. Champion: " + tournament.getChampion().getName());
      return;
    }
    ArrayList<Match> current = tournament.getCurrentRound();
    System.out.println("Matches waiting for a score:");
    for (int i = 0; i < current.size(); i++) {
      Match m = current.get(i);
      if (!m.isBye() && !m.isPlayed()) {
        System.out.println((i + 1) + ". " + m.getPlayer1().getName() + " vs. " + m.getPlayer2().getName());
      }
    }
    int index = readInt("Match number: ") - 1;
    boolean recorded = false;
    while (!recorded) {
      int g1 = readInt("Games won by player 1: ");
      int g2 = readInt("Games won by player 2: ");
      try {
        tournament.recordResult(index, g1, g2);
        recorded = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        //bad match choice, returns to menu
        if (e.getMessage().startsWith("No such") || e.getMessage().startsWith("That match")) {
          return;
        }
      }
    }
    if (tournament.isFinished()) {
      System.out.println("Champion: " + tournament.getChampion().getName());
    }
  }
  private static void viewBracket() {
    if (roundRobin != null) {
      enterRoundRobinScore();
      return;
    }
    if (tournament == null) {
      System.out.println("No tournament started yet.");
      return;
    }
    ArrayList<ArrayList<Match>> rounds = tournament.getRounds();
    for (int r = 0; r < rounds.size(); r++) {
      System.out.println("Round " + (r + 1));
      ArrayList<Match> matches = rounds.get(r);
      for (int m = 0; m < matches.size(); m++) {
        Match match = matches.get(m);
        String line = "Match " + (m + 1) + ": " + match.getPlayer1().getName();
        if (match.isBye()) {
          line += " (bye)";
        } else {
          line += (" vs. " + match.getPlayer2().getName());
          if (match.isPlayed()) {
            line += (" [" + match.getGames1() + "-" + match.getGames2() + "]");
          }
        }
        System.out.println(line);
      }
    }
    if (tournament.isFinished()) {
      System.out.println("Champion: " + tournament.getChampion().getName());
    }
  }
  private static void saveTournament() {
    try {
      if (roundRobin != null) {
        TournamentStorage.save(roundRobin, TournamentStorage.FILE_PATH);
      } else if (tournament != null) {
         TournamentStorage.save(tournament, TournamentStorage.FILE_PATH);
      } else {
        System.out.println("No tournament to save.");
        return;
      }
      System.out.println("Saved to " + TournamentStorage.FILE_PATH);
    } catch (IOException e) {
      System.out.println("Could not save: " + e.getMessage());
    }
  }
  private static void loadTournament() {
    try {
      String format = TournamentStorage.readFormat(TournamentStorage.FILE_PATH);
      players.clear();
      if (format.equals("ROUNDROBIN")) {
        roundRobin =  TournamentStorage.loadRoundRobin(TournamentStorage.FILE_PATH);
        for (Player p : roundRobin.getPlayers()) {
          players.add(p);
        }
      } else {
        tournament = TournamentStorage.load(TournamentStorage.FILE_PATH);
        for (Player p : tournament.getPlayers()) {
          players.add(p);
        }
      }
      System.out.println("Loaded saved tournament.");
    } catch (FileNotFoundException e) {

    } catch (IOException e) {
      System.out.println("Could not load saved tournament: " + e.getMessage());
    }
  }
  private static void startRoundRobin() {
    if (players.size() < 2) {
      System.out.println("Add at least 2 players first.");
      return;
    }
    int bestOf = readInt("Best of (3 or 5): ");
    try {
      roundRobin = new RoundRobin(players, bestOf);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }
    tournament = null;
    System.out.println("Schedule generated!");
    viewRoundRobin();
  }
  private static void enterRoundRobinScore() {
    ArrayList<Match> matches = roundRobin.getMatches();
    if (roundRobin.isFinished()) {
      System.out.println("All matches are finished.");
      return;
    }
    System.out.println("Matches waiting for a score:");
    for (int i = 0; i < matches.size(); i++) {
      Match m = matches.get(i);
      if (!m.isPlayed()) {
        System.out.println((i + 1) + ". " + m.getPlayer1().getName() + " vs. " + m.getPlayer2().getName());
      }
    }
    int index = readInt("Match number: ") - 1;
    if (index < 0 || index >= matches.size() || matches.get(index).isPlayed()) {
      System.out.println("Pick a match from the list.");
      return;
    }
    boolean recorded = false;
    while (!recorded) {
      int g1 = readInt("Games won by player 1: ");
      int g2 = readInt("Games won by player 2: ");
      try {
        roundRobin.recordResult(index, g1, g2);
        recorded = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    if (roundRobin.isFinished()) {
      System.out.println("All matches are finished.");
    }
  }
  private static void viewRoundRobin() {
    ArrayList<Match> matches = roundRobin.getMatches();
    for (int m = 0; m < matches.size(); m++) {
        Match match = matches.get(m);
        String line = "Match " + (m + 1) + ": " + match.getPlayer1().getName() + " vs. " + match.getPlayer2().getName();
        if (match.isPlayed()) {
          line += (" [" + match.getGames1() + "-" + match.getGames2() + "]");
        }
        System.out.println(line);
    }
    System.out.println("Standings:");
    ArrayList<Player> standings = roundRobin.getStandings();
    for (int i = 0; i < standings.size(); i++) {
      Player p = standings.get(i);
      System.out.println((i+1) + ". " + p.getName() + " " + p.getWins() + "-" + p.getLosses()
                         + " (game diff " + roundRobin.getGameDifferential(p) + ")");
    }
    if (roundRobin.isFinished()) {
      System.out.println("Winner: " + standings.get(0).getName());
    }
  }
}

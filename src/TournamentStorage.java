import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
public class TournamentStorage {
  public static final String FILE_PATH = "data/tournament.txt";
  public static void save(Tournament t, String path) throws IOException {
    try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
      out.println("BESTOF," + t.getBestOf());
      for (Player p : t.getPlayers()) {
        out.println("PLAYER," + p.getName() + "," + p.getWins() + "," + p.getLosses());
      }
      for (ArrayList<Match> round : t.getRounds()) {
        out.println("ROUND");
        for (Match m : round) {
          String second = "";
          if (m.isBye()) {
            second = "BYE";
          } else {
            second = m.getPlayer2().getName();
          }
          out.println("MATCH," + m.getPlayer1().getName() + "," + second + "," + m.getGames1() + "," + m.getGames2() + "," + m.isPlayed());
        }
      }
      if (t.isFinished()) {
        out.println("CHAMPION," + t.getChampion().getName());
      }
    }
  }
  public static Tournament load(String path) throws IOException {
    HashMap<String, Player> byName = new HashMap<>();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<ArrayList<Match>> rounds = new ArrayList<>();
    int bestOf = 3;
    String championName = null;
    try (BufferedReader in = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = in.readLine()) != null) {
        String[] parts = line.split(",");
        switch(parts[0]) {
          case "BESTOF" -> 
            bestOf = Integer.parseInt(parts[1]);
          case "PLAYER" -> {
            Player p = new Player(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
            players.add(p);
            //adds key-value pair to hash map(name, player)
            byName.put(p.getName(), p);
          }
          case "ROUND" -> 
            rounds.add(new ArrayList<>());
          case "MATCH" -> {
            Player p1 = byName.get(parts[1]);
            Player p2 = null;
            if (parts[2].equals("BYE")) {
              p2 = null;
            } else {
              p2 = byName.get(parts[2]);
            }
            Match m = new Match(p1, p2, bestOf);
            //converts a string to a boolean, then the boolean value checks if the match has been played
            if (Boolean.parseBoolean(parts[5])) {
              //records the result based on the scores for each player
              m.recordResult(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
            }
            rounds.get(rounds.size() - 1).add(m);
          }
          case "CHAMPION" -> 
            championName = parts[1];
          default -> {
          }
        }
      }
      Player champion = null;
      if (championName != null) {
        chamption = byName.get(championName);
      }
      return new Tournament(players, bestOf, rounds, champion);
    }
}
  
          
          

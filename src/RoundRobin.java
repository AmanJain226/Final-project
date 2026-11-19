import java.util.ArrayList;
public class RoundRobin {
  private final ArrayList<Player> players;
  private final int bestOf;
  private final ArrayList<Match> matches = new ArrayList<>();
  public RoundRobin(ArrayList<Player> players, int bestOf) {
    if (players.size() < 2) {
      throw new IllegalArgumentException("Need at least 2 players.");
    }
    this.players = new ArrayList<>(players);
    this.bestOf = bestOf;
    //pairs each player with every player after them in the list, so each pair meets once
    for (int i = 0; i < this.players.size(); i++) {
      for (int j = i + 1; j < this.players.size(); j++) {
        matches.add(new Match(this.players.get(i), this.players.get(j), bestOf));
      }
    }
  }
  //used for loading a saved round-robin tournament from a file
  public RoundRobin(ArrayList<Player> players, int bestOf, ArrayList<Match> matches) {
    this.players = new ArrayList<>(players);
    this.bestOf = bestOf;
    for (Match m : matches) {
      this.matches.add(m);
    }
  }
  public void recordResult(int matchIndex, int g1, int g2) {
    if (matchIndex < 0 || matchIndex >= matches.size()) {
      throw new IllegalArgumentException("No such match.");
    }
    Match match = matches.get(matchIndex);
    if (match.isPlayed()) {
      throw new IllegalArgumentException("That match was already played.");
    }
    match.recordResult(g1, g2);
    Player winner = match.getWinner();
    Player loser;
    if (winner == match.getPlayer1()) {
      loser = match.getPlayer2();
    } else {
      loser = match.getPlayer1();
    }
    winner.recordWin();
    loser.recordLoss();
  }
  //games won minus games lost across all played matches, used for a tiebreaker
  public int getGameDifferential(Player p) {
    int diff = 0;
    for (Match m : matches) {
      if (!m.isPlayed()) {
        continue;
      }
      if (m.getPlayer1() == p) {
        diff += (m.getGames1() - m.getGames2());
      } else if (m.getPlayer2() == p) {
        diff += (m.getGames2() - m.getGames1());
      }
    }
    return diff;
  }
  public ArrayList<Player> getStandings() {
    ArrayList<Player> standings = new ArrayList<>(players);
    //the sort uses a comparator here, where it gets 2 players, and sorts the players
    //standings using the most wins first and ties broken by the game differential
    standings.sort((a, b) -> {
      if (a.getWins() != b.getWins()) {
        return b.getWins() - a.getWins();
      }
      return getGameDifferential(b) - getGameDifferential(b);
    };
    return standings;
  }
  public boolean isFinished() {
    for (Match m : matches) {
      if (!m.isPlayed()) {
        return false;
      }
    }
    return true;
  }
  public ArrayList<Match> getMatches() {
    return matches;
  }
  public ArrayList<Player> getPlayers() {
    return players;
  }
  public int getBestOf() {
    return bestOf;
  }
}

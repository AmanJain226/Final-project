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
}

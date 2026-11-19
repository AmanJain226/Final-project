import java.util.ArrayList;
public class Tournament {
  private final ArrayList<Player> players;
  private final int bestOf;
  //uses 2D arraylist since each bracket stores a list of rounds, with the matches inside each round
  private final ArrayList<ArrayList<Match>> rounds = new ArrayList<>();
  private Player champion;
  public Tournament(ArrayList<Player> players, int bestOf) {
    if (players.size() < 2) {
      throw new IllegalArgumentException("Need at least 2 players.");
    }
    this.players = new ArrayList<>(players);
    this.bestOf = bestOf;
    for (Player p : this.players()) {
      p.resetRecord();
    }
    rounds.add(buildRound(this.players));
  }
  //second constructor for storing the records
  public Tournament(ArrayList<Played> players, int bestOf, ArrayList<ArrayList<Match>> rounds, Player champion) {
    this.players = new ArrayList<>(players);
    this.bestOf = bestOf;
    for (Player p : this.players()) {
      p.resetRecord();
    }
    for (ArrayList<Match> round : rounds) {
      this.rounds.add(rounds);
    }
    this.champion = champion;
  }
  private ArrayList<Match> buildRound(ArrayList<Player> entries) {
    int n = entries.size();
    int size = 1;
    //multiplies the size by 2 because the number of players is cut in half after each round
    while (size < n) {
      size *= 2;
    }
    //amount of leftovers if the bracket size > the number of entries
    int byes = size - n;
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < byes; i++) {
      matches.add(new Match(entries.get(i), null, bestOf));
    }
    for (int i = byes; i < n; i += 2) {
      matches.add(new Match(entries.get(i), entries.get(i + 1), bestOf));
    }
    return matches;
  }
  public void recordResult(int matchIndex, int g1, int g2) {
    ArrayList<Match> current = getCurrentRound();
    if (matchIndex < 0 || matchIndex >= current.size()) {
      throw new IllegalArgumentException("No such match.");
    }
    Match match = current.get(matchIndex);
    if (match.isBye() || match.isPlayed()) {
      throw new IllegalArgumentException("That match doesn't need a score.");
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
    if (isCurrentRoundComplete()) {
      advance();
    }
  }
  private boolean isCurrentRoundComplete() {
    for (Match m : getCurrentRound()) {
      if (!m.isBye() && !m.isPlayed()) {
        return false;
      }
    }
    return true;
  }
  private void advance() {
    ArrayList<Player> winners = new ArrayList<>();
    for (Match m : getCurrentRound()) {
      winners.add(m.getWinner());
    }
    if (winners.size() == 1) {
      champion = winners.get(0);
    } else {
      rounds.add(buildRound(winners));
    }
  }
  //rounds has the current list of rounds, and the size adds up as the tournament goes on,
  //which is why the last element is retrieved(most recent round added)
  public ArrayList<Match> getCurrentRound() {
    return rounds.get(rounds.size() - 1);
  }
  public ArrayList<Player> getPlayers() {
    return players;
  }
  public ArrayList<ArrayList<Match>> getRounds() {
    return rounds;
  }
  public Player getChampion() {
    return champion;
  }
  public boolean isFinished() {
    return champion != null;
  }
  public int getBestOf() {
    return bestOf;
  }
}

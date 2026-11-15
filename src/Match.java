public class Match {
  private final Player player1;
  private final Player player2;
  private final int bestOf;
  private int games1;
  private int games2;
  private boolean played;
  public Match(Player player1, Player player2, int bestOf) {
    if (bestOf % 2 == 0 || bestOf < 1) {
      throw new IllegalArgumentException("Best-of must be an odd number.");
    }
    this.player1 = player1;
    this.player2 = player2;
    this.bestOf = bestOf;
  }
  public int gamesToWin() {
    return bestOf / 2 + 1;
  }
  public boolean isValidScore(int g1, int g2) {
    int needed = gamesToWin();
    if (g1 < 0 || g2 < 0) {
      return false;
    }
    boolean p1Wins = false;
    boolean p2Wins = false;
    if (g1 == needed && g2 < needed) {
      p1Wins = true;
      p2Wins = false;
    } 
    if (g2 == needed && g1 < needed) {
      p2Wins = true;
      p1Wins = false;
    }
    return p1Wins || p2Wins;
  }
  public void recordResult(int g1, int g2) {
    if (!isValidScore(g1, g2)) {
      throw new IllegalArgumentException("Invalid score for a best-of-" + bestOf + ". One player must win exactly " + gamesToWin() + " games.");
    }
    this.games1 = g1;
    this.games2 = g2;
    this.played = true;
  }
  public Player getWinner() {
    if (player2 == null) {
      return player1;
    }
    if (!played) {
      return null;
    }
    if (games1 > games2) {
      return player1;
    } else {
      return player2;
    }
  }
  public boolean isPlayed() {
    return played;
  }
  public boolean isBye() {
    return player2 == null;
  }
  public Player getPlayer1() {
    return player1;
  }
  public Player getPlayer2() {
    return player2;
  }
  public int getGames1() {
    return games1;
  }
  public int getGames2() {
    return games2;
  }
  public int getBestOf() {
    return bestOf;
  }
}

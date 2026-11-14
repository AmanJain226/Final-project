public class Match {
  private final Player player1;
  private final Player player2;
  private int score1;
  private int score2;
  private boolean played;
  public Match(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
  }
  public void recordResult(int score1, int score2) {
    if (score1 == score2) {
      throw new IllegalArgumentException("Ping pong matches can't end in a tie.");
    }
    this.score1 = score1;
    this.score2 = score2;
    this.played = true;
  }
  public Player getWinner() {
    if (player2 == null) {
      return player1;
    }
    if (!played) {
      return null;
    }
    if (score1 > score2) {
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
  public int getScore1() {
    return score1;
  }
  public int getScore2() {
    return score2;
  }
}

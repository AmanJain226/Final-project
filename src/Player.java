public class Player {
  private final String name;
  private int wins;
  private int losses;
  public Player(String name) {
    this.name = name;
  }
  //second constructor used for storing the records
  public Player(String name, int wins, int losses) {
    this.name = name;
    this.wins = wins;
    this.losses = losses;
  }
  public String getName() {
    return name;
  }
  public int getWins() {
    return wins;
  }
  public int getLosses() {
    return losses;
  }
  public void recordWin() {
    wins++;
  }
  public void recordLoss() {
    losses++;
  }
  //resets for new tournament
  public void resetRecord() {
    wins = 0;
    losses = 0;
  }
  public String toString() {
    return name + " (" + wins + "-" + losses+ ")";
  }
}

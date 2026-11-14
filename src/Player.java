public class Player {
  private final String name;
  private int wins;
  private int losses;
  public Player(String name) {
    this.name = name;
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
  public String toString() {
    return name + " (" + wins + "-" + losses+ ")";
  }
}

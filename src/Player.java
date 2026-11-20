public class Player {
  private final String name;
  private int wins;
  private int losses;
  private static final double STARTING_RATING = 1000;
  private double rating;
  public Player(String name) {
    this(name, 0, 0, STARTING_RATING);
  }
  //third constructor used for the ratings
  public Player(String name, int wins, int losses, double rating) {
    this.name = name;
    this.wins = wins;
    this.losses = losses;
    this.rating = rating;
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
  public double getRating() {
    return rating;
  }
  public void setRating(double rating) {
    this.rating = rating;
  }
  public String toString() {
    return name + " (" + wins + "-" + losses+ ", rating " + Math.round(rating) + ")";
  }
}

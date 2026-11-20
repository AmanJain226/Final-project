public class EloRating {
  //how much one match can move a rating
  private static final double K = 32;
  //chance that a player rated ratingA beats a player with ratingB
  //uses the two scores and determines the probability that one beats another
  //using the gap between the score
  //the 400.0 is a scale factor which means that a 400-point gape means the higher
  //rated player is roughly 10 more times likely to win
  public static double expectedScore(double ratingA, double ratingB) {
    return 1.0 / (1.0 + Math.pow(10, (ratingB - ratingA) / 400));
  }
  //the point rating system is structured so that if a player
  //who already has a strong rating wins, their points go up just a little
  //while someone who was at a lower rating who wins gains more points because of the 
  //turnaround
  public static void update(Player winner, Player loser) {
    double expectedWin = expectedScore(winner.getRating(), loser.getRating());
    double newWinner = winner.getRating() + K * (1 - expectedWin);
    double newLoser = loser.getRating() - K * (1 - expectedWin);
    winner.setRating(newWinner);
    loser.setRating(newLoser);
  }
}

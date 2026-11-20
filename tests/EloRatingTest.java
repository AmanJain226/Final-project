import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class EloRatingTest {
  @Test
  public void equalPlayersExpectHalf() {
    assertEquals(0.5, EloRating.expectedScore(1000, 1000), 0.001);
  }

  @Test
  public void evenMatchMovesSixteenPoints() {
    Player winner = new Player("W");
    Player loser = new Player("L");
    EloRating.update(winner, loser);
    assertEquals(1016, winner.getRating(), 0.001);
    assertEquals(984, winner.getRating(), 0.001);
  }

  @Test
  public void upsetMovesMorePointsThanExpectedWin() {
    Player underdog = new Player("U", 0, 0, 1000);
    Player favorite = new Player("F", 0, 0, 1400);
    EloRating.update(underdog, favorite);
    double upsetGain = underdog.getRating() - 1000;
    Player favorite2  = new Player("F2", 0, 0, 1400);
    Player underdog2 = new Player("U2", 0, 0, 1000);
    double expectedGain = favorite2.getRating() - 1400;
    assertTrue(upsetGain > expectedGain);
  }

  @Test
  public void totalRatingStaysConstant() {
    Player a = new Player("A", 0, 0, 1200);
    Player b = new Player("B", 0, 0, 950);
    EloRating.update(a, b);
    assertEquals(2150, a.getRating() + b.getRating(), 0.001);
  }
}
    Pl

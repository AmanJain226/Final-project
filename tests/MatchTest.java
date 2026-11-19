import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class MatchTest {
  private final Player a = new Player("Alex");
  private final Player b = new Player("Sam");

  @Test
  public void validScoresForBestOf3() {
    Match m = new Match(a, b, 3);
    assertTrue(m.isValidScore(2, 0));
    assertTrue(m.isValidScore(1, 2));
  }

  @Test
  public void invalidScoresForBestOf3() {
    Match m = new Match(a, b, 3);
    assertFalse(m.isValidScore(2, 2));
    assertFalse(m.isValidScore(3, 0));
    assertFalse(m.isValidScore(1, 0));
    assertFalse(m.isValidScore(-1, 2));
  }

  @Test
  public void bestOf5NeedsThreeGames() {
    Match m = new Match(a, b, 5);
    assertTrue(m.isValidScore(3, 2));
    assertFalse(m.isValidScore(2, 1));
  }

  @Test
  public void eventBestOfIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> new Match(a, b, 4));
  }

  @Test
  public void winnerIsSetAfterResult() {
    Match m = new Match(a, b, 3);
    assertNull(m.getWinner());
    m.recordResult(2, 1);
    assertEquals(a, m.getWinner());
  }

  @Test
  public void byeAdvancesPlayerOne() {
    Match m = new Match(a, null, 3);
    assertTrue(m.isBye());
    assertEquals(a, m.getWinner());
  }

  @Test
  public void invalidResultThrowsAndLeavesMatchUnemployed() {
    Match m = new Match(a, b, 3);
    assertThrows(IllegalArgumentException.class, () -> m.recordResult(2, 2));
    assertFalse(m.isPlayed());
  }
}

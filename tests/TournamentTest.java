import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
public class TournamentTest {
  private ArrayList<Player> makePlayers(int n) {
    ArrayList<Player> list = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      list.add(new Player("P" + i));
    }
    return list;
  }

  @Test
  public void fourPlayersMakeTwoFirstRoundMatches() {
    Tournament t = new Tournament(makePlayers(4), 3);
    assertEquals(1, t.getRounds().size());
    assertEquals(2, t.getCurrentRound().size());
  }

  @Test
  public void fivePlayersGetThreeByes() {
    Tournament t = new Tournament(makePlayers(5), 3);
    int byes = 0;
    for (Match m : t.getCurrentRound()) {
      if (m.isBye()) {
        byes++;
      }
    }
    assertEquals(4, t.getCurrentRound().size());
    assertEquals(3, byes);
  }

  @Test
  public void winnersAdvanceAndChampionIsCrowned() {
    ArrayList<Player> players = makePlayers(4);
    Tournament t = new Tournament(players, 3);
    t.recordResult(0, 2, 0);
    t.recordResult(1, 2, 1);
    assertEquals(2, t.getRounds().size());
    assertEquals(1, t.getCurrentRound().size());
    assertFalse(t.isFinished());
    t.recordResult(0, 2, 0);
    assertTrue(t.isFinished());
    assertEquals(players.get(0), t.getChampion());
  }

  @Test
  public void byeAndAlreadyPlayedMatchesCannotBeScored() {
    Tournament t = new Tournament(makePlayers(5), 3);
    assertThrows(IllegalArgumentException.class, () -> t.recordResult(0, 2, 0));
  }

  @Test
  public void needsAtLeastTwoPlayers() {
    assertThrows(IllegalArgumentException.class, () -> new Tournament(makePlayers(1), 3));
  }
}

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
public class RoundRobinTest {
  private ArrayList<Player> makePlayers(String[] names) {
    ArrayList<Player> list = new ArrayList<>();
    for (String name : names) {
      list.add(new Player(name));
    }
    return list;
  }

  @Test
  public void fourPlayersMakeSixMatches() {
    RoundRobin rr = new RoundRobin(makePlayers(new String[]{"A", "B", "C", "D"}), 3);
    assertEquals(6, rr.getMatches().size());
  }

  @Test
  public void tiedWinsAreBrokenByGameDifferential() {
     RoundRobin rr = new RoundRobin(makePlayers(new String[]{"A", "B", "C"}), 3);
     rr.recordResult(0, 2, 0);
     rr.recordResult(1, 1, 2);
     rr.recordResult(2, 2, 1);
     ArrayList<Player> standings = rr.getStandings();
     assertEquals("A", standings.get(0).getName());
     assertEquals("C", standings.get(1).getName());
     assertEquals("B", standings.get(2).getName());
     assertTrue(rr.isFinished());
  }

  @Test
  public void playedMatchCannotBeScoredTwice() {
    RoundRobin rr = new RoundRobin(new String[]{"A", "B"}), 3);
    rr.recordResult(0, 2, 0);
    assertThrows(IllegalArgumentException.class, () -> rr.recordResult(0, 2, 0));
  }
}

package ly.logic.gm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class GmPlayerReflectionUtilsTest {

  @Test
  public void shouldPatchPlainField() {
    SampleModule module = new SampleModule();

    GmPlayerReflectionUtils.patch(module, "level", "12");

    assertEquals(12, module.level);
  }

  @Test
  public void shouldPatchListFieldByIndex() {
    SampleModule module = new SampleModule();

    GmPlayerReflectionUtils.patch(module, "heroes[0].star", "5");

    assertEquals(5, module.heroes.getFirst().star);
  }

  @Test
  public void shouldPatchMapFieldByKey() {
    SampleModule module = new SampleModule();

    GmPlayerReflectionUtils.patch(module, "resources.gold", "1000");

    assertEquals(1000, module.resources.get("gold").intValue());
  }

  private static class SampleModule {
    int level = 1;
    List<Hero> heroes = new ArrayList<>(List.of(new Hero()));
    Map<String, Integer> resources = new LinkedHashMap<>(Map.of("gold", 10));
  }

  private static class Hero {
    int star = 1;
  }
}

package unit.plugin;

import events.server.opensearch.EventsServerPlugin;
import events.server.resthandlers.StatisticsRestHandler;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class PluginTests {

    @Test
    public void testCreatePieAggregation() throws Exception
    {
        List<?> restHandlers = new EventsServerPlugin().getRestHandlers(null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertThat(restHandlers.get(0), instanceOf(StatisticsRestHandler.class));

    }

}

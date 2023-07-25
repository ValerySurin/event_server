package unit.json;

import com.google.gson.JsonObject;
import events.server.support.processors.JsonProcessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonConversionTests {

    @Test
    public void testCreatePieAggregation() throws Exception
    {
        String jsonSample = "{\"type\":\"PIE\",\"name\":\"users\"}";
        assertEquals(JsonProcessor.fromJson(jsonSample, JsonObject.class).get("type").getAsString(), "PIE");
    }
}

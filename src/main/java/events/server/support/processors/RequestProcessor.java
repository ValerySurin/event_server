package events.server.support.processors;

import com.google.gson.JsonObject;
import org.opensearch.rest.RestRequest;

public class RequestProcessor {

    public static String parseRequest(RestRequest request, String paramName) {
        String result;
        String content = request.content().utf8ToString();
        JsonObject jsonContent;
        try {
            jsonContent = JsonProcessor.fromJson(content, JsonObject.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("[sme] Failed to parse request payload", e);
        }
        try {
            result = jsonContent.get(paramName).getAsString();
        } catch (Exception ex) {
            return null;
        }
        return result;
    }
}

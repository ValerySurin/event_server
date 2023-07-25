package events.server.support;

import org.opensearch.action.search.SearchResponse;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestStatus;

public class Responses {

    public static void responseException(RestChannel channel, Exception ex) {
        channel.sendResponse(new BytesRestResponse(RestStatus.SERVICE_UNAVAILABLE,
                "application/json; charset=UTF-8",
                ex.getMessage()));
    }

    public static void responseWithBody(RestChannel channel, SearchResponse response) {
        channel.sendResponse(new BytesRestResponse(RestStatus.SERVICE_UNAVAILABLE,
                "application/json; charset=UTF-8", response.getHits().toString()));
    }
}

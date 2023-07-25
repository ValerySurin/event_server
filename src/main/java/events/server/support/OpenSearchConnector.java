package events.server.support;

import org.opensearch.action.search.SearchRequestBuilder;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;

public class OpenSearchConnector {

    public static SearchResponse run(SearchRequestBuilder searchRequestBuilder) {
        return searchRequestBuilder.get(new TimeValue(1000, TimeUnit.SECONDS));
    }
}

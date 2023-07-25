package events.server.support.objects.aggregations;

import org.opensearch.rest.RestRequest;

import static events.server.support.processors.RequestProcessor.parseRequest;

public class LineAggregation {

    private final String byField;
    private final String keyField;

    public LineAggregation(RestRequest request) {
        byField = parseRequest(request, "byField");
        keyField = parseRequest(request, "keyField");
    }

    public LineAggregation(String byField, String keyField) {
        this.byField = byField;
        this.keyField = keyField;
    }

    public String getByField() {
        return byField;
    }

    public String getKeyField() {
        return keyField;
    }
}

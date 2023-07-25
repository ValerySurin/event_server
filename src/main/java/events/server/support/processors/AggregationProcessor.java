package events.server.support.processors;

import events.server.support.constants.StatisticsType;
import events.server.support.objects.aggregations.LineAggregation;
import events.server.support.objects.aggregations.PieAggregation;
import events.server.support.objects.aggregations.TableAggregation;
import org.opensearch.action.search.SearchAction;
import org.opensearch.action.search.SearchRequestBuilder;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.RestRequest;
import org.opensearch.search.aggregations.AggregationBuilders;

import static events.server.support.processors.RequestProcessor.parseRequest;

public class AggregationProcessor {

    public static SearchRequestBuilder prepareAggregation(RestRequest request, NodeClient client) {
        SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder(client, SearchAction.INSTANCE).setSize(0);
        if (request.path().contains("events")) {
            searchRequestBuilder.setIndices("events");
        } else if (request.path().contains("users")) {
            searchRequestBuilder.setIndices("users");
        }
        switch (StatisticsType.valueOf(parseRequest(request, "type"))) {
            case TABLE -> {
                return prepareTableAggregation(new TableAggregation(request), searchRequestBuilder);
            }
            case PIE -> {
                return preparePieAggregation(new PieAggregation(request), searchRequestBuilder);
            }
            case LINE -> {
                return prepareLineAggregation(new LineAggregation(request), searchRequestBuilder);
            }
            default -> {
                return null;
            }
        }
    }

    public static SearchRequestBuilder prepareTableAggregation(TableAggregation tableAggregation,
                                                         SearchRequestBuilder searchRequestBuilder) {
        return null;
    }

    public static SearchRequestBuilder preparePieAggregation(PieAggregation pieAggregation,
                                                       SearchRequestBuilder searchRequestBuilder) {
        return searchRequestBuilder.addAggregation(AggregationBuilders
                .terms("byField")
                .field(pieAggregation.getByField()).size(10000)
                .subAggregation(AggregationBuilders
                        .count(pieAggregation.getKeyField())
                        .field(pieAggregation.getKeyField())));
    }

    public static SearchRequestBuilder prepareLineAggregation(LineAggregation lineAggregation,
                                                              SearchRequestBuilder searchRequestBuilder) {
        return searchRequestBuilder.addAggregation(AggregationBuilders
                .terms("byField")
                .field(lineAggregation.getByField()).size(10000)
                .subAggregation(AggregationBuilders
                        .sum(lineAggregation.getKeyField())
                        .field(lineAggregation.getKeyField())));
    }
}

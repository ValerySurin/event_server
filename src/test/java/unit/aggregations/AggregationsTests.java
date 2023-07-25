package unit.aggregations;

import events.server.support.objects.aggregations.LineAggregation;
import events.server.support.objects.aggregations.PieAggregation;
import org.junit.Test;
import org.opensearch.action.search.SearchAction;
import org.opensearch.action.search.SearchRequestBuilder;

import static events.server.support.processors.AggregationProcessor.prepareLineAggregation;
import static events.server.support.processors.AggregationProcessor.preparePieAggregation;
import static org.junit.Assert.assertEquals;

public class AggregationsTests {

    @Test
    public void testCreatePieAggregation() throws Exception
    {
        PieAggregation pieAggregation = new PieAggregation("test.by.field", "test.key.field");
        SearchRequestBuilder searchRequestBuilder = preparePieAggregation(pieAggregation,
                new SearchRequestBuilder(null, SearchAction.INSTANCE));
        assertEquals(searchRequestBuilder.toString(), "{\"aggregations\":{\"byField\":{\"terms\":{\"field\":\"test.by.field\",\"size\":10000,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_key\":\"asc\"}]},\"aggregations\":{\"test.key.field\":{\"value_count\":{\"field\":\"test.key.field\"}}}}}}");
    }

    @Test
    public void testCreateLineAggregation() throws Exception
    {
        LineAggregation lineAggregation = new LineAggregation("test.by.field", "test.key.field");
        SearchRequestBuilder searchRequestBuilder = prepareLineAggregation(lineAggregation,
                new SearchRequestBuilder(null, SearchAction.INSTANCE));
        assertEquals(searchRequestBuilder.toString(), "{\"aggregations\":{\"byField\":{\"terms\":{\"field\":\"test.by.field\",\"size\":10000,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_key\":\"asc\"}]},\"aggregations\":{\"test.key.field\":{\"sum\":{\"field\":\"test.key.field\"}}}}}}");
    }
}

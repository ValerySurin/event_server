package events.server.resthandlers;

import events.server.support.ThreadSettings;
import events.server.support.constants.StatisticsType;
import events.server.support.processors.RequestProcessor;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;

import java.util.List;

import static events.server.support.OpenSearchConnector.run;
import static events.server.support.Responses.responseWithBody;
import static events.server.support.constants.Dictionary.MAIN_URL;
import static events.server.support.processors.AggregationProcessor.prepareAggregation;
import static org.opensearch.rest.RestRequest.Method.POST;

public class StatisticsRestHandler extends BaseEventsServerRestHandler {
    @Override
    public String getName() {
        return "statistics_handler";
    }

    @Override
    public List<Route> routes() {
        return List.of(
                new Route(POST, MAIN_URL + "/statistics/events/")
        );
    }


    @Override
    public void executeRestAction(RestRequest request,
                                  NodeClient client,
                                  RestChannel channel,
                                  ThreadSettings threadSettings) {
        responseWithBody(channel, run(prepareAggregation(request, client)));
    }
}

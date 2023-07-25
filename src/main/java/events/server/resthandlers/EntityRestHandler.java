package events.server.resthandlers;

import com.google.gson.Gson;
import events.server.support.ThreadSettings;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.RestStatus;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.List;


import static events.server.sql.ConnectionDriver.runQuery;
import static events.server.sql.SqlQueryProcessor.createGetEntityQuery;
import static events.server.support.constants.Dictionary.MAIN_URL;
import static org.opensearch.rest.RestRequest.Method.*;

public class  EntityRestHandler extends BaseEventsServerRestHandler {
    @Override
    public String getName() {
        return "entity_handler";
    }

    @Override
    public List<Route> routes() {
        return List.of(
                new Route(GET, MAIN_URL + "/{entity}"),
                new Route(GET, MAIN_URL + "/{entity}/{id}"),
                new Route(POST, MAIN_URL + "/{entity}"),
                new Route(POST, MAIN_URL + "/{entity}/{id}"),
                new Route(PUT, MAIN_URL + "/{entity}/{id}")
        );
    }


    @Override
    public void executeRestAction(RestRequest request,
                                  NodeClient client,
                                  RestChannel channel,
                                  ThreadSettings threadSettings) {
        request.param("entity");
        request.param("id");
        if (request.method().equals(GET)) {
            try {
                Gson gson = new Gson();
                String o = AccessController.doPrivileged((PrivilegedExceptionAction<String>)
                        () -> gson.toJson(runQuery(createGetEntityQuery(request.param("entity"), request.param("id"), null))));
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, "application/json; charset=UTF-8", o));
            } catch (Exception ex) {
                channel.sendResponse(new BytesRestResponse(RestStatus.SERVICE_UNAVAILABLE, ex.getMessage()));
            }
        }
    }
}

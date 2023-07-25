package events.server.resthandlers;

import events.server.support.Responses;
import events.server.support.ThreadSettings;
import events.server.support.objects.SecurityUser;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

public abstract class BaseEventsServerRestHandler extends BaseRestHandler {
    @Override
    public String getName() {
        return "events_server_base_actions";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient nodeClient) throws IOException {
        restRequest.param("entity");
        restRequest.param("id");
        return channel -> {
            ThreadSettings threadSettings = new ThreadSettings();
            SecurityUser securityUser = new SecurityUser();
            Object user = nodeClient.threadPool().getThreadContext().getTransient("_opendistro_security_user");
            try {
                Method getUserName = user.getClass().getMethod("getName");
                Method getRoles = user.getClass().getMethod("getRoles");
                Method getSecurityRoles = user.getClass().getMethod("getSecurityRoles");
                securityUser.setUserName((String) getUserName.invoke(user));
                securityUser.setRoles((Set<String>) getSecurityRoles.invoke(user));
                securityUser.setBackendRoles((Set<String>) getRoles.invoke(user));
            } catch (Exception e) {
                Responses.responseException(channel, e);
                return;
            }
            threadSettings.setSecurityUser(securityUser);
            executeRestAction(restRequest, nodeClient, channel, threadSettings);
        };
    }

    public abstract void executeRestAction(RestRequest request,
                                           NodeClient client,
                                           RestChannel channel,
                                           ThreadSettings threadSettings);
}

package events.server.support.processors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public abstract class JsonProcessor {
    private static final Gson GSON_INSTANCE = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static Gson getGson() {
        return GSON_INSTANCE;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws PrivilegedActionException {
        return AccessController.doPrivileged((PrivilegedExceptionAction<T>)() -> getGson().fromJson(json, classOfT));
    }
}

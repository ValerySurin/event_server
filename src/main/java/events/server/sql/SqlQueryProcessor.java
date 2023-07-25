package events.server.sql;

import events.server.support.constants.BaseEntity;
import events.server.support.constants.JoinedEntity;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SqlQueryProcessor {

    public static String createGetEntityQuery(String entity, String id, Integer limit) {
        if (EnumUtils.isValidEnum(BaseEntity.class, entity)) {
            if (id == null && limit != null) {
                if (entity.equals(BaseEntity.events.toString())) {
                    return "SELECT * FROM " + entity + "WHERE end_date < NOW() ORDER BY _create_date DESC LIMIT " + limit;
                }
                return "SELECT * FROM " + entity + " ORDER BY _create_date DESC LIMIT " + limit;
            } else if (limit == null) {
                if (entity.equals(BaseEntity.events.toString())) {
                    return "SELECT * FROM " + entity + "WHERE end_date < NOW() ORDER BY _create_date DESC";
                }
                return "SELECT * FROM " + entity + " ORDER BY _create_date DESC";
            } else {
                return "SELECT * FROM " + entity + " WHERE ID = " + id;
            }
        }

        JoinedEntity joinedEntity = JoinedEntity.valueOf(entity);

        switch (joinedEntity) {
            case history -> {
                return createHistoryQuery(id);
            }
        }
        return "";
    }

    public String createPostEntityQuery(String entity, TreeMap<String, Object> insertOptions) {
        StringBuilder query = new StringBuilder("INSERT INTO ")
                .append(entity)
                .append(" (")
                .append(String.join(", ", insertOptions.keySet()))
                .append(")")
                .append(" VALUES (");

        for (Object value : insertOptions.values()) {
            if (value instanceof String) {
                query.append("'").append(value).append("'");
            } else {
                query.append(value);
            }
        }

        return query.toString();
    }

    public String createPutEntityQuery(String entity, Integer id, TreeMap<String, Object> updateOptions) {
        StringBuilder query = new StringBuilder("UPDATE ").append(entity).append("SET ");

        ArrayList<String> updateValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : updateOptions.entrySet()) {
            if (entry.getValue() instanceof String) {
                updateValues.add(entry.getKey() + "='" + entry.getValue() + "'");
            } else {
                updateValues.add(entry.getKey() + "=" + entry.getValue());
            }
        }

        query.append(String.join(", ", updateValues));
        query.append(" WHERE ID=").append(id);

        return query.toString();
    }

    public static String createHistoryQuery(String userId) {
         return "SELECT * FROM choosen_events INNER JOIN events ON choosen_events.event_id=events.id WHERE choosen_events.email=" + userId;
    }
}

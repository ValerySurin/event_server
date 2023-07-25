package events.server.sql;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static events.server.support.constants.Dictionary.MY_SQL_CONNECTION_STRING;

public class ConnectionDriver {

    public static Object runQuery(String query) throws PrivilegedActionException {
        return AccessController.doPrivileged((PrivilegedExceptionAction<Object>)
                () -> {
                    Class.forName("com.mysql.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(MY_SQL_CONNECTION_STRING, "root", "root");
                         PreparedStatement ps = conn.prepareStatement(query);
                         ResultSet rs = ps.executeQuery()) {
                        return resultSetToArrayList(rs);
                    }
                }
        );
    }

    private static List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        while (rs.next()){
            HashMap<String, Object> row = new HashMap<>(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

}

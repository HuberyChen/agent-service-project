package com.lx.agent.utility;

import com.core.database.ConnectionPoolDataSource;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Ignore
public class ViewGeneratorTest {
    @Test
    public void generateDomainTest() throws SQLException {
        //String databaseName = "CustomerTransDb";
        String databaseName = "agentDB";
        String tableName = "User";

        ResultSet resultSet = getResultSet(databaseName, tableName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@XmlRootElement(name = \"" + convert(tableName.replace("_", "")) + "\")\n"
                + "@XmlAccessorType(XmlAccessType.FIELD)\n");
        stringBuilder.append("public class ").append(tableName.replace("_", "")).append("View {\n");
        while (resultSet.next()) {
            String columnName = resultSet.getString("field");
            String type = toJavaType(resultSet.getString("type"), resultSet.getBoolean("null"));
            stringBuilder.append("@XmlElement(name = \"").append(convert(columnName)).append("\")\n");
            stringBuilder.append("private ").append(type).append(" ").append(columnName.substring(0, 1).toLowerCase()).append(columnName.substring(1)).append(";\n");
            stringBuilder.append("\n");
        }
        stringBuilder.append("}\n");
        System.out.println(stringBuilder);
    }

    private String convert(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String result;
            char c = str.charAt(i);
            if (Character.isUpperCase(c) && (i == 0)) {
                if (Character.isUpperCase(str.charAt(i + 1)) && Character.isUpperCase(str.charAt(i + 2))) {
                    builder.append(Character.toLowerCase(c)).
                            append(Character.toLowerCase(str.charAt(i + 1))).
                            append(Character.toLowerCase(str.charAt(i + 2)));
                    i += 2;
                    continue;
                } else {
                    builder.append(Character.toLowerCase(c));
                    continue;
                }
            } else if (Character.isUpperCase(c) && (i != 0)) {
                if (Character.isUpperCase(str.charAt(i + 1)) && Character.isUpperCase(str.charAt(i + 2))) {
                    builder.append("-").append(Character.toLowerCase(c)).
                            append(Character.toLowerCase(str.charAt(i + 1))).
                            append(Character.toLowerCase(str.charAt(i + 2)));
                    i += 2;
                    continue;
                } else {
                    builder.append("-").append(Character.toLowerCase(c));
                    continue;
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private ResultSet getResultSet(String databaseName, String tableName) throws SQLException {
        String sql = "SHOW COLUMNS from " + tableName;
        Connection connection = getConnection(databaseName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    String toJavaType(String dbType, boolean nullable) {
        String javaType = "String";
        switch (dbType) {
            case "int":
            case "smallint":
                javaType = nullable ? "Integer" : "int";
                break;
            case "datetime":
                javaType = "Date";
                break;
            case "decimal":
            case "numeric":
            case "money":
                javaType = "BigDecimal";
                break;
            case "xml":
            case "varchar":
            default:
                break;
        }
        return javaType;
    }

    public Connection getConnection(String databaseName) throws SQLException {
        ConnectionPoolDataSource dataSource = new ConnectionPoolDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/" + databaseName);
        dataSource.setUsername("root");
        dataSource.setPassword("0000");
        dataSource.setValidationQuery("select 1");
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        return dataSource.getConnection();
    }
}

package com.lx.agent.utility;

import com.core.database.ConnectionPoolDataSource;
import com.core.utils.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Ignore
public class DomainGeneratorTest {
    @Test
    public void generateDomainTest() throws SQLException {
        //String databaseName = "CustomerTransDb";
        String databaseName = "agentDB";
        String tableName = "User";

        ResultSet resultSet = getResultSet(databaseName, tableName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Entity\n");
        stringBuilder.append("@Table(name = \"").append(tableName).append("\")\n");
        stringBuilder.append("public class ").append(tableName.replace("_", "")).append(" {\n");
        while (resultSet.next()) {
            String columnName = resultSet.getString("field");
            String type = toJavaType(resultSet.getString("type"), resultSet.getBoolean("null"));
//            if ("Date".equals(type)) {
//                stringBuilder.append("@Temporal(TemporalType.TIMESTAMP)\n");
//            }
            if (StringUtils.hasText(resultSet.getString("key"))) {
                stringBuilder.append("@GeneratedValue\n");
            }
            stringBuilder.append("@Column(name = \"").append(columnName).append("\")\n");
            stringBuilder.append("private ").append(type).append(" ").append(columnName.substring(0, 1).toLowerCase()).append(columnName.substring(1)).append(";\n");
            stringBuilder.append("\n");
        }
        stringBuilder.append("}\n");
        System.out.println(stringBuilder);
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
            case "float":
                javaType = nullable ? "Float" : "float";
                break;
            case "int":
            case "smallint":
                javaType = nullable ? "Integer" : "int";
                break;
            case "datetime":
            case "date":
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

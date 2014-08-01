package com.lx.agent.utility;

import com.core.database.ConnectionPoolDataSource;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Ignore
public class DataMockGeneratorTest {

    private String tableName = "ReviewSnapshot";
    private String databaseName = "WebDB";

    @Test
    public void dataMockTest() throws SQLException {
        ResultSet resultSet = getResultSet();
        ResultSet resultValue = getValue();
        StringBuilder stringBuilder = new StringBuilder();
        String className = tableName.replace("_", "");
        String entityName = className.replace(className.charAt(0), Character.toLowerCase(className.charAt(0)));
        stringBuilder.append(className).append(" ").append(entityName).append(" = new ").append(className).append("();\n");
        while (resultValue.next()) {
            while (resultSet.next()) {
                String columnName = resultSet.getString("name");
                String mockValue = toJavaType(resultSet.getString("TypeName"), resultValue, columnName);
                stringBuilder.append(entityName).append(".set").append(columnName).append("(").append(mockValue).append(");");
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("return ").append(entityName).append(";");
        System.out.println(stringBuilder);
    }

    private ResultSet getValue() throws SQLException {
        String sql = "select top 1* from " + tableName;
        Connection connection = dataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    private ResultSet getResultSet() throws SQLException {
        String sql = "select c.name,t.name as TypeName,c.is_identity,c.is_nullable from sys.columns c left join sys.types t on c.user_type_id = t.user_type_id  where c.object_id=object_id('" + tableName + "')";
        Connection connection = dataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }


    String toJavaType(String dbType, ResultSet resultValue, String columnName) throws SQLException {
        String javaType = "String";
        switch (dbType) {
            case "float":
                javaType = String.valueOf(resultValue.getFloat(columnName));
                break;
            case "int":
                javaType = String.valueOf(resultValue.getInt(columnName));
                break;
            case "datetime":
            case "date":
                javaType = String.valueOf(resultValue.getDate(columnName));
                break;
            case "decimal":
                javaType = "BigDecimal.valueOf(" + resultValue.getBigDecimal(columnName) + ")";
                break;
            case "numeric":
            case "money":
                javaType = String.valueOf(resultValue.getBigDecimal(columnName));
                break;
            case "xml":
            case "varchar":
                javaType = "\"" + resultValue.getString(columnName) + "\"";
                break;
            default:
                break;
        }
        return javaType;
    }

    public DataSource dataSource() {
        ConnectionPoolDataSource dataSource = new ConnectionPoolDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://testing.suryani.cn;databaseName=" + databaseName);
        dataSource.setUsername("prodService");
        dataSource.setPassword("prodService123");
        dataSource.setValidationQuery("select 1");
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        return dataSource;
    }

}

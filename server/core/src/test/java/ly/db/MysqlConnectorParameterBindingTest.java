package ly.db;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** 验证实体字段中的 Java null 会被参数化 SQL 正确绑定为数据库 NULL。 */
public class MysqlConnectorParameterBindingTest {

    @Test
    public void shouldBindNullAndNonNullParametersWithoutChangingTheirMeaning() throws Exception {
        List<String> calls = new ArrayList<>();
        PreparedStatement statement = (PreparedStatement) Proxy.newProxyInstance(
                PreparedStatement.class.getClassLoader(),
                new Class<?>[] {PreparedStatement.class},
                (proxy, method, args) -> {
                    if ("setNull".equals(method.getName())) {
                        calls.add("setNull:" + args[0] + ':' + args[1]);
                    } else if ("setObject".equals(method.getName())) {
                        calls.add("setObject:" + args[0] + ':' + args[1]);
                    }
                    return null;
                });

        MysqlConnector.addSqlParams(new Object[] {null, "game1001"}, statement);

        assertEquals(
                List.of("setNull:1:" + Types.NULL, "setObject:2:game1001"),
                calls);
    }
}

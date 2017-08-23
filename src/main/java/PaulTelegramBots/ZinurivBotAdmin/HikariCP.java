package PaulTelegramBots.ZinurivBotAdmin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCP {
    private static DataSource dataSource;


    public static DataSource getDataSource(){
        if (dataSource == null){
            try {
                URI dbUri = new URI(System.getenv("DATABASE_URL"));
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":"+ dbUri.getPort() + dbUri.getPath();
                HikariConfig poolConfig = new HikariConfig();
                poolConfig.setJdbcUrl(dbUrl);
                if(dbUri.getUserInfo() != null){
                    poolConfig.setUsername(dbUri.getUserInfo().split(":")[0]);
                    poolConfig.setPassword(dbUri.getUserInfo().split(":")[1]);
                }
                poolConfig.setMaximumPoolSize(1);
                poolConfig.addDataSourceProperty("sslmode","require");
                dataSource = (DataSource) new HikariDataSource(poolConfig);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
}

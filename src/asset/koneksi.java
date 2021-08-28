/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asset;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class koneksi {
    public static String PathReport=System.getProperty("user.dir")+"src/global/view";
    static Connection con;
    
    public static Connection connection(){
        if (con==null){
            //postgresqlDataSource data = new PostgresqlDataSource();
           MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("project2");
            data.setUser("root");
            data.setPassword("");
            try{
                con = data.getConnection();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return con;
    }
}

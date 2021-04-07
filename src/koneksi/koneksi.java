package koneksi;
import java.sql.*;

/**
 *
 * @author Iziq
 */
public class koneksi {
    
    Connection con;
    public Connection getkoneksi() throws SQLException{
        try {
            String url = "jdbc:h2:./database/wildventory;ACCESS_MODE_DATA=rws";
            String user = "admin";
            String pass = "";
            con= DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e){
        }
    
        return con;
        }
    
    //Connection con;
  //  public Connection getkoneksi(){
    //    try {
   //         con= DriverManager.getConnection("jdbc:mysql://localhost/wildventory","root","");
    //    }
    //    catch (Exception e){
    //    }
   //     return con;
   //     }
    }

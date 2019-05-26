package modelo;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import javax.swing.JOptionPane;
import modelo.empleado;

public class conexion {
    public Connection conn;
    
    public Connection getConexion() throws SQLException{
        /*Eso es en la compurtadora de samu
        String db="proyecto";//Nombre de la base de datos
        String un="p";//Usuario de la base de datos
        String pass="123";//Contraseña de la base de datos
        */
        
        /*Esto es en la computadora de wicho*/
        String db="taller";
        String un="sa";
        String pass="12345";
        
        try{
            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //String connectionUrl="jdbc:sqlserver://DESKTOP-1AVQNU4\\SQLEXPRESS:1433;databaseName="+db+";user="+un+";password="+pass+";";
            String connectionUrl="jdbc:sqlserver://CAAG-SL-LAB8-PC:1433;databaseName="+db+";user="+un+";password="+pass+";";
            //-------------------------NOMBRE DE PC, Nombre de sqlserver y puesto 
            conn=DriverManager.getConnection(connectionUrl);
        }
        catch(ClassNotFoundException e){
          JOptionPane.showMessageDialog(null,"Error"+e.getMessage());  
        }
        catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Error"+e.getMessage());  
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null,"Error"+e.getMessage());  
        }
        return conn;
    }
    
    static empleado Activo=null;
    public empleado getEmpleadoActivo(){
        return Activo;
    }
    public void setEmpleadoActivo(int id){
        Activo=new empleado();
        Activo.setIdEmpleado(id);
    }
}

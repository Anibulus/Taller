package modelo;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class clienteDAO {
    conexion c=null;
    
    public clienteDAO(){
        c=new conexion();
    }//Fin de el constructor
    
    public boolean ingresarCliente(String usu, String nombre, String app, String apm, String direccion, String telefono, String correo) throws SQLException{
        boolean registro=false;
        
        Connection con=c.getConexion();
        
        if(con!=null){
            /*
                 Si la conexion no esnula,entra a hacer la funcion
            */
            PreparedStatement ps;
            cliente cl=null;
            cl=consultarCliente(0, nombre);
            System.out.println("consulto cliente");
            /*
                Aqui consulta a la persona y si se llena el objeto quiere decir que ya habia sido registrado
                por lo que no lepermitira continuar
            */
            if(cl==null){
                ps=con.prepareStatement("select * from usuarios where usuario=?");
                ps.setString(1, usu);
                ResultSet rs=ps.executeQuery();
                System.out.println("lleno idUsuario");
                /*
                    Aqui como no se de SQL hago una consulta para buscarle el idUsuario a la persona y  asi poder registrarlo (En teoria no debe dar problemas)
                */           
                if(rs.next()){
                    System.out.println("entro grtghrhrt");
                    /*
                        Se prosigue a una insersion comun
                    */
                    cl=new cliente();
                    cl.setIdUsuario(rs.getInt("idusuario"));
                    System.out.println("Hago la insercion");
                    ps=con.prepareStatement("insert into cliente (idusuario, nombre, app, apm, direccion, telefono, correo) values (?, ?, ?, ?, ?, ?, ?)");
                    ps.setInt(1, cl.getIdUsuario());
                    ps.setString(2, nombre);
                    ps.setString(3, app);
                    ps.setString(4, apm);
                    ps.setString(5, direccion);
                    ps.setString(6, telefono);
                    ps.setString(7, correo);
                    int numFilas=ps.executeUpdate();
                    if(numFilas>0){
                        registro=true;
                        JOptionPane.showMessageDialog(null, "Usted ha registrado a su nuevo empleado "+nombre+" "+app);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "El cliente ya ha sido registrado");
            }
            con.close();
        }
        
        return registro;
    }//Fin del proceso de registro
    /*Esta funcion siempre debe acompañar a registrar empleado*/
    
    public boolean registrarUsuario(String usu, String pass) throws SQLException{
        boolean registro=false;
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("select * from  usuarios where usuario=?");
            ps.setString(1, usu);
            /*
                Las anteriores lineas de codigo verifican que el nombre de usuario no exista todavia
            */
            ResultSet rs=ps.executeQuery();
                if(!rs.next()){
                ps=con.prepareStatement("insert into usuarios (usuario, contrasenia, estatus) values (?, ?, 'C')");
                ps.setString(1, usu);
                ps.setString(2, pass);
                int numFilas=ps.executeUpdate();
                if(numFilas>0){
                    registro=true;
                }
                con.close();
            }
            else{
                 JOptionPane.showMessageDialog(null, "El usuario ya existe, intenta de nuevo");
                 usu=JOptionPane.showInputDialog("Ingresa otro usuario");
                 registrarUsuario(usu,pass);
                 /*
                    Este else sirve para que si anoto un usuario ya registrado, le de oportunidad
                    de registrarse otra vez, y si pasa lo mismo, vuelve a entrar en la funcion
                    REVISAR SI SI SIRVE
                    Validar su usuario va vacio, no ingresar
                    
                 */
            }
        }
        return registro;
    }//Aqui termina la funcion que registra los usuarios
    
    
    public cliente consultarCliente(int id, String nom) throws SQLException{
        cliente cl=null;
        
        Connection con=c.getConexion();
        if(con!=null){
            
            PreparedStatement ps;
            ps=con.prepareStatement("select * from cliente where idCliente=? or nombre=?");
            ps.setInt(1, id);
            ps.setString(2, nom);
            ResultSet rs=ps.executeQuery();
            
            if(rs.next()){
                cl=new cliente();
                cl.setIdCliente(rs.getInt("idCliente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApp(rs.getString("app"));
                cl.setApm(rs.getString("apm"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setCorreo(rs.getString("correo"));
            }
            
            con.close();
        }        
        return cl;
    }//Fin de la funcion de consulta
    
    public boolean modificarCliente(int id, String nom, String ap,String apm, String dir, String tel, String correo) throws SQLException{
        boolean cambio=false;
        
        Connection con=c.getConexion();
        
        if(con!=null){
            PreparedStatement ps;
            
            ps=con.prepareStatement("update cliente set nombre=?, app=?, apm=?, direccion=?, telefono=? , correo=? where idcliente=?");
            ps.setString(1, nom);
            ps.setString(2, ap);
            ps.setString(3, apm);
            ps.setString(4, dir);            
            ps.setString(5, tel);
            ps.setString(6, correo);
            ps.setInt(7, id);
            int numFilas=ps.executeUpdate();
            if(numFilas>0){
                JOptionPane.showMessageDialog(null, "Se ha modificado correctamente");
                cambio=true;
            }
            
            con.close();
        }
        
        return cambio;
    }
    
    public ArrayList<cliente> consultarTodos() throws SQLException{
        ArrayList listaClientes=new ArrayList();
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("select * from cliente");
            ResultSet rs=ps.executeQuery();
            int e=0;
            while(rs.next()){
                e++;
                cliente emp=new cliente();
                emp.setIdCliente(rs.getInt("idcliente"));
                emp.setIdUsuario(rs.getInt("idusuario"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApp(rs.getString("app"));
                emp.setApm(rs.getString("apm"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setCorreo(rs.getString("correo"));           
                listaClientes.add(emp);
            }
            System.out.println("Estoy aqui  "+e);
            con.close();
        }               
        return listaClientes;
    }//Fin de consultar todos
}
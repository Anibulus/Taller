package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class productoDAO {
    conexion c;
    public productoDAO(){
        c=new conexion();
    }
    
    public boolean insertarProducto(String nom, String desc, double costo) throws SQLException{
        boolean insercion=false;
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("insert into producto (nombre, descripcion, costo, stock, estatus) values (? ,? ,? , 0, 'A')");        
            ps.setString(1, nom);
            ps.setString(2, desc);
            ps.setDouble(3, costo);
            System.out.println(nom);
            System.out.println(desc);
            System.out.println(costo);
            System.out.println("Lleno los comodines");
            int numFilas=ps.executeUpdate();
            if(numFilas>0){
                insercion=true;
                JOptionPane.showMessageDialog(null, "Se ha insertado correctamente");
            }
            con.close();
        }
        return insercion;
    }//Fin de la funcion de insertar
    
    public boolean modificarProducto(int id,String nom, int stock, String desc, double costo) throws SQLException{
        boolean modificar=false;
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("update producto  set nombre=?, stock=?, descripcion=?, costo=? where idproducto=?");
            ps.setString(1, nom);
            ps.setInt(2, stock);
            ps.setString(3, desc);
            ps.setDouble(4, costo);
            ps.setInt(5, id);
            int numFilas=ps.executeUpdate();
            if(numFilas>0){
                modificar=true;
                JOptionPane.showMessageDialog(null, "Se ha modificado correctamente");
            }
            con.close();
        } 
        return modificar;
    }
    
    public producto cosultarProducto(int id, String nom) throws SQLException{
        producto p=null;
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareCall("select * from producto where idproducto=? or nombre=?");
            ps.setInt(1, id);
            ps.setString(2, nom);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                p=new producto();
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCosto(rs.getDouble("costo"));
                p.setEstatus(rs.getString("estatus"));
            }           
            con.close();
        }   
        return p;
    }
    
    public ArrayList<producto> consultarTodo() throws SQLException{
        ArrayList inventario=new ArrayList();
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareCall("select * from producto");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                producto p=new producto();
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCosto(rs.getDouble("costo"));
                p.setEstatus(rs.getString("estatus"));
                inventario.add(p);
            }
            con.close();
        } 
        return inventario;
    }
    
    public boolean eliminarProducto() throws SQLException{
        boolean eliminar=false;
        Connection con=c.getConexion();
        if(con!=null){
            con.close();
        }
        return eliminar;
    }
    
}//Fin de la clase

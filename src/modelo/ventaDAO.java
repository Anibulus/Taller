package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ventaDAO {
    conexion c;
    
    public ventaDAO(){
        c=new conexion();
    }// fin del contructor sin parametros
    
    public boolean vender(int idEmpleado, int idCliente, String desc, int [][] detalle, double [] costo) throws SQLException{
        boolean venta=false;
        int folio=0;
        String fecha="";
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps=con.prepareCall("select convert(char(10), getdate(), 103) as hoy");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                fecha=rs.getString("hoy");
                System.out.println(fecha);
            }
            ps=con.prepareCall("insert into venta (idempleado, idcliente, fecha, descripcion) values (?, ?, ?, ?)");
            ps.setInt(1, idEmpleado);
            ps.setInt(2, idCliente);
            ps.setString(3, fecha);
            ps.setString(4, desc);
            System.out.println("llene los comodines de la tabla venta");
            int numFilas=ps.executeUpdate();
            System.out.println("No surgio error al ingresar");
            if(numFilas>0){
                ps=con.prepareCall("SELECT @@identity AS id");
                rs=ps.executeQuery();
                if(rs.next()){
                    folio=rs.getInt("id");
                    System.out.println(folio);
                }
                int i=0;
                double total=0;
                System.out.println(detalle[i][0]);
                System.out.println("Creo variableslocales");
                
                while(detalle[i][0]!=0){//De este modo digo que mientras lo que se registro no sea 0, es decir, ingresaron datos
                    System.out.println("Entro a while");
                    ps=con.prepareCall("select * from producto where idproducto=?");
                    ps.setInt(1, detalle[i][0]);
                    rs=ps.executeQuery();
                    System.out.println("Aqui veo La cantidad de productos");
                    if(rs.next()){
                        int cantidad=rs.getInt("stock");
                        System.out.println("Guardo la cantidad");
                        System.out.println(cantidad);
                        if(cantidad>=detalle[i][1]){
                            System.out.println("Valido lo que se quieren llevar");
                            ps=con.prepareCall("insert into factura (idventa, idproducto, cantidad, subtotal) values (?, ?, ?, ?)");
                            System.out.println("Hago la consulta");
                            ps.setInt(1, folio);
                            ps.setInt(2, detalle[i][0]);
                            ps.setInt(3, detalle[i][1]);
                            double t=detalle[i][1]*costo[i];
                            System.out.println("Aqui llegue");
                            ps.setDouble(4, t);
                            System.out.println("llene los comodines correctamente");
                            // 1 es cantidad y 2 es costo
                            /*despues de esta insercion, insertar el total*/
                            numFilas=ps.executeUpdate();
                            System.out.println("hice la insercion");
                            if(numFilas>0){
                                total+=t;
                                ps=con.prepareCall("update producto set stock=? where idproducto=?");
                                ps.setInt(1, cantidad-detalle[i][1]);
                                ps.setInt(2, detalle[i][0]);
                                numFilas=ps.executeUpdate();
                                if(numFilas>0){
                                    System.out.println("Se uso el trigger con exito");
                                }
                            }
                        }//Si hay suficiente cantidad hace el procedimiento con normalidad
                        else{
                            JOptionPane.showMessageDialog(null, "No se puede vender esa cantidad del producto con el 'CÓDIGO' "+detalle[i][0]);
                            System.out.println(detalle[i][0]);
                            System.out.println(detalle[i+1][0]);
                        }//Aqui termina el if else si hay suficiente cantidad o no para un pedido
                        i++;
                    }//Aqui regresa la cantidad una consulta
                }//Aqui termina el ciclo que mete datos de los productos
                /*
                    Al final inserta el total de la compra en la venta original
                */
                ps=con.prepareCall("update venta set totalventa=? where idventa=?");
                ps.setDouble(1, total);
                ps.setInt(2, folio);
                numFilas=ps.executeUpdate();
                if(numFilas>0){
                   JOptionPane.showMessageDialog(null, "Venta realizada con exito");
                    venta=true; 
                }        
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo hacer la venta");
            }
            con.close();
        }
        return venta;
    }
    
    public boolean devolucion(int idEmpleado, int idCliente, String desc, int [][] detalle, double [] costo) throws SQLException{
        boolean devolucion=false;
        String fecha="";
        int folio=0;
        Connection con=c.getConexion();
        if(con!=null){
            //Aqui saco la fecha
            PreparedStatement ps;
            ps=con.prepareCall("select convert(char(10), getdate(), 103) as hoy");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                fecha=rs.getString("hoy");
                System.out.println(fecha);
            }
            //Aqui comienza la insercion a la tabla
            ps=con.prepareCall("insert into venta (idempleado, idcliente, fecha, descripcion) values (?, ?, ?, ?)");
            ps.setInt(1, idEmpleado);
            ps.setInt(2, idCliente);
            ps.setString(3, fecha);
            ps.setString(4, desc);
            System.out.println("llene los comodines de la tabla venta");
            int numFilas=ps.executeUpdate();
            System.out.println("No surgio error al ingresar");
            if(numFilas>0){
                ps=con.prepareCall("SELECT @@identity AS id");
                rs=ps.executeQuery();
                if(rs.next()){
                    folio=rs.getInt("id");
                    System.out.println(folio);
                }
                int i=0;
                double total=0;
                
                System.out.println("Creo variableslocales");
                
                while(detalle[i][0]!=0){//De este modo digo que mientras lo que se registro no sea 0, es decir, ingresaron datos
                    ps=con.prepareCall("insert into factura (idventa, idproducto, cantidad, subtotal) values (?, ?, ?, ?)");
                    System.out.println("Hago la insercion");
                    ps.setInt(1, folio);
                    ps.setInt(2, detalle[i][0]);
                    ps.setInt(3, detalle[i][1]);
                    double t=(detalle[i][1]*costo[i])*-1;
                    System.out.println("Aqui llegue");
                    ps.setDouble(4, t);
                    System.out.println("llene los comodines correctamente");
                    // 1 es cantidad y 2 es costo
                    /*despues de esta insercion, insertar el total*/
                    numFilas=ps.executeUpdate();
                    System.out.println("hice la insercion");
                    if(numFilas>0){
                        total+=t;
                    }           
                    i++;
                }//Aqui termina el ciclo que inserta todos los productos
                
                ps=con.prepareCall("update venta set totalventa=? where idventa=?");
                ps.setDouble(1, total);
                ps.setInt(2, folio);
                numFilas=ps.executeUpdate();
                if(numFilas>0){
                   JOptionPane.showMessageDialog(null, "Venta realizada con exito");
                    devolucion=true; 
                }   
            }//aqui termina el SI ya inserto en venta     
            con.close();
        }//Aqui termina lel SI la conexion no fue nula
        return devolucion;
    }//Fin de la funcion de la devolcion
}//Fin de la clase

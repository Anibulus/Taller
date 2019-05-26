package modelo;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class empleadoDAO {
    conexion c=null;
    /*
    Se utiliza coonexion, primero teniendolo con un valor nulo (investigar por que)------------------------------------------------    
    */
    public empleadoDAO(){
        c=new conexion();
        /*
        Se tiene un constructior de empleado DAO para que asi realice las funciones consultando bases de datos
        */
    }
    
    public boolean validaInicioSesion(String usu, String pass) throws SQLException{
        boolean entrar=false;//Esta variable le dira a la ventana vista si la persona pudo entrar a la base de datos, sino, no le permite entrar
        Connection con=c.getConexion();//Usamos el objeto conexion que se dirige a la base de datos de sqlServer + Connection (Investigar)----------
        if(con!=null){
            PreparedStatement ps;//Investigar funcionalidad-----------------------------------------------------------------------------------------
            ps=con.prepareStatement("select * from empleado");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ps=con.prepareStatement("select * from usuarios where usuario=?");
                ps.setString(1, usu);
                rs=ps.executeQuery();
                    if(rs.next()){
                        ps=con.prepareStatement("select * from usuarios where contrasenia=?");
                        ps.setString(1, pass);
                        rs=ps.executeQuery();
                        if(rs.next()){
                            ps=con.prepareStatement("select * from usuarios where usuario=? and contrasenia=? and estatus='A'");
                            ps.setString(1, usu);
                            ps.setString(2, pass);
                            rs=ps.executeQuery();
                            /*
                            Nota:Guardar en el programa cual es el empleado que inicio la sesion
                            
                            Diferencia entre excecute Queryññpp y Update
                            ¿Qué es el resulSet?
                            ExcecuteUpdate se va a utilizar para las intrucciones insert, delete y update
                            Query se usa paralos select debido a que entrega informacion
                            */
                            if(rs.next()){
                                
                                ps=con.prepareStatement("select * from empleado where idusuario=?");
                                ps.setInt(1, rs.getInt("idusuario"));
                                System.out.println("Soy idUsuario en empDAO "+rs.getInt("idusuario"));
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    c.setEmpleadoActivo(rs.getInt("idempleado"));
                                    System.out.println("Estoy en empleado DAO"+rs.getInt("idempleado"));
                                }
                                /*
                                Si retorna un registo se utiliza .next con el executeQuery, si se utiliza execute update se utiliza un if que verifique si se alteraron filas
                                .next quiere decir donde me traiga la primera informacion, si se vuelve a aplicar, encuentra la siguiente de la que ya habia encontrado
                                */
                                entrar=true;
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");  
                            }
                        }
                        else{
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");         
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                    }              
                /*
                Con esto se cierra la conexion a la base de datos ya que la variable para ello es "con"
                */               
            }
            else{
                JOptionPane.showMessageDialog(null, "No hay ningun registro");
                JOptionPane.showMessageDialog(null, "Se abre paso al primer empleado de la empresa");
                String nombre=JOptionPane.showInputDialog(null, "Nombre de la persona a cargo");
                String usuario=JOptionPane.showInputDialog(null, "Usuario para ingresar al sistema");
                String contrasena=JOptionPane.showInputDialog(null, "Agrega una contraseña");
                            ps=con.prepareStatement("insert into usuarios (usuario, contrasenia, estatus) values (?, ?, 'A')");
                            ps.setString(1, usuario);
                            ps.setString(2, contrasena);                     
                        int numFilas=ps.executeUpdate();
                        if(numFilas>0){
                            System.out.println("inserte en usuarios");
                            /*Esto es solo para estar seguro de el usuario que tendta el emp´leado de la empresa*/
                            ps=con.prepareCall("select * from usuarios where usuario=?");
                            ps.setString(1, usuario);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                int a=rs.getInt("idusuario");
                                ps=con.prepareStatement("insert into empleado (idusuario,nombre, app,apm,  direccion, telefono, curp, sueldo, puesto) values (?,?,'','','','','',0, 'Administrador')");
                                ps.setInt(1,a);
                                ps.setString(2, nombre);
                                numFilas=ps.executeUpdate();
                                if(numFilas>0){
                                    JOptionPane.showMessageDialog(null, "Más informacion se puede agregar o modificar en PERFIL");
                                    JOptionPane.showMessageDialog(null, "Se te ha registrado, procede a Ingresar");
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Error en usuario");
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Ocurrio un error");
                        }
                    }
            con.close();
        }
        return entrar;
        /*
        Si la funcion se realizo exitosamente, el valor de entrar cambiara a true, en caso de que no, seguira siendo false y no iniciara sesion
        */
    }//Fin de la funcion validar inicio de sesion
    
    public boolean registrarEmpleado(String usu, String nombre, String app, String apm, String direccion, String telefono, String curp, String puesto, double sueldo) throws SQLException{
        boolean registro=false;
        
        Connection con=c.getConexion();
        
        if(con!=null){
            /*
                 Si la conexion no esnula,entra a hacer la funcion
            */
            PreparedStatement ps;
            empleado emp=null;
            emp=consultarEmpleado(0, nombre);
            System.out.println("consulto empleado");
            /*
                Aqui consulta a la persona y si se llena el objeto quiere decir que ya habia sido registrado
                por lo que no lepermitira continuar
            */
            if(emp==null){
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
                    emp=new empleado();
                    emp.setIdusuario(rs.getInt("idusuario"));
                    System.out.println("Hago la insercion");
                    ps=con.prepareStatement("insert into empleado (idusuario, nombre, app, apm, direccion, telefono, curp,  puesto, sueldo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setInt(1, emp.getIdusuario());
                    ps.setString(2, nombre);
                    ps.setString(3, app);
                    ps.setString(4, apm);
                    ps.setString(5, direccion);
                    ps.setString(6, telefono);
                    ps.setString(7, curp);
                    ps.setString(8, puesto);
                    ps.setDouble(9, sueldo);
                    int numFilas=ps.executeUpdate();
                    if(numFilas>0){
                        registro=true;
                        JOptionPane.showMessageDialog(null, "Usted ha registrado a su nuevo empleado "+nombre+" "+app);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "El empleado ya ha sido registrado");
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
                ps=con.prepareStatement("insert into usuarios (usuario, contrasenia, estatus) values (?, ?, 'A')");
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
    
    public boolean modificarEmpleado(int idEmpleado, String nombre, String app, String apm, String direccion, String telefono, String curp, String puesto, double sueldo) throws SQLException{
       boolean cambio=false;
       
       Connection con=c.getConexion();
       if(con!=null){
           PreparedStatement ps;
           empleado emp=null;
           emp=consultarEmpleado(idEmpleado, nombre);
           /*
           La anterior linea de codigo valida que el empleado se encuentre en la base de datos antes de modificar de lo contrario marcaria un error
           */
           if(emp!=null){
                ps=con.prepareStatement("update empleado  set nombre=?, app=?, apm=?, direccion=?, telefono=? , curp=? , puesto=? ,sueldo=? where idempleado=?");      
                ps.setString(1, nombre);
                ps.setString(2, app);
                ps.setString(3, apm);
                ps.setString(4, direccion);
                ps.setString(5, telefono);
                ps.setString(6, curp);
                ps.setString(7, puesto);
                ps.setDouble(8, sueldo);
                ps.setInt(9, idEmpleado);
                int numFilas=ps.executeUpdate();
                if(numFilas>0){
                    cambio=true;
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro correctamente");
                }
            }       
            else{
                JOptionPane.showMessageDialog(null, "No se puede modificar un empleado que no existe");
            }
            con.close(); 
       }
       return cambio;
    }
    
    /*Aqui estan los modificar pero de perfil*/
    public boolean modificarPerfil(int idEmpleado, String usu, String contrasena, String direccion, String telefono) throws SQLException{
        System.out.println("Acabo de entrar en modificarPerfil SSC");
        boolean perfil=false;
        boolean usuario=true;
        System.out.println("Creo las variables bandera");
        empleado emp=null;
        System.out.println("Creo el objeto empleado");
        emp=consultarEmpleado(idEmpleado, "");//Pido la informacion del empleado activo
        System.out.println("Consulto al empleado");
        if(emp.getContrasena().equals(contrasena)){
            System.out.println("Reviso que la contraseña sea la misma");
            Connection con=c.getConexion();
            if(con!=null){
                System.out.println("Entro si la conexion fue exitosa");
                System.out.println(emp.getUsuario()+"----"+usu);
                if(!emp.getUsuario().equals(usu)){
                    System.out.println(emp.getUsuario()+"----"+usu);
                    usuario=false;
                    int eleccion=JOptionPane.showConfirmDialog(null, "¿Estás seguro de cambiar tu usuario?");
                    if(eleccion==JOptionPane.YES_OPTION){
                        PreparedStatement ps=con.prepareCall("update usuarios set usuario=? where idusuario=?");
                        ps.setString(1, usu);
                        ps.setInt(2, emp.getIdusuario());
                        int numFilas=ps.executeUpdate();
                        System.out.println("soy numero de filas en usuario " +numFilas);
                        if(numFilas>0){
                            usuario=true;
                        }          
                    }//Aqui termina si la opcion fue si
                    else{
                        JOptionPane.showMessageDialog(null, "Usuario no cambiado");
                        usuario=true;
                    }
                }//Aqui termina el si el usuario ingresado es diferente del original
                System.out.println("Termine de ingresar el usuario");
                PreparedStatement ps=con.prepareCall("update empleado set direccion=?, telefono=? where idempleado=?");
                ps.setString(1, direccion);
                ps.setString(2, telefono);
                ps.setInt(3, emp.getIdEmpleado());
                System.out.println("lleno los comodines");
                int numFilas=ps.executeUpdate();
                System.out.println("soy numero de filas en empleado" + numFilas);
                if(numFilas>0){
                    perfil=true;
                }
                con.close();
            }//Aqui termina el if de si la conexion fue abierta
        }//Aqui termina el if si la contraseña es incorrecta
        else{
            JOptionPane.showMessageDialog(null, "Tu contraseña es incorrecta");
            System.out.println(emp.getContrasena());
            System.out.println(contrasena);
        }
        System.out.println(usuario);
        System.out.println(perfil);
        return perfil==usuario;
    }//Aqui termina la funcion

    public boolean modificarPerfil(int id,String usu, String contrasena, String NContrasena, String direccion, String telefono) throws SQLException{
        boolean perfil=false;
        boolean usuario=false;
        int eleccion;
        empleado emp=consultarEmpleado(id, "");
        Connection con = c.getConexion();
        if(emp.getContrasena().equals(contrasena)){
            if (con != null) {
                if(!emp.getUsuario().equals(usu)){
                    usuario=false;
                    eleccion=JOptionPane.showConfirmDialog(null, "¿Estás seguro de cambiar tu usuario?");
                    if(eleccion==JOptionPane.YES_OPTION){
                        usuario=modificarUsuario(emp.getIdusuario(), usu, NContrasena);                             
                    }//Aqui termina si la opcion fue si
                    else{                            
                        JOptionPane.showMessageDialog(null, "Usuario no cambiado");
                    }//Aqui si modifica la contrasena
                }//Aqui termina el si el usuario ingresado es diferente del original
                else if(!usuario){
                    eleccion=JOptionPane.showConfirmDialog(null, "¿Estás seguro de cambiar tu contraseña?");
                    if (eleccion == JOptionPane.YES_OPTION) {
                        usuario = true;
                        PreparedStatement ps = con.prepareCall("update usuarios set contrasenia=? where idusuario=?");
                        ps.setString(1, NContrasena);
                        ps.setInt(2, emp.getIdusuario());
                        int numFilas = ps.executeUpdate();
                        System.out.println("soy numero de filas en usuario " + numFilas);
                        if (numFilas > 0) {
                            usuario = true;
                        }
                    }
                    usuario=true;
                }
                 PreparedStatement ps=con.prepareCall("update empleado set direccion=?, telefono=? where idempleado=?");
                    ps.setString(1, direccion);
                    ps.setString(2, telefono);
                    ps.setInt(3, emp.getIdEmpleado());
                    int numFilas=ps.executeUpdate();
                    if(numFilas>0){
                        perfil=true;
                    }
            }
        }//Aqui termina si la contrasena es igual
        else{
            JOptionPane.showMessageDialog(null, "La contraseña es incorrecta");
        }
        return perfil==usuario;
    }//Aqui termina modificar empleado con sobrecarga
    
    public boolean modificarUsuario(int id, String usu, String pass) throws SQLException{
        boolean cambio=false;
        
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("update usuarios set usuario=?, contrasenia=? where idusuario=?");
            ps.setString(1, usu);
            ps.setString(2, pass);
            ps.setInt(3, id);
            int numFilas=ps.executeUpdate();
            if(numFilas>0){
                cambio=true;
            }
            con.close();
        }   
        return cambio;
    }
    
    /*
        Aqui retoma ls funciones CRUD sin tanta especificacion
    */
    public boolean eliminarEmpleado(int id) throws SQLException{
        boolean funcion=false;
        
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            /*
            ps=con.prepareStatement("update from empleado where idEmpleado=? set estatus='I' ");
            */
            /*ps=con.prepareStatement("delete from empleado where idEmpleado=?");
            ps.setInt(1,id);
            int numFilas=ps.executeUpdate();
            */
            ps=con.prepareStatement("select * from empleado where idempleado=?");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                empleado e=new empleado();
                e.setIdusuario(rs.getInt("idusuario"));
                ps=con.prepareStatement("update usuarios set estatus='I' where idusuario=?");
                ps.setInt(1, e.getIdusuario());
                int numFilas=ps.executeUpdate();
                if(numFilas>0){
                 funcion=true;
                 JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente");
                }
            }
            /*Pensar en un else para esta funcion */            
            /*
            Aqui no se utiliza result set porque no se regresa una consulta sino un numero de filas afectadas
            y en base a eso sabremos si se elimino correctamente
            */
            
            con.close();
            /*
            Siempre se cierra la conexion una vez se acaba
            */
        }
        
        return funcion;
    }
    
    public empleado consultarEmpleado(int id, String nom) throws SQLException{
        empleado emp=null;
        /*
        Se crea un objeto vacio de tipo empleado, Y esta vacio porque si no se encuentra la persona no tiene nada que registrar
        En caso de que si encuentre a alguien, dentro del if se llenaran los datos de la clase empleados necesarios (Se le hace llenado de datos si encuentra)
        */
        
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("select * from empleado where idempleado=? or nombre=?");
            ps.setInt(1, id);
            ps.setString(2, nom);
            ResultSet rs=ps.executeQuery();
            /*
            Luego de que se hace la consulta junto al llenado de datos, se verifica si se retorno algun valor
            */
            if(rs.next()){
                emp=new empleado();
                emp.setIdEmpleado(rs.getInt("idempleado"));
                emp.setIdusuario(rs.getInt("idusuario"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellidoP(rs.getString("app"));
                emp.setApellidoM(rs.getString("apm"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setCurp(rs.getString("curp"));
                emp.setPuesto(rs.getString("puesto"));
                emp.setSueldo(rs.getDouble("sueldo"));
                ps=con.prepareStatement("select * from usuarios where idusuario=?");
                ps.setInt(1,emp.getIdusuario());
                rs=ps.executeQuery();
                if(rs.next()){
                    emp.setUsuario(rs.getString("usuario"));
                    emp.setContrasena(rs.getString("contrasenia"));
                    emp.setEstatus(rs.getString("estatus"));
                    if(emp.getEstatus().equals("I")){
                        emp=null;
                    }
                }
                /*
                Agregar que si el estatus de el empleado es I, no lo muestre por medio de la consulta    
                */
                
                /*
                En esta parte se llena el objeto empleado con toda la informacion que retorna la consulta
                emp tiene las funciones de la clase empleado y lo llenamos con los setters
                rs es la variable de resultSet y la usamos para objeter los datos de cada columna de la tabla
                */
            }
            con.close();
            /*
            Se cierra la conexion y esta dentro de if porque si se abrio se debe cerrar, de otro modo nunca estuvo abierta
            */
        }
        return emp;
        /*
        Se regresa el valor de empleado, ya sea que encontrara infrormacion o no
        */
    }//Fin de la funcion para consultar un empleado
    
    public empleado consultaPorUsuario(String usuario, String contrasena) throws SQLException{
        empleado emp=null;
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("select * from usuarios where usuario=? or contrasenia=?");
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                emp=new empleado();
                emp.setIdusuario(rs.getInt("idusuario"));
                emp.setUsuario(rs.getString("usuario"));
                emp.setContrasena(rs.getString("contrasenia"));
                emp.setEstatus(rs.getString("estatus"));
                /*
                   Se llena un pequeño objeto de usuario
                */
                if(emp.getEstatus().equals("I")){
                    emp=null;
                }//Aqui termina el Si el empleado no es inactivo
                else{
                    ps=con.prepareCall("select * from empleado where idusuario=?");
                    ps.setInt(1, emp.getIdusuario());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        emp.setIdEmpleado(rs.getInt("idempleado"));
                        emp.setNombre(rs.getString("nombre"));
                        emp.setApellidoP(rs.getString("app"));
                        emp.setApellidoM(rs.getString("apm"));
                        emp.setDireccion(rs.getString("direccion"));
                        emp.setTelefono(rs.getString("telefono"));
                        emp.setCurp(rs.getString("curp"));
                        emp.setPuesto(rs.getString("puesto"));
                        emp.setSueldo(rs.getDouble("sueldo")); 
                    }//Aqui se lleno el objeto si no era inactivo
                }
            }//Aqui termina elprimer if
            con.close();
        }
        return emp;
    }
    
    public ArrayList<empleado> consultarTodos() throws SQLException{
        ArrayList listaEmpleados=new ArrayList();
        Connection con=c.getConexion();
        if(con!=null){
            PreparedStatement ps;
            ps=con.prepareStatement("select * from empleado");
            ResultSet rs=ps.executeQuery();
            int e=0;
            while(rs.next()){
                e++;
                empleado emp=new empleado();
                emp.setIdEmpleado(rs.getInt("idempleado"));
                emp.setIdusuario(rs.getInt("idusuario"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellidoP(rs.getString("app"));
                emp.setApellidoM(rs.getString("apm"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setCurp(rs.getString("curp"));
                emp.setPuesto(rs.getString("puesto"));
                emp.setSueldo(rs.getDouble("sueldo"));             
                listaEmpleados.add(emp);
            }
            System.out.println("Estoy aqui  "+e);
            con.close();
        }               
        return listaEmpleados;
    }//Fin de consultar todos
    
    /*Funciones auxiliares para mostrar ventanas (dependiendo si es administrador)*/
    
    public boolean validaPuesto (int id) throws SQLException{
        boolean puesto=false;
        Connection con=c.getConexion();
        empleado e=null;
        if(con!=null){   
            PreparedStatement ps=con.prepareStatement("select * from empleado where idempleado=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                e=new empleado();
                e.setPuesto(rs.getString("puesto"));
                if(e.getPuesto().equals("Administrador")){
                    puesto=true;
                }
            }           
            con.close();
        } 
        return puesto;
    }
}//Fin de la clase 

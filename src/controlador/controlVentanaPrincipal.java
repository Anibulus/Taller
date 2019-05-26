package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import net.sf.jasperreports.engine.JRException;
import vista.*;

public class controlVentanaPrincipal implements ActionListener{

    private principal menu;
    private empleadoDAO empDAO;
    private DefaultTableModel tabla;//Esta es para la tabla de consultar empleado entre otros
    conexion c=null;
    empleado e=null;
    clienteDAO cDAO;
    productoDAO pDAO;
    
    
    public controlVentanaPrincipal(principal menu, empleadoDAO emp) throws SQLException{
        this.menu=menu;
        cDAO=new clienteDAO();
        pDAO=new productoDAO();
        this.empDAO=emp;
        this.c=new conexion();
        e=c.getEmpleadoActivo();
        /*Aqui es el menu de empleados*/
        menu.menuEmpleados.addActionListener(this);
        menu.itemEmpleadoPerfil.addActionListener(this);
        menu.itemEmpleadoNuevo.addActionListener(this);
        menu.itemEmpleadoBuscar.addActionListener(this);
        menu.itemControlEmpleados.addActionListener(this);
        /*Aqui esta el menu de clientes*/
        menu.itemBuscarCliente.addActionListener(this);
        menu.itemNuevoCliente.addActionListener(this);
        menu.itemControlCliente.addActionListener(this);
        /*Aqui esta la opcion de inventario*/
        menu.itemNuevoProducto.addActionListener(this);
        menu.itemBuscarProducto.addActionListener(this);
        menu.itemControlInventario.addActionListener(this);
        /*Aqui esta la opcion de vender*/
        menu.btnVender.addActionListener(this);
        /*Aqui son los reportes*/
        menu.reporteTodosEmpleados.addActionListener(this);
        menu.itemEnviarCorreo.addActionListener(this);
        
        /*
        empleado e=new empleado();
        e=emp.consultarEmpleado(1, "gvb");
        menu.letreroBienvenido=String.valueOf(e.getNombre());
        */
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        /*Esto es para las opciones de venta*/
        /*----------------------------------------------------------------------------------------------------------------------------*/
        if(ae.getSource()==menu.btnVender){
            ventanaVenta vv=new ventanaVenta();
            vv.setLocationRelativeTo(null);
            vv.setVisible(true);
            controlVenta cv=new controlVenta(empDAO,vv, cDAO, pDAO);
            menu.dispose();
        }
        
        
        /*Aqui estan las opciones del menu empleados*/
        /*----------------------------------------------------------------------------------------------------------------------------*/
        if(ae.getSource()==menu.itemEmpleadoPerfil){//Aqui es para Ver el perfil
            perfil p=new perfil();
            p.setLocationRelativeTo(null);
            p.setVisible(true);
            controlPerfil cp=new controlPerfil( p,empDAO);
            try {
                e = empDAO.consultarEmpleado(e.getIdEmpleado(), "");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Un error ocurrio al consultar");
            }
            /*//Revisar cual manera esta correcra
            cp.p.letreroNombre.setText("Nombre: "+e.getNombre());
            cp.p.letreroApellidos.setText("Apellidos: "+e.getApellidoP()+" "+e.getApellidoM());
            cp.p.letreroPuesto.setText("Puesto que desempeña: "+e.getPuesto());
            cp.p.letreroSueldo.setText("Salario Mensual: $"+e.getSueldo());
            cp.p.letreroDireccion.setText("Direccion: "+e.getDireccion());
            cp.p.letreroTelefono.setText("Telefono: "+e.getTelefono());
            cp.p.letreroCurp.setText("CURP: "+e.getCurp());
            cp.p.letreroUsuario.setText("Usuario: "+e.getUsuario());
            */
            p.letreroNombre.setText("Nombre: "+e.getNombre());
            p.letreroApellidos.setText("Apellidos: "+e.getApellidoP()+" "+e.getApellidoM());
            p.letreroPuesto.setText("Puesto que desempeña: "+e.getPuesto());
            p.letreroSueldo.setText("Salario Mensual: $"+e.getSueldo());
            p.letreroDireccion.setText("Direccion: "+e.getDireccion());
            p.letreroTelefono.setText("Telefono: "+e.getTelefono());
            p.letreroCurp.setText("CURP: "+e.getCurp());
            p.letreroUsuario.setText("Usuario: "+e.getUsuario());
            
            /*
                Me pidio hacer un package private, quiero ver que a que se referia
            */
        }//Aqui termina la opcion de Perfil
        else if(ae.getSource()==menu.itemEmpleadoNuevo){try {
            //Aqui es para agregar a un nuevo empleado
            if(empDAO.validaPuesto(e.getIdEmpleado())){
                ventanaNuevoEmpleado vne=new ventanaNuevoEmpleado();
                vne.setVisible(true);
                vne.setLocationRelativeTo(null);
                menu.dispose();
                controlNuevoEmpleado ce=new controlNuevoEmpleado(empDAO, vne);
            }
            else{
                JOptionPane.showMessageDialog(null, "No tienes permiso para ver esta sección");
            }
            } catch (SQLException ex) {
                Logger.getLogger(controlVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//Aqui termina Nuevo Empleado
        else if(ae.getSource()==menu.itemEmpleadoBuscar){//Aqui se busca empleado
            /*
                Aqui se crea la ventana
            */
            buscarEmpleado ventanaBE=new buscarEmpleado();
            ventanaBE.setLocationRelativeTo(null);
            ventanaBE.setVisible(true);   
            /*
                Aqui la funcion principal de la ventana se llena (la tabla) desde que entra
            */
            tabla= (DefaultTableModel) ventanaBE.tablaTodosEmpleados.getModel(); //---------------------------Entender esta linea         
           for(int a=0;a<tabla.getRowCount();a++){
               tabla.removeRow(tabla.getRowCount()-1);
           }
           
           Object[] columna=new Object[9];
           int maximo=0;
           double promedio=0;
            try {
                maximo=empDAO.consultarTodos().size();
                System.out.println("soy maximo "+maximo);
            } catch (SQLException ex) {
            
            }
            
            /*
                Esta funcion limpia la tabla antes de mostrarla
            */
            while(tabla.getRowCount()!=0){
                tabla.removeRow(0);
            }
            
            for(int i=0; i<maximo; i++){
               try {
                   columna[0]=empDAO.consultarTodos().get(i).getIdEmpleado();
                   columna[1]=empDAO.consultarTodos().get(i).getNombre();
                   columna[2]=empDAO.consultarTodos().get(i).getApellidoP();
                   columna[3]=empDAO.consultarTodos().get(i).getApellidoM();
                   columna[4]=empDAO.consultarTodos().get(i).getPuesto();
                   columna[5]=empDAO.consultarTodos().get(i).getSueldo();
                   columna[6]=empDAO.consultarTodos().get(i).getCurp();
                   columna[7]=empDAO.consultarTodos().get(i).getTelefono();
                   columna[8]=empDAO.consultarTodos().get(i).getDireccion();
               } 
               catch (SQLException ex) {
                   i=maximo;
                   System.out.println("No se pudo");
               }
                
                tabla.addRow(columna);
            }        
            /*
                Aqui termina la funcio de consultar todos
            */
            controlBuscarEmpleado cbe=new controlBuscarEmpleado(ventanaBE, empDAO);
            menu.dispose();
        }//Aqui termina buscar empleado
        else if(ae.getSource()==menu.itemControlEmpleados){try {
            //Aqui esta el control de empleado
            if(empDAO.validaPuesto(e.getIdEmpleado())){
                ventanaEmpleado v=new ventanaEmpleado();
                v.setVisible(true);
                v.setLocationRelativeTo(null);
                menu.dispose();
                controlEmpleado cE=new controlEmpleado(v, empDAO);  
            }
            else{
                JOptionPane.showMessageDialog(null, "No tienes permiso para acceder a esta opcion");
            }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al validar tu puesto");
            }
        }//Aqui termina control empleados
        
        /*Aqui empieza la opcion de clientes*/
        /*-----------------------------------------------------------------------------------------------------------------------*/
        if(ae.getSource()==menu.itemBuscarCliente){
            /*Crear ventana y controlador*/
            ventanaClienteBuscar vcb=new ventanaClienteBuscar();
            vcb.setLocationRelativeTo(null);
            vcb.setVisible(true);
            controlClienteBuscar control=new controlClienteBuscar( cDAO, vcb);
            /*
                Aqui empieza el llenad de la tabla clientes
            */
            tabla= (DefaultTableModel) vcb.tablaTodosClientes.getModel(); //---------------------------Entender esta linea         
           for(int a=0;a<tabla.getRowCount();a++){
               tabla.removeRow(tabla.getRowCount()-1);
           }
           //Se crea un lugar donde se almacenan los datos
           Object[] columna=new Object[7];
           int maximo=0;
           double promedio=0;
            try {
                maximo=cDAO.consultarTodos().size();
                System.out.println("soy maximo "+maximo);
            } catch (SQLException ex) {
                
            } 
            /*
                Esta funcion limpia la tabla antes de mostrarla
            */
            while(tabla.getRowCount()!=0){
                tabla.removeRow(0);
            }
            //Llenado de datos
            for(int i=0; i<maximo; i++){
               try {
                   columna[0]=cDAO.consultarTodos().get(i).getIdCliente();
                   columna[1]=cDAO.consultarTodos().get(i).getNombre();
                   columna[2]=cDAO.consultarTodos().get(i).getApp();
                   columna[3]=cDAO.consultarTodos().get(i).getApm();
                   columna[4]=cDAO.consultarTodos().get(i).getCorreo();
                   columna[5]=cDAO.consultarTodos().get(i).getTelefono();
                   columna[6]=cDAO.consultarTodos().get(i).getDireccion();
               } 
               catch (SQLException ex) {
                   i=maximo;
                   System.out.println("No se pudo");
               }
                //Insercion en la tabla con los datos
                tabla.addRow(columna);
            }   
            
            menu.dispose();
        }
        else if(ae.getSource()==menu.itemNuevoCliente){
            ventanaClienteNuevo vcn=new ventanaClienteNuevo();
            vcn.setVisible(true);
            vcn.setLocationRelativeTo(null);
            controlClienteNuevo control=new controlClienteNuevo(vcn, cDAO);
            menu.dispose();
        }
        else if(ae.getSource()==menu.itemControlCliente){
            try {
                if(empDAO.validaPuesto(e.getIdEmpleado())){
                    ventanaCliente vc=new ventanaCliente();
                    vc.setVisible(true);
                    vc.setLocationRelativeTo(null);
                    /*Añadir controlador*/
                    controlClienteControl ccc=new controlClienteControl(vc,cDAO);
                    menu.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "No tienes permiso para acceder a esta opción");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al validar tu puesto");
            }
        }
        
        
        
        /*
            Aqui esta el menu de inventario
            ----------------------------------------------------------------------------------------------            
        */
        if(ae.getSource()==menu.itemNuevoProducto){
            ventanaProductoNuevo ventanaPN=new ventanaProductoNuevo();
            ventanaPN.setLocationRelativeTo(null);
            ventanaPN.setVisible(true);
            controlProductoNuevo cpn=new controlProductoNuevo(ventanaPN, pDAO);
            menu.dispose();
        }
        else if(ae.getSource()==menu.itemBuscarProducto){
            /*
                Aqui se crea la ventana
            */
            ventanaProductoBuscar ventanaPB=new ventanaProductoBuscar();
            ventanaPB.setLocationRelativeTo(null);
            ventanaPB.setVisible(true);   
            /*
                Aqui la funcion principal de la ventana se llena (la tabla) desde que entra
            */
            tabla= (DefaultTableModel) ventanaPB.tablaTodosEmpleados.getModel(); //---------------------------Entender esta linea         
           for(int a=0;a<tabla.getRowCount();a++){
               tabla.removeRow(tabla.getRowCount()-1);
           }
           
           Object[] columna=new Object[9];
           int maximo=0;
           double promedio=0;
            try {
                maximo=pDAO.consultarTodo().size();
                System.out.println("soy maximo "+maximo);
            } catch (SQLException ex) {
            
            }
            
            /*
                Esta funcion limpia la tabla antes de mostrarla
            */
            while(tabla.getRowCount()!=0){
                tabla.removeRow(0);
            }
            
            for(int i=0; i<maximo; i++){
               try {
                   columna[0]=pDAO.consultarTodo().get(i).getIdProducto();
                   columna[1]=pDAO.consultarTodo().get(i).getNombre();
                   columna[2]=pDAO.consultarTodo().get(i).getStock();
                   columna[3]=pDAO.consultarTodo().get(i).getDescripcion();
                   columna[4]=pDAO.consultarTodo().get(i).getCosto();
               } 
               catch (SQLException ex) {
                   i=maximo;
                   System.out.println("No se pudo");
               }
                
                tabla.addRow(columna);
            }        
            /*
                Aqui termina la funcio de consultar todos
            */
            controlProductoBuscar cbp=new controlProductoBuscar(ventanaPB, pDAO);
            menu.dispose();
        }
        else if(ae.getSource()==menu.itemControlInventario){
            ventanaProductoControl ventanaPC=new ventanaProductoControl();
            ventanaPC.setLocationRelativeTo(null);
            ventanaPC.setVisible(true);
            controlProductoControl cpc=new controlProductoControl(ventanaPC, pDAO);
            menu.dispose();
        }
        
        /*
            Aqui esta el menu de proveedores
            ----------------------------------------------------------------------------------------------------------------
        */
        if(ae.getSource()==menu.itemControlProveedor){
            
        }
        
        
        /*
            Aqui es el otro menu de los reportes
            --------------------------------------------------------------------------------------------------------------------
        */
        if(ae.getSource()==menu.reporteTodosEmpleados){
            ventanaMenuReportes ventana=new ventanaMenuReportes();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            controlVentanaReportes cvr=new controlVentanaReportes(ventana, empDAO);
        }
        
        if(ae.getSource()==menu.itemEnviarCorreo){
            ventanaCorreo v=new ventanaCorreo();
            v.setLocationRelativeTo(null);
            v.setVisible(true);
            controlCorreoVentana control=new controlCorreoVentana(v);
        }
    }  
}

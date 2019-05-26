package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.cliente;
import modelo.clienteDAO;
import modelo.conexion;
import modelo.empleado;
import modelo.empleadoDAO;
import modelo.productoDAO;
import modelo.ventaDAO;
import vista.principal;
import vista.ventanaTablaProductos;
import vista.ventanaVenta;

public class controlVenta implements ActionListener {
    private empleadoDAO empDAO;
    private productoDAO pDAO;
    private clienteDAO cDAO;
    private ventaDAO vDAO;
    private ventanaVenta ventanaV;
    private DefaultTableModel tabla;
    
    conexion c=null;
    empleado e=null;
    public controlVenta(empleadoDAO emp,ventanaVenta v, clienteDAO cDAO, productoDAO pDAO){
        this.cDAO=cDAO;
        this.pDAO=pDAO;
        this.empDAO=emp;  
        this.vDAO=new ventaDAO();
        this.ventanaV=v;
        c=new conexion();
        e=c.getEmpleadoActivo();
        this.ventanaV.btnRegresar.addActionListener(this);
        this.ventanaV.btnBuscarCliente.addActionListener(this);
        this.ventanaV.btnMasProducto.addActionListener(this);
        this.ventanaV.btnMenosProducto.addActionListener(this);
        this.ventanaV.btnTerminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaV.btnRegresar){
            principal inicio=new principal();
            inicio.setLocationRelativeTo(null);
            inicio.setVisible(true);
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(inicio, empDAO);
            } catch (SQLException ex) {
                
            }
            ventanaV.dispose();
        }//Aqui termina si la persona quizo regresar
        else if(ae.getSource()==ventanaV.btnBuscarCliente){//Esta opcion llena los datos del cliente en elcasode que seaencontrado
            String nom=ventanaV.txtNombre.getText();
            String id=String.valueOf(ventanaV.txtCodigoCliente.getValue());
            cliente cl=null;
            /*
                Aqui se consulta al cliente para saber si existe
            */
            try {
                cl=cDAO.consultarCliente(Integer.parseInt(id), nom);
            } catch (SQLException ex) {
                System.out.println("No se ha podido consultar correctamente");
            }
            if(cl!=null){
                ventanaV.txtNombre.setText(cl.getNombre());
                ventanaV.txtxCorreo.setText(cl.getCorreo());
                ventanaV.txtTelefono.setText(cl.getTelefono());
                ventanaV.txtDireccion.setText(cl.getDireccion());
            }
            else{
                JOptionPane.showMessageDialog(null, "No se encontro al cliente");
            }
        }
        else if(ae.getSource()==ventanaV.btnMasProducto){//Esta opcion buscara todos los productos de la tabla,no solo uno en especifico
            ventanaTablaProductos vtp=new ventanaTablaProductos();
            vtp.setLocationRelativeTo(null);
            vtp.setVisible(true);
            controladorVentanaTablaProductos cvtp=new controladorVentanaTablaProductos(vtp, pDAO, ventanaV);
            
            tabla= (DefaultTableModel) vtp.tablaProductos.getModel(); //---------------------------Entender esta linea           
            //Se crea un lugar donde se almacenan los datos
            Object[] columna=new Object[4];
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
            //Llenado de datos
            for(int i=0; i<maximo; i++){
               try {
                   columna[0]=pDAO.consultarTodo().get(i).getIdProducto();
                   columna[1]=pDAO.consultarTodo().get(i).getNombre();
                   columna[2]=pDAO.consultarTodo().get(i).getDescripcion();
                   columna[3]=pDAO.consultarTodo().get(i).getCosto();
               } 
               catch (SQLException ex) {
                   i=maximo;
                   System.out.println("No se pudo");
               }
                //Insercion en la tabla con los datos
                tabla.addRow(columna);
            }   
        }//Esta opcion es cuando lapersona busca mas productos
        else if(ae.getSource()==ventanaV.btnMenosProducto){
            int remover=ventanaV.tablaProductos.getSelectedRow();
            if(remover==-1){
                JOptionPane.showMessageDialog(null, "Necesitas seleccionar una fila para eliminar");
            }else{
                remover=JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto");
                if(remover==JOptionPane.YES_OPTION){
                    System.out.println(remover);
                    tabla= (DefaultTableModel) ventanaV.tablaProductos.getModel();
                    tabla.removeRow(remover);
                }
            }
        }//Esta opcion quita una fila
        else if(ae.getSource()==ventanaV.btnTerminar){
            if(!String.valueOf(ventanaV.txtCodigoCliente.getValue()).equals("0")){
                /*txt necesarios para las funciones*/
                String validar=(String)ventanaV.listaTipo.getSelectedItem(); 
                String codigoCliente=String.valueOf(ventanaV.txtCodigoCliente.getValue());
                String desc=ventanaV.txtDescripcion.getText();
                /*Arreglo con el que se lleva mas de un solo producto*/
                int [][] detalle=new int[10][3];
                //Declaracion de la tabla modo local
                tabla= (DefaultTableModel) ventanaV.tablaProductos.getModel();
                //Variables auxiliares para el llenado de el arreglo
                int maximo=tabla.getRowCount();
                double[] costo=new double[10];
                //llenado delarreglo y COSTO
                for (int i = 0; i < maximo; i++) {
                    String aux=String.valueOf(tabla.getValueAt(i, 0));
                    //System.out.println(aux);
                    detalle[i][0]=Integer.parseInt(aux);
                    aux=String.valueOf(tabla.getValueAt(i, 2));
                    //System.out.println(aux);
                    detalle[i][1]=Integer.parseInt(aux);
                    aux=String.valueOf(tabla.getValueAt(i, 3));
                    //System.out.println(aux);
                    costo[i]=Double.valueOf(aux);
                    /*
                     Hacer que diga el subtotal
                    */
                }
           if(validar.equals("Devolución")){
                try {
                    if(!empDAO.validaPuesto(e.getIdEmpleado())){
                        String contrasena=JOptionPane.showInputDialog(null, "Ingrese la contraseña maestra");
                        if(contrasena.equals("12345")){
                            if(vDAO.devolucion(e.getIdEmpleado(), Integer.parseInt(codigoCliente), desc, detalle, costo)){
                            //Losiguiente limpia el formato
                            while(tabla.getRowCount()!=0){
                                tabla.removeRow(0);
                            }
                            ventanaV.txtCodigoCliente.setValue(0);
                            ventanaV.txtNombre.setText("");
                            ventanaV.txtxCorreo.setText("");
                            ventanaV.txtTelefono.setText("");
                            ventanaV.txtDireccion.setText("");
                            ventanaV.txtDescripcion.setText("");
                            } 
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                        }
                    }
                    else{
                        if(vDAO.devolucion(e.getIdEmpleado(), Integer.parseInt(codigoCliente), desc, detalle, costo)){
                            
                        }                 
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(controlVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
           }//Aqui termina la validaciopn si es devolucion
           else{ 
                try {                    
                    if(vDAO.vender(e.getIdEmpleado(),Integer.parseInt(codigoCliente), desc , detalle, costo)){
                        JOptionPane.showMessageDialog(null, "Venta realizada con exito");
                        /*
                            Procede a limpriar toda la ventana
                        */
                        while(tabla.getRowCount()!=0){
                            tabla.removeRow(0);
                        }
                        ventanaV.txtCodigoCliente.setValue(0);
                        ventanaV.txtNombre.setText("");
                        ventanaV.txtxCorreo.setText("");
                        ventanaV.txtTelefono.setText("");
                        ventanaV.txtDireccion.setText("");
                        ventanaV.txtDescripcion.setText("");
                    }
                }catch (SQLException ex) {
                    System.out.println("No se pudo ingresar la venta");
                }
           }//Aqui termina la validacion si efectivamente se esta vendiendo
            }else{
                JOptionPane.showMessageDialog(null, "Tiene que llenar los datos del cliente");
            }
        }//Aqui termina laopcion de terminar la venta
    }//Aqui termina el action listener   
}//Fin de la claseque controla la ventana

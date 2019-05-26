package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.productoDAO;
import vista.ventanaTablaProductos;
import vista.ventanaVenta;

public class controladorVentanaTablaProductos implements ActionListener{
    private productoDAO pDAO;
    private ventanaTablaProductos ventanaProducto;
    private ventanaVenta ventana;
    DefaultTableModel tabla;
    public controladorVentanaTablaProductos(ventanaTablaProductos v, productoDAO pDAO, ventanaVenta ventana){
        this.pDAO=pDAO;
        this.ventanaProducto=v;
        this.ventana=ventana;
        this.ventanaProducto.btnAceptar.addActionListener(this);
        this.ventanaProducto.btnCancelar.addActionListener(this);
    }//Aqui termina el constructor
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaProducto.btnCancelar){
            ventanaProducto.dispose();
        }else if(ae.getSource()==ventanaProducto.btnAceptar){
            int seleccionado=ventanaProducto.tablaProductos.getSelectedRow();
            try {
                String codigo, nombre, descripcion, costo;
                if(seleccionado==-1){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
                }else{
                    String cantidad=String.valueOf(ventanaProducto.txtCodigo.getValue());
                    if(Integer.parseInt(cantidad)>0){
                        System.out.println("Cantidad mas de 0");
                        tabla=(DefaultTableModel) ventanaProducto.tablaProductos.getModel();
                        codigo=String.valueOf(ventanaProducto.tablaProductos.getValueAt(seleccionado, 0));
                        nombre=String.valueOf(ventanaProducto.tablaProductos.getValueAt(seleccionado, 1));
                        descripcion=String.valueOf(ventanaProducto.tablaProductos.getValueAt(seleccionado, 2));
                        costo=String.valueOf(ventanaProducto.tablaProductos.getValueAt(seleccionado, 3));
                        System.out.println(costo+"    "+cantidad);
                        /*Esta es la cantidad quese llevara la persona*/
                        System.out.println("lleno mis variables");
                        /*Aqui se saca el subtotal de un soloproducto*/      
                        
                        
                        
                        System.out.println("Calculo totales");
                        tabla=(DefaultTableModel) ventana.tablaProductos.getModel();
                        System.out.println("Regreso a la tablaoriginal");
                        Object [] fila={codigo, nombre, cantidad, costo};               
                        tabla.addRow(fila);
                        ventanaProducto.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Debes elegir una cantidad");
                    }
                }
                
            } catch (Exception e) {
            }
        }
    }//Aqui termina el escuchador  
}//Aqui termina la clase

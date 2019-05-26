package controlador;

import groovy.ui.text.FindReplaceUtility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.empleadoDAO;
import modelo.producto;
import modelo.productoDAO;
import vista.principal;
import vista.ventanaProductoNuevo;

public class controlProductoNuevo implements ActionListener{
    private ventanaProductoNuevo ventanaProductoN;
    private productoDAO pDAO;
    
    public controlProductoNuevo(ventanaProductoNuevo ventana, productoDAO pDAO){
        this.ventanaProductoN=ventana;
        this.pDAO=pDAO;
        this.ventanaProductoN.btnRegresar.addActionListener(this);
        this.ventanaProductoN.btnIngresar.addActionListener(this);
    }//Fin del constructor

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaProductoN.btnRegresar){
            principal menu=new principal();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            empleadoDAO eDAO=new empleadoDAO();
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(menu, eDAO);
            } catch (SQLException ex) {
                System.out.println("Ocurrio un problema alcambiar la ventana");
            }    
            ventanaProductoN.dispose();           
        }
        else if(ae.getSource()==ventanaProductoN.btnIngresar){
            String nombre=ventanaProductoN.txtNombre.getText();
            String desc=ventanaProductoN.txtDescripcion.getText();
            String costo=ventanaProductoN.txtCosto.getText();
            producto p=null;
            try {
                p=pDAO.cosultarProducto(0, nombre);
            } catch (SQLException ex) {
                System.out.println("No pude consultar el producto");
            }
            
            if(p==null){
                try {
                    if(pDAO.insertarProducto(nombre, desc, Double.parseDouble(costo))){
                        JOptionPane.showMessageDialog(null, "Se ha ingresado correctamente");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No se ha podido ingresar");
                    }
                } catch (SQLException ex) {
                    System.out.println("Ocurrio un error al ingresar el producto");
                }
            }else{
                JOptionPane.showMessageDialog(null, "El producto ya existe");
            }
        }
    }//Fin del escuchador
    
}//fin del controlador

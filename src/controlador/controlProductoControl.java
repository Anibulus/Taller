package controlador;

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
import vista.ventanaProductoControl;

public class controlProductoControl implements ActionListener{
    private ventanaProductoControl ventanaProductoC;
    private productoDAO pDAO;
    
    public controlProductoControl(ventanaProductoControl ventanaProductoC, productoDAO pDAO){
        this.ventanaProductoC=ventanaProductoC;
        this.pDAO=pDAO;
        this.ventanaProductoC.btnRegresar.addActionListener(this);
        this.ventanaProductoC.btnLimpiar.addActionListener(this);
        this.ventanaProductoC.btnConsultar.addActionListener(this);
        this.ventanaProductoC.btnActualizar.addActionListener(this);       
    }//Fin del constructor

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaProductoC.btnRegresar){
            principal menu=new principal();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            empleadoDAO eDAO=new empleadoDAO();
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(menu, eDAO);
            } catch (SQLException ex) {
                System.out.println("Ocurrrioun error al cambiarte de ventana");
            }
            ventanaProductoC.dispose();
        }
        else if(ae.getSource()==ventanaProductoC.btnLimpiar){
            ventanaProductoC.txtCodigo.setValue(0);
            ventanaProductoC.txtNombre.setText("");
            ventanaProductoC.txtCantidad.setValue(0);
            ventanaProductoC.txtxDescripcion.setText("");
            ventanaProductoC.txtxCosto.setText("");
        }
        else if(ae.getSource()==ventanaProductoC.btnConsultar){
            String id=String.valueOf(ventanaProductoC.txtCodigo.getValue());
            String nombre=ventanaProductoC.txtNombre.getText();
            producto p=null;
            try {
                p=pDAO.cosultarProducto(Integer.parseInt(id), nombre);
            } catch (SQLException ex) {
                System.out.println("No se ha podido consultar el producto");
            }
            if(p!=null){
                ventanaProductoC.txtCodigo.setValue(p.getIdProducto());
                ventanaProductoC.txtNombre.setText(p.getNombre());
                ventanaProductoC.txtCantidad.setValue(p.getStock());
                ventanaProductoC.txtxDescripcion.setText(p.getDescripcion());
                ventanaProductoC.txtxCosto.setText(String.valueOf(p.getCosto()));
            }
        }//Aqui termina la opcion de consultar
        else if(ae.getSource()==ventanaProductoC.btnActualizar){
            String id=String.valueOf(ventanaProductoC.txtCodigo.getValue());
            String nombre=ventanaProductoC.txtNombre.getText();
            String cantidad=String.valueOf(ventanaProductoC.txtCantidad.getValue());
            String desc=ventanaProductoC.txtxDescripcion.getText();
            String costo=ventanaProductoC.txtxCosto.getText();
            try {
                if(pDAO.modificarProducto(Integer.parseInt(id), nombre, Integer.parseInt(cantidad), desc, Double.parseDouble(costo))){
                    JOptionPane.showMessageDialog(null, "Se ha modificado correctamente");
                }
            } catch (SQLException ex) {
                System.out.println("Ocurrio un error al modificar el registro");
            }
        }
    }//Aqui termina el escuchador   
}//Fin del controlador

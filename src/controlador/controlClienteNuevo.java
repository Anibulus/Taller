package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.clienteDAO;
import modelo.empleadoDAO;
import vista.principal;
import vista.ventanaClienteNuevo;

public class controlClienteNuevo implements ActionListener {
    ventanaClienteNuevo ventanaCliente;
    clienteDAO cDAO;
    public controlClienteNuevo(ventanaClienteNuevo vcn, clienteDAO cDAO){
        this.ventanaCliente=vcn;
        this.cDAO=cDAO;
        this.ventanaCliente.btnIngresar.addActionListener(this);
        this.ventanaCliente.btnRegresar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaCliente.btnIngresar){
            String nombre=ventanaCliente.txtNombre.getText();
            String app=ventanaCliente.txtApp.getText();
            String apm=ventanaCliente.txtApm.getText();
            String direccion=ventanaCliente.txtDireccion.getText();
            String correo=ventanaCliente.txtCorreo.getText();
            String tel=ventanaCliente.txtTelefono.getText();
            String usu=ventanaCliente.txtUsuario.getText();
            String pass=ventanaCliente.txtContrasena.getText();
            System.out.println("Estas ingresando un cliente");
            if(!usu.equals("")){//Si ingresaron datos en la caja usuario, se ingresara cliente con usuario y contrasena
                try {
                if(cDAO.registrarUsuario(usu, pass)){
                    if(cDAO.ingresarCliente(usu, nombre, app, apm, direccion, tel, correo)){
                        JOptionPane.showMessageDialog(null, "Se ha ingresado correctamente a "+nombre);
                        JOptionPane.showMessageDialog(null, "Agregar limpiado de campos");
                        System.out.println("");
                    }
                    else{
                        System.out.println("Ocurrio un error");
                    }
                }
                } catch (SQLException ex) {

                }
            }
        }//Fin del boton de ingresar
        else if(ae.getSource()==ventanaCliente.btnRegresar){
            principal menu=new principal();
            empleadoDAO empDAO=new empleadoDAO();          
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(menu, empDAO);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un errro al regresarte");
            }
            ventanaCliente.dispose();
        }
    }//Aqui termina el escuchador
    
}

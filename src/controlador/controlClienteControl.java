package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.cliente;
import modelo.clienteDAO;
import modelo.empleadoDAO;
import vista.perfil;
import vista.principal;
import vista.ventanaCliente;

public class controlClienteControl implements ActionListener {
    private ventanaCliente ventanaControl;
    private clienteDAO cDAO;
    
    public controlClienteControl(ventanaCliente vc, clienteDAO cDAO){
        this.ventanaControl = vc;
        this. cDAO=cDAO;
        this.ventanaControl.btnActualizar.addActionListener(this);
        this.ventanaControl.btnConsultar.addActionListener(this);
        this.ventanaControl.btnLimpiar.addActionListener(this);
        this.ventanaControl.btnRegresar.addActionListener(this);
    }//Aqui termina el constructor
    
    /*
        A partir de aqui empieza el escuchador
    */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaControl.btnConsultar){//--------Aqui escucha cuando van a consultar
            String id= String.valueOf(ventanaControl.txtCodigo.getValue());
            String nombre=ventanaControl.txtNombre.getText();
            cliente c=null;
            try {
                c=cDAO.consultarCliente(Integer.parseInt(id), nombre);
            } catch (SQLException ex) {
                System.out.println("No se te ha podido consultar");
            }
            
            if(c!=null){
                ventanaControl.txtCodigo.setValue(c.getIdCliente());
                ventanaControl.txtNombre.setText(c.getNombre());
                ventanaControl.txtApellidoP.setText(c.getApp());               
                ventanaControl.txtApellidoM.setText(c.getApm());
                ventanaControl.txtDireccion.setText(c.getDireccion());
                System.out.println(c.getDireccion());
                ventanaControl.txtTelefono.setText(c.getTelefono());
                ventanaControl.txtCorreo.setText(c.getCorreo());
            }
        }
        else if(ae.getSource()==ventanaControl.btnActualizar){// ---------- Aqui escucha cuando van a Actualizar
            String id=String.valueOf(ventanaControl.txtCodigo.getValue());
            String nombre=ventanaControl.txtNombre.getText();
            String app=ventanaControl.txtApellidoP.getText();
            String apm=ventanaControl.txtApellidoM.getText();
            String dir=ventanaControl.txtDireccion.getText();
            String tel=ventanaControl.txtTelefono.getText();
            String correo=ventanaControl.txtCorreo.getText();
            try {
                System.out.println("Entre a Actualizar");
                if(cDAO.modificarCliente(Integer.parseInt(id), nombre, app, apm, dir, tel, correo)){
                    JOptionPane.showMessageDialog(null, "Se modifico correctamente el registro");
                }
            } catch (SQLException ex) {
                System.out.println("Ocurrio un error al modificar");
            }
        }
        else if(ae.getSource()==ventanaControl.btnLimpiar){//------------Aqui escucha cuando van a Limpiar
            ventanaControl.txtCodigo.setValue(0);
            ventanaControl.txtNombre.setText("");
            ventanaControl.txtApellidoP.setText("");
            ventanaControl.txtApellidoM.setText("");
            ventanaControl.txtDireccion.setText("");
            ventanaControl.txtTelefono.setText("");
            ventanaControl.txtCorreo.setText("");
        }
        else if(ae.getSource()==ventanaControl.btnRegresar){//--------Aqui escucha cuando regresa a el menu principal
            principal ventanaP=new principal();
            ventanaP.setVisible(true);
            ventanaP.setLocationRelativeTo(null);
            /*Añadir controlador*/
            empleadoDAO empDAO=new empleadoDAO();
            try {
                controlVentanaPrincipal cvp = new controlVentanaPrincipal(ventanaP, empDAO);
            } catch (SQLException ex) {
               //No se abrio :c
            }
            ventanaControl.dispose();
        }
    }
    
}//aqui termina la clase

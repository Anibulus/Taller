package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.conexion;
import modelo.empleado;
import modelo.empleadoDAO;
import vista.perfil;
import vista.principal;
import vista.ventanaEditarPerfil;

public class controlPerfil implements ActionListener{
    
    private empleadoDAO empDAO;
    private perfil p;
    private conexion c;
    
    /*Este constructor es para cuando solo se muestra el perfil*/
    public controlPerfil(perfil p, empleadoDAO empDAO){
        this.p=p;
        this.empDAO=empDAO;
        c=new conexion();
        this.p.btnRegresar.addActionListener(this);
        this.p.btnEditar.addActionListener(this);
    }
    
    @Override 
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Estoy en controlPerfil");
        if(ae.getSource()==p.btnRegresar){           
            p.dispose();
        }//Fin de volver a la pantalla de  inicio
        else if(ae.getSource()==p.btnEditar){
            ventanaEditarPerfil ventanaPerfil=new ventanaEditarPerfil();
            ventanaPerfil.setVisible(true);
            ventanaPerfil.setLocationRelativeTo(null);
            controlEditarPerfil cp=new  controlEditarPerfil(ventanaPerfil, empDAO);
    
            empleado emp=c.getEmpleadoActivo();
            try {
                emp=empDAO.consultarEmpleado(emp.getIdEmpleado(), null);
            } catch (SQLException ex) {
                Logger.getLogger(controlPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventanaPerfil.txtDireccion.setText(emp.getDireccion());
            ventanaPerfil.txtTelefono.setText(emp.getTelefono());
            ventanaPerfil.txtUsuario.setText(emp.getUsuario());
            p.dispose();
        }//Aqui se acaban los botones de la ventana de perfil 
    }//Fin de accion de escuchar 
}//Fin de la clase

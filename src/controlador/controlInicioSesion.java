package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.inicio;
import vista.principal;
import modelo.empleadoDAO;
import modelo.empleado;
import sun.awt.AWTAccessor;
import vista.ventanaEmpleado;

public class controlInicioSesion implements ActionListener{
    
    /*se implementa la interfaz de actionListener, y con ello se da una funcion obligada*/
    
    private inicio ventanaInicioS;
    private empleadoDAO empDAO;
    int intentos=0;
    
    /*
    Estas son las clases que se necesitan
    investigar para que sirve private final----------------------------------------
    */
   
    public controlInicioSesion(empleadoDAO emp, inicio ventana){
        this.ventanaInicioS=ventana;
        this.empDAO=emp;
        ventanaInicioS.btnEntrar.addActionListener(this);
    }
    /*
    Investigas acerca de actionListener
    */

    @Override
    public void actionPerformed(ActionEvent ae) {
        String usu=ventanaInicioS.txtUsuario.getText();
        String pass=String.valueOf(ventanaInicioS.txtContra.getPassword());
        intentos++;
        try {
            if(empDAO.validaInicioSesion(usu, pass)){
                principal ventanaP=new principal();
                ventanaP.setVisible(true);
                ventanaP.setLocationRelativeTo(null);
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(ventanaP, empDAO);
                ventanaInicioS.dispose();//Investigar que hace dispose     
                intentos=0;
            }
            else{
                /*
                    Esto borra la informacion en la caja de tecto de contaseña para agilizar el incio de sesion
                */
                ventanaInicioS.txtContra.setText("");
            }
        } catch (SQLException ex) {

        }//fin del catch
        
        
        /*
            Este apartado sirve para hacerque el usuario pueda recuperar su contrasena
        */
        if(intentos==2){
            JOptionPane.showMessageDialog(null, "Maximo de intentos sobre pasado");
            ventanaInicioS.dispose();
        }
    }//de termina la funcion para iniciar secion
}//Se termina la clase

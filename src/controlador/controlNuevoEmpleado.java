package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.empleado;
import modelo.empleadoDAO;
import vista.principal;
import vista.ventanaNuevoEmpleado;

public class controlNuevoEmpleado implements ActionListener {
    empleadoDAO empDAO;
    ventanaNuevoEmpleado vne;
    public controlNuevoEmpleado(empleadoDAO e, ventanaNuevoEmpleado v){
        this.empDAO=e;
        this.vne=v;
        this.vne.btnIngresar.addActionListener(this);
        this.vne.btnRegresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==vne.btnIngresar){
            String nom=vne.txtNombre.getText();
            String app=vne.txtApp.getText();
            String apm=vne.txtApm.getText();
            String direccion=vne.txtDireccion.getText();
            String telefono=vne.txtTelefono.getText();
            String curp=vne.txtCurp.getText();
            String puesto=(String) vne.listaPuesto.getSelectedItem();
            String sueldo=vne.txtSueldo.getText();
            String usu=vne.txtUsuario.getText();
            String pass=vne.txtContrasena.getText();
            empleado e=null;
            try {
                /*
                    Cambiar a qu8e consulte por CURP
                */
                e=empDAO.consultarEmpleado(0, nom);
                if(e==null){
                    try{
                        if(Double.parseDouble(sueldo)<0){
                            JOptionPane.showMessageDialog(null, "El sueldo debe tener un valor valido");
                        } 
                    }catch(Exception o){
                        JOptionPane.showMessageDialog(null, "El sueldo debe tener un valor valido");
                    }
                    if(empDAO.registrarUsuario(usu, pass)){
                        /*Necesito que me regrese el numero de usuario*/
                        System.out.println("Estoy a punto de entrar a registrar empleado");
                        if(empDAO.registrarEmpleado(usu,nom,app,apm,direccion,telefono,curp,puesto,Integer.parseInt(sueldo))){
                            JOptionPane.showMessageDialog(null, "Se ha ingresado correctamente al empleado");
                            limpiarCampos();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "El empleado ya ha sido registrado");
                    /*
                        Agregar que permita recontrtar empleado
                    */
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"No se pudo joven");
                System.out.println(ex.getMessage());
            }
                
            
        }//Aui termina la condicion si presionan ingresar
        else if(ae.getSource()==vne.btnRegresar){
            principal inicio=new principal();
            inicio.setVisible(true);
            inicio.setLocationRelativeTo(null);
            vne.dispose();
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(inicio,empDAO);
            } catch (SQLException ex) {
                //Logger.getLogger(controlEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//Aqui twermina el action listener
    
    public void limpiarCampos(){
        vne.txtNombre.setText("");
        vne.txtApp.setText("");
        vne.txtApm.setText("");  
        vne.listaPuesto.setSelectedItem("");
        vne.txtSueldo.setText("");
        vne.txtUsuario.setText("");
        vne.txtContrasena.setText("");
        vne.txtCurp.setText("");
        vne.txtDireccion.setText("");
        vne.txtTelefono.setText("");
    }
}//Aqui termina la clase

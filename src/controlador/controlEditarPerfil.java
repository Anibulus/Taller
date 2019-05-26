package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.conexion;
import modelo.empleado;
import vista.ventanaEditarPerfil;
import modelo.empleadoDAO;
import vista.perfil;

public class controlEditarPerfil implements ActionListener{
    ventanaEditarPerfil vep;
    empleadoDAO empDAO;
    conexion c;
    
    /*
        Agregamos las caracteristicas que necesita el objeto y secrea elconstructor 
        para asi poder usarlo
    */
    public controlEditarPerfil(ventanaEditarPerfil vep, empleadoDAO empDAO){
        this.vep=vep;
        this.empDAO=empDAO;
        this.vep.btnCancelar.addActionListener(this);
        this.vep.btnGuardar.addActionListener(this);
        c=new conexion();
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==vep.btnGuardar){
            String direccion=vep.txtDireccion.getText();
            String telefono=vep.txtTelefono.getText();
            String usuario=vep.txtUsuario.getText();
            String contrasenaOriginal=String.valueOf(vep.txtContrasena.getPassword());
            empleado e=c.getEmpleadoActivo();
            /*
                Contraseña original siempre se necesitara si se quieren realizar ccambios
            */
            String contrasena1=String.valueOf(vep.txtContrasenaNueva.getPassword());
            if(!contrasenaOriginal.equals("")){
                if(!contrasena1.equals("")){
                        /*
                            Aqui vemos si la contraseña no es nulo quiere decir que
                            el usuario la quiere cammbiar, por lo que se procede a 
                            hacer la funciones necesarias
                        */
                    String contrasena2=String.valueOf(vep.txtContrasenaNuevaAgain.getPassword());
                    if(contrasena1.equals(contrasena2)){
                        /*
                            Tambien se verificaque ambas contraseñas sean iguales
                        */
                        System.out.println(contrasena2);
                        try {
                            System.out.println("Entraste a modificar con contrasena");
                            if(empDAO.modificarPerfil(e.getIdEmpleado(), usuario, contrasenaOriginal, contrasena2, direccion, telefono)){
                                JOptionPane.showMessageDialog(null, "Se te ha modificado correctamente");
                            }
                        } catch (SQLException ex) {
                            System.out.println("No se pudo modificar correctamente");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Las nuevas contraseñas tienen que ser iguales");
                    }
                }//Aqui termina si quieren cambiar la contraseña
                else{
                    /*
                        Si el usuario no lleno ni el primer campo denueva contraseña
                        No la va a cambiar y solo se hace la funcion simple
                        */
                    System.out.println("Entre en el boton si no quiere cambiar contraseña");
                    try {              
                        if(empDAO.modificarPerfil(e.getIdEmpleado(), usuario, contrasenaOriginal, direccion, telefono)){
                            JOptionPane.showMessageDialog(null, "Se te ha modificado correctamente");
                        }
                    } catch (SQLException ex) {
                        System.out.println("No se te ha podido modificar");
                    }
                }
            }//Aqui termina si si lleno la contraseña original
            else{
                JOptionPane.showMessageDialog(null, "Necesitas llenar la contraseña Original");
            }//Aqui termina la validacion de la contraseña opriginal
        }//Aqui termina si presionaron el boton de editar
        else if(ae.getSource()==vep.btnCancelar){
            perfil per=new perfil();
            per.setVisible(true);
            per.setLocationRelativeTo(null);
            controlPerfil cp=new controlPerfil(per, empDAO);
            vep.dispose();
            empleado emp=c.getEmpleadoActivo();
            int id=emp.getIdEmpleado();
            System.out.println(id);
            try {
                emp = empDAO.consultarEmpleado(id, "");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Un error ocurrio al cargar datos de tu perfil");
            }
            System.out.println("saliendo de control Editar");
            
            per.letreroNombre.setText("Nombre: "+emp.getNombre());
            per.letreroApellidos.setText("Apellidos: "+emp.getApellidoP()+" "+emp.getApellidoM());
            per.letreroPuesto.setText("Puesto que desempeña: "+emp.getPuesto());
            per.letreroSueldo.setText("Salario Mensual: $"+emp.getSueldo());
            per.letreroDireccion.setText("Direccion: "+emp.getDireccion());
            per.letreroTelefono.setText("Telefono: "+emp.getTelefono());
            per.letreroCurp.setText("CURP: "+emp.getCurp());
            per.letreroUsuario.setText("Usuario: "+emp.getUsuario());                     
        }//Fin de la opcion de cancelar
    }//Fin de la accion de escuchador 
}// fin de la clase

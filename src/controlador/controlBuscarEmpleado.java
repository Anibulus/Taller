package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.empleadoDAO;
import vista.buscarEmpleado;
import vista.principal;
import controlador.controlVentanaPrincipal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class controlBuscarEmpleado implements ActionListener {
    empleadoDAO empDAO;
    buscarEmpleado ventanaBE;
    controlBuscarEmpleado(buscarEmpleado ventana,empleadoDAO emp){
        this.ventanaBE=ventana;
        this.empDAO=emp;
        this.ventanaBE.btnRegresar.addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaBE.btnRegresar){
            principal inicio=new principal();
            inicio.setLocationRelativeTo(null);
            inicio.setVisible(true);
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(inicio, empDAO);
            } catch (SQLException ex) {
                
            }
            
            /*
                Investigar por que esto funciona
            */    
            ventanaBE.dispose();
        }
        
    }
}

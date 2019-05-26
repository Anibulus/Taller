package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.empleadoDAO;
import modelo.productoDAO;
import vista.principal;
import vista.ventanaProductoBuscar;

public class controlProductoBuscar implements ActionListener {
    private ventanaProductoBuscar ventanaProductoB;
    private productoDAO pDAO;
    
    public controlProductoBuscar(ventanaProductoBuscar ventana, productoDAO pDAO){
        this.ventanaProductoB=ventana;
        this.pDAO=pDAO;
        this.ventanaProductoB.btnRegresar.addActionListener(this);
    }//Fin del constructor

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaProductoB.btnRegresar){
            principal menu=new principal();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            empleadoDAO eDAO=new empleadoDAO();
            try {
                controlVentanaPrincipal cpv=new controlVentanaPrincipal(menu, eDAO);
            } catch (SQLException ex) {
                Logger.getLogger(controlProductoBuscar.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventanaProductoB.dispose();
        }
    }//Fin del escuchador  
}//Fin de la clase controlador

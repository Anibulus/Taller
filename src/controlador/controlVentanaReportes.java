package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.empleadoDAO;
import modelo.reportes;
import net.sf.jasperreports.engine.JRException;
import vista.ventanaMenuReportes;

public class controlVentanaReportes implements ActionListener {
    private ventanaMenuReportes ventanaReportes;
    private empleadoDAO empDAO;
    private reportes r;
    public controlVentanaReportes(ventanaMenuReportes ventanaReporte, empleadoDAO empDAO){
        this.ventanaReportes=ventanaReporte;
        this.empDAO=empDAO;  
        this.r=new reportes();
        this.ventanaReportes.btnProductos.addActionListener(this);
        this.ventanaReportes.btnTodosEmpleados.addActionListener(this);
        this.ventanaReportes.btnVentaMes.addActionListener(this);
        this.ventanaReportes.btnVentas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventanaReportes.btnProductos){
            try {
                r.todosProductos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al consultar en la base de datos");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el reporte");
            }
        }
        else if(ae.getSource()==ventanaReportes.btnTodosEmpleados){
            try {
                r.todosLosEmpleados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al consultar en la base de datos");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el reporte");
            }
        }
        else if(ae.getSource()==ventanaReportes.btnVentaMes){
            try {
                r.ventasMes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al consultar en la base de datos");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el reporte");
            }
        }
        else if(ae.getSource()==ventanaReportes.btnVentas){
            try {
                r.ventasEmpleados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al consultar en la base de datos");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el reporte");
            }
        }
    }     
}//Aqui termina la clase

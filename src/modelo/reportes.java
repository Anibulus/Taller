package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class reportes {
    conexion c;
    public reportes(){
        c=new conexion();
    }

    public void todosLosEmpleados() throws SQLException, JRException {
        System.out.println("Ya me meti");
        //En compu de samu
        //String path="C:\\Users\\samu\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*En escritorio*/
        String path="C:\\Users\\CAAG-SL-LAB8\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*Desde la USB*/
        //String path = "G:\\4to Cuatrimestre\\Desarrollo de Aplicaciones II\\Proyectos\\taller\\src\\vista\\todosEmpleados.jasper";
        JasperReport jr = null;
        System.out.println("Todo bien");
        try {
            Connection con = c.getConexion();
            System.out.println("Hice conexion");
            jr = (JasperReport) JRLoader.loadObject(path);
            System.out.println("Nose que hice");
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            System.out.println("la wea fome");
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Deberia verse ahora");
            jv.setTitle(path);
            jv.setVisible(true);
            System.out.println("Cerre conexion");
            con.close();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al generar el reporte");
        }
    }//Aui termina el reporte de todos los empleados
    
    public void ventasEmpleados() throws SQLException, JRException {
        System.out.println("Ya me meti");
        //En compu de samu
        //String path="C:\\Users\\samu\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*En escritorio*/
        String path = "C:\\Users\\CAAG-SL-LAB8\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*Desde la USB*/
        //String path = "G:\\4to Cuatrimestre\\Desarrollo de Aplicaciones II\\Proyectos\\taller\\src\\vista\\todosEmpleados.jasper";
        JasperReport jr = null;
        System.out.println("Todo bien");
        try {
            Connection con = c.getConexion();
            System.out.println("Hice conexion");
            jr = (JasperReport) JRLoader.loadObject(path);
            System.out.println("Nose que hice");
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            System.out.println("la wea fome");
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Deberia verse ahora");
            jv.setTitle(path);
            jv.setVisible(true);
            System.out.println("Cerre conexion");
            con.close();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al generar el reporte");
        }
    }//Aui termina el reporte de todos los empleados

    public void ventasMes() throws SQLException, JRException {
        System.out.println("Ya me meti");
        //En compu de samu
        //String path="C:\\Users\\samu\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*En escritorio*/
        String path = "C:\\Users\\CAAG-SL-LAB8\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*Desde la USB*/
        //String path = "G:\\4to Cuatrimestre\\Desarrollo de Aplicaciones II\\Proyectos\\taller\\src\\vista\\todosEmpleados.jasper";
        JasperReport jr = null;
        System.out.println("Todo bien");
        try {
            Connection con = c.getConexion();
            System.out.println("Hice conexion");
            jr = (JasperReport) JRLoader.loadObject(path);
            System.out.println("Nose que hice");
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            System.out.println("la wea fome");
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Deberia verse ahora");
            jv.setTitle(path);
            jv.setVisible(true);
            System.out.println("Cerre conexion");
            con.close();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al generar el reporte");
        }
    }//Aui termina el reporte de todos los empleados

    public void todosProductos() throws SQLException, JRException {
        System.out.println("Ya me meti");
        //En compu de samu
        //String path="C:\\Users\\samu\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*En escritorio*/
        String path = "C:\\Users\\CAAG-SL-LAB8\\Desktop\\taller\\src\\vista\\todosEmpleados.jasper";
        /*Desde la USB*/
        //String path = "G:\\4to Cuatrimestre\\Desarrollo de Aplicaciones II\\Proyectos\\taller\\src\\vista\\todosEmpleados.jasper";
        JasperReport jr = null;
        System.out.println("Todo bien");
        try {
            Connection con = c.getConexion();
            System.out.println("Hice conexion");
            jr = (JasperReport) JRLoader.loadObject(path);
            System.out.println("Nose que hice");
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            System.out.println("la wea fome");
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Deberia verse ahora");
            jv.setTitle(path);
            jv.setVisible(true);
            System.out.println("Cerre conexion");
            con.close();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al generar el reporte");
        }
    }//Aui termina el reporte de todos los empleados

}//Aqui termina la clase

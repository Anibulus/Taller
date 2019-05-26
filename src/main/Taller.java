package main;
import vista.inicio;
import modelo.empleado;
import modelo.empleadoDAO;
import controlador.controlInicioSesion;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class Taller {
    public static void main(String[] args) {
        inicio ventana=new inicio();
        empleadoDAO e=new empleadoDAO();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        /*Se crea el objeto ventana y se establece que sea visible*/
        controlInicioSesion ce=new controlInicioSesion(e, ventana);
        
        /*PUEBAS DESDE EL MAIN*/
        Date fecha=new Date();
        System.out.println("dia "+fecha.getDay()+" date "+fecha.getDate()+" mes "+fecha.getMonth());
        System.out.println("año"+fecha.getYear());
        System.out.println(fecha);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = new GregorianCalendar();
        System.out.println(c1 +"\n"+c2);
        String    dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String annio = Integer.toString(c2.get(Calendar.YEAR));
        System.out.println("aqui");
        System.out.println(dia+mes+annio);
    }   
}

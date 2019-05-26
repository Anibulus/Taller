package modelo;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

public class controlCorreo {
    correo c=new correo();
    public boolean enviarCorreo(correo c){
        boolean enviar=false;
        try {
            Properties p=new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", c.getUssuario());
            p.setProperty("mail.smtp.auth", "true");
            System.out.println("paso 1");
            
            Session s=Session.getDefaultInstance(p, null);
            BodyPart texto=new MimeBodyPart();
            texto.setText(c.getMensaje());
            BodyPart adjunto=new  MimeBodyPart();
            System.out.println("paso 2");
            /*
            if(!c.getRutaArcivo().equals("")){
                adjunto.setDataHandler(new DataHandler(new FileDataSource(c.getRutaArcivo())));
                adjunto.setFileName(c.getNombreArchivo());
            }
            */
            MimeMultipart m=new MimeMultipart();
            m.addBodyPart(texto);
            
            System.out.println("paso 3");
            
            /*
            if(!c.getRutaArcivo().equals("")){
                m.addBodyPart(adjunto);
            }
            */
            MimeMessage mensaje=new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(c.getUssuario()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
            mensaje.setSubject(c.getAsunto());
            mensaje.setContent(m);
            System.out.println("paso 4");
            
            Transport t=s.getTransport("smtp");
            System.out.println("4.1");
            t.connect(c.getUssuario(),c.getContrasena());
            System.out.println("4.2");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            System.out.println("paso 5");
            t.close();
            enviar=true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
        }
        return enviar;
    }
}

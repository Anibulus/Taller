package modelo;
public class correo {

    /**
     * @return the ussuario
     */
    public String getUssuario() {
        return ussuario;
    }

    /**
     * @param ussuario the ussuario to set
     */
    public void setUssuario(String ussuario) {
        this.ussuario = ussuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the rutaArcivo
     */
    public String getRutaArcivo() {
        return rutaArcivo;
    }

    /**
     * @param rutaArcivo the rutaArcivo to set
     */
    public void setRutaArcivo(String rutaArcivo) {
        this.rutaArcivo = rutaArcivo;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    private String ussuario;
    private String contrasena;
    private String rutaArcivo;
    private String nombreArchivo;
    private String destino;
    private String asunto;
    private String mensaje;
    
}

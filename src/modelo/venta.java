package modelo;
public class venta {
    private int folio;
    private int idEmpleado;
    private int idCliente;
    private String fecha;
    private double total;
    private int idUsuario;
    private int idVenta;
    String[] venta=null;//Aqui ira idProduto, cantidad y total de 1 solo tipo de articulo descripcion    
    /*quitar fecha de la venta (base de datos) porque ya existe en factura*/

    public venta() {
        this.folio = 0;
        this.idEmpleado = 0;
        this.idCliente = 0;
        this.fecha = "";
        this.total = 0;
        this.idUsuario = 0;
        this.idVenta = 0;
    }

    public venta(int folio, int idEmpleado, int idCliente, String fecha, double total, int idUsuario, int idVenta) {
        this.folio = folio;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.idUsuario = idUsuario;
        this.idVenta = idVenta;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String[] getVenta() {
        return venta;
    }

    public void setVenta(String[] venta) {
        this.venta = venta;
    }
    
    
}//Fin del objeto venta

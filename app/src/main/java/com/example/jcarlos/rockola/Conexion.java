package com.example.jcarlos.rockola;

/**
 * Created by JCarlos on 05/10/2017.
 */

public class Conexion {
    private String IP;
    private int puerto;
    private String usuario;
    private Client cliente;
    public Conexion(String ip, int puerto, String usuario ) {
        this.IP = ip;
        this.puerto = puerto;
        this.usuario = usuario;
    }
    public boolean mensaje(String msg){
        cliente = new Client(this.getIP(),this.getPuerto());
        cliente.enviarMensaje(msg);
        return true;
    }
    public int getPuerto() {
        return puerto;
    }
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    public String getIP() {
        return IP;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getIPAdress(){
        cliente = new Client(this.getIP(),this.getPuerto());
        return cliente.getIpAddress();
    }
}

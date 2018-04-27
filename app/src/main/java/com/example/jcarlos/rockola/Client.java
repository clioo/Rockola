package com.example.jcarlos.rockola;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Client {
    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    Client(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }
    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip +=e.toString() + "\n";
        }
        return ip;
    }
    public void enviarMensaje(String msg){
        Mensaje mensaje = new Mensaje(msg);
        mensaje.execute();
    }
    public class Mensaje extends AsyncTask<Void,Integer, Void>{
        OutputStream salida;
        String msg;

        public Mensaje(String cadena) {
            this.msg = cadena;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Código para mandar el mensaje al socket, al mismo tiempo que se conecta
            Socket socket;
            try {
                socket = new Socket(dstAddress, 8080);
                if (socket != null) {
                    salida = socket.getOutputStream();
                    PrintStream printStream = new PrintStream(salida);
                    printStream.print(msg);
                    printStream.close();
                    if (socket != null)socket.close();
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


}

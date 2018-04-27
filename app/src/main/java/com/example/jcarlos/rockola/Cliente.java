package com.example.jcarlos.rockola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cliente extends AppCompatActivity {
    Client myClient;
    EditText txt_IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        String port=getIntent().getStringExtra("PORT");
        final EditText txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        final EditText txt_puerto = (EditText) findViewById(R.id.txt_Puerto);
        txt_IP = (EditText) findViewById(R.id.txt_IP);
        TextView lbl_nombre = (TextView)  findViewById(R.id.txtviewNombre);
        TextView lbl_ip = (TextView)  findViewById(R.id.textViewIP);
        TextView lbl_puerto = (TextView)  findViewById(R.id.textViewPuerto);

        Button cmd_conectar = (Button) findViewById(R.id.cmd_conectar);
        cmd_conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexion conexion = new Conexion(String.valueOf(txt_IP.getText()),Integer.parseInt(txt_puerto.getText().toString()), txt_nombre.getText().toString());

                Intent intent   = new Intent(v.getContext(),BusquedaCliente.class);
                intent.putExtra("IP", String.valueOf(txt_IP.getText()));
                intent.putExtra("Puerto", txt_puerto.getText().toString());
                startActivityForResult(intent,0);



            }
        });




    }
}

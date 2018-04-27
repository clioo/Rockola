package com.example.jcarlos.rockola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cmd_cliente = (Button) findViewById(R.id.cmdCliente);
        cmd_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVentana = new Intent(v.getContext(),Cliente.class);
                startActivityForResult(abrirVentana, 0);
                finish();
            }

        });
        Button cmd_servidor = (Button) findViewById(R.id.cmdServidor);
        cmd_servidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirVentana = new Intent(v.getContext(),Servidor.class);
                startActivityForResult(abrirVentana, 0);
            }

        });
    }
}

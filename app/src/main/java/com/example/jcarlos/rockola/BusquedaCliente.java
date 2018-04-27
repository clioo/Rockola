package com.example.jcarlos.rockola;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class BusquedaCliente extends AppCompatActivity {
    String ip;
    int puerto;
    public String videos[] = new String[6];
    Button cmdLink1, cmdLink2, cmdLink3, cmdBuscar;
    EditText txtBuscar;
    Conexion conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);
        ip = getIntent().getStringExtra("IP");
        puerto = Integer.parseInt(getIntent().getStringExtra("Puerto"));
        conexion = new Conexion(ip,puerto,"usuario");
        cmdLink1 = (Button) findViewById(R.id.cmdLink1);
        cmdLink2 = (Button) findViewById(R.id.cmdLink2);
        cmdLink3 = (Button) findViewById(R.id.cmdLink3);
        cmdBuscar = (Button) findViewById(R.id.cmdBuscar);
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        cmdLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion.mensaje(videos[1]);
            }

        });
        cmdLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion.mensaje((videos[1]));
            }

        });
        cmdLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion.mensaje(videos[2]);
            }

        });
        cmdBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarEnYoutube(txtBuscar.getText().toString());
            }

        });
    }
    void BuscarEnYoutube(String busqueda)
    {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + busqueda.replace(" ", "+") + "&type=video&key=AIzaSyA34P-87LY0QqyvFaSvH_FCnJZF7vmRgWw";
        Ion.with(getApplicationContext()).load(url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                //INSERTE CODIGO PARA ESCOGER EL LINK CORRECTO
                JsonParser jsonParser = new JsonParser();
                videos = new String[6];
                JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
                JsonObject pageInfo = jsonObject.getAsJsonObject("pageInfo");
                if (pageInfo.get("totalResults").toString() == "0")
                {
                    return;
                }
                JsonArray items = jsonObject.getAsJsonArray("items");
                for (int i = 0; i < items.size(); i++)
                {
                    try {
                        JsonElement jsonElementHijo = items.get(i);
                        JsonObject jsonObjectHijo = jsonElementHijo.getAsJsonObject();
                        JsonObject id = jsonObjectHijo.getAsJsonObject("id");
                      if (id.get("kind").toString().replace("\"", "").equals("youtube#video")){
                            JsonObject snippet = jsonObjectHijo.getAsJsonObject("snippet");
                        videos[i] = id.get("videoId").toString().replace("\"", "");//ID DEL VIDEO
                        if (i == 0) {
                            cmdLink1.setText(snippet.get("title").toString().replace("\"", ""));
                            cmdLink1.setVisibility(View.VISIBLE);
                        }
                        if (i == 1)
                        {
                            cmdLink2.setText(snippet.get("title").toString().replace("\"", ""));
                            cmdLink2.setVisibility(View.VISIBLE);
                        }
                        if (i == 2)
                        {
                            cmdLink3.setText(snippet.get("title").toString().replace("\"", ""));
                            cmdLink3.setVisibility(View.VISIBLE);
                        }
                    }} catch (JsonIOException Error) {
                        Log.e("Parser JSON", Error.toString());
                    }
                }
            }
        });
    }
}

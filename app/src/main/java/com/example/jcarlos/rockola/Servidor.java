package com.example.jcarlos.rockola;


import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Servidor extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String msg,ipReproduciendo;
    Queue cola = new PriorityQueue();
    Queue colaTemporal = new PriorityQueue();
    YouTubePlayer reproductor;
    TextView txt_msg,lbl_ip, lbl_puerto;
    Button cmdLink1,cmdLink2,cmdLink3,cmdBuscar;
    public JsonArray Playlist = new JsonArray();
    EditText txtBuscar;
    YouTubePlayerView vistaReproductor;
    public String videos[] = new String[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);
        txt_msg = (TextView) findViewById(R.id.msg);
        lbl_ip = (TextView) findViewById(R.id.textViewIP);
        lbl_puerto = (TextView) findViewById(R.id.textViewPuerto);
        cmdBuscar = (Button) findViewById(R.id.cmdBuscar);
        cmdLink1 = (Button) findViewById(R.id.cmdLink1);
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        cmdLink2 = (Button) findViewById(R.id.cmdLink2);
        cmdLink3 = (Button) findViewById(R.id.cmdLink3);
        cmdLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AñadirAPlaylist(videos[0]);
            }
        });
        cmdLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AñadirAPlaylist(videos[1]);
            }
        });
        cmdLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AñadirAPlaylist(videos[2]);
            }
        });
        //lbl_puerto.setText(server.getPort());
        vistaReproductor = (YouTubePlayerView) findViewById(R.id.vistaReproductor);
        vistaReproductor.initialize("AIzaSyAlwWniXcFh6hTnGoViyF_kf9ha3lxgQkQ", this);
    }
    void ocultar(){
        cmdLink1.setVisibility(View.INVISIBLE);
        cmdLink2.setVisibility(View.INVISIBLE);
        cmdLink3.setVisibility(View.INVISIBLE);
    }

    void BuscarEnYoutube(String busqueda)
    {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + busqueda.replace(" ", "+") + "&type=video&key=AIzaSyA34P-87LY0QqyvFaSvH_FCnJZF7vmRgWw";
        Ion.with(getApplicationContext()).load(url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                //INSERTE CODIGO PARA ESCOGER EL LINK CORRECTO
                JsonParser jsonParser = new JsonParser();
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
                        }}
                    } catch (JsonIOException Error) {
                        Log.e("Parser JSON", Error.toString());
                    }
                }
            }
        });
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        reproductor = youTubePlayer;
        reproductor.setShowFullscreenButton(false);
        cmdBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultar();
                BuscarEnYoutube(txtBuscar.getText().toString());
            }
        });
        Server server = new Server(this);
        lbl_ip.setText(server.getIpAddress());
        Metodos metedos = new Metodos();
        reproductor.setPlayerStateChangeListener(metedos);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(Servidor.this,youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
        ocultar();
    }
    public void AñadirAPlaylist(String idVideo)
    {

        if (reproductor.isPlaying())
        {

            cola.add(idVideo);
            Toast.makeText(this, " Se ha recibido una canción", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try{
                cola.add(idVideo);
                    reproductor.loadVideo(cola.remove().toString());


            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private class Metodos implements YouTubePlayer.PlayerStateChangeListener{
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }

        @Override
        public void onVideoEnded() {

            if (!cola.isEmpty()){
                reproductor.loadVideo(cola.remove().toString());
            }

        }
    }
}

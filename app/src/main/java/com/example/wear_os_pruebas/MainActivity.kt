package com.example.wear_os_pruebas

import android.view.View
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.wear_os_pruebas.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Response
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo



////
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService


import java.net.HttpURLConnection
import java.net.URL


class MainActivity : Activity() {

    ///esto es lo que esucha los eventos inicio
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }
    private val dataClient by lazy { Wearable.getDataClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Añade un escuchador de DataClient para recibir datos
        dataClient.addListener(object : DataClient.OnDataChangedListener {
            override fun onDataChanged(dataEvents: DataEventBuffer) {
                // Recorre todos los eventos de datos
                for (dataEvent in dataEvents) {
                    if (dataEvent.type == DataEvent.TYPE_CHANGED) {
                        val dataItem = dataEvent.dataItem
                        val dataMap = DataMapItem.fromDataItem(dataItem).dataMap

                        // Obtén el valor de una clave específica
                        val value = dataMap.getString("key")
                        Log.i("Data Layer Example", "Valor recibido: $value")
                    }
                }
            }
        })
    }


    ///esto es lo que esucha los eventos final
    var contador = 0 ;
    private lateinit var binding: ActivityMainBinding


    fun sendRequest(url: String, method: String = "GET", headers: Map<String, String>? = null, body: String? = null): Response {
        val conn = URL(url).openConnection() as HttpURLConnection

        with(conn) {
            requestMethod = method
            doOutput = body != null
            headers?.forEach(this::setRequestProperty)
        }

        if (body != null) {
            conn.outputStream.use {
                it.write(body.toByteArray())
            }
        }

        val responseBody = conn.inputStream.use { it.readBytes() }.toString(Charsets.UTF_8)

        return Response(conn.responseCode, conn.headerFields, responseBody)
    }
    data class Response(val statusCode: Int, val headers: Map<String, List<String>>? = null, val body: String? = null)

    fun onButtonClicked(view: View){
        this.setContentView(R.layout.activity_main)


        var tx1: TextView = findViewById(R.id.textView);
        tx1.setText("" + ++this.contador)

        //val valor = sendRequest("https://rickandmortyapi.com/api/character")
        //println(valor.body)


        /*val thread = Thread{

            try {
                val valor = sendRequest("https://rickandmortyapi.com/api/character")
                println(valor.body)
            } catch (e: Exception){
                Log.e("TAG", e.message!!)
            }

        }
        thread.start() */

    }

    fun onResetButton(view: View){
        this.setContentView(R.layout.activity_main)
        var tx1: TextView = findViewById(R.id.textView);
        this.contador = 0 ;
        tx1.text = "0";
    }


}






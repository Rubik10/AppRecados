package com.rubik.apprecadosp4.app.volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rubik.apprecadosp4.MainActivity;
import com.rubik.apprecadosp4.app.UtilsApp;
import com.rubik.apprecadosp4.model.Recado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rubik on 3/8/16.
 */
public class RequestVolley {

    private static final String TAG = RequestVolley.class.getSimpleName();
    private final static String URL_JSON = "http://elrecadero-ebtm.rhcloud.com/ObtenerListaRecados";
    private Context context;

    public static List<Recado> listRecados;
    private ProgressDialog dialog;


    public RequestVolley (Context context) {
        this.context = context;
    }


        //Load data by jsonArray from server
    public void JSONArrayRequest(Context context) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.show();

            // Crear nueva cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(context);

            // Nueva petición JSONObject
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                URL_JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                           Log.d(TAG, "JSON load" + response.toString());
                        listRecados = parseJsonArray(response); //Parse the JSon data in Java Object
                        UtilsApp.sortByDate(listRecados); //Order the data by date
                        for (int i = 0; i < listRecados.size(); i++) {
                            Log.d(TAG + ">>> ", String.valueOf(listRecados.get(i).getFecha_hora()));
                        }
                        MainActivity.myAdapter.notifyDataSetChanged(); // Update the recycle adapter
                        dialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                        Log.d(TAG, "Error al cargar el JSOn" + error.getMessage());
                        dialog.hide();
                    }
                }
        );
             // Añadir petición a la cola
        requestQueue.add(jsonObjectRequest);
    }


    private  List<Recado> parseJsonArray (JSONArray jsonArray) {
        List<Recado> listProducts = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Recado recado = new Recado(
                        convertStringToDate(jsonObject.getString("fecha_hora")),
                        jsonObject.getString("nombre_cliente"),
                        jsonObject.getString("telefono"),
                        jsonObject.getString("direccion_recogida"),
                        jsonObject.getString("direccion_entrega"),
                        jsonObject.getString("descripcion"),
                        convertStringToDate(jsonObject.getString("fecha_hora_maxima"))
                        );

                listProducts.add(recado);

            } catch (JSONException e) {Log.e(TAG, "Error de parsing: "+ e.getMessage());}
        }

        return listProducts;
    }


    private Date convertStringToDate(String date)  { //throws ParseException
        String[] dates = date.split(" ");
        String month = dates[0].trim();
        String day = dates[1].trim();
        String year = dates[2].trim();
        String time = dates[3].trim();

        Date convertedDate  = new Date();

        try {
                // Create a new string with concatenated data splited to format them
            String finalDate = day.replace(","," ").trim() + "/" + formatMonth(month) + "/" + year + " " + time;
                //24 h format----> KK -> 1-24 hrs // HH -> 0-23
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US); //dd-MM-yyyy HH:mm MM dd, yyyy hh:mm:ss aa
            convertedDate = dateFormat.parse(finalDate); //parse StringDate to Date with especific format.
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    /*
        M -> 9
        MM -> 09
        MMM -> Sep
        MMMM -> September

        En este caso le paso MMM (Sep) para km devuelva MM (09)
     */
    private String formatMonth(String month) throws ParseException {
        SimpleDateFormat monthParse = new SimpleDateFormat("MMM");  // Formato del mes a parsear
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MM"); // Formato del mes a visualizar despues de parsearlo

        return monthDisplay.format(monthParse.parse(month));
    }


}

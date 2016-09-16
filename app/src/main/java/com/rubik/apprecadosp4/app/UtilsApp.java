package com.rubik.apprecadosp4.app;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.rubik.apprecadosp4.model.Recado;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Rubik on 15/9/16.
 */
public class UtilsApp {

    private static final String TAG = UtilsApp.class.getSimpleName();

    public static void showToast(Context cxt, String msg) {
        Toast.makeText(cxt,msg, Toast.LENGTH_LONG).show();
    };
    public static void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    };


    public static void sortByDate (List<Recado> listToOrder) {
        Collections.sort(listToOrder, new Comparator<Recado>() {
            @Override
            public int compare(Recado date, Recado date2) {
                return date.getFecha_hora().compareTo(date2.getFecha_hora());
            }
        });
    }

    public static void sortByName (List<Recado> listToOrder) {
        Collections.sort(listToOrder, new Comparator<Recado>() {
            @Override
            public int compare(Recado name, Recado name2) {
                return name.getNombre_cliente().compareTo(name2.getNombre_cliente());
            }
        });
    }

}

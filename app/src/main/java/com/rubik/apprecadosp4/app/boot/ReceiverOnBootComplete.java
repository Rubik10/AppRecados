package com.rubik.apprecadosp4.app.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rubik.apprecadosp4.MainActivity;
import com.rubik.apprecadosp4.app.UtilsApp;

public class ReceiverOnBootComplete extends BroadcastReceiver {

    public ReceiverOnBootComplete() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            UtilsApp.showToast(context,"Boot Complete -> InitApp");

            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }

    }



}

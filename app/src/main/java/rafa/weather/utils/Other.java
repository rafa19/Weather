package rafa.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by rafiz_q on 30.05.2017.
 */

public class Other {
    public static final String API_KEY = "d31fa3a3eccb8cebb042752117ccf7f5&lang=ru";
    public static String[] CITY_IDS = new String[]{"709930", "703448", "706483", "687700"};
    public static String[] CITY_NAMES = new String[]{"Днепропетровск", "Киев", "Xарьков", "Запорожье"};

    public static boolean isOnline(Context ctx) {
        if (ctx == null) {
            return false;
        }
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void showToast(final Activity ctx, final String message) {

        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, message,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}

package toong.vn.androidboundservicefromapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PhanVanLinh on 19/10/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class BoundService extends Service {
    public static String TAG = "BoundService";
    private final IBinder binder = new LocalBinder();
    List<BoundServiceClient> mBoundServiceClients = new ArrayList<>();
    private final TimerTask t = new TimerTask() {
        int i = 0;

        @Override
        public void run() {
            Log.i(TAG, "" + i++);
            notifyClient(i);
        }
    };
    Timer timer;

    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        timer = new Timer();
        timer.scheduleAtFixedRate(t, 0, 500);
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public void addBoundServiceClient(BoundServiceClient boundServiceClient) {
        mBoundServiceClients.add(boundServiceClient);
    }

    public void removeBoundServiceClient(BoundServiceClient boundServiceClient){
        mBoundServiceClients.remove(boundServiceClient);
    }

    public void notifyClient(int value) {
        for (BoundServiceClient serviceClient : mBoundServiceClients) {
            serviceClient.doSomething(value);
        }
    }
}

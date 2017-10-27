package toong.vn.androidboundservicefromapplication;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhanVanLinh on 26/10/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class App extends Application {
    private boolean binded = false;
    private BoundService mService;
    private List<BoundServiceClient> pendingBoundServiceClients = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, BoundService.class);
        this.bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection weatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(BoundService.TAG, "onServiceConnected");
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            mService = binder.getService();
            binded = true;
            addPendingBoundService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(BoundService.TAG, "onServiceDisconnected");
            binded = false;
        }
    };

    /**
     * onServiceConnected run after {@link MainActivity#onStart()} so we need to put client
     * to pending bound service client
     */
    public synchronized void addServiceClient(BoundServiceClient client) {
        if (binded) {
            mService.addBoundServiceClient(client);
            return;
        }
        pendingBoundServiceClients.add(client);
    }

    public void removeServiceClient(BoundServiceClient client) {
        mService.removeBoundServiceClient(client);
    }

    private void addPendingBoundService() {
        for (BoundServiceClient boundService : pendingBoundServiceClients) {
            mService.addBoundServiceClient(boundService);
        }
        pendingBoundServiceClients.clear();
    }
}

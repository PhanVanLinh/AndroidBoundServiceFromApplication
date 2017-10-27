package toong.vn.androidboundservicefromapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity implements BoundServiceClient {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((App) getApplication()).addServiceClient(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((App) getApplication()).removeServiceClient(this);
    }

    @Override
    public void doSomething(int value) {
        Log.i(TAG, "value = " + value);
    }
}

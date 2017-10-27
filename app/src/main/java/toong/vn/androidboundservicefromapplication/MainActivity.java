package toong.vn.androidboundservicefromapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements BoundServiceClient {
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
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

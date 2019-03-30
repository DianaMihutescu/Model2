package ro.pub.cs.systems.eim.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button changeActivity, topRight, topLeft, center, bottomRight, bottomLeft;
    EditText textConcat;
    int totalPresses = 0;

    private int serviceStatus = STOPPED;

    private final static int MAX_NR = 5;
    private final static int STOPPED = 0;
    private final static int STARTED = 1;

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("wow", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter.addAction("actionRandom");

        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString("totalPresses") != null)
            {
                totalPresses = Integer.valueOf(savedInstanceState.getString("totalPresses"));
            }
        }

        changeActivity = findViewById(R.id.changeActivity);
        topRight = findViewById(R.id.topRight);
        topLeft = findViewById(R.id.topLeft);
        center = findViewById(R.id.center);
        bottomLeft = findViewById(R.id.bottomLeft);
        bottomRight = findViewById(R.id.bottomRight);
        textConcat = findViewById(R.id.sablon);

        textConcat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (totalPresses > MAX_NR && serviceStatus == STOPPED) {
                    Intent intentSeriviciu = new Intent(getApplicationContext(), ServicePractic.class);
                    intentSeriviciu.putExtra("val1", textConcat.getText().toString());
                    getApplicationContext().startService(intentSeriviciu);
                    Toast.makeText(MainActivity.this, "porneste serviciu", Toast.LENGTH_SHORT).show();
                    serviceStatus = STARTED;
                }
            }
        });

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sablonText = textConcat.getText().toString();
                sablonText += ", " + topRight.getText().toString();
                Log.d("wow", sablonText);
                textConcat.setText(sablonText);
                totalPresses ++;
            }
        });

        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sablonText = textConcat.getText().toString();
                sablonText += ", " + topLeft.getText().toString();
                textConcat.setText(sablonText);
                totalPresses ++;
            }
        });

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sablonText = textConcat.getText().toString();
                sablonText += ", " + center.getText().toString();
                textConcat.setText(sablonText);
                totalPresses ++;
            }
        });

        bottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sablonText = textConcat.getText().toString();
                sablonText += ", " + bottomRight.getText().toString();
                textConcat.setText(sablonText);
                totalPresses ++;
            }
        });

        bottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sablonText = textConcat.getText().toString();
                sablonText += ", " + bottomLeft.getText().toString();
                textConcat.setText(sablonText);
                totalPresses ++;
            }
        });

        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("totalPresses", String.valueOf(totalPresses)); //Optional parameters
                MainActivity.this.startActivity(intent);
            }
        });

        Intent intent = this.getIntent();
        String raspuns = intent.getStringExtra("val1");
        if (raspuns != null) {
            Toast.makeText(this, raspuns, Toast.LENGTH_SHORT).show();
            totalPresses = 0;
            textConcat.setText("");
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("totalPresses", String.valueOf(totalPresses));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString("totalPresses") != null)
            {
                totalPresses = Integer.valueOf(savedInstanceState.getString("totalPresses"));
                Toast.makeText(this, String.valueOf(totalPresses), Toast.LENGTH_SHORT).show();

            } else {
                //nimic
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, ServicePractic.class);
        stopService(intent);
        super.onDestroy();
    }


}

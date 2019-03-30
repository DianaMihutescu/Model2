package ro.pub.cs.systems.eim.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    Button verify, cancel;
    EditText totalPresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        verify = findViewById(R.id.verify);
        cancel = findViewById(R.id.cancel);
        totalPresses = findViewById(R.id.total);

        Intent intent = this.getIntent();
        String sumaFromIntent = intent.getStringExtra("totalPresses");
        totalPresses.setText(sumaFromIntent);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("val1", "Verify"); //Optional parameters
                SecondActivity.this.startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("val1", "Cancel"); //Optional parameters
                SecondActivity.this.startActivity(intent);
            }
        });

    }

}

package com.example.feelcoder.mypregnancyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PositiveResultActivity extends AppCompatActivity {
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //will hide the title not the title bar

        //code that displays the content in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_positive_result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textResult = (TextView) findViewById(R.id.textView3);
        String name = getIntent().getStringExtra("name");
        Toast.makeText(this, "Results page", Toast.LENGTH_LONG).show();
        textResult.setText(name);

    }

}

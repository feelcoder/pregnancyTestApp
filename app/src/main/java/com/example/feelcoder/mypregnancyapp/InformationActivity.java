package com.example.feelcoder.mypregnancyapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InformationActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private TextView editTextDpicker;
    private EditText numberOfDay;
    private TextView resultView;
    private Button startButton;
    private String startDate1;
    private String cycle;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    private long dateLastMenses;
    private long currentDate;
    int cycleFinal;
    private int finalResult;
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //will hide the title not the title bar

        //code that displays the content in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_information);
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_WEEK);
        showDialogOnEditTextClick();
        startButton = (Button) findViewById(R.id.button2);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            comparingDate();
                            startResultActivity(resultView.getText().toString());

                    }
                }
        );
    }

    public void showDialogOnEditTextClick(){

        editTextDpicker = (EditText) findViewById(R.id.editText2);


        editTextDpicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog( DIALOG_ID);
                    }
                }
        );

    }

    @Override
    protected Dialog onCreateDialog(int id){
            // Use the current date as the default date in the picker
            year_x = cal.get(Calendar.YEAR);
            month_x = cal.get(Calendar.MONTH);
            day_x = cal.get(Calendar.DAY_OF_MONTH);

        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x,month_x,day_x);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListener
        = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            editTextDpicker.setText(InformationActivity.this.day_x + "/" + month_x + "/" + year_x);

        }
    };


    /* Function to compare duration of cycle and difference in date*/

    public void comparingDate(){
        numberOfDay = (EditText) findViewById(R.id.editText3);
        editTextDpicker = (EditText) findViewById(R.id.editText2);
        cycle = numberOfDay.getText().toString();
        startDate1 = editTextDpicker.getText().toString();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = formatter.parse(startDate1);
            dateLastMenses = startDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cycleFinal = Integer.parseInt(cycle);


        final Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        currentDate = now.getTime();
        resultView = (TextView) findViewById(R.id.textView4);
        currentDate = currentDate - dateLastMenses;
        finalResult = (int) (currentDate/(24*60*60*1000));

        if(finalResult > cycleFinal){

            resultView.setText("Great You are " + Integer.toString(finalResult-cycleFinal)+" Days pregnant, Please visit a doctor for confirmation");

        }
        else {
            if(currentDate < 0){
                resultView.setText("This date is yet to come. Please enter a past or current date");
            }
            else {
                resultView.setText("You are not pregnant,give it a try next time");
            }
        }

    }



    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Enter your information");

        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(InformationActivity.this,"Alert",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void startResultActivity(String myString){
            Intent startIntent = new Intent(this, PositiveResultActivity.class);
            startIntent.putExtra("name", myString);
            startActivity(startIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewWelcome = (TextView) findViewById(R.id.textView2);
        String name = getIntent().getStringExtra("name");
        Toast.makeText(this, "Information page", Toast.LENGTH_LONG).show();
        textViewWelcome.setText("Hello " + name + " Please enter your information");
    }

}

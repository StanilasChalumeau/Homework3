package fr.stanislas.homework3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    Sensor mSensor;
    static public SensorManager mSensorManager;
    public String[] answers = {"It is certain.", "It is decidedly so.","Without a doubt.","Yes-definitely.","You may rely on it.",
    "As I see it, yes.","Most likely.","Outlook good.","Yes.", "Signs point to yes.", "Reply hazy, try again","Ask again later.",
    "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.", "Dont count on it.","My reply is no.",
    "My sources say no.","Outlook not so good.","Very doubtful."};
    Timestamp time;
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean startFlag;
    public TextView msgAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //we choose the default light sensor
        startFlag = false;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0]<100 && startFlag == false) {
            a = (int) event.timestamp; //time in nanoseconds at which the event <100 happened
            startFlag = true;
        }else if(startFlag &&event.values[0]> 100)  //we have start flag = true so it means we already had a value less than 100
            {
            b = (int) event.timestamp; //time in nanoseconds at which the event >100 happened
            c = (int) b-a; //time between the two events
            d = abs(c) % answers.length; //modulo the number of values in answers array
            TextView msgAnswer = (TextView) findViewById(R.id.msgAnswer); //target the textView in the layout
            msgAnswer.setText(answers[d]);  //set the text according to the value of the result of the modulo
            startFlag=false;  // we put start flag again to false to enable to do the process as many times we want

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    public void onResume(){
        // Register a listener for the sensor

        super.onResume();
        mSensorManager.registerListener(this, mSensor, 500000);

    }


    @Override
    public void onPause(){
        //Be sure to unregister the sensor when the activity pauses.

        super.onPause();
        mSensorManager.unregisterListener(this);

    }

}

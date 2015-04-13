package com.example.vigneshwar.sensorapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener{

    TextView text1,text2;
    Sensor accelerate;
    SensorManager accelerateManager;
    boolean shaked;
    public final static String EXTRA_MESSAGE = "com.example.vigneshwar.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerateManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        accelerate=accelerateManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerateManager.registerListener(this,accelerate,SensorManager.SENSOR_DELAY_NORMAL);
        text1=(TextView)findViewById(R.id.textView);
        text2=(TextView)findViewById(R.id.Shaked);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        text1.setText("X Value: "+event.values[0]+
        "\nY Value: "+event.values[1]+
        "\nZ Value: "+event.values[2]);

        float shakeValue=(float)(event.values[0]+event.values[1]+event.values[2]);
        if(shakeValue>15){
            shaked=true;
        }
        else {
            shaked = false;
        }
        if(shaked)
        {
            text2.setText("Shaked!");
            View v=(View)text2.getParent();
            v.setBackground(Drawable.createFromPath("@color/button_material_dark"));
        }
        else
        {
            text2.setText("NotShaked!");
        }
    }

    public void onPause()
    {
        super.onPause();
        accelerateManager.unregisterListener(this);
    }

    public void onResume()
    {
        super.onResume();
        accelerateManager.registerListener(this,accelerate,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void redirectActivity(View view)
    {
        Intent intent= new Intent(this,MainActivity2Activity.class);
        TextView MessageText = (TextView)findViewById(R.id.textView);
        String message=MessageText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
        MainActivity2Activity ma=new MainActivity2Activity();

    }
}

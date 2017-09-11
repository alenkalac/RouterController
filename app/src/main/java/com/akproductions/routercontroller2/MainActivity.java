package com.akproductions.routercontroller2;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    EditText startText;
    EditText endText;
    EditText activeText;
    ToggleButton button;

    private int s_h, s_m, e_h, e_m, active;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getSharedPreferences("MyData", Context.MODE_PRIVATE);

        s_h = sharedPref.getInt(getString(R.string.start_hour), 0);
        s_m = sharedPref.getInt(getString(R.string.start_min), 0);
        e_h = sharedPref.getInt(getString(R.string.end_hour), 0);
        e_m = sharedPref.getInt(getString(R.string.end_min), 0);

        int checked = sharedPref.getInt(getString(R.string.checked), 0);

        startText = (EditText)findViewById(R.id.pickStartTimeField);
        this.setTimeText(startText, s_h, s_m);
        startText.setOnClickListener(this);

        endText = (EditText)findViewById(R.id.pickEndTimeField);
        this.setTimeText(endText, e_h, e_m);
        endText.setOnClickListener(this);

        button = (ToggleButton)findViewById(R.id.toggleButton);
        button.setChecked(checked!=0);
        button.setOnClickListener(this);

        Button b = (Button) findViewById(R.id.turnOnBtn);
        b.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        TimePickerDialog tpd = new TimePickerDialog(this, this, 0,0, true);

        switch (view.getId()) {
            case R.id.pickStartTimeField :
                Log.d("Switch", "Start Field");
                activeText = this.startText;
                active = 0;
                tpd.show();
                break;
            case R.id.pickEndTimeField :
                Log.d("Switch", "End Field");
                activeText = this.endText;
                active = 1;
                tpd.show();
                break;
            case R.id.toggleButton :
                Log.d("Switch", "Button");
                this.handleToggle();
                break;
            case R.id.turnOnBtn :
                Intent i = new Intent(this, TelnetService.class);
                startService(i);
                break;
            default :
                break;
        }
    }

    private void handleToggle() {
        if(button.isChecked()){
            saveData("start", s_h, s_m, 1);
            saveData("end", e_h, e_m, 1);
            OnTimeHandler.scheduleAlarms(this);
        } else {
            markButtonChecked(0);
            OnTimeHandler.cancelAlarms(this);
        }
    }

    private void saveData(String tag, int h, int m, int checked) {
        Log.d("Button State", "State: " + checked);
        Log.d("Time", h + ":" + m);
        SharedPreferences.Editor editor = sharedPref.edit();
        String str_hr = tag.equals("start") ? getString(R.string.start_hour) : getString(R.string.end_hour);
        String str_min = tag.equals("start") ? getString(R.string.start_min) : getString(R.string.end_min);

        editor.putInt(str_hr, h);
        editor.putInt(str_min, m);
        editor.apply();

        markButtonChecked(checked);
    }

    public void markButtonChecked(int checked) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.checked), checked);
        editor.apply();
    }

    private void setTimeText(EditText field, int hours, int minutes) {
        String h = (hours < 10) ? "0"+hours : ""+hours;
        String m = (minutes < 10) ? "0"+minutes : ""+minutes;
        field.setText(h + ":" + m);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        setTimeText(activeText, hours, minutes);

        if(active == 0) {
            s_h = hours;
            s_m = minutes;
        } else if(active == 1) {
            e_h = hours;
            e_m = minutes;
        }
    }
}

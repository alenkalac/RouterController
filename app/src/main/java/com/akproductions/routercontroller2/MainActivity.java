package com.akproductions.routercontroller2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.akproductions.routercontroller2.managers.MyAlarmManager;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    EditText startText;
    EditText endText;
    EditText activeText;
    ToggleButton toggleButton;

    private int s_h, s_m, e_h, e_m, active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pref pref = new Pref(this);
        s_h = pref.getInt(getString(R.string.start_hour));
        s_m = pref.getInt(getString(R.string.start_min));
        e_h = pref.getInt(getString(R.string.end_hour));
        e_m = pref.getInt(getString(R.string.end_min));

        int checked = pref.getInt(getString(R.string.checked));

        startText = (EditText)findViewById(R.id.pickStartTimeField);
        startText.setOnClickListener(this);
        this.setTimeText(startText, s_h, s_m);

        endText = (EditText)findViewById(R.id.pickEndTimeField);
        endText.setOnClickListener(this);
        this.setTimeText(endText, e_h, e_m);

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        toggleButton.setChecked(checked!=0);
        toggleButton.setOnClickListener(this);

        Button turnOnBtn = (Button) findViewById(R.id.turnOnBtn);
        turnOnBtn.setOnClickListener(this);

        Button turnOffBtn = (Button)findViewById(R.id.turnOffBtn);
        turnOffBtn.setOnClickListener(this);

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
                Intent intent1 = new Intent(this, TelnetService.class);
                intent1.putExtra("status", "up");
                startService(intent1);
                break;
            case R.id.turnOffBtn:
                Intent intent2 = new Intent(this, TelnetService.class);
                intent2.putExtra("status", "down");
                startService(intent2);
            default :
                break;
        }
    }

    private void handleToggle() {
        if(toggleButton.isChecked())
            this.handleButtonOn();
        else
            this.handleButtonOff();
    }

    private void handleButtonOn() {
        Pref pref = new Pref(this);
        if(pref.getString("host").equals("")) {
            SettingsActivity.startActivity(this);
            toggleButton.setChecked(false);
            return;
        }
        saveData("start", s_h, s_m, 1);
        saveData("end", e_h, e_m, 1);
        MyAlarmManager.scheduleAlarms(this, MyAlarmManager.MODE.BOTH, false);
        markButtonChecked(1);
    }

    private void handleButtonOff() {
        MyAlarmManager.cancelAlarms(this);
        markButtonChecked(0);
    }

    private void saveData(String tag, int h, int m, int checked) {
        String str_hr = tag.equals("start") ? getString(R.string.start_hour) : getString(R.string.end_hour);
        String str_min = tag.equals("start") ? getString(R.string.start_min) : getString(R.string.end_min);

        Pref pref = new Pref(this);
        pref.putInt(str_hr, h);
        pref.putInt(str_min, m);
    }

    public void markButtonChecked(int checked) {
        new Pref(this).putInt(getString(R.string.checked), checked);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                SettingsActivity.startActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

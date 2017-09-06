package com.akproductions.routercontroller2;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    EditText startText;
    EditText endText;
    EditText activeText;
    ToggleButton button;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String startValue = sharedPref.getString(getString(R.string.start), "");
        String endValue = sharedPref.getString(getString(R.string.end), "");
        int checked = sharedPref.getInt(getString(R.string.checked), 0);

        startText = (EditText)findViewById(R.id.pickStartTimeField);
        startText.setText(startValue);
        startText.setOnClickListener(this);

        endText = (EditText)findViewById(R.id.pickEndTimeField);
        endText.setText(endValue);
        endText.setOnClickListener(this);

        button = (ToggleButton)findViewById(R.id.toggleButton);
        button.setChecked(checked!=0);
        button.setOnClickListener(this);
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
                tpd.show();
                break;
            case R.id.pickEndTimeField :
                Log.d("Switch", "End Field");
                activeText = this.endText;
                tpd.show();
                break;
            case R.id.toggleButton :
                Log.d("Switch", "Button");
                this.handleToggle();
                break;
            default :
                break;
        }
    }

    private void handleToggle() {
        SharedPreferences.Editor editor = sharedPref.edit();
        if(button.isChecked()) {
            Log.d("Button State", "State: Checked");
            Log.d("Start Time", this.startText.getText().toString());
            editor.putString(getString(R.string.start), this.startText.getText().toString());
            editor.putString(getString(R.string.end), this.endText.getText().toString());
            editor.putInt(getString(R.string.checked), 1);
            editor.apply();
        } else {
            Log.d("Button State", "State: Unchecked");
            editor.putInt(getString(R.string.checked), 0);
            editor.apply();
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        String h = (hours < 10) ? "0"+hours : ""+hours;
        String m = (minutes < 10) ? "0"+minutes : ""+minutes;
        activeText.setText(h + ":" + m);
    }
}

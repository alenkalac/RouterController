package com.akproductions.routercontroller2;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startText = (EditText)findViewById(R.id.pickStartTimeField);
        startText.setOnClickListener(this);

        endText = (EditText)findViewById(R.id.pickEndTimeField);
        endText.setOnClickListener(this);

        button = (ToggleButton)findViewById(R.id.toggleButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TimePickerDialog tpd = new TimePickerDialog(this, this, 0,0, true);
        tpd.show();

        switch (view.getId()) {
            case R.id.pickStartTimeField :
                Log.d("Switch", "Start Field");
                activeText = this.startText;
                break;
            case R.id.pickEndTimeField :
                Log.d("Switch", "End Field");
                activeText = this.endText;
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

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        activeText.setText(hours + ":" + minutes);
    }
}

package com.akproductions.routercontroller2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText host;
    private EditText username;
    private EditText password;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button save = (Button)findViewById(R.id.buttonSave);
        save.setOnClickListener(this);

        this.host = (EditText)findViewById(R.id.host);
        this.username = (EditText)findViewById(R.id.username_input);
        this.password = (EditText)findViewById(R.id.password_input);

        Pref pref = new Pref(this);

        this.host.setText(pref.getString("host"));
        this.username.setText(pref.getString("username"));
        this.password.setText(pref.getString("password"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getParentActivityIntent() == null)
                    onBackPressed();
                else
                    NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSettings() {
        Pref pref = new Pref(this);
        pref.putString("host", this.host.getText().toString());
        pref.putString("username", this.username.getText().toString());
        pref.putString("password", this.password.getText().toString());

        Toast.makeText(this, "Settings Saved", Toast.LENGTH_LONG).show();

        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSave:
                saveSettings();
                break;
        }
    }
}

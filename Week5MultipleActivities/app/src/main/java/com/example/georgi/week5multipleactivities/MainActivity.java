package com.example.georgi.week5multipleactivities;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void action(View view) {
        String parameter = ((TextView) findViewById(R.id.parameter_view)).getText().toString();
        Intent intent = null;
        String patternString = null;

        switch (view.getId()){
            case R.id.dial_button:{
                patternString = "[0-9\\+]{3,20}";
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + parameter));
                break;
            }
            case R.id.browse_button:{
                patternString = "[a-z]+\\.[a-z]+(\\.[a-z])+";
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://" + parameter));
                break;
            }
            case R.id.alarm_button:{
                patternString = "[0-9][0-9]:[0-9][0-9]";
                intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm");
                String[] parts = parameter.split(":");
                intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(parts[0]));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(parts[1]));
                break;
            }
        }

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(parameter);

        if (!matcher.matches()){
            Toast.makeText(this, "Please enter a valid input.", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(intent);
    }
}
package ca.yorku.eecs.mack.proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetupActivity extends Activity
{
    final static String[] TYPE_OF_CONTROL = { "Button", "Gesture" }; // NOTE: do not change strings
    final static String[] TEST_TYPE = { "Action Only", "Movement + Action" };
    final static String[] PATH_TYPE = { "Square", "Circle", "Free" };
    final static String[] PATH_WIDTH = { "Narrow", "Medium", "Wide" };

    // somewhat arbitrary mappings for gain by order of control
    final static int[] GAIN_ARG_POSITION_CONTROL = { 5, 10, 20, 40, 80 };
    final static int[] GAIN_ARG_VELOCITY_CONTROL = { 25, 50, 100, 200, 400 };

    Spinner spinTypeOfControl, spinTestType;

    // called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        spinTypeOfControl = (Spinner) findViewById(R.id.paramTypeOfControl);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, R.layout.spinnerstyle, TYPE_OF_CONTROL);
        spinTypeOfControl.setAdapter(adapter2);

        spinTestType = (Spinner) findViewById(R.id.paramTestType);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(this, R.layout.spinnerstyle, TEST_TYPE);
        spinTestType.setAdapter(adapter3);
        spinTestType.setSelection(1); // "medium" default

    }

    // called when the "OK" button is tapped
    public void clickOK(View view)
    {
        // get user's choices...
        String typeOfControl = (String) spinTypeOfControl.getSelectedItem();

        // actual testType value depends on order of control
        String testType = (String) spinTestType.getSelectedItem();
        // bundle up parameters to pass on to activity
        Bundle b = new Bundle();
        b.putString("typeOfControl", typeOfControl);
        b.putString("testType", testType);

        // start experiment activity
        Intent i;
        if(typeOfControl.equals("Button")) i = new Intent(getApplicationContext(), SecondActivity.class);
        else i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtras(b);
        startActivity(i);

        // comment out (return to setup after clicking BACK in main activity
        //finish();
    }

    /** Called when the "Exit" button is pressed. */
    public void clickExit(View view)
    {
        super.onDestroy(); // cleanup
        this.finish(); // terminate
    }
}

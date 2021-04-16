package ca.yorku.eecs.mack.proj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TestResultsActivity extends Activity {

    TextView testcountLabel, totaltimeLabel, missrateLabel;
    int testcount, totaltime;

    long testresult[];
    int missrates[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        testcountLabel = (TextView) findViewById(R.id.resulttestcount);
        totaltimeLabel = (TextView) findViewById(R.id.resulttotaltime);
        missrateLabel = (TextView) findViewById(R.id.resultmissrate);
        TableLayout table = (TableLayout) findViewById(R.id.resulttable);

        Bundle b = getIntent().getExtras();
        testcount = b.getInt("testCount");
        testresult = b.getLongArray("testResult");
        missrates = b.getIntArray("missRates");

        float avg_missrate = 0;
        for(int mr : missrates) {
            avg_missrate += (float) mr;
        }
        avg_missrate = avg_missrate / testcount;

        totaltime = 0;
        TableRow row = new TableRow(this);
        for(int i = 0 ; i < testresult.length ; i++) {
            totaltime += testresult[i];
            if(i % 2 == 0) row = new TableRow(this);
            TextView label = new TextView(this);
            TextView result = new TextView(this);
            TextView miss = new TextView(this);

            label.setText("Data #" + (i + 1) + ": ");
            result.setText(String.format("%.2f", (float) testresult[i] / 1000));
            miss.setText(String.format("%d miss ", missrates[i]));

            row.addView(label);
            row.addView(result);
            row.addView(miss);
            if(i % 2 == 0) row.addView(new View(this));
            if(i % 2 == 1) table.addView(row);
        }

        String avg_totaltime = String.format("%.2f", (float) totaltime / 1000);
        String avg_misstext = String.format("%.2f", avg_missrate);

        testcountLabel.setText("" + testcount);
        totaltimeLabel.setText("" + avg_totaltime + " s");
        missrateLabel.setText("" + avg_misstext + " (miss / count)");

    }

    

    public void setupButtonClicked(View view) {
        super.onDestroy();
        this.setResult(0);
        this.finish();
    }

    /** Called when the "Exit" button is pressed. */
    public void clickExit(View view) // copied from DemoTiltBallSetup class
    {
        super.onDestroy(); // cleanup
        this.setResult(1);
        this.finish();
    }
}

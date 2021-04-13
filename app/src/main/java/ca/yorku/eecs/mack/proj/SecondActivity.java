package ca.yorku.eecs.mack.proj;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SecondActivity extends Activity {


    private GamePanel_2 game2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
        game2 = new GamePanel_2(this);
        setContentView(game2);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}

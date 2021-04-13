package ca.yorku.eecs.mack.proj;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;




public class MainActivity extends Activity {



    private GamePanel game;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
        game = new GamePanel(this);
        setContentView(game);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
package ca.yorku.eecs.mack.proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{


    private GameThread thread;



    public EmptyGhost eg;



    private Joystick_1 js;
    private int jsid = 0;

    private Mycharacter myc;


    private String label;
    Paint labelpaint;


    // Provide three constructors to correspond to each of the three in View
    public GamePanel(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize(context);
    }

    public GamePanel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    public GamePanel(Context context)
    {
        super(context);
        initialize(context);
    }


    private void initialize(Context context)
    {
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(),this);

        js = new Joystick_1(400,700,30,100);
        myc = new Mycharacter(getContext(), js, 50,50);


        setFocusable(true);

        labelpaint = new Paint();
        labelpaint.setColor(Color.BLACK);
        labelpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        labelpaint.setTextSize(100);
        labelpaint.setAntiAlias(true);

    }




    public void update(){

        js.update();
        myc.update();

    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);


        myc.draw(canvas);
        js.draw(canvas);

        float width = this.getWidth();
        float height = this.getHeight();

        canvas.drawText(this.label, width / 2 - 50, height / 3, labelpaint);

        //dont know what is going on, cannot delete this unused class.
        eg.draw();
        //like a ghost, cannot get rid of it.

    }

    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        switch (me.getActionMasked())
        {
            // --------------------------
            case MotionEvent.ACTION_DOWN:

            // --------------------------

            case MotionEvent.ACTION_POINTER_DOWN:{
                //Joystick_1 control active
                if(js.isPressed(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setActive();
                }
                //Joystick_1 spell active
                if(js.isOnSpell(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setSpellActive();
                }

            }
            case MotionEvent.ACTION_MOVE:
            {
                if(js.isActive){
                    js.setFinalPosition(me.getX(),me.getY());
                }
                if(js.isSpellActive){
                    js.setFinalSpellPosition(me.getX(),me.getY());
                }

                return true;
            }

            // -------------------------
            case MotionEvent.ACTION_UP:{
                if(js.isSpellActive){
                    if(js.isOnSpell(me.getX(),me.getY())){
                        //Spell button=======================
                        label = "3";
                    }else if(me.getX()< js.buttonX && me.getY()< js.buttonY){
                        //Direction top-left======================
                        label = "1";
                    }else if(me.getX()> js.buttonX && me.getY()< js.buttonY){
                        //Direction top-right=====================
                        label = "2";
                    }else if(me.getX()< js.buttonX && me.getY()> js.buttonY){
                        //Direction bot-left=======================
                        label = "4";
                    }else if(me.getX() > js.buttonX && me.getY()> js.buttonY){
                        //Direction bot-right====================
                        label = "5";
                    }

                }
            }

            // ---------------------------------
            case MotionEvent.ACTION_POINTER_UP:
            {
                if(jsid == me.getPointerId(me.getActionIndex())){
                    //shutdown joystick when finished
                    js.setOff();
                    js.setOffSpell();
                }

                return true;
            }

        }
        invalidate();
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        while(true){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}

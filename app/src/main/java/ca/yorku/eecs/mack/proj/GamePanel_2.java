package ca.yorku.eecs.mack.proj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GamePanel_2 extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;

    private Joystick_2 js;
    private int jsid = 0;

    private Mycharacter myc;

    private EmptyGhost eg;

    private String label;
    Paint labelpaint;

    // Provide three constructors to correspond to each of the three in View
    public GamePanel_2(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize(context);
    }

    public GamePanel_2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    public GamePanel_2(Context context)
    {
        super(context);
        initialize(context);
    }

    public void initialize(Context c){
        getHolder().addCallback(this);


        js = new Joystick_2(400,700,30,100);
        myc = new Mycharacter(getContext(), js, 50,50);
        label = "";

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

                if(js.isOnSpell1(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setSpell1Active();
                }
                if(js.isOnSpell2(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setSpell2Active();
                }
                if(js.isOnSpell3(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setSpell3Active();
                }
                if(js.isOnSpell4(me.getX(),me.getY())){
                    jsid = me.getPointerId(me.getActionIndex());
                    js.setSpell4Active();
                }



            }
            case MotionEvent.ACTION_MOVE:
            {
                if(js.isActive){
                    js.setFinalPosition(me.getX(),me.getY());
                }

                return true;
            }

            // -------------------------
            case MotionEvent.ACTION_UP:{
                if(js.isSpellActive){
                    if(js.isOnSpell(me.getX(),me.getY())){
                        //Spell button=======================spell 3
                        this.label = "3";
                    }
                }
                else if(js.is1Active){
                    if(js.isOnSpell1(me.getX(),me.getY())){
                        //spell 1
                        this.label = "1";
                    }
                }
                else if(js.is2Active){
                    if(js.isOnSpell2(me.getX(),me.getY())){
                        //spell 2
                        this.label = "2";
                    }
                }
                else if(js.is3Active){
                    if(js.isOnSpell3(me.getX(),me.getY())){
                        //spell 4
                        this.label = "4";
                    }
                }
                else if(js.is4Active){
                    if(js.isOnSpell4(me.getX(),me.getY())){
                        //spell 5
                        this.label = "5";

                        Toast.makeText(getContext(),"spell_5",Toast.LENGTH_SHORT).show();
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

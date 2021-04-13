package ca.yorku.eecs.mack.proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick_gesture {
    public boolean isActive,isSpellActive,is1Active,is2Active,is3Active,is4Active = false;
    private int innerX,innerY,outerX,outerY,innerR,outerR;

    public final int buttonX = 1600;
    public int buttonY;
    public final int buttonR = 100;

    public int button1X;
    public int button1Y;

    public int button2X;
    public int button2Y;

    public int button3X;
    public int button3Y;

    public int button4X;
    public int button4Y;



    private Paint outerP, innerP;


    private Paint bPaint,bActPaint;


    public double innerFinalX, innerFinalY;

    public Joystick_gesture(int positionX, int positionY, int iR, int oR) {
        innerX = positionX;
        innerY = positionY;
        outerX = positionX;
        outerY = positionY;

        innerR = iR;
        outerR = oR;

        buttonY = positionY;





        init();
    }
    public void init(){
        outerP = new Paint();
        outerP.setColor(Color.BLUE);
        outerP.setStyle(Paint.Style.FILL_AND_STROKE);

        innerP = new Paint();
        innerP.setColor(Color.WHITE);
        innerP.setStyle(Paint.Style.FILL_AND_STROKE);

        bPaint = new Paint();
        bPaint.setColor(0xFF990000);
        bPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        bActPaint = new Paint();
        bActPaint.setColor(0xFF550000);
        bActPaint.setStyle(Paint.Style.FILL_AND_STROKE);



    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(outerX,outerY,outerR,outerP);
        canvas.drawCircle(innerX,innerY,innerR,innerP);


        //spell button
        if(isSpellActive){
            canvas.drawCircle(buttonX,buttonY,buttonR,bActPaint);
        }
        else
            canvas.drawCircle(buttonX,buttonY,buttonR,bPaint);



    }

    public void update() {
        updateInner();
    }

    private void updateInner() {
        innerX = (int) (outerX + innerFinalX*outerR);
        innerY = (int) (outerY + innerFinalY*outerR);
    }

    public boolean isPressed(float x, float y) {
        double xx = outerX - x;
        double yy = outerY - y;
        double distantJ = Math.sqrt(xx * xx + yy * yy);
        return distantJ < outerR;
    }

    public boolean isOnSpell(float x, float y){
        double xx = buttonX - x;
        double yy = buttonY - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setSpellActive(){ this.isSpellActive = true; }

    public void setFinalPosition(float lastTouchX, float lastTouchY) {
        double dX = lastTouchX - outerX;
        double dY = lastTouchY - outerY;
        double dDistant = Math.sqrt(dX*dX + dY*dY);
        if(dDistant < outerR){
            innerFinalX = dX / outerR;
            innerFinalY = dY / outerR;
        }else{
            innerFinalX = dX / dDistant;
            innerFinalY = dY / dDistant;
        }
        if(isSpellActive) {
            innerX = (int) lastTouchX;
            innerY = (int) lastTouchY;
            outerX = (int) lastTouchX;
            outerY = (int) lastTouchY;
        }

    }

    public void setOff() {
        this.isActive = false;
        innerFinalX = 0;
        innerFinalY = 0;
    }

    public void setOffSpell(){
        this.isSpellActive = false;
    }

}

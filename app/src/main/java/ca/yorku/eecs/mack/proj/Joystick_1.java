package ca.yorku.eecs.mack.proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick_1{

    public boolean isActive,isSpellActive = false;
    private int innerX,innerY,outerX,outerY,innerR,outerR;

    public int buttonX = 1600;
    public int buttonY;
    public final int buttonR = 100;

    private Paint outerP, innerP;
    private double distantJ,distantS;

    private Paint bPaint,bActPaint;

    private Paint labelpaint;


    public double innerFinalX, innerFinalY;
    public double buttonFinalX, buttonFinalY;

    public Joystick_1(int positionX, int positionY, int iR, int oR){
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

        labelpaint = new Paint();
        labelpaint.setColor(Color.LTGRAY);
        labelpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        labelpaint.setTextSize(100);
        labelpaint.setAntiAlias(true);

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(outerX,outerY,outerR,outerP);
        canvas.drawCircle(innerX,innerY,innerR,innerP);


        //spell button
        if(isSpellActive){
            canvas.drawCircle(buttonX + (int) buttonFinalX,buttonY + (int) buttonFinalY,buttonR,bActPaint);
        }
        else
            canvas.drawCircle(buttonX + (int) buttonFinalX,buttonY + (int) buttonFinalY,buttonR,bPaint);

        float labelX = buttonX-25;
        float labelY = buttonY+30;
        float offset = 150;
        canvas.drawText("1", labelX-offset, labelY-offset, labelpaint);
        canvas.drawText("2", labelX+offset, labelY-offset, labelpaint);
        canvas.drawText("3", labelX, labelY, labelpaint);
        canvas.drawText("4", labelX-offset,labelY+offset, labelpaint);
        canvas.drawText("5", labelX+offset,labelY+offset, labelpaint);
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
        distantJ = Math.sqrt(xx * xx + yy * yy);
        return distantJ < outerR;
    }

    public boolean isOnSpell(float x, float y){
        double xx = buttonX - x;
        double yy = buttonY - y;
        distantS = Math.sqrt(xx * xx + yy * yy);
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

    }

    public void setFinalSpellPosition(float lastTouchX, float lastTouchY) {
        double dX = lastTouchX - buttonX;
        double dY = lastTouchY - buttonY;
        double dDistant = Math.sqrt(dX*dX + dY*dY);
        if(dDistant < outerR){
            buttonFinalX = dX / outerR;
            buttonFinalY = dY / outerR;
        }else{
            buttonFinalX = dX / dDistant;
            buttonFinalY = dY / dDistant;
        }
    }

    public void setOff() {
        this.isActive = false;
        innerFinalX = 0;
        innerFinalY = 0;
        buttonFinalX = 0;
        buttonFinalY = 0;
    }

    public void setOffSpell(){
        this.isSpellActive = false;
    }
}

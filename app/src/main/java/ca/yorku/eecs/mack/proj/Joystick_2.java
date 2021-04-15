package ca.yorku.eecs.mack.proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick_2 {
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
    private Paint labelpaint;


    public double innerFinalX, innerFinalY;

    public Joystick_2(int positionX, int positionY, int iR, int oR) {
        innerX = positionX;
        innerY = positionY;
        outerX = positionX;
        outerY = positionY;

        innerR = iR;
        outerR = oR;

        buttonY = positionY;


        button1X = buttonX - 150;
        button1Y = buttonY - 150;

        button2X = buttonX + 150;
        button2Y = buttonY - 150;

        button3X = buttonX - 150;
        button3Y = buttonY + 150;

        button4X = buttonX + 150;
        button4Y = buttonY + 150;




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
            canvas.drawCircle(buttonX,buttonY,buttonR,bActPaint);
        }
        else
            canvas.drawCircle(buttonX,buttonY,buttonR,bPaint);


        if(is1Active){
            canvas.drawCircle(button1X,button1Y,buttonR,bActPaint);
        }
        else
            canvas.drawCircle(button1X,button1Y,buttonR,bPaint);

        if(is2Active){
            canvas.drawCircle(button2X,button2Y,buttonR,bActPaint);
        }else
            canvas.drawCircle(button2X,button2Y,buttonR,bPaint);

        if(is3Active){
            canvas.drawCircle(button3X,button3Y,buttonR,bActPaint);
        }else
            canvas.drawCircle(button3X,button3Y,buttonR,bPaint);

        if(is4Active){
            canvas.drawCircle(button4X,button4Y,buttonR,bActPaint);
        }else
            canvas.drawCircle(button4X,button4Y,buttonR,bPaint);


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
        double distantJ = Math.sqrt(xx * xx + yy * yy);
        return distantJ < outerR;
    }

    public boolean isOnSpell(float x, float y){
        double xx = buttonX - x;
        double yy = buttonY - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }

    public boolean isOnSpell1(float x, float y){
        double xx = button1X - x;
        double yy = button1Y - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }

    public boolean isOnSpell2(float x, float y){
        double xx = button2X - x;
        double yy = button2Y - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }
    public boolean isOnSpell3(float x, float y){
        double xx = button3X - x;
        double yy = button3Y - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }
    public boolean isOnSpell4(float x, float y){
        double xx = button4X - x;
        double yy = button4Y - y;
        double distantS = Math.sqrt(xx * xx + yy * yy);
        return distantS < buttonR;
    }



    public void setActive() {
        this.isActive = true;
    }

    public void setSpellActive(){ this.isSpellActive = true; }
    public void setSpell1Active(){ this.is1Active = true; }
    public void setSpell2Active(){ this.is2Active = true; }
    public void setSpell3Active(){ this.is3Active = true; }
    public void setSpell4Active(){ this.is4Active = true; }

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

    public void setOff() {
        this.isActive = false;
        innerFinalX = 0;
        innerFinalY = 0;
    }

    public void setOffSpell(){
        this.isSpellActive = false;
        this.is1Active = false;
        this.is2Active = false;
        this.is3Active = false;
        this.is4Active = false;
    }

}

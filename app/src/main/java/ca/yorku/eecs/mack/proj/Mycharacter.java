package ca.yorku.eecs.mack.proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Mycharacter implements GameObject{
    private Joystick_1 joystick;
    private Joystick_2 joystick2;
    private Joystick_gesture joystick_gesture;

    public int playOnJoysitck =0;

    private static final double SPEED = 350 / GameThread.GAME_UPS;

    public Context mc;
    public double positionX,positionY = 0;
    public double velocityX,velocityY = 0;
    public Paint charP;

    public Mycharacter(Context c, Joystick_1 j, double x, double y){
        mc = c;
        this.joystick = j;
        positionX = x;
        positionY = y;

        charP = new Paint();
        charP.setStyle(Paint.Style.FILL_AND_STROKE);
        charP.setColor(Color.RED);
        charP.setAntiAlias(true);
        playOnJoysitck = 1;
    }
    public Mycharacter(Context c, Joystick_2 j, double x, double y){
        mc = c;
        this.joystick2 = j;
        positionX = x;
        positionY = y;

        charP = new Paint();
        charP.setStyle(Paint.Style.FILL_AND_STROKE);
        charP.setColor(Color.RED);
        charP.setAntiAlias(true);

        playOnJoysitck =2;
    }
    public Mycharacter(Context c, Joystick_gesture j, double x, double y){
        mc = c;
        this.joystick_gesture = j;
        positionX = x;
        positionY = y;

        charP = new Paint();
        charP.setStyle(Paint.Style.FILL_AND_STROKE);
        charP.setColor(Color.RED);
        charP.setAntiAlias(true);

        playOnJoysitck =3;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle((float) this.positionX,(float) this.positionY,40, charP);
    }

    @Override
    public void update() {
        if(playOnJoysitck == 1){
            velocityX = joystick.innerFinalX * SPEED;
            velocityY = joystick.innerFinalY * SPEED;

            positionX += velocityX;
            positionY += velocityY;
        }
        else if(playOnJoysitck == 2){
            velocityX = joystick2.innerFinalX * SPEED;
            velocityY = joystick2.innerFinalY * SPEED;

            positionX += velocityX;
            positionY += velocityY;
        } else if(playOnJoysitck == 3){
            velocityX = joystick_gesture.innerFinalX * SPEED;
            velocityY = joystick_gesture.innerFinalY * SPEED;

            positionX += velocityX;
            positionY += velocityY;
        }

    }

    public void update(double xPosition, double yPosition){
        this.positionX = xPosition;
        this.positionY = yPosition;

    }

}

package ca.yorku.eecs.mack.proj;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    public static final double GAME_FPS = 30.0;
    public static final double GAME_UPS = 1E+3/GAME_FPS;
    private double averageFPS;
    private double averageUPS;

    public static Canvas canvas;

    private SurfaceHolder surfH;
    private GamePanel gp;
    private GamePanel_2 gp2;
    private boolean isRunning = false;

    private int gpmode = 0;


    public GameThread(SurfaceHolder sh, GamePanel gamepanel){
        super();
        this.surfH = sh;
        this.gp = gamepanel;
        gpmode = 1;
    }
    public GameThread(SurfaceHolder sh, GamePanel_2 gamepanel){
        super();
        this.surfH = sh;
        this.gp2 = gamepanel;
        gpmode = 2;
    }

    @Override
    public void run(){
        super.run();
        long startT;
        long waitT;
        long runT;



        int frameCount = 0;
        int updataCount=0;

        startT = System.currentTimeMillis();
        canvas = null;

        while(isRunning){

            try {
                canvas = this.surfH.lockCanvas();
                synchronized (surfH){
                    if(gpmode == 1){
                        this.gp.update();
                        updataCount++;
                        this.gp.draw(canvas);
                    }
                    else if(gpmode == 2){
                        this.gp2.update();
                        updataCount++;
                        this.gp2.draw(canvas);
                    }

                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    try{
                        surfH.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


            //slowdown
            runT = System.currentTimeMillis() - startT;
            waitT = (long) (updataCount*GAME_UPS - runT);
            if(waitT > 0){
                try {
                    sleep(waitT);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }


            //speedup
            while(waitT < 0 && updataCount < GAME_UPS - 1){
                if(gpmode == 1){
                    this.gp.update();
                    updataCount++;
                    runT = System.currentTimeMillis() - startT;
                    waitT = (long) (updataCount*GAME_UPS - runT);
                }
                else if(gpmode == 2){
                    this.gp2.update();
                    updataCount++;
                    runT = System.currentTimeMillis() - startT;
                    waitT = (long) (updataCount*GAME_UPS - runT);
                }

            }

            //averageUPS and FPS
            runT = System.currentTimeMillis() - startT;
            if(runT > 1000){
                averageUPS = updataCount / (runT * 1E-3);
                averageFPS = frameCount / (runT * 1E-3);
                updataCount = 0;
                frameCount = 0;
                startT = System.currentTimeMillis();
            }



        }//end while


    }//end run()

    public void setRunning(Boolean b){
        this.isRunning = b;
    }





}//end class

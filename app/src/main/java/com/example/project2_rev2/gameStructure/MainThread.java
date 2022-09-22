package com.example.project2_rev2.gameStructure;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.project2_rev2.utils.Display;

public class MainThread extends Thread {

    public static final int MAX_FPS = 30;
    private double AVG_FPS;
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean isRunning;
    private Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void startThread() {
        isRunning = true;
        start();
    }

    public void stopThread() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        long frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (isRunning) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS) {
                AVG_FPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("FPS: " + AVG_FPS);
            }
        }
    }
}

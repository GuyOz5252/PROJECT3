package com.android.project3.gameStructure;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.android.project3.data.Constants;
import com.android.project3.data.GameValues;

public class MainThread extends Thread {

    private double avgFPS;
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean isRunning;
    private Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public double getFPS() {
        return avgFPS;
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
        long timeMillis = 1000 / Constants.MAX_FPS;
        long waitTime;
        long frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / Constants.MAX_FPS;

        while (isRunning) {
            if (!GameValues.isPaused) {
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
                if (frameCount == Constants.MAX_FPS) {
                    avgFPS = 1000 / ((totalTime / frameCount) / 1000000);
                    frameCount = 0;
                    totalTime = 0;
                    System.out.println("FPS: " + avgFPS);
                }
            }
        }
    }
}

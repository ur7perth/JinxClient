package com.abo9kr.jinxclient.util;

public class Draggable {
    private double xFraction;
    private double yFraction;

    public Draggable(double xFraction, double yFraction) {
        this.xFraction = xFraction;
        this.yFraction = yFraction;
    }

    public int getX(int screenWidth) {
        return (int) (xFraction * screenWidth);
    }

    public int getY(int screenHeight) {
        return (int) (yFraction * screenHeight);
    }

    public void setFromPixels(int x, int y, int screenWidth, int screenHeight) {
        this.xFraction = clamp01((double) x / screenWidth);
        this.yFraction = clamp01((double) y / screenHeight);
    }

    public double getXFraction() {
        return xFraction;
    }

    public double getYFraction() {
        return yFraction;
    }

    public void setFractions(double x, double y) {
        this.xFraction = clamp01(x);
        this.yFraction = clamp01(y);
    }

    private static double clamp01(double v) {
        return Math.max(0.0, Math.min(1.0, v));
    }
}

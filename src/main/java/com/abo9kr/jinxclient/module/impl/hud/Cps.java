package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.util.Draggable;

import java.util.ArrayDeque;
import java.util.Deque;

public class Cps extends Module {

    public final Draggable position = new Draggable(0.02, 0.06);

    private final Deque<Long> leftClicks = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();

    public Cps() {
        super("Cps", Category.HUD, "Displays left-click and right-click clicks-per-second.");
    }

    public void onLeftClick() {
        leftClicks.addLast(System.currentTimeMillis());
    }

    public void onRightClick() {
        rightClicks.addLast(System.currentTimeMillis());
    }

    public int getLeftCps() {
        return countRecent(leftClicks);
    }

    public int getRightCps() {
        return countRecent(rightClicks);
    }

    private int countRecent(Deque<Long> deque) {
        long now = System.currentTimeMillis();
        while (!deque.isEmpty() && now - deque.peekFirst() > 1000L) {
            deque.pollFirst();
        }
        return deque.size();
    }
}

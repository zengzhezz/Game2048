package com.example.zz.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2015/12/3.
 */
public class GameView extends GridLayout {

    private Card[][] cardMap = new Card[4][4];

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        //指明GridLayout为4列
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void swipeLeft() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardMap[x1][y].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x1][y],
                                    cardMap[x][y], x1, x, y, y);
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x1][y],
                                    cardMap[x][y], x1, x, y, y);
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandowNum();
            checkComplete();
        }
    }

    private void swipeRight() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x1][y],
                                    cardMap[x][y], x1, x, y, y);
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x1][y],
                                    cardMap[x][y], x1, x, y, y);
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandowNum();
            checkComplete();
        }
    }

    private void swipeUp() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x][y1],
                                    cardMap[x][y], x, x, y1, y);
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x][y1],
                                    cardMap[x][y], x, x, y1, y);
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandowNum();
            checkComplete();
        }
    }

    private void swipeDown() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x][y1],
                                    cardMap[x][y], x, x, y1, y);
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardMap[x][y1],
                                    cardMap[x][y], x, x, y1, y);
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandowNum();
            checkComplete();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Config.CARD_WIDTH = (Math.min(w, h) - 10) / Config.LINES;
        addCards(Config.CARD_WIDTH, Config.CARD_WIDTH);
        startGame();
    }

    private void addCards(int cardWidth, int cardWidth1) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardWidth);
                cardMap[x][y] = c;
            }
        }
    }

    public void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardMap[x][y].setNum(0);
            }
        }
        addRandowNum();
        addRandowNum();
        MainActivity.getMainActivity().clearScore();
    }

    private List<Point> emptyPoints = new ArrayList<Point>();
    private void addRandowNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void checkComplete() {
        boolean complete = true;
        All:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum() == 0 || (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y]))
                        || (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y]))
                        || (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1]))
                        || (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))) {
                    complete = false;
                    break All;
                }
            }
        }
        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

}

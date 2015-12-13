package com.example.zz.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2015/12/3.
 */
public class GameView extends GridLayout {

    public static Card[][] cardMap = new Card[4][4];
    public static Card[][] tempCardMap = new Card[4][4];
    public static GameView gameView = null;
    public Context context;

    public static GameView getGameView() {
        return gameView;
    }

    public GameView(Context context) {
        super(context);
        initGameView();
        this.context = context;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
        this.context = context;
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
        this.context = context;
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

    MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.hehe);
    private void swipeLeft() {
        boolean merge = false;
        int saveNum = 0;
        asyNum();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (tempCardMap[x1][y].getNum() > 0) {
                        if (tempCardMap[x][y].getNum() <= 0) {
                            saveNum = tempCardMap[x1][y].getNum();
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x1][y],
                                    tempCardMap[x][y], x1, x, y, y, saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x1][y].getNum());
                            tempCardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        } else if (tempCardMap[x][y].equals(tempCardMap[x1][y])) {
                            saveNum = tempCardMap[x][y].getNum() *2;
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x1][y],
                                    tempCardMap[x][y], x1, x, y, y,saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y].getNum() * 2);
                            tempCardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(tempCardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            mediaPlayer.start();
            addRandowNum();
            checkComplete();
        }
    }

    public static Handler getThisHandler() {
        return handler;
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Config.FRESH_CARDS:
                    asyNum2();
                    break;
                default:
                    break;
            }
        }
    };

    private void swipeRight() {
        boolean merge = false;
        int saveNum = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (tempCardMap[x1][y].getNum() > 0) {
                        if (tempCardMap[x][y].getNum() <= 0) {
                            saveNum = tempCardMap[x1][y].getNum();
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x1][y],
                                    tempCardMap[x][y], x1, x, y, y,saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x1][y].getNum());
                            tempCardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        } else if (tempCardMap[x][y].equals(tempCardMap[x1][y])) {
                            saveNum = tempCardMap[x][y].getNum() * 2;
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x1][y],
                                    tempCardMap[x][y], x1, x, y, y,saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y].getNum() * 2);
                            tempCardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(tempCardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            mediaPlayer.start();
            addRandowNum();
            checkComplete();
        }
    }

    private void swipeUp() {
        boolean merge = false;
        int saveNum = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (tempCardMap[x][y1].getNum() > 0) {
                        if (tempCardMap[x][y].getNum() <= 0) {
                            saveNum = tempCardMap[x][y1].getNum();
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x][y1],
                                    tempCardMap[x][y], x, x, y1, y, saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y1].getNum());
                            tempCardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if (tempCardMap[x][y].equals(tempCardMap[x][y1])) {
                            saveNum = tempCardMap[x][y1].getNum() * 2;
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x][y1],
                                    tempCardMap[x][y], x, x, y1, y, saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y].getNum() * 2);
                            tempCardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(tempCardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            mediaPlayer.start();
            addRandowNum();
            checkComplete();
        }
    }

    private void swipeDown() {
        boolean merge = false;
        int saveNum = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (tempCardMap[x][y1].getNum() > 0) {
                        if (tempCardMap[x][y].getNum() <= 0) {
                            saveNum = tempCardMap[x][y1].getNum();
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x][y1],
                                    tempCardMap[x][y], x, x, y1, y, saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y1].getNum());
                            tempCardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (tempCardMap[x][y].equals(tempCardMap[x][y1])) {
                            saveNum = tempCardMap[x][y1].getNum() * 2;
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(tempCardMap[x][y1],
                                    tempCardMap[x][y], x, x, y1, y, saveNum);
                            tempCardMap[x][y].setNum(tempCardMap[x][y].getNum() * 2);
                            tempCardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(tempCardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            mediaPlayer.start();
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

    private void addCards(int cardWidth, int cardHeight) {
        Card c;
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                c = new Card(getContext());
                addView(c, cardWidth, cardHeight);
                cardMap[x][y] = c;
            }
        }

        Card c2;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c2 = new Card(getContext());
                tempCardMap[x][y] = c2;
            }
        }
    }

    public void startGame() {
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                cardMap[x][y].setNum(0);
                tempCardMap[x][y].setNum(0);
            }
        }
        addRandowNum();
        addRandowNum();
        MainActivity.getMainActivity().clearScore();
        MainActivity.getMainActivity().showBestScore();
        asyNum2();
    }

    public void asyNum() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                tempCardMap[x][y].setNum(cardMap[x][y].getNum());
            }
        }
    }
    public static void asyNum2() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardMap[x][y].setNum(tempCardMap[x][y].getNum());
            }
        }
    }

    private List<Point> emptyPoints = new ArrayList<Point>();
    private void addRandowNum() {
        emptyPoints.clear();
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                if (tempCardMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        if (emptyPoints.size() > 0) {
            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
            tempCardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        }
    }

    private void checkComplete() {
        boolean complete = true;
        All:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tempCardMap[x][y].getNum() == 0 || (x > 0 && tempCardMap[x][y].equals(tempCardMap[x - 1][y]))
                        || (x < 3 && tempCardMap[x][y].equals(tempCardMap[x + 1][y]))
                        || (y > 0 && tempCardMap[x][y].equals(tempCardMap[x][y - 1]))
                        || (y < 3 && tempCardMap[x][y].equals(tempCardMap[x][y + 1]))) {
                    complete = false;
                    break All;
                }
            }
        }
        if (complete) {
            if (MainActivity.getMainActivity().getScore() > MainActivity.getMainActivity().getBestScore()) {
                MainActivity.getMainActivity().saveBestScore(MainActivity.getMainActivity().getScore());
            }
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

}

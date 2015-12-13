package com.example.zz.game2048;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore, tvBestScore;
    private Button btnNewGame;
    private AnimLayer animLayer = null;
    private static final String SP_KEY_BEST_SCORE = "bestScore";
    private LinearLayout root = null;
    private GameView gameView = null;
//    //TODO
//    private int[] res = {R.id.imageView_a, R.id.imageView_b, R.id.imageView_c, R.id.imageView_d,
//            R.id.imageView_e, R.id.imageView_f, R.id.imageView_g, R.id.imageView_h};
//    private List<ImageView> imageViewList = new ArrayList<ImageView>();
//    private boolean flag = true;
//    //END_TODO

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
        root = (LinearLayout) findViewById(R.id.container);
        root.setBackgroundColor(0xfffaf8ef);
        tvBestScore = (TextView) findViewById(R.id.tvBestScore);
        gameView = (GameView) findViewById(R.id.gameView);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        animLayer = (AnimLayer) findViewById(R.id.animLayer);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
            }
        });
//        //TODO
//        for (int i = 0; i < res.length; i++) {
//            ImageView imageView = (ImageView) findViewById(res[i]);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (v.getId()) {
//                        case R.id.imageView_a:
//                            if (flag == true) {
//                                startAnim();
//                                flag = false;
//                            } else {
//                                closeAnim();
//                                flag = true;
//                            }
//                            break;
//                        default:
//                            Toast.makeText(MainActivity.this,"click" + v.getId(), Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                }
//            });
//            imageViewList.add(imageView);
//        }
//        //END_TODO
    }

//    //TODO
//    private void startAnim() {
//        for (int i = 1; i < res.length; i++) {
//            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, i * 130);
//            animator.setDuration(300);
//            animator.start();
//        }
//    }
//
//    private void closeAnim() {
//        for (int i = 1; i < res.length; i++) {
//            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", i * 130, 0F);
//            animator.setDuration(300);
//            animator.start();
//        }
//    }
//    //END_TODO

    public AnimLayer getAnimLayer() {
        return animLayer;
    }

    private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    private int score = 0;
    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }

    public int getScore() {
        return score;
    }

    public void saveBestScore(int score) {
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putInt(SP_KEY_BEST_SCORE, score);
        e.commit();
    }

    public int getBestScore() {
        return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }

    public void showBestScore() {
        int score = getBestScore();
        tvBestScore.setText(score + "");
    }
}

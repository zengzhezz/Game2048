package com.example.zz.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2015/12/4.
 */
public class AnimLayer extends FrameLayout {

    private List<Card> cards = new ArrayList<Card>();

    public AnimLayer(Context context) {
        super(context);
    }

    public AnimLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void createMoveAnim(final Card from, final Card to, final int fromX, int toX, int fromY, int toY) {
        final Card c = getCard(from.getNum());
        LayoutParams lp = new LayoutParams(Config.CARD_WIDTH, Config.CARD_WIDTH);
        lp.leftMargin = fromX * Config.CARD_WIDTH;
        lp.topMargin = fromY * Config.CARD_WIDTH;
        c.setLayoutParams(lp);

//        if (to.getNum() <= 0) {
//            to.getLabel().setVisibility(View.INVISIBLE);
//        }
        TranslateAnimation ta = new TranslateAnimation(0, Config.CARD_WIDTH * (toX - fromX),
                0, Config.CARD_WIDTH * (toY - fromY));
        ta.setDuration(1000);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                to.getLabel().setVisibility(View.VISIBLE);
                recycleCard(c);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        c.startAnimation(ta);
    }

    private Card getCard(int num) {
        Card c;
        if (cards.size() > 0) {
            c = cards.remove(0);
        } else {
            c = new Card(getContext());
            addView(c);
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        return c;
    }

    private void recycleCard(Card card) {
        card.setVisibility(View.INVISIBLE);
        card.setAnimation(null);
        cards.add(card);
    }

    public void CreateScaleTo1(Card target) {
        ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(100);
        target.setAnimation(null);
        target.getLabel().startAnimation(sa);
    }
}

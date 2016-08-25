package co.zero.android.armyofones.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import co.zero.android.armyofones.R;
import co.zero.android.armyofones.util.Constants;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by htenjo on 8/23/16.
 */
public class SplashActivity extends BaseActivity{
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000;
    private ExplosionField explosionField;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        explosionField = ExplosionField.attach2Window(this);
        buildAnimators();
        buildIntentHandler();
    }

    /**
     *
     */
    private void buildAnimators(){
        Animation backgroundAnimator = AnimationUtils.loadAnimation(this, R.anim.background_animator);
        Animation logoAnimator = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.logo_animator);
        TextView brand = setCustomFontToLogo();

        backgroundAnimator.setFillEnabled(true);
        backgroundAnimator.setFillBefore(false);
        backgroundAnimator.setFillAfter(true);
        logoAnimator.setFillEnabled(true);
        logoAnimator.setFillBefore(false);
        logoAnimator.setFillAfter(true);

        View view = findViewById(R.id.splash_background);
        logoAnimator.setAnimationListener(buildAnimationListener(brand));
        view.startAnimation(backgroundAnimator);
        brand.startAnimation(logoAnimator);
    }

    /**
     *
     * @return
     */
    private Animation.AnimationListener buildAnimationListener(final TextView view){
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                explosionField.explode(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    /**
     *
     */
    private void buildIntentHandler(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     *
     * @return
     */
    private TextView setCustomFontToLogo(){
        TextView brand = (TextView)findViewById(R.id.text_brand);
        Typeface customFont = Typeface.createFromAsset(getAssets(), Constants.FONT_HUGE_BOLD_PATH);
        brand.setTypeface(customFont);
        return brand;
    }
}

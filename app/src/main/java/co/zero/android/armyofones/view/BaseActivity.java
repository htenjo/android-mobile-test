package co.zero.android.armyofones.view;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by htenjo on 8/19/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Helper method to get the TAG used in the logs
     * @return
     */
    protected String getLogTag(){
        return this.getClass().getSimpleName();
    }
}
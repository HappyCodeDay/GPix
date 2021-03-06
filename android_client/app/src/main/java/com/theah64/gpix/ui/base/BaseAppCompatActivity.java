package com.theah64.gpix.ui.base;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.theah64.gpix.callback.ToastCallback;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by shifar on 23/7/16.
 */
public class BaseAppCompatActivity extends AppCompatActivity implements ToastCallback {

    public static final String FATAL_ERROR_NO_SERIALIZABLE_FOUND_WITH_KEY_S = "No serializable found with key %s";
    protected static final String FATAL_ERROR_FORGOT_TO_HANDLE = "Forgot to handle";
    private static final String FATAL_ERROR_S_IS_MISSING = "%s is missing";
    private static final String X = BaseAppCompatActivity.class.getSimpleName();


    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        assert toolbar != null;
    }

    @NotNull
    protected final String getStringOrThrow(final String key) {
        final String value = getIntent().getStringExtra(key);
        if (value == null) {
            throw new IllegalArgumentException(String.format(FATAL_ERROR_S_IS_MISSING, key));
        }
        return value;
    }

    @NotNull
    protected int getIntOrThrow(String key) {
        final int value = getIntent().getIntExtra(key, -1);
        if (value == -1) {
            throw new IllegalArgumentException(String.format(FATAL_ERROR_NO_SERIALIZABLE_FOUND_WITH_KEY_S, key));
        }
        return value;
    }

    @NotNull
    protected Serializable getSerializableOrThrow(String key) {
        final Serializable ob = getIntent().getSerializableExtra(key);
        if (ob == null) {
            throw new IllegalArgumentException(String.format(FATAL_ERROR_NO_SERIALIZABLE_FOUND_WITH_KEY_S, key));
        }
        return ob;
    }

    @NotNull
    protected Parcelable getParcelableOrThrow(String key) {
        final Parcelable ob = getIntent().getParcelableExtra(key);
        if (ob == null) {
            throw new IllegalArgumentException(String.format("No parcelable found with the key %s", key));
        }
        return ob;
    }

    @Override
    public void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void enableBackNavigation() {
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void enableBackNavigation(final String title) {
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    //Enabling back navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Close this activity
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

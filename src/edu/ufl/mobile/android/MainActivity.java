package edu.ufl.mobile.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Load the UF Mobile website in a WebView.
 * 
 * The splash screen code is based on:
 * 
 * http://blog.iangclifton.com/2011/01/01/android-splash-screens-done-right/
 * 
 * @author dwc
 */
public class MainActivity extends Activity {
    /**
     *  Simple Dialog used to show the splash screen.
     */
    protected Dialog mSplashDialog;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyStateSaver data = (MyStateSaver) getLastNonConfigurationInstance();
        if (data != null) {
            // Show splash screen if still loading
            if (data.showSplashScreen) {
                showSplashScreen();
            }

            setContentView(R.layout.main);
        }
        else {
            showSplashScreen();
            setContentView(R.layout.main);

            WebView view = (WebView) findViewById(R.id.webview);
            view.getSettings().setJavaScriptEnabled(true);
            view.loadUrl("http://m.ufl.edu/");
            
            removeSplashScreen();
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        MyStateSaver data = new MyStateSaver();
        
        if (mSplashDialog != null) {
            data.showSplashScreen = true;
            removeSplashScreen();
        }
        
        return data;
    }

    /**
     * Show the splash screen over the full Activity.
     */
    protected void showSplashScreen() {
        mSplashDialog = new Dialog(this, R.style.splash);
        mSplashDialog.setContentView(R.layout.splash);
        mSplashDialog.setCancelable(false);
        mSplashDialog.show();
    }
    
    /**
     * Remove the Dialog that displays the splash screen.
     */
    protected void removeSplashScreen() {
        if (mSplashDialog != null) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
        }
    }

    /**
     * Simple class for storing data across configuration changes.
     */
    private class MyStateSaver {
        public boolean showSplashScreen = false;
    }
}

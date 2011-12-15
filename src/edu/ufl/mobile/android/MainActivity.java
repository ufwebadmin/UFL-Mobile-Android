package edu.ufl.mobile.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
     * The tag used in log entries for this Activity.
     */
    private static final String TAG = "UFMobile";

    /**
     *  Simple Dialog used to show the splash screen.
     */
    protected Dialog mSplashDialog;
    
    /**
     * WebView used to display the UF Mobile website.
     */
    protected WebView mWebView;

    /**
     * When the Activity is first created, show the splash screen and begin
     * loading UF Mobile.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSplashScreen();
        setContentView(R.layout.main);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl("http://m.ufl.edu/?utm_campaign=Mobile+Apps&utm_source=Android&utm_medium=Startup");
    }

    /**
     * Called when a button is pressed.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the back button was tapped and that there is history
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
     * Hide the splash screen when UF Mobile first finishes loading. Also,
     * handle any errors loading URLs by showing a toast notification.
     *
     * @author dwc
     */
    private class MyWebViewClient extends WebViewClient {
        /**
         * Remove the splash screen when the page is finished loading.
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            removeSplashScreen();
        }

        /**
         * Show a friendly error message when there is a problem loading a URL.
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {
            Log.i(TAG, "Error loading " + url + ": " + description + " (" + errorCode + ")");

            if (errorCode == ERROR_HOST_LOOKUP) {
                view.loadUrl("file:///android_asset/error.html");
            }
            else {
                Toast.makeText(getApplicationContext(), "Error loading UF Mobile: " + description, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

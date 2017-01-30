package its.arun.miniums;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends ActionBarActivity {
    private WebView myWebView;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            myWebView = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            if (AppStatus.getInstance(this).isOnline(this)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    webSettings.setAllowFileAccessFromFileURLs(true);
                    webSettings.setAllowUniversalAccessFromFileURLs(true);
                }
                // myWebView.loadUrl("https://its-arun.github.io/Mini-UMS/");
                myWebView.loadUrl("file:///android_asset/index.html");
                myWebView.setWebViewClient(new WebViewClient());
            }
            else
            {
                Toast.makeText(getBaseContext(),
                        "No Internet connection available", 4000).show();
            }
            //Analytics Integration
            // Obtain the shared Tracker instance.
            GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
            mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Main Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    }

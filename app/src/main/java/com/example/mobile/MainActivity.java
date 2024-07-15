package com.example.mobile;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String myWebSite = "soigne-moi-web-c4a4cea6515a.herokuapp.com";

    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Chargement...");
        progressDialog.show();

        //webView.setWebChromeClient(new WebChromeClient());
        webView = findViewById(R.id.webView);
        webView.canGoBackOrForward(99);
        webView.setWebViewClient(new myWebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        //settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl("https://" + myWebSite);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebViewClient extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //showing the progress bar once the page has started loading
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // hide the progress bar once the page has loaded
            progressDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            // show the error message = no internet access
            webView.loadUrl("file:///android_asset/no_internet.html");
            // hide the progress bar on error in loading
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Probleme de connexion", Toast.LENGTH_SHORT).show();
        }
    }
}


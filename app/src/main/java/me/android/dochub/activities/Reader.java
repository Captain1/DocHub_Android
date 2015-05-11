package me.android.dochub.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import me.android.dochub.R;

public class Reader extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
        setContentView(webview);
    }


    public void back(MenuItem item)
    {
        finish();
    }

}
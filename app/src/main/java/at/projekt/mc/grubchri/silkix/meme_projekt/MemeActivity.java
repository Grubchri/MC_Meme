package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);
        Meme meme = (Meme)getIntent().getExtras().get("meme");
        WebView view= findViewById(R.id.WebView);
        view.loadUrl(meme.getUrl());
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);

    }
}

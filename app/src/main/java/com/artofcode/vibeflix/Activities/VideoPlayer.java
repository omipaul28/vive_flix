package com.artofcode.vibeflix.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

import com.artofcode.vibeflix.Domains.Film;
import com.artofcode.vibeflix.R;
import com.artofcode.vibeflix.databinding.ActivityDetailBinding;
import com.artofcode.vibeflix.databinding.ActivityVideoPlayerBinding;

public class VideoPlayer extends AppCompatActivity {

    private ActivityVideoPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Force landscape mode
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Enable full-screen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );



        // Enable JavaScript and other WebView settings
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView,true);

        binding.webView.setWebChromeClient(new WebChromeClient());

        // Load the video URL
        String videoUrl =getIntent().getStringExtra("link");
        binding.webView.loadUrl(videoUrl);

        // Handle back button click
        binding.backBtn.setOnClickListener(view -> {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
            } else {
                finish(); // Exit the activity if there's no back history
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            super.onBackPressed();
 }
}
}
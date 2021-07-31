package com.swapnilshukla.quicure;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splashscreen extends Activity {
    private SharedPreferences sharedPreferences;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    sharedPreferences=getApplicationContext().getSharedPreferences("Preferences",0);
                    String login=sharedPreferences.getString("Login",null);
                    Intent intent;
                    if(login!=null)
                    {
                        String em=sharedPreferences.getString("E",null);
                        String n=sharedPreferences.getString("N",null);
                        String h=sharedPreferences.getString("Hos",null);
                        if(h.equals("No")) {
                            intent = new Intent(Splashscreen.this, Tabbed.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("X", login);
                            bundle.putString("E",em);
                            bundle.putString("N",n);
                            intent.putExtras(bundle);
                        }
                        else{
                            intent=new Intent(Splashscreen.this,Hos_settings.class);
                            intent.putExtra("X",login);
                        }
                    }else {
                        intent = new Intent(Splashscreen.this, Login.class);
                    }
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                }




            }
        };

        splashTread.start();

    }
}



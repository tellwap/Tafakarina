package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Welcome extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private int[] layouts;
    private  MyPagerAdapter myPagerAdapter;
    private LinearLayout mDots_Layout;
    private ImageView[] dots;
    private Button bnSkip,bnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferenceManager(this).checkPreference()){
            loadHome();
        }

        if (Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome_screen);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        layouts =new int[] {R.layout.first_layout,R.layout.second_layout,R.layout.third_layout};
        myPagerAdapter = new MyPagerAdapter(this,layouts);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if (position == layouts.length-1){
                    bnNext.setText("Anza");
//                    bnSkip.setVisibility(View.INVISIBLE);
                }else{
                    bnNext.setText("Mbele");
                  //  bnSkip.setVisibility(View.VISIBLE );
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bnNext = (Button) findViewById(R.id.bnNext);
        //bnSkip = (Button) findViewById(R.id.bnSkip);

        bnNext.setOnClickListener(this);
//        bnSkip.setOnClickListener(this);

        mDots_Layout = (LinearLayout) findViewById(R.id.dotLayout);


    }

    private void createDots(int current_position){

        if (mDots_Layout != null)

            mDots_Layout.removeAllViews();
         dots = new ImageView[layouts.length];
        for ( int i = 0; i<layouts.length; i++){
            dots[i] = new ImageView(this);
            if (i==current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dot));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dot));

            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4,0,4,0);
            mDots_Layout.addView(dots[i],params);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bnNext:
                loadNextSlide();
                break;
//            case R.id.bnSkip:
//                loadHome();
//                new PreferenceManager(this).writePreference();
//                break;
        }
    }
        private void loadHome(){
          //  startActivity(new Intent(this,MainActivity.class));
            startActivity(new Intent(getApplicationContext(),PhoneAuthActivity.class));
            finish();
    }

    private void loadNextSlide(){
            int next_slide = mViewPager.getCurrentItem()+1;
            if (next_slide < layouts.length){
                mViewPager.setCurrentItem(next_slide);
            }else{
                loadHome();
                new PreferenceManager(this).writePreference();
            }
    }

    }


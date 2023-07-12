package com.example.techtrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private OnboardingAdapter onboardingAdapter;
   private LinearLayout linear;
   private FloatingActionButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear=findViewById(R.id.linear);
        next=findViewById(R.id.next);

        setuponboardingItems();
        ViewPager2 onboardingViewPager =findViewById(R.id.viewpager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setOnboardingIndicator();
        setCurrentOnboarding(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboarding(position);
            }
        });

        next.setOnClickListener(v -> {
            if(onboardingViewPager.getCurrentItem()+1 < onboardingAdapter.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
            }else {
               startActivity(new Intent(getApplicationContext(),SignIn.class));
               finish();
            }
        });
    }

    private void setuponboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemGPS = new OnboardingItem();
        itemGPS.setTitle("GPS Tracking System");
        itemGPS.setDescription("Once connected, you can track your smart luggage's real-time location using GPS technology for accurate and up-to-date information.\n");
        itemGPS.setImage(R.raw.gps);

        OnboardingItem itemFaceRecognition = new OnboardingItem();
        itemFaceRecognition.setTitle("Lock System");
        itemFaceRecognition.setDescription("Our app offers a face recognition lock system for easy and secure access to your TeckTrek smart luggage.");
        itemFaceRecognition.setImage(R.raw.facerecognition);

        OnboardingItem itemPoids = new OnboardingItem();
        itemPoids.setTitle("Weight System");
        itemPoids.setDescription("The maximum weight for your TeckTrek smart luggage is 2kg.");
        itemPoids.setImage(R.raw.poids);

        onboardingItems.add(itemGPS);
        onboardingItems.add(itemFaceRecognition);
        onboardingItems.add(itemPoids);

         onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setOnboardingIndicator(){
        ImageView[] indicators= new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i=0;i<indicators.length;i++){
            indicators[i]= new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.formebase
            ));

            indicators[i].setLayoutParams(layoutParams);
            linear.addView(indicators[i]);

        }

    }
    private void setCurrentOnboarding(int index){
        int childCount=linear.getChildCount();
        for(int i=0;i<childCount;i++){
            ImageView imageView=(ImageView)linear.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.formechange));
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.formebase));
            }
        }


    }
}
package com.payzout.business.common;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payzout.business.R;
import com.payzout.business.auth.PhoneActivity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

class OnboardPagerAdapter extends PagerAdapter {

    Context context;

    public OnboardPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_screen, container, false);

        ImageView ivOnboard = view.findViewById(R.id.ivOnboard);
        ImageView ivSlide1 = view.findViewById(R.id.ivSlide1);
        ImageView ivSlide2 = view.findViewById(R.id.ivSlide2);
        ImageView ivSlide3 = view.findViewById(R.id.ivSlide3);

        TextView tvOnboardTitle = view.findViewById(R.id.tvOnboardTitle);
        TextView tvSkipSlider = view.findViewById(R.id.tvSkipSlider);



        switch (position)
        {
            case  0:
                ivOnboard.setImageResource(R.drawable.slider_1);

                ivSlide1.setImageResource(R.drawable.slider_selected);
                ivSlide2.setImageResource(R.drawable.slider_unselect);
                ivSlide3.setImageResource(R.drawable.slider_unselect);

                tvOnboardTitle.setText("Invest with Payzout Business we provide a dedicated manager for you to help.");
                tvSkipSlider.setText("Skip");

                tvSkipSlider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnboardActivity.viewPager.setCurrentItem(position+1);
                    }
                });
                break;

            case 1:
                ivOnboard.setImageResource(R.drawable.slider_2);

                ivSlide1.setImageResource(R.drawable.slider_unselect);
                ivSlide2.setImageResource(R.drawable.slider_selected);
                ivSlide3.setImageResource(R.drawable.slider_unselect);

                tvOnboardTitle.setText("Invest in best, Payzout India's best peer to peer lending platform ");
                tvSkipSlider.setText("Skip");

                tvSkipSlider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnboardActivity.viewPager.setCurrentItem(position+1);
                    }
                });
                break;

            case 2:
                ivOnboard.setImageResource(R.drawable.slider_3);

                ivSlide1.setImageResource(R.drawable.slider_unselect);
                ivSlide2.setImageResource(R.drawable.slider_unselect);
                ivSlide3.setImageResource(R.drawable.slider_selected);

                tvOnboardTitle.setText("Invest once and get profit every month with Payzout Business ");
                tvSkipSlider.setText("Get Started");

                tvSkipSlider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PhoneActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

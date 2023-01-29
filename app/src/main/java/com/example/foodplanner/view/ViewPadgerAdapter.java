package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodplanner.R;

public class ViewPadgerAdapter extends PagerAdapter {
    Context context;
    int[] images = {
            R.drawable.onboarding1,
            R.drawable.onboarding2,
            R.drawable.onboarding3,
    };
    int[] description = {
            R.string.txt1, R.string.txt2, R.string.txt3
    };

    public ViewPadgerAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return description.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding, container, false);

        ImageView slideimage = view.findViewById(R.id.image_onboarding);
        TextView slideDescription = view.findViewById(R.id.tv_onboarding);


        slideimage.setImageResource(images[position]);
        slideDescription.setText(description[position]);


        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

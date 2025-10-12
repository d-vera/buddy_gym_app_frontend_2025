package com.example.buddy_gym_app_frontend_2025.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.buddy_gym_app_frontend_2025.fragments.HistoryFragment;
import com.example.buddy_gym_app_frontend_2025.fragments.PerformanceFragment;

public class HistoryPagerAdapter extends FragmentStateAdapter {

    private int exerciseId;

    public HistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity, int exerciseId) {
        super(fragmentActivity);
        this.exerciseId = exerciseId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return HistoryFragment.newInstance(exerciseId);
        } else {
            return PerformanceFragment.newInstance(exerciseId);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

package com.majazeh.risloo.Views.Adapters.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.majazeh.risloo.Views.Fragments.Edit.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionTimeFragment;

import java.util.HashMap;

public class EditSessionAdapter extends FragmentStateAdapter {

    public HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public EditSessionAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Fragment fragment0 = new EditSessionTimeFragment();
                hashMap.put(position, fragment0);
                return fragment0;
            case 1:
                Fragment fragment1 = new EditSessionSessionFragment();
                hashMap.put(position, fragment1);
                return fragment1;
            case 2:
                Fragment fragment2 = new EditSessionPaymentFragment();
                hashMap.put(position, fragment2);
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
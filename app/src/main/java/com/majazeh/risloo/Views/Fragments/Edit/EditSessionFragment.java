package com.majazeh.risloo.Views.Fragments.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Adapters.Tab.EditSessionAdapter;
import com.majazeh.risloo.databinding.FragmentEditSessionBinding;

public class EditSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionBinding binding;

    // Adapters
    public EditSessionAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new EditSessionAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.EditSessionTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position]));
    }

    private void setData() {
        binding.viewPager.setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
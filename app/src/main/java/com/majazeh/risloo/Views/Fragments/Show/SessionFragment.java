package com.majazeh.risloo.Views.Fragments.Show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.databinding.FragmentSessionBinding;

public class SessionFragment extends Fragment {

    // Binding
    private FragmentSessionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        return binding.getRoot();
    }

    private void initializer() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
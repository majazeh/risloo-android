package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentPasswordRecoverBinding;

public class PasswordRecoverFragment extends Fragment {

    // Binding
    private FragmentPasswordRecoverBinding binding;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentPasswordRecoverBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.passwordRecoverIncludeLayout.inputEditText.setHint(getResources().getString(R.string.PasswordRecoverFragmentInput));

        binding.passwordRecoverTextView.componentAuthButton.setText(getResources().getString(R.string.PasswordRecoverFragmentButton));

        binding.loginTextView.componentAuthLink.setText(getResources().getString(R.string.AuthLogin));
        binding.registerTextView.componentAuthLink.setText(getResources().getString(R.string.AuthRegister));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.passwordRecoverTextView.componentAuthButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordRecoverIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.passwordRecoverIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), binding.passwordRecoverIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.passwordRecoverIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.passwordRecoverIncludeLayout.inputEditText.length() == 11) {
                    ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), binding.passwordRecoverIncludeLayout.inputEditText, binding.passwordRecoverIncludeLayout.errorImageView, binding.passwordRecoverIncludeLayout.errorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passwordRecoverTextView.componentAuthButton.setOnClickListener(v -> {
            binding.passwordRecoverTextView.componentAuthButton.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.passwordRecoverTextView.componentAuthButton.setClickable(true), 300);

            if (binding.passwordRecoverIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), binding.passwordRecoverIncludeLayout.inputEditText, binding.passwordRecoverIncludeLayout.errorImageView, binding.passwordRecoverIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), binding.passwordRecoverIncludeLayout.inputEditText, binding.passwordRecoverIncludeLayout.errorImageView, binding.passwordRecoverIncludeLayout.errorTextView);
                doWork();
            }
        });

        binding.loginTextView.componentAuthLink.setOnClickListener(v -> {
            binding.loginTextView.componentAuthLink.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.loginTextView.componentAuthLink.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        binding.registerTextView.componentAuthLink.setOnClickListener(v -> {
            binding.registerTextView.componentAuthLink.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.registerTextView.componentAuthLink.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });
    }

    private void doWork() {
        mobile = binding.passwordRecoverIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
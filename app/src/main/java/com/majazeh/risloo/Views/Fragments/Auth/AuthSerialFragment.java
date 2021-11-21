package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthSerialBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AuthSerialFragment extends Fragment {

    // Binding
    private FragmentAuthSerialBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String serial = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthSerialBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((AuthActivity) requireActivity()).singleton.getAuthorization());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentTitle));
        binding.serialEditText.getRoot().setHint(getResources().getString(R.string.SerialFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.SerialFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentButton));

        InitManager.txtTextAppearance(requireActivity(), binding.dashboardLinkTextView.getRoot(), getResources().getString(R.string.AuthDashboardLink), R.style.danaDemiBoldTextStyle);
        binding.logoutLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLogoutLink));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), userModel.getName(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarImageView);

        binding.serialEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.serialEditText.getRoot().hasFocus())
                ((AuthActivity) requireActivity()).inputon.select(binding.serialEditText.getRoot());
            return false;
        });

        binding.serialEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            serial = binding.serialEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.serialEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).validatoon.emptyValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
            } else {
                if (binding.errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((AuthActivity) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);

                doWork("serial");
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> IntentManager.main(requireActivity())).widget(binding.dashboardLinkTextView.getRoot());

        CustomClickView.onDelayedListener(() -> doWork("logout")).widget(binding.logoutLinkTextView.getRoot());
    }

    private void setData() {
        userModel = ((AuthActivity) requireActivity()).singleton.getUserModel();

        if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && userModel.getAvatar() .getMedium().getUrl() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.LightBlue500).into(binding.avatarIncludeLayout.avatarImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (userModel.getName() != null && !userModel.getName().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(userModel.getName()));
            else if (userModel.getId() != null && !userModel.getId().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(userModel.getId()));
            else
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultUnknown)));
        }
    }

    private void setHashmap(String method) {
        if (method.equals("serial")) {
            data.put("authorized_key", serial);
        } else if (method.equals("logout")) {
            data.remove("authorized_key");
        }
    }

    private void doWork(String method) {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap(method);

        if (method.equals("serial")) {
            // Todo : Place Code Here
        } else if (method.equals("logout")) {
            Auth.logout(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((AuthActivity) requireActivity()).singleton.logout();
                            ((AuthActivity) requireActivity()).navigatoon.navigateToAuthLoginFragment();

                            DialogManager.dismissLoadingDialog();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // Place Code if Needed
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.databinding.BottomSheetImageBinding;

public class ImageBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetImageBinding binding;

    // Fragments
    private Fragment current, child;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetImageBinding.inflate(inflater, viewGroup, false);

        intializer();

        listener();

        return binding.getRoot();
    }

    private void intializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        child = ((MainActivity) requireActivity()).fragmont.getChild();
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
            if (PermissionManager.galleryPermission(requireActivity())) {
                IntentManager.gallery(requireActivity());
            }

            dismiss();
        }).widget(binding.galleryButton);

        CustomClickView.onClickListener(() -> {
            if (PermissionManager.cameraPermission(requireActivity())) {
                if (current instanceof CreateCenterFragment)
                    ((CreateCenterFragment) current).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditCenterTabAvatarFragment)
                    ((EditCenterTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditUserTabAvatarFragment)
                    ((EditUserTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());
            }

            dismiss();
        }).widget(binding.cameraButton);

        CustomClickView.onClickListener(this::dismiss).widget(binding.returnButton);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
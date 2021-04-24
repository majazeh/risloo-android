package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionTimeFragment;
import com.majazeh.risloo.databinding.BottomSheetTimeBinding;

import java.util.Objects;

public class TimeBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetTimeBinding binding;

    // Vars
    public int hour, minute;
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

        setNumberPicker();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearNumberPicker();
    }

    private void initializer() {
        switch (method) {
            case "startTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartTimeEntry));
                break;
        }
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSessionFragment != null) {
                        CreateSessionTimeFragment createSessionTimeFragment = (CreateSessionTimeFragment) createSessionFragment.adapter.getRegisteredFragment(0);

                        createSessionTimeFragment.responseBottomSheet(method, getTime());
                    }
                    break;
                case R.id.createScheduleFragment:
                    CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createScheduleFragment != null) {
                        CreateScheduleTimeFragment createScheduleTimeFragment = (CreateScheduleTimeFragment) createScheduleFragment.adapter.getRegisteredFragment(0);

                        createScheduleTimeFragment.responseBottomSheet(method, getTime());
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editSessionFragment != null) {
                        EditSessionTimeFragment editSessionTimeFragment = (EditSessionTimeFragment) editSessionFragment.adapter.getRegisteredFragment(0);

                        editSessionTimeFragment.responseBottomSheet(method, getTime());
                    }
                    break;
            }

            dismiss();
        }).widget(binding.entryButton);
    }

    private void setNumberPicker() {
        binding.hourNumberPicker.setMinValue(0);
        binding.hourNumberPicker.setMaxValue(23);
        binding.hourNumberPicker.setValue(hour);

        binding.minuteNumberPicker.setMinValue(0);
        binding.minuteNumberPicker.setMaxValue(59);
        binding.minuteNumberPicker.setValue(minute);
    }

    private void clearNumberPicker() {
        binding.hourNumberPicker.setValue(hour);
        binding.minuteNumberPicker.setValue(minute);
    }

    private String getTime() {
        hour = binding.hourNumberPicker.getValue();
        minute = binding.minuteNumberPicker.getValue();

        if (hour < 10) {
            if (minute < 10)
                return "0" + hour + ":" + "0" + minute;
            else
                return "0" + hour + ":" + minute;
        } else {
            if (minute < 10)
                return hour + ":" + "0" + minute;
            else
                return hour + ":" + minute;
        }
    }

    public void setTime(int hour, int minute, String method) {
        this.hour = hour;
        this.minute = minute;
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
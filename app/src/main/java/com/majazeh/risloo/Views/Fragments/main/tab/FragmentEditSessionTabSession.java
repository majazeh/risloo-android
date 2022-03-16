package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentEditSessionTabSessionBinding;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditSession;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.HashMap;

public class FragmentEditSessionTabSession extends Fragment {

    // Binding
    private FragmentEditSessionTabSessionBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    private String status = "", description = "", coordination = "";
    private String startType = "relative", endType = "relative";
    private String startAccurateTime = "", startAccurateDate = "", endAccurateTime = "", endAccurateDate = "";
    private String startRelativeDay = "", startRelativeHour = "", startRelativeMinute = "", endRelativeDay = "", endRelativeHour = "", endRelativeMinute = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionStatusHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionCoordinationHeader));

        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.EditSessionTabSessionCoordinationGuide));

        binding.startTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.StartTime));
        binding.startTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.AccurateTime));
        binding.startTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.RelativeTime));
        binding.startTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.endTypeIncludeLayout.headerCheckBox.setText(getResources().getString(R.string.EndTime));
        binding.endTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.AccurateTime));
        binding.endTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.RelativeTime));
        binding.endTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.startRelativeIncludeLayout.dayTextView.setText(getResources().getString(R.string.DayHint));
        binding.startRelativeIncludeLayout.hourTextView.setText(getResources().getString(R.string.HourHint));
        binding.startRelativeIncludeLayout.minuteTextView.setText(getResources().getString(R.string.MinuteStartHint));

        binding.endRelativeIncludeLayout.dayTextView.setText(getResources().getString(R.string.DayHint));
        binding.endRelativeIncludeLayout.hourTextView.setText(getResources().getString(R.string.HourHint));
        binding.endRelativeIncludeLayout.minuteTextView.setText(getResources().getString(R.string.MinuteEndHint));

        DropdownManager.spinner12ssp(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabSessionButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {

        // -------------------- Spinner

        binding.statusIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.statusIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    status = parent.getItemAtPosition(position).toString();

                    if (status.equals("زمان\u200Cبندی شده")) {
                        binding.scheduledGroup.setVisibility(View.VISIBLE);

                        if (binding.startTypeIncludeLayout.firstRadioButton.isChecked()) {
                            binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                        }

                        if (binding.endTypeIncludeLayout.firstRadioButton.isChecked()) {
                            binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                        }

                    } else {
                        binding.scheduledGroup.setVisibility(View.GONE);

                        if (binding.startAccurateIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                            binding.startAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                        if (binding.endAccurateIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                            binding.endAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // -------------------- RadioGroup

        binding.startTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    startType = "absolute";

                    binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
                case R.id.second_radioButton:
                    startType = "relative";

                    binding.startRelativeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.startAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    break;
            }
        });

        binding.endTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    endType = "absolute";

                    binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
                case R.id.second_radioButton:
                    endType = "relative";

                    binding.endRelativeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.endAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    break;
            }
        });

        // -------------------- Checkbox

        binding.endTypeIncludeLayout.headerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 1);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 1);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 1);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 1);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(true);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(true);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(true);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(true);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(true);
            } else {
                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 0.4);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 0.4);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 0.4);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 0.4);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(false);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(false);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(false);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(false);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(false);
            }
        });

        // -------------------- Touch

        binding.startRelativeIncludeLayout.dayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.dayEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.startRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.hourEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.startRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.minuteEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.startRelativeIncludeLayout.minuteEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.dayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.dayEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.endRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.hourEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.endRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.minuteEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.endRelativeIncludeLayout.minuteEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.coordinationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.coordinationIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.coordinationIncludeLayout.inputEditText);
            return false;
        });

        // -------------------- Focus

        binding.startRelativeIncludeLayout.dayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeDay = binding.startRelativeIncludeLayout.dayEditText.getText().toString().trim();
        });

        binding.startRelativeIncludeLayout.hourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeHour = binding.startRelativeIncludeLayout.hourEditText.getText().toString().trim();
        });

        binding.startRelativeIncludeLayout.minuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeMinute = binding.startRelativeIncludeLayout.minuteEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.dayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeDay = binding.endRelativeIncludeLayout.dayEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.hourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeHour = binding.endRelativeIncludeLayout.hourEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.minuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeMinute = binding.endRelativeIncludeLayout.minuteEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.coordinationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            coordination = binding.coordinationIncludeLayout.inputEditText.getText().toString().trim();
        });

        // -------------------- BottomSheet

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetTime(requireActivity(), startAccurateTime, "accurateStartTime");
        }).widget(binding.startAccurateIncludeLayout.timeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetDate(requireActivity(), startAccurateDate, "accurateStartDate");
        }).widget(binding.startAccurateIncludeLayout.dateTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetTime(requireActivity(), endAccurateTime, "accurateEndTime");
        }).widget(binding.endAccurateIncludeLayout.timeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetDate(requireActivity(), endAccurateDate, "accurateEndDate");
        }).widget(binding.endAccurateIncludeLayout.dateTextView);

        // -------------------- Done

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentEditSession)
                ((FragmentEditSession) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditSession) {
            SessionModel model = ((FragmentEditSession) current).sessionModel;

            if (model.getStatus() != null && !model.getStatus().equals("")) {
                status = JsonManager.getSessionStatus(requireActivity(), "fa", model.getStatus());
                for (int i=0; i<binding.statusIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.statusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                        binding.statusIncludeLayout.selectSpinner.setSelection(i);

                        if (status.equals("زمان\u200Cبندی شده")) {
                            binding.scheduledGroup.setVisibility(View.VISIBLE);

                            if (model.getOpensAtType() != null && !model.getOpensAtType().equals("") && model.getOpensAtType().equals("absolute")) {
                                binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                            }

                            if (model.getClosedAtType() != null && !model.getClosedAtType().equals("") && model.getClosedAtType().equals("absolute")) {
                                binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                            }

                        } else {
                            binding.scheduledGroup.setVisibility(View.GONE);
                        }
                    }
                }
            }

            if (model.getDescription() != null && !model.getDescription().equals("")) {
                description = model.getDescription();
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }

            if (model.getClientReminder() != null && !model.getClientReminder().equals("")) {
                coordination = model.getClientReminder();
                binding.coordinationIncludeLayout.inputEditText.setText(coordination);
            }

            if (model.getOpensAtType() != null && !model.getOpensAtType().equals("")) {
                startType = model.getOpensAtType();
                switch (startType) {
                    case "absolute":
                        binding.startTypeIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "relative":
                        binding.startTypeIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }
            }

            if (model.getClosedAtType() != null && !model.getClosedAtType().equals("")) {
                endType = model.getClosedAtType();
                switch (endType) {
                    case "absolute":
                        binding.endTypeIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "relative":
                        binding.endTypeIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }

                binding.endTypeIncludeLayout.headerCheckBox.setChecked(true);

                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 1);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 1);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 1);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 1);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(true);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(true);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(true);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(true);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(true);

            } else {
                binding.endTypeIncludeLayout.headerCheckBox.setChecked(true);

                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 0.4);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 0.4);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 0.4);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 0.4);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(false);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(false);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(false);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(false);

                binding.endRelativeIncludeLayout.getRoot().setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(false);
            }

            if (model.getOpensAt() != 0 && startType.equals("absolute")) {
                startAccurateTime = String.valueOf(model.getOpensAt());
                startAccurateDate = String.valueOf(model.getOpensAt());

                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(startAccurateTime));
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            } else {
                startAccurateTime = String.valueOf(DateManager.currentTimestamp());
                startAccurateDate = String.valueOf(DateManager.currentTimestamp());

                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(startAccurateTime));
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            }

            if (model.getClosedAt() != 0 && endType.equals("absolute")) {
                endAccurateTime = String.valueOf(model.getClosedAt());
                endAccurateDate = String.valueOf(model.getClosedAt());

                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(endAccurateTime));
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            } else {
                endAccurateTime = String.valueOf(DateManager.currentTimestamp());
                endAccurateDate = String.valueOf(DateManager.currentTimestamp());

                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(endAccurateTime));
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            }
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "accurateStartTime":
                startAccurateTime = data;
                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(startAccurateTime));
                break;
            case "accurateStartDate":
                startAccurateDate = data;
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
                break;
            case "accurateEndTime":
                endAccurateTime = data;
                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHcMM(endAccurateTime));
                break;
            case "accurateEndDate":
                endAccurateDate = data;
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
                break;
        }
    }

    public void setHashmap(HashMap data) {
        if (!status.equals(""))
            data.put("status", JsonManager.getSessionStatus(requireActivity(), "en", status));
        else
            data.remove("status");

        if (status.equals("زمان\u200Cبندی شده")) {
            if (!startType.equals("")) {
                data.put("opens_at_type", startType);

                if (startType.equals("relative"))
                    data.put("opens_at", DateManager.timestampRelative(startRelativeDay, startRelativeHour, startRelativeMinute));
                else
                    data.put("opens_at", DateManager.timestampAccurate(startAccurateTime, startAccurateDate));

            } else {
                data.remove("opens_at_type");
                data.remove("opens_at");
            }

            if (binding.endTypeIncludeLayout.headerCheckBox.isChecked()) {

                if (!endType.equals("")) {
                    data.put("closed_at_type", endType);

                    if (endType.equals("relative"))
                        data.put("closed_at", DateManager.timestampRelative(endRelativeDay, endRelativeHour, endRelativeMinute));
                    else
                        data.put("closed_at", DateManager.timestampAccurate(endAccurateTime, endAccurateDate));

                } else {
                    data.remove("closed_at_type");
                    data.remove("closed_at");
                }

            }

        }

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");

        if (!coordination.equals(""))
            data.put("client_reminder", coordination);
        else
            data.remove("client_reminder");
    }

    public void hideValid() {
        ((ActivityMain) requireActivity()).validatoon.hideValid(binding);
    }

    public void showValid(String key, String validation) {
        ((ActivityMain) requireActivity()).validatoon.showValid2(key, validation, binding);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}
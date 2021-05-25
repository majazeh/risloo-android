package com.majazeh.risloo.Views.Fragments.Edit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditCenterUserFragment extends Fragment {

    // Binding
    private FragmentEditCenterUserBinding binding;

    // Vars
    private String centerId = "", userId = "";
    private String position = "", nickname = "", status ="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.positionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentPositionHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentStatusHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusAccept));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusKick));

        InitManager.spinner(requireActivity(), binding.positionIncludeLayout.selectSpinner, R.array.UserTypes, "main");

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterUserFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.positionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditCenterUserFragment.this.position = parent.getItemAtPosition(position).toString();

                if (position == 3) {
                    binding.clientGroup.setVisibility(View.VISIBLE);
                } else {
                    binding.clientGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nicknameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nicknameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.statusIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    status = "accept";
                    break;
                case R.id.second_radioButton:
                    status = "kick";
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nicknameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nicknameIncludeLayout.inputEditText, binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nicknameIncludeLayout.inputEditText, binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                centerId = getArguments().getString("id");
            }

            if (getArguments().getString("user_id") != null && !getArguments().getString("user_id").equals("")) {
                userId = getArguments().getString("user_id");
            }

            if (getArguments().getString("position") != null && !getArguments().getString("position").equals("")) {
                position = getArguments().getString("position");
                for (int i = 0; i < binding.positionIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.positionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                        binding.positionIncludeLayout.selectSpinner.setSelection(i);

                        if (i == 3) {
                            binding.clientGroup.setVisibility(View.VISIBLE);
                        } else {
                            binding.clientGroup.setVisibility(View.GONE);
                        }
                    }
                }
            }

            if (getArguments().getString("nickname") != null && !getArguments().getString("nickname").equals("")) {
                nickname = getArguments().getString("nickname");
                binding.nicknameIncludeLayout.inputEditText.setText(nickname);
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                status = getArguments().getString("status");
                switch (status) {
                    case "accept":
                        binding.statusIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "kick":
                        binding.statusIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }
            }
        }
    }

    private void doWork() {
        nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();

        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap<>();
        data.put("id", centerId);
        data.put("userId", userId);
        data.put("position", position);
        data.put("nickname", nickname);
        data.put("status", status);

        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        Center.editUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Bundle extras = new Bundle();
                        extras.putString("id", centerId);

                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigator(R.id.centerUsersFragment, extras);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        switch (key) {
                                            case "position":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.positionIncludeLayout.selectSpinner, binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "nickname":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nicknameIncludeLayout.inputEditText, binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Adapters.Recycler.FieldsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ProfilesAdapter;
import com.majazeh.risloo.databinding.FragmentSampleBinding;

import java.util.ArrayList;

public class SampleFragment extends Fragment {

    // Binding
    private FragmentSampleBinding binding;

    // Adapters
    private ProfilesAdapter profilesAdapter;
    private FieldsAdapter fieldsGeneralAdapter, fieldsPrerequisiteAdapter, fieldsAnswerAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager profilesLayoutManager, fieldsGeneralLayoutManager, fieldsPrerequisiteLayoutManager, fieldsAnswerLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        profilesAdapter = new ProfilesAdapter(requireActivity());
        fieldsGeneralAdapter = new FieldsAdapter(requireActivity());
        fieldsPrerequisiteAdapter = new FieldsAdapter(requireActivity());
        fieldsAnswerAdapter = new FieldsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        profilesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        fieldsGeneralLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        fieldsPrerequisiteLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        fieldsAnswerLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.profilesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHeader));
        binding.fieldsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.recyclerView(binding.profilesRecyclerView, itemDecoration, profilesLayoutManager);
        InitManager.recyclerView(binding.fieldsGeneralRecyclerView, itemDecoration, fieldsGeneralLayoutManager);
        InitManager.recyclerView(binding.fieldsPrerequisiteRecyclerView, itemDecoration, fieldsPrerequisiteLayoutManager);
        InitManager.recyclerView(binding.fieldsAnswerRecyclerVIew, itemDecoration, fieldsAnswerLayoutManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> doWork("primary")).widget(binding.primaryTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> doWork("secondary")).widget(binding.secondaryTextView.getRoot());

         binding.profilesTextView.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();

                // TODO : Place the specified url of the current type in the below method to download it

                IntentManager.download(requireContext(), "");

                binding.profilesTextView.selectSpinner.setSelection(binding.profilesTextView.selectSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fieldsEditableCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.fieldsEditableCheckBox.setTextColor(getResources().getColor(R.color.Gray900));

                fieldsGeneralAdapter.setEditable(true);
                fieldsPrerequisiteAdapter.setEditable(true);
                fieldsAnswerAdapter.setEditable(true);
            } else {
                binding.fieldsEditableCheckBox.setTextColor(getResources().getColor(R.color.Gray600));

                fieldsGeneralAdapter.setEditable(false);
                fieldsPrerequisiteAdapter.setEditable(false);
                fieldsAnswerAdapter.setEditable(false);
            }
        });
    }

    private void setData() {
        // Todo : Place Code Here

        binding.nameTextView.setText("آزمون ریون");
        binding.referenceTextView.setText("دکتر مسعود جان\u200Cبزرگی");
        binding.statusTextView.setText("باز");

        setStatus(binding.statusTextView.getText().toString());

//        profilesAdapter.setProfiles(null);
//        fieldsGeneralAdapter.setFields(null);
//        fieldsPrerequisiteAdapter.setFields(null);
//        fieldsAnswerAdapter.setFields(null);
        binding.profilesRecyclerView.setAdapter(profilesAdapter);
        binding.fieldsGeneralRecyclerView.setAdapter(fieldsGeneralAdapter);
        binding.fieldsPrerequisiteRecyclerView.setAdapter(fieldsPrerequisiteAdapter);
        binding.fieldsAnswerRecyclerVIew.setAdapter(fieldsAnswerAdapter);

        binding.profilesHeaderIncludeLayout.countTextView.setText("(" + profilesAdapter.getItemCount() + ")");
        binding.fieldsHeaderIncludeLayout.countTextView.setText("(" + (fieldsGeneralAdapter.getItemCount() + fieldsPrerequisiteAdapter.getItemCount() + fieldsAnswerAdapter.getItemCount()) + ")");

        new Handler().postDelayed(() -> {
            binding.profilesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.profilesRecyclerView.setVisibility(View.VISIBLE);

            binding.fieldsGeneralShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.fieldsGeneralRecyclerView.setVisibility(View.VISIBLE);

            binding.fieldsPrerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.fieldsPrerequisiteRecyclerView.setVisibility(View.VISIBLE);

            binding.fieldsAnswerShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.fieldsAnswerRecyclerVIew.setVisibility(View.VISIBLE);
        }, 1000);
    }

    private void doWork(String button) {
        if (button.equals("primary")) {
            switch (binding.statusTextView.getText().toString()) {
                case "seald":
                    // TODO ; Place code Here
                    break;
                case "open":
                    // TODO ; Place code Here
                    break;
                case "closed":
                    // TODO ; Place code Here
                    break;
                case "scoring":
                    // TODO ; Place code Here
                    break;
                case "creating_files":
                    // TODO ; Place code Here
                    break;
                case "done":
                    // TODO ; Place code Here
                    break;
            }
        } else {
            switch (binding.statusTextView.getText().toString()) {
                case "seald":
                    // TODO ; Place code Here
                    break;
                case "open":
                    // TODO ; Place code Here
                    break;
                case "closed":
                    // TODO ; Place code Here
                    break;
                case "scoring":
                    // TODO ; Place code Here
                    break;
                case "creating_files":
                    // TODO ; Place code Here
                    break;
                case "done":
                    // TODO ; Place code Here
                    break;
            }
        }
    }

    private void setStatus(String status) {
        switch (status) {
            case "seald":
            case "open":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.Gray500));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentClose), getResources().getColor(R.color.Gray500));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);
                break;
            case "closed":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentOpen), getResources().getColor(R.color.Blue600));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);
                break;
            case "scoring":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                // TODO : Place the right condition

                binding.scoringConstraintLayout.setVisibility(View.VISIBLE);
                break;
            case "creating_files":
            case "done":
                ArrayList<String> profiles = new ArrayList<>();

                profiles.add("");
                profiles.add("");
                profiles.add("");
                profiles.add("");

                InitManager.customizedSpinner(requireActivity(), binding.profilesTextView.selectSpinner, profiles, "profiles");

                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.profilesTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.profilesTextView.selectTextView, getResources().getString(R.string.SampleFragmentProfiles), getResources().getColor(R.color.Blue600));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White));

                ImageViewCompat.setImageTintList(binding.profilesTextView.angleImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.profilesTextView.selectTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
                } else {
                    binding.profilesTextView.selectTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
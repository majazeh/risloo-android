package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.ImageDialog;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.squareup.picasso.Picasso;

public class CreateCenterFragment extends Fragment {

    // Binding
    public FragmentCreateCenterBinding binding;

    // Objects
    private ImageDialog imageDialog;
    public Bitmap avatarBitmap;

    // Vars
    public String center = "personal", manager = "", name = "", avatarPath = "", address = "", description ="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        imageDialog = new ImageDialog();

        binding.centerIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentCenterHeader));
        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentManagerHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentNameHeader));
        binding.avatarIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentDescriptionHeader));

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarGuide));

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentDescriptionHint));

        binding.centerIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCenterPersonal));
        binding.centerIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCenterClinic));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.centerIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    center = "personal";

                    binding.clinicGroup.setVisibility(View.GONE);
                    break;
                case R.id.second_radioButton:
                    center = "clinic";

                    binding.clinicGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.managerIncludeLayout.selectTextView);

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            imageDialog.show(requireActivity().getSupportFragmentManager(), "imageBottomSheet");
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.addressIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.addressIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.phonesIncludeLayout.selectRecyclerView);

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (center.equals("personal")) {
                if (manager.equals("")) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }

                if (!manager.equals("") && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView);

                    doWork();
                }
            } else {
                if (manager.equals("")) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }

                if (!manager.equals("") && binding.nameIncludeLayout.inputEditText.length() != 0 && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView);

                    doWork();
                }
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            center = ((MainActivity) requireActivity()).singleton.getName();
            switch (center) {
                case "personal":
                    binding.centerIncludeLayout.firstRadioButton.setChecked(true);

                    binding.clinicGroup.setVisibility(View.GONE);
                    break;
                case "clinic":
                    binding.centerIncludeLayout.secondRadioButton.setChecked(true);

                    binding.clinicGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getManager().equals("")) {
            manager = ((MainActivity) requireActivity()).singleton.getManager();
            binding.managerIncludeLayout.selectTextView.setText(manager);
        }
        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            address = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.addressIncludeLayout.inputEditText.setText(address);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            address = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.addressIncludeLayout.inputEditText.setText(address);
        }

        // TODO : Set Phones Here

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void doWork() {
        if (center.equals("personal")) {
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

            // TODO : Call Work Method
        } else {
            FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

            // TODO : Call Work Method
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
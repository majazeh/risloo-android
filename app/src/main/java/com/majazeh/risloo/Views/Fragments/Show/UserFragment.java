package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView, usernameTextView;
    private ImageView badgeImageView;
    private TextView educationTextView, birthdayTextView, emailTextView, mobileTextView;
    private ImageView educationImageView, birthdayImageView, emailImageView, mobileImageView;
    private TextView editTextView;
    private ImageView enterImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        avatarCircleImageView = view.findViewById(R.id.component_avatar_86sdp_border_white_circleImageView);

        charTextView = view.findViewById(R.id.component_avatar_86sdp_border_white_textView);

        nameTextView = view.findViewById(R.id.fragment_user_name_textView);
        usernameTextView = view.findViewById(R.id.fragment_user_username_textView);

        badgeImageView = view.findViewById(R.id.fragment_user_badge_imageView);

        educationTextView = view.findViewById(R.id.fragment_user_education_textView);
        birthdayTextView = view.findViewById(R.id.fragment_user_birthday_textView);
        emailTextView = view.findViewById(R.id.fragment_user_email_textView);
        mobileTextView = view.findViewById(R.id.fragment_user_mobile_textView);

        educationImageView = view.findViewById(R.id.fragment_user_education_imageView);
        birthdayImageView = view.findViewById(R.id.fragment_user_birthday_imageView);
        emailImageView = view.findViewById(R.id.fragment_user_email_imageView);
        mobileImageView = view.findViewById(R.id.fragment_user_mobile_imageView);

        editTextView = view.findViewById(R.id.fragment_user_edit_textView);
        editTextView.setText(getResources().getString(R.string.UserFragmentEdit));
        editTextView.setTextColor(getResources().getColor(R.color.Gray500));

        enterImageView = view.findViewById(R.id.fragment_user_enter_imageView);
        enterImageView.setImageResource(R.drawable.ic_user_cog_light);
        ImageViewCompat.setImageTintList(enterImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue600));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        avatarCircleImageView.setOnClickListener(v -> {
            avatarCircleImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        editTextView.setOnClickListener(v -> {
            editTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        enterImageView.setOnClickListener(v -> {
            enterImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> enterImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });
    }

    private void setData() {
        if (((MainActivity) getActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) getActivity()).singleton.getName());
        }

        if (((MainActivity) getActivity()).singleton.getUsername().equals("")) {
            usernameTextView.setVisibility(View.GONE);
        } else {
            usernameTextView.setText(((MainActivity) getActivity()).singleton.getUsername());
        }

        if (((MainActivity) getActivity()).singleton.getEducation().equals("")) {
            educationTextView.setVisibility(View.GONE);
            educationImageView.setVisibility(View.GONE);
        } else {
            educationTextView.setText(((MainActivity) getActivity()).singleton.getEducation());
        }

        if (((MainActivity) getActivity()).singleton.getBirthday().equals("")) {
            birthdayTextView.setVisibility(View.GONE);
            birthdayImageView.setVisibility(View.GONE);
        } else {
            birthdayTextView.setText(((MainActivity) getActivity()).singleton.getBirthday());
        }

        if (((MainActivity) getActivity()).singleton.getEmail().equals("")) {
            emailTextView.setVisibility(View.GONE);
            emailImageView.setVisibility(View.GONE);
        } else {
            emailTextView.setText(((MainActivity) getActivity()).singleton.getEmail());
        }

        if (((MainActivity) getActivity()).singleton.getMobile().equals("")) {
            mobileTextView.setVisibility(View.GONE);
            mobileImageView.setVisibility(View.GONE);
        } else {
            mobileTextView.setText(((MainActivity) getActivity()).singleton.getMobile());
        }

        if (((MainActivity) getActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) getActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }
    }

}
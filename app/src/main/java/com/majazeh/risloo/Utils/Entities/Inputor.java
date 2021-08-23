package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;

import java.util.Objects;

public class Inputor {

    // Widgets
    public EditText editText;

    public Inputor() {
        // TODO : Place Code If Needed
    }

    /*
    ---------- Methods ----------
    */

    public void select(Activity activity, EditText editText) {
        this.editText = editText;

        editText.requestFocus();
        if (activity instanceof AuthActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue500);
        else if (activity instanceof MainActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
        else if (activity instanceof TestActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
    }

    public void clear(Activity activity, EditText editText) {
        editText.clearFocus();
        if (activity instanceof AuthActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        else if (activity instanceof MainActivity) {
            View searchView = activity.findViewById(R.id.search_editText);

            if (searchView != null && searchView.getClass().getName().equals(editText.getClass().getName()))
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
            else
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        } else if (activity instanceof TestActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);

        hideKeyboard(activity, editText);
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
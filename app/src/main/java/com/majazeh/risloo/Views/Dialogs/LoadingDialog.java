package com.majazeh.risloo.Views.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.databinding.DialogLoadingBinding;

public class LoadingDialog extends AppCompatDialogFragment {

    // Binding
    private DialogLoadingBinding binding;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogLoadingBinding binding = DialogLoadingBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setAttributes(ParamsManager.applyWrapContent(dialog));

        setCancelable(false);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogLoadingBinding.inflate(inflater, viewGroup, false);

        setDialog();

        return binding.getRoot();
    }

    private void setDialog() {
        switch (method) {
            case "loading":
                binding.titleTextView.setText(getResources().getString(R.string.DialogLoadingTitle));
                break;
            case "payment":
                binding.titleTextView.setText(getResources().getString(R.string.DialogPaymentTitle));
                break;
            case "callback":
                binding.titleTextView.setText(getResources().getString(R.string.DialogCallbackTitle));
                break;
        }
    }

    public void setData(String method) {
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.databinding.FragmentCreateCaseUserBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateCaseUserFragment extends Fragment {

    // Binding
    private FragmentCreateCaseUserBinding binding;

    // Adapters
    public SelectedAdapter referencesAdapter;

    // Objects
    private HashMap data, header;

    // Vars
    public String caseId = "", roomId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new SelectedAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseUserFragmentReferenceHeader));

        binding.referenceGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCaseUserFragmentReferenceGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCaseUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "references");
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateCaseUserFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateCaseUserFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("case")) {
                CaseModel caseModel = (CaseModel) CreateCaseUserFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(caseModel);
            }
        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        }
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            caseId = model.getCaseId();
            data.put("id", caseId);
        }

        if (model.getCaseRoom() != null && model.getCaseRoom().getRoomId() != null && !model.getCaseRoom().getRoomId().equals("")) {
            roomId = model.getCaseRoom().getRoomId();
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
         if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "references": {
                UserModel model = (UserModel) item;

                int position = referencesAdapter.getIds().indexOf(model.getId());

                if (position == -1)
                    referencesAdapter.addItem(item);
                else
                    referencesAdapter.removeItem(position);

                if (referencesAdapter.getIds().size() != 0) {
                    binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                    binding.referenceIncludeLayout.countTextView.setText(StringManager.bracing(referencesAdapter.getIds().size()));
                } else {
                    binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                    binding.referenceIncludeLayout.countTextView.setText("");
                }
            } break;
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity());

        data.put("client_id", referencesAdapter.getIds());

        Case.addClient(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewReferenceAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder errors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String validation = errorsObject.getJSONArray(key).get(i).toString();

                                        switch (key) {
                                            case "client_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, validation);
                                                break;
                                        }

                                        errors.append(validation);
                                        errors.append("\n");
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
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
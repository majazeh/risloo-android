package com.majazeh.risloo.Views.Fragments.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.EditSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionBinding binding;

    // Adapters
    public EditSessionAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Fragments
    private Fragment time, reference, session, platform, payment;

    // Vars
    private String[] tabs;
    private boolean hasCase = false;
    private HashMap data, header;
    public SessionModel sessionModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        sessionModel = (SessionModel) EditSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (sessionModel.getCaseModel() != null && sessionModel.getCaseModel().getCaseId() != null && !sessionModel.getCaseModel().getCaseId().equals("")) {
            hasCase = true;
            tabs = getResources().getStringArray(R.array.EditSessionHasCaseTabs);
        } else {
            tabs = getResources().getStringArray(R.array.EditSessionNoCaseTabs);
        }

        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        adapter = new EditSessionAdapter(requireActivity(), hasCase);

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());
        tabLayoutMediator.attach();

        setData(sessionModel);
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        reference = ((MainActivity) requireActivity()).fragmont.getReference();
        session = ((MainActivity) requireActivity()).fragmont.getSessionEditSession(hasCase);
        platform = ((MainActivity) requireActivity()).fragmont.getPlatformEditSession(hasCase);
        payment = ((MainActivity) requireActivity()).fragmont.getPaymentEditSession(hasCase);

        if (hasCase) {
            if (time instanceof EditSessionTimeFragment) {
                if (((EditSessionTimeFragment) time).startTime.equals("")) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                } else {
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView);
                    doWork();
                }
            }
        } else {
            if (time instanceof EditSessionTimeFragment && reference instanceof EditSessionReferenceFragment) {
                if (((EditSessionTimeFragment) time).startTime.equals(""))
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                else
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView);

                if (((EditSessionReferenceFragment) reference).type.equals("case") && ((EditSessionReferenceFragment) reference).caseId.equals(""))
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                else
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.errorTextView);

                if (((EditSessionReferenceFragment) reference).type.equals("case")) {
                    if (!((EditSessionTimeFragment) time).startTime.equals("") && !((EditSessionReferenceFragment) reference).caseId.equals(""))
                        doWork();
                } else {
                    if (!((EditSessionTimeFragment) time).startTime.equals(""))
                        doWork();
                }
            }
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof EditSessionTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((EditSessionTimeFragment) time).startTime));
            data.put("duration", ((EditSessionTimeFragment) time).duration);
        }

        // Reference Data
        if (hasCase) {
            data.put("case_id", sessionModel.getCaseModel().getCaseId());
            data.put("clients_type", "case");
        } else {
            if (reference instanceof EditSessionReferenceFragment) {
                data.put("selection_type", SelectionManager.getSelectionType(requireActivity(), "en", ((EditSessionReferenceFragment) reference).selection));

                if (((EditSessionReferenceFragment) reference).type.equals("اعضاء ریسلو")) {
                    data.put("clients_type", "risloo");
                } else if (((EditSessionReferenceFragment) reference).type.contains("مرکز")) {
                    data.put("clients_type", "center");
                } else if (((EditSessionReferenceFragment) reference).type.contains("اتاق درمان")) {
                    data.put("clients_type", "room");
                } else if (((EditSessionReferenceFragment) reference).type.equals("اعضاء پرونده درمانی …")) {
                    data.put("clients_type", "case");
                } else if (((EditSessionReferenceFragment) reference).type.equals("ساخت پرونده جدید")) {
                    data.put("clients_type", "new_case");
                }

                if (data.get("clients_type").equals("case")) {
                    data.put("case_id", ((EditSessionReferenceFragment) reference).caseId);
                } else if (data.get("clients_type").equals("new_case")) {
                    data.put("problem", ((EditSessionReferenceFragment) reference).problem);
                }

                data.put("group_session", ((EditSessionReferenceFragment) reference).groupSession);
                if (data.get("group_session").equals("on")) {
                    data.put("clients_number", ((EditSessionReferenceFragment) reference).count);
                }
            }
        }

        // Session Data
        if (session instanceof EditSessionSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((EditSessionSessionFragment) session).status));
            data.put("description", ((EditSessionSessionFragment) session).description);
            data.put("client_reminder", ((EditSessionSessionFragment) session).coordination);
        }

        // Payment Data
        if (payment instanceof EditSessionPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((EditSessionPaymentFragment) payment).payment));
        }

        Session.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
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
                                            case "time":
                                                if (time instanceof EditSessionTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                break;
                                            case "case_id":
                                                if (!hasCase && reference instanceof EditSessionReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
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
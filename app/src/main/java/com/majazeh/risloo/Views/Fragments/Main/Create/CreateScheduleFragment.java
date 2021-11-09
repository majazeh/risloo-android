package com.majazeh.risloo.Views.Fragments.Main.Create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateScheduleAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateScheduleFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleBinding binding;

    // Adapters
    public CreateScheduleAdapter adapter;

    // Models
    public RoomModel roomModel;

    // Fragments
    private Fragment time, reference, session, platform, payment;

    // Objects
    private TabLayoutMediator tabLayoutMediator;
    private HashMap data, header;

    // Vars
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreateScheduleAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateScheduleTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        TypeModel typeModel = CreateScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel")) {
                roomModel = (RoomModel) typeModel;
                setData(roomModel);
            }
        }
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            data.put("id", model.getRoomId());
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        reference = ((MainActivity) requireActivity()).fragmont.getReference();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        // Time Data
        if (time instanceof CreateScheduleTabTimeFragment)
            ((CreateScheduleTabTimeFragment) time).hideValid();

        // Reference Data
        if (reference instanceof CreateScheduleTabReferenceFragment)
            ((CreateScheduleTabReferenceFragment) reference).hideValid();

        // Session Data
        if (session instanceof CreateScheduleTabSessionFragment)
            ((CreateScheduleTabSessionFragment) session).hideValid();

        // Platform Data
        if (platform instanceof CreateScheduleTabPlatformFragment)
            ((CreateScheduleTabPlatformFragment) platform).hideValid();

        // Payment Data
        if (payment instanceof CreateScheduleTabPaymentFragment)
            ((CreateScheduleTabPaymentFragment) payment).hideValid();

        doWork();
    }

    private void setHashmap() {

        // Time Data
        if (time instanceof CreateScheduleTabTimeFragment)
            ((CreateScheduleTabTimeFragment) time).setHashmap(data);

        // Reference Data
        if (reference instanceof CreateScheduleTabReferenceFragment)
            ((CreateScheduleTabReferenceFragment) reference).setHashmap(data);

        // Session Data
        if (session instanceof CreateScheduleTabSessionFragment)
            ((CreateScheduleTabSessionFragment) session).setHashmap(data);

        // Platform Data
        if (platform instanceof CreateScheduleTabPlatformFragment)
            ((CreateScheduleTabPlatformFragment) platform).setHashmap(data);

        // Payment Data
        if (payment instanceof CreateScheduleTabPaymentFragment)
            ((CreateScheduleTabPaymentFragment) payment).setHashmap(data);
    }

    public void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Room.createSchedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                ScheduleModel scheduleModel = (ScheduleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSchedule));

                        NavDirections action = NavigationMainDirections.actionGlobalSessionFragment(scheduleModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
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

                                            // Time Data
                                            case "time":
                                            case "duration":
                                            case "date_type":
                                            case "date":
                                            case "week_days":
                                            case "repeat_status":
                                            case "repeat":
                                            case "repeat_from":
                                            case "repeat_to":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((CreateScheduleTabTimeFragment) time).showValid(key, validation);

                                                break;

                                            // Reference Data
                                            case "selection_type":
                                            case "clients_type":
                                            case "case_id":
                                            case "group_session":
                                            case "clients_number":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((CreateScheduleTabReferenceFragment) reference).showValid(key, validation);

                                                break;

                                            // Session Data
                                            case "status":
                                            case "opens_at_type":
                                            case "opens_at":
                                            case "closed_at_type":
                                            case "closed_at":
                                            case "fields":
                                            case "description":
                                            case "client_reminder":
                                                if (session instanceof CreateScheduleTabSessionFragment)
                                                    ((CreateScheduleTabSessionFragment) session).showValid(key, validation);

                                                break;

                                            // Platform Data
                                            case "platforms":
                                            case "pin_platform":
                                            case "identifier_platform":
                                                if (platform instanceof CreateScheduleTabPlatformFragment)
                                                    ((CreateScheduleTabPlatformFragment) platform).showValid(key, validation);

                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof CreateScheduleTabPaymentFragment)
                                                    ((CreateScheduleTabPaymentFragment) payment).showValid(key, validation);

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
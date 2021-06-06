package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSession2Binding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Sessions2Adapter extends RecyclerView.Adapter<Sessions2Adapter.Sessions2Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> sessions;
    private boolean userSelect = false;

    public Sessions2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Sessions2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Sessions2Holder(SingleItemSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sessions2Holder holder, int i) {
        SessionModel session = (SessionModel) sessions.get(i);

        initializer(holder);

        detector(holder);

        listener(holder, session);

        setData(holder, session);
    }

    @Override
    public int getItemCount() {
        if (this.sessions != null)
            return sessions.size();
        else
            return 0;
    }

    public void setSessions(ArrayList<TypeModel> sessions) {
        if (this.sessions == null)
            this.sessions = sessions;
        else
            this.sessions.addAll(sessions);
        notifyDataSetChanged();
    }

    public void clearSessions() {
        if (this.sessions != null) {
            this.sessions.clear();
            notifyDataSetChanged();
        }
    }

    private void initializer(Sessions2Holder holder) {
        InitManager.spinner(activity, holder.binding.statusSpinner, R.array.SessionStatus, "adapter");
    }

    private void detector(Sessions2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(Sessions2Holder holder, SessionModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sessionFragment, getExtras(model))).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String status = parent.getItemAtPosition(position).toString();

                    doWork(holder, model.getId(), SelectionManager.getSessionStatus(activity, "en", status));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.editSessionFragment, getExtras(model))).widget(holder.binding.editImageView);
    }

    private void setData(Sessions2Holder holder, SessionModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.startTimeTextView.setText(DateManager.gregorianToJalali5(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getStarted_at()))));
        holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");

        setStatus(holder, model);
    }

    private void setStatus(Sessions2Holder holder, SessionModel model) {
        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            switch (model.getStatus()) {
                case "draft":
                    holder.binding.statusSpinner.setSelection(0);
                    break;
                case "scheduled":
                    holder.binding.statusSpinner.setSelection(1);
                    break;
                case "client_awaiting":
                    holder.binding.statusSpinner.setSelection(2);
                    break;
                case "session_awaiting":
                    holder.binding.statusSpinner.setSelection(3);
                    break;
                case "in_session":
                    holder.binding.statusSpinner.setSelection(4);
                    break;
                case "finished":
                    holder.binding.statusSpinner.setSelection(5);
                    break;
                case "canceled_by_client":
                    holder.binding.statusSpinner.setSelection(6);
                    break;
                case "canceled_by_center":
                    holder.binding.statusSpinner.setSelection(7);
                    break;
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.statusSpinner.setEnabled(true);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);

            holder.binding.statusAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.statusSpinner.setEnabled(false);
            holder.binding.statusSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.statusAngleImageView.setVisibility(View.GONE);
        }
    }

    private void doWork(Sessions2Holder holder, String id, String status) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap<>();
        data.put("id", id);
        data.put("status", status);

        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

//        Session.changeStatus(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                SessionModel model = (SessionModel) object;
//
//                activity.runOnUiThread(() -> {
//                    setStatus(holder, model);
//
//                    ((MainActivity) activity).loadingDialog.dismiss();
//                    Toast.makeText(activity, activity.getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
//                });
//            }
//
//            @Override
//            public void onFailure(String response) {
//                // Place Code if Needed
//            }
//        });
    }

    private Bundle getExtras(SessionModel model) {
        Bundle extras = new Bundle();
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return extras;
    }

    public class Sessions2Holder extends RecyclerView.ViewHolder {

        private SingleItemSession2Binding binding;

        public Sessions2Holder(SingleItemSession2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
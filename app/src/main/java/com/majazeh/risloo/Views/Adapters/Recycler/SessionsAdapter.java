package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> sessions;

    public SessionsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SessionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SessionsHolder(SingleItemSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsHolder holder, int i) {
        SessionModel session = (SessionModel) sessions.get(i);

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

    private void detector(SessionsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    private void listener(SessionsHolder holder, SessionModel model) {
        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalSessionFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.editImageView);
    }

    private void setData(SessionsHolder holder, SessionModel model) {
        try {
            if (holder.getBindingAdapterPosition() == 0)
                holder.binding.topView.setVisibility(View.GONE);
             else
                holder.binding.topView.setVisibility(View.VISIBLE);

            holder.binding.serialTextView.setText(model.getId());

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null) {
                holder.binding.roomTextView.setText(model.getRoom().getRoomManager().getName());
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail().has("title") && !model.getRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            if (model.getCaseModel() != null && model.getCaseModel().getCaseId() != null) {
                holder.binding.caseTextView.setText(model.getCaseModel().getCaseId());
            }

            if (model.getCaseModel() != null && model.getCaseModel().getClients() != null && !model.getCaseModel().getClients().data().isEmpty()) {
                for (int i = 0; i < model.getCaseModel().getClients().data().size(); i++) {
                    UserModel user = (UserModel) model.getCaseModel().getClients().data().get(i);
                    if (user != null) {
                        holder.binding.referenceTextView.setText(user.getName());
                    }
                }
            }

            holder.binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getStarted_at()), " "));
            holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
            holder.binding.statusTextView.setText(SelectionManager.getSessionStatus(activity, "fa", model.getStatus()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class SessionsHolder extends RecyclerView.ViewHolder {

        private SingleItemSessionBinding binding;

        public SessionsHolder(SingleItemSessionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
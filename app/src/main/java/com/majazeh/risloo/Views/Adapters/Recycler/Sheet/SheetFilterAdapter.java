package com.majazeh.risloo.Views.Adapters.Recycler.Sheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Sheet.SheetFilterHolder;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemSheetFilterBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class SheetFilterAdapter extends RecyclerView.Adapter<SheetFilterHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> rooms;
    private ArrayList<String> status;
    private String method;

    public SheetFilterAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SheetFilterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SheetFilterHolder(SingleItemSheetFilterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SheetFilterHolder holder, int i) {
        if (method.equals("rooms")) {
            RoomModel model = (RoomModel) rooms.get(i);

            intializer();

            listener(holder, model);

            setData(holder, model);
        } else {
            String item = status.get(i);

            intializer();

            listener(holder, item);

            setData(holder, item);
        }
    }

    @Override
    public int getItemCount() {
        if (method.equals("rooms")) {
            if (this.rooms != null)
                return rooms.size();
            else
                return 0;
        } else {
            if (this.status != null)
                return status.size();
            else
                return 0;
        }
    }

    public void setRooms(ArrayList<TypeModel> items) {
        method = "rooms";

        if (this.rooms == null)
            this.rooms = items;
        else
            this.rooms.addAll(items);
        notifyDataSetChanged();
    }

    public void setStatus(ArrayList<String> items) {
        method = "status";

        if (this.status == null)
            this.status = items;
        else
            this.status.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (method.equals("rooms")) {
            if (this.rooms != null) {
                this.rooms.clear();
                notifyDataSetChanged();
            }
        } else {
            if (this.status != null) {
                this.status.clear();
                notifyDataSetChanged();
            }
        }
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SheetFilterHolder holder, RoomModel model) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof CenterSchedulesFragment)
                ((CenterSchedulesFragment) current).filterSchedules(model.getRoomId(), "");

        }).widget(holder.binding.getRoot());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SheetFilterHolder holder, String item) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof CenterSchedulesFragment)
                ((CenterSchedulesFragment) current).filterSchedules("", SelectionManager.getSessionStatus2(activity, "en", item));
            else if (current instanceof RoomSchedulesFragment)
                ((RoomSchedulesFragment) current).filterSchedules(SelectionManager.getSessionStatus2(activity, "en", item));

        }).widget(holder.binding.getRoot());
    }

    private void setData(SheetFilterHolder holder, RoomModel model) {
        if (model.getRoomManager() != null && !model.getRoomManager().getName().equals(""))
            holder.binding.getRoot().setText(model.getRoomManager().getName());
        else if (model.getRoomManager() != null)
            holder.binding.getRoot().setText(activity.getResources().getString(R.string.BottomSheetScheduleFilterHint) + " " + model.getRoomManager().getId());
        else
            holder.binding.getRoot().setText(activity.getResources().getString(R.string.BottomSheetScheduleFilterHint) + " " + "نامعلوم");
    }

    private void setData(SheetFilterHolder holder, String item) {
        holder.binding.getRoot().setText(item);
    }

}
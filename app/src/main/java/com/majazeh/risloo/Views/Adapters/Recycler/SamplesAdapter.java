package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSampleBinding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.SamplesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> samples;

    public SamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SamplesHolder(SingleItemSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SamplesHolder holder, int i) {
        SampleModel sample = (SampleModel) samples.get(i);

        detector(holder);

        listener(holder, sample);

        setData(holder, sample);
    }

    @Override
    public int getItemCount() {
        if (this.samples != null)
            return samples.size();
        else
            return 0;
    }

    public void setSamples(ArrayList<TypeModel> samples) {
        if (this.samples == null)
            this.samples = samples;
        else
            this.samples.addAll(samples);
        notifyDataSetChanged();
    }

    public void clearSamples() {
        if (this.samples != null) {
            this.samples.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(SamplesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(SamplesHolder holder, SampleModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sampleFragment, getExtras(model))).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> IntentManager.test(activity, model.getSampleId())).widget(holder.binding.statusTextView);
    }

    private void setData(SamplesHolder holder, SampleModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getSampleId());
        holder.binding.nameTextView.setText(model.getSampleScaleTitle());

        if (!model.getSampleEdition().equals(""))
            holder.binding.editionTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getSampleVersion());

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null && model.getSampleRoom().getRoomManager().getName() != null) {
            holder.binding.roomTextView.setText(model.getSampleRoom().getRoomManager().getName());
        }

        if (model.getSampleCase() != null && model.getSampleCase().getCaseId() != null) {
            holder.binding.caseTextView.setText(model.getSampleCase().getCaseId());
        }

        if (model.getClient() != null && model.getClient().getName() != null) {
            holder.binding.referenceTextView.setText(model.getClient().getName());
        }

        setStatus(holder, model.getSampleStatus());
    }

    private void setStatus(SamplesHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSampleStatus2(activity, "fa", status));

        switch (status) {
            case "seald":
            case "open":
                holder.binding.statusTextView.setEnabled(true);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
                else
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
                break;
            default:
                holder.binding.statusTextView.setEnabled(false);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
        }
    }

    private Bundle getExtras(SampleModel model) {
        Bundle extras = new Bundle();

        extras.putString("id", model.getSampleId());

        extras.putString("scale_id", model.getSampleScaleId());
        extras.putString("scale_title", model.getSampleScaleTitle());

        extras.putString("edition", model.getSampleEdition());
        extras.putString("version", String.valueOf(model.getSampleVersion()));

        if (model.getSampleRoom() != null) {
            extras.putString("room_id", model.getSampleRoom().getRoomId());

            if (model.getSampleRoom().getRoomManager() != null) {
                extras.putString("room_name", model.getSampleRoom().getRoomManager().getName());

                if (model.getSampleRoom().getRoomCenter() != null) {
                    extras.putString("center_id", model.getSampleRoom().getRoomCenter().getCenterId());

                    if (model.getSampleRoom().getRoomCenter().getManager() != null)
                        extras.putString("center_name", model.getSampleRoom().getRoomCenter().getManager().getName());
                }
            }
        }

        extras.putString("case_id", model.getCaseId());
        extras.putString("session_id", model.getSessionId());

        if (model.getClient() != null)
            extras.putString("client", model.getClient().getName());

        extras.putString("status", model.getSampleStatus());

        return extras;
    }

    public class SamplesHolder extends RecyclerView.ViewHolder {

        private SingleItemSampleBinding binding;

        public SamplesHolder(SingleItemSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
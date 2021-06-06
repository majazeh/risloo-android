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
import com.majazeh.risloo.databinding.SingleItemSample4Binding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class Samples4Adapter extends RecyclerView.Adapter<Samples4Adapter.Samples4Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> samples;

    public Samples4Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Samples4Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Samples4Holder(SingleItemSample4Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Samples4Holder holder, int i) {
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

    private void detector(Samples4Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Samples4Holder holder, SampleModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sampleFragment, getExtras(model))).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> IntentManager.test(activity, model.getSampleId())).widget(holder.binding.statusTextView);
    }

    private void setData(Samples4Holder holder, SampleModel model) {
        try {
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

            if (model.getSampleCase() != null && model.getSampleCase().getClients() != null && !model.getSampleCase().getClients().data().isEmpty()) {
                holder.binding.referenceTextView.setText(model.getSampleCase().getClients().data().get(0).object.getString("name")); // TODO : name return empty
            }

            setStatus(holder, model.getSampleStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStatus(Samples4Holder holder, String status) {
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
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return extras;
    }

    public class Samples4Holder extends RecyclerView.ViewHolder {

        private SingleItemSample4Binding binding;

        public Samples4Holder(SingleItemSample4Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
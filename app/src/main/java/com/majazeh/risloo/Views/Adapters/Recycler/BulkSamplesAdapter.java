package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemBulkSampleBinding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class BulkSamplesAdapter extends RecyclerView.Adapter<BulkSamplesAdapter.BulkSamplesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> bulkSamples;

    public BulkSamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BulkSamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BulkSamplesHolder(SingleItemBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BulkSamplesHolder holder, int i) {
        SampleModel bulkSample = (SampleModel) bulkSamples.get(i);

        detector(holder);

        listener(holder, bulkSample);

        setData(holder, bulkSample);
    }

    @Override
    public int getItemCount() {
        if (this.bulkSamples != null)
            return bulkSamples.size();
        else
            return 0;
    }

    public void setBulkSamples(ArrayList<TypeModel> bulkSamples) {
        if (this.bulkSamples == null)
            this.bulkSamples = bulkSamples;
        else
            this.bulkSamples.addAll(bulkSamples);
        notifyDataSetChanged();
    }

    public void clearBulkSamples() {
        if (this.bulkSamples != null) {
            this.bulkSamples.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(BulkSamplesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(BulkSamplesHolder holder, SampleModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.bulkSampleFragment, getExtras(model))).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String menu = parent.getItemAtPosition(position).toString();

                switch (menu) {
                    case "لینک ثبت نام":
                        Log.e("menu", "link");
                        break;
                    case "کپی کردن لینک":
                        IntentManager.clipboard(activity, menu);
                        Toast.makeText(activity, activity.getResources().getString(R.string.AppLinkSaved), Toast.LENGTH_SHORT).show();
                        break;
                    case "ویرایش نمونه":
                        Log.e("menu", "edit");
                        break;
                }

                holder.binding.menuSpinner.setSelection(holder.binding.menuSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(BulkSamplesHolder holder, SampleModel model) {
        try {
            if (holder.getBindingAdapterPosition() == 0) {
                holder.binding.topView.setVisibility(View.GONE);
            } else {
                holder.binding.topView.setVisibility(View.VISIBLE);
            }

            holder.binding.serialTextView.setText(model.getSampleId());
            holder.binding.nameTextView.setText(model.getSampleTitle());
            holder.binding.caseTextView.setText(model.getCaseStatus());

            if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null && model.getSampleRoom().getRoomManager().getName() != null) {
                holder.binding.roomTextView.setText(model.getSampleRoom().getRoomManager().getName());
            }

            if (model.getSampleRoom() != null && model.getSampleRoom().getRoomCenter() != null && model.getSampleRoom().getRoomCenter().getDetail() != null && !model.getSampleRoom().getRoomCenter().getDetail().getString("title").equals("")) {
                holder.binding.centerTextView.setText(model.getSampleRoom().getRoomCenter().getDetail().getString("title"));
            }

            holder.binding.statusTextView.setText(SelectionManager.getSampleStatus(activity, "fa", model.getSampleStatus()));
            holder.binding.referenceTextView.setText(model.getMembersCount() + " / " + model.getJoined());

            InitManager.customizedSpinner(activity, holder.binding.menuSpinner, R.array.BulkSamplesTasks, "bulkSamples");
        } catch (JSONException e) {
            e.printStackTrace();
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

    public class BulkSamplesHolder extends RecyclerView.ViewHolder {

        private SingleItemBulkSampleBinding binding;

        public BulkSamplesHolder(SingleItemBulkSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
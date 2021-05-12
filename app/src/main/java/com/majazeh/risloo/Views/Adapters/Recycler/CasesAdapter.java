package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCaseBinding;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.CasesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Case> cases;

    public CasesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CasesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CasesHolder(SingleItemCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CasesHolder holder, int i) {
//        Cases case = cases.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return cases.size();
        return 4;
    }

//    public void setCases(ArrayList<Case> cases) {
//        this.cases = cases;
//        notifyDataSetChanged();
//    }

    private void detector(CasesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(CasesHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.caseFragment)).widget(holder.binding.getRoot());
    }

    private void setData(CasesHolder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("SE9666669");
        holder.binding.roomTextView.setText("اتاق درمان محمدعلی نخلی");
        holder.binding.centerTextView.setText("مرکز مشاوره ریسلو");
        holder.binding.referenceTextView.setText("محمد نخلی");
    }

    public class CasesHolder extends RecyclerView.ViewHolder {

        private SingleItemCaseBinding binding;

        public CasesHolder(SingleItemCaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
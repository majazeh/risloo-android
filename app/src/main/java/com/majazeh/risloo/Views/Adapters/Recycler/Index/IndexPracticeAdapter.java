package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderPracticeHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexPracticeHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexPracticeBinding;
import com.majazeh.risloo.databinding.SingleItemIndexPracticeBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexPracticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexPracticeAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderPracticeHolder(HeaderItemIndexPracticeBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexPracticeHolder(SingleItemIndexPracticeBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof IndexPracticeHolder) {
//            PracticeModel model = (PracticeModel) items.get(i - 1);

            listener((IndexPracticeHolder) holder);

            setData((IndexPracticeHolder) holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        userSelect = false;

        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexPracticeHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "دریافت پیوست":
                            // TODO : Place Code Here
                            break;
                        case "ارسال تکلیف":
                            // TODO : Place Code Here
                            break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(IndexPracticeHolder holder) {
        holder.binding.serialTextView.setText("P966663D");
        holder.binding.nameTextView.setText("تمرین abc");
        holder.binding.descriptionTextView.setText("چرا عاقل کند کاری که بازآید به کنعان غم مخور");

        setMenu(holder);
    }

    private void setMenu(IndexPracticeHolder holder) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.PracticesAdapterAttachment));
        items.add(activity.getResources().getString(R.string.PracticesAdapterHomeWork));

        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

}
package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderTransactionHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableTransactionHolder;
import com.majazeh.risloo.databinding.HeaderItemTableTransactionBinding;
import com.majazeh.risloo.databinding.SingleItemTableTransactionBinding;
import com.mre.ligheh.Model.TypeModel.TransactionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TableTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableTransactionAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderTransactionHolder(HeaderItemTableTransactionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableTransactionHolder(SingleItemTableTransactionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderTransactionHolder) {
            setWidget((HeaderTransactionHolder) holder);
        } else if (holder instanceof TableTransactionHolder) {
            TransactionModel model = (TransactionModel) items.get(i - 1);

            listener((TableTransactionHolder) holder, model);

            setData((TableTransactionHolder) holder, model);
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

    private void setWidget(HeaderTransactionHolder holder) {
        holder.binding.leftTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.TransactionAdapterLeft), 11, 18, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(TableTransactionHolder holder, TransactionModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToBillFragment(model.getBilling());
        }).widget(holder.binding.billImageView);
    }

    private void setData(TableTransactionHolder holder, TransactionModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getCreatedAt()), " "));

        holder.binding.creditorTextView.setText(StringManager.separate(model.getCredit()));
        holder.binding.debtorTextView.setText(StringManager.separate(model.getDebt()));

        if (model.getBalance().equals("0")) {
            holder.binding.leftTextView.setText(String.valueOf(model.getBalance()));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.coolGray700));
        } else if (String.valueOf(model.getBalance()).contains("-")) {
            holder.binding.leftTextView.setText(StringManager.minusSeparate(String.valueOf(model.getBalance())));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.red600));
        } else {
            holder.binding.leftTextView.setText(StringManager.separate(String.valueOf(model.getBalance())));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.emerald600));
        }
    }

}
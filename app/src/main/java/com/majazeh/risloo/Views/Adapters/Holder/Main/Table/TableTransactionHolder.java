package com.majazeh.risloo.views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableTransactionBinding;

public class TableTransactionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableTransactionBinding binding;

    public TableTransactionHolder(SingleItemTableTransactionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
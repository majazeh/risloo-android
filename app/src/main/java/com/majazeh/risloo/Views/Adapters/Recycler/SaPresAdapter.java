package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.SaPresHolder;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragment;
import com.majazeh.risloo.databinding.SingleItemSaBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SaPresAdapter extends RecyclerView.Adapter<SaPresHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private Handler handler;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false, editable = false;

    public SaPresAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SaPresHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SaPresHolder(SingleItemSaBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaPresHolder holder, int i) {
        PrerequisitesModel model = (PrerequisitesModel) items.get(i);

        intializer();

        listener(holder, i);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> prerequisites) {
        if (this.items == null)
            this.items = prerequisites;
        else
            this.items.addAll(prerequisites);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();

        handler = new Handler();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SaPresHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable)
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                    ((TestActivity) activity).validatoon.select(activity, holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.inputEditText.hasFocus()) {
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> {
                        if (current instanceof SampleFragment)
                            ((SampleFragment) current).sendPre(item + 1, holder.binding.inputEditText.getText().toString());
                    }, 750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    if (current instanceof SampleFragment)
                        ((SampleFragment) current).sendPre(item + 1, String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(SaPresHolder holder, PrerequisitesModel model) {
        holder.binding.headerTextView.setText((holder.getBindingAdapterPosition() + 1) + " - " + model.getText());

        setType(holder, model);

        setClickable(holder);
    }

    private void setType(SaPresHolder holder, PrerequisitesModel model) {
        try {
            switch (model.getAnswer().getString("type")) {
                case "text":
                    holder.binding.selectGroup.setVisibility(View.GONE);

                    holder.binding.inputEditText.setVisibility(View.VISIBLE);
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "number":
                    holder.binding.selectGroup.setVisibility(View.GONE);

                    holder.binding.inputEditText.setVisibility(View.VISIBLE);
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "select":
                    holder.binding.inputEditText.setVisibility(View.GONE);

                    holder.binding.selectGroup.setVisibility(View.VISIBLE);
                    setSpinner(holder, model);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUser_answered()) - 1);
                    else
                        holder.binding.selectSpinner.setSelection(holder.binding.selectSpinner.getCount());

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setClickable(SaPresHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectSpinner.setEnabled(true);

            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectSpinner.setEnabled(false);

            holder.binding.getRoot().setAlpha((float) 0.5);
        }
    }

    private void setSpinner(SaPresHolder holder, PrerequisitesModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();

            for (int i = 0; i < model.getAnswer().getJSONArray("options").length(); i++) {
                options.add(model.getAnswer().getJSONArray("options").get(i).toString());
            }

            options.add("");

            InitManager.normal10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
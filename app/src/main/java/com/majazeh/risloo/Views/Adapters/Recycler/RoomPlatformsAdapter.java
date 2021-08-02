package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.RoomPlatformsFragment;
import com.majazeh.risloo.databinding.SingleItemRoomPlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomPlatformsAdapter extends RecyclerView.Adapter<RoomPlatformsAdapter.RoomPlatformsHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private Handler handler;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public RoomPlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomPlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomPlatformsHolder(SingleItemRoomPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomPlatformsHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

        initializer(holder);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
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

    private void initializer(RoomPlatformsHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.normalAdapterSpinner(activity, holder.binding.levelSpinner, R.array.PlatformLevels);
    }

    private void detector(RoomPlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(RoomPlatformsHolder holder, SessionPlatformModel model) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.identifierEditText.hasFocus())
                ((MainActivity) activity).controlEditText.select(activity, holder.binding.identifierEditText);
            return false;
        });

        holder.binding.identifierEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.identifierEditText.hasFocus()) {
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> {
                        if (model.isAvailable() && model.isPin()) {
                            String identifier = holder.binding.identifierEditText.getText().toString().trim();

                            if (!identifier.equals(""))
                                doWork(holder, model, identifier, "identifier");
                        }
                    },750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.centerCheckBox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.centerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "pin");
                else
                    doWork(holder, model, "0", "pin");

                userSelect = false;
            }
        });

        holder.binding.availableSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked) {
                    doWork(holder, model, "1", "available");

                    holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
                    holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
                    holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
                } else {
                    doWork(holder, model, "0", "available");

                    holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
                    holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
                    holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
                }

                setClickable(holder, isChecked);

                userSelect = false;
            }
        });

        holder.binding.levelSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    doWork(holder, model, SelectionManager.getPlatformLevel(activity, "en", pos), "selected_level");

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(RoomPlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType()));

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));
        holder.binding.identifierEditText.setText(model.getIdentifier());

        setAvailable(holder, model);

        setPinned(holder, model);

        setLevel(holder, model);
    }

    private void setAvailable(RoomPlatformsHolder holder, SessionPlatformModel model) {
        if (model.isAvailable()) {
            holder.binding.availableSwitchCompat.setChecked(true);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
        } else {
            holder.binding.availableSwitchCompat.setChecked(false);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        }

        setClickable(holder, model.isAvailable());
    }

    private void setPinned(RoomPlatformsHolder holder, SessionPlatformModel model) {
        if (model.isPin())
            holder.binding.centerCheckBox.setChecked(true);
        else
            holder.binding.centerCheckBox.setChecked(false);
    }

    private void setLevel(RoomPlatformsHolder holder, SessionPlatformModel model) {
        String level = SelectionManager.getPlatformLevel(activity, "fa", String.valueOf(model.getSelected_level()));
        for (int i = 0; i < holder.binding.levelSpinner.getCount(); i++) {
            if (holder.binding.levelSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(level)) {
                holder.binding.levelSpinner.setSelection(i);
            }
        }
    }

    private void setClickable(RoomPlatformsHolder holder, boolean isAvailable) {
        if (isAvailable) {
            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.centerCheckBox.setEnabled(true);
            holder.binding.levelSpinner.setEnabled(true);

            holder.binding.identifierEditText.setAlpha((float) 1);
            holder.binding.centerCheckBox.setAlpha((float) 1);
            holder.binding.levelSpinner.setAlpha((float) 1);
            holder.binding.angleImageView.setAlpha((float) 1);
        } else {
            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.centerCheckBox.setEnabled(false);
            holder.binding.levelSpinner.setEnabled(false);

            holder.binding.identifierEditText.setAlpha((float) 0.6);
            holder.binding.centerCheckBox.setAlpha((float) 0.6);
            holder.binding.levelSpinner.setAlpha((float) 0.6);
            holder.binding.angleImageView.setAlpha((float) 0.6);
        }
    }

    private void doWork(RoomPlatformsHolder holder, SessionPlatformModel model, String value, String method) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        if (current instanceof RoomPlatformsFragment)
            data.put("id", ((RoomPlatformsFragment) current).roomId);

        data.put("platformId", model.getId());
        data.put(method, value);

        Room.editRoomSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    ((MainActivity) activity).loadingDialog.dismiss();
                    ToastManager.showToast(activity, activity.getResources().getString(R.string.ToastChangesSaved));
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    if (method.equals("switch")) {
                        if (value.equals("1")) {
                            holder.binding.availableSwitchCompat.setChecked(false);

                            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
                            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
                            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

                            setClickable(holder, false);
                        } else if (value.equals("0")) {
                            holder.binding.availableSwitchCompat.setChecked(true);

                            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
                            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
                            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);

                            setClickable(holder, true);
                        }
                    } else if (method.equals("checkbox")) {
                        if (value.equals("1"))
                            holder.binding.centerCheckBox.setChecked(false);
                        else if (value.equals("0"))
                            holder.binding.centerCheckBox.setChecked(true);
                    }
                });
            }
        });
    }

    public class RoomPlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomPlatformBinding binding;

        public RoomPlatformsHolder(SingleItemRoomPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
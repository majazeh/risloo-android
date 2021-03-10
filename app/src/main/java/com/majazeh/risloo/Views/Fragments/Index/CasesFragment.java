package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.CasesAdapter;

public class CasesFragment extends Fragment {

    // Adapters
    private CasesAdapter casesAdapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private TextView casesTitleTextView, casesCountTextView;
    private EditText casesSearchEditText;
    private ProgressBar casesSearchProgressBar;
    private ImageView casesAddImageView;
    private ShimmerFrameLayout casesShimmerLayout;
    private View casesShimmerTopView;
    private ConstraintLayout casesHeaderLayout, casesConstraintLayout;
    private RecyclerView casesRecyclerView;
    private TextView casesEmptyTextView;
    private ProgressBar casesProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cases, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            casesShimmerLayout.setVisibility(View.GONE);
            casesHeaderLayout.setVisibility(View.VISIBLE);
            casesConstraintLayout.setVisibility(View.VISIBLE);
        }, 5000);

        return view;
    }

    private void initializer(View view) {
        casesAdapter = new CasesAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        casesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        casesTitleTextView.setText(getResources().getString(R.string.CasesFragmentTitle));
        casesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        casesSearchEditText = view.findViewById(R.id.component_input_search_editText);

        casesSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        casesAddImageView = view.findViewById(R.id.fragment_cases_add_imageView);
        casesAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(casesAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));

        casesShimmerLayout = view.findViewById(R.id.fragment_cases_index_shimmerLayout);
        casesShimmerTopView = view.findViewById(R.id.shimmer_item_case_top_view);
        casesShimmerTopView.setVisibility(View.GONE);

        casesHeaderLayout = view.findViewById(R.id.fragment_cases_index_header_constraintLayout);
        casesConstraintLayout = view.findViewById(R.id.fragment_cases_index_constraintLayout);

        casesRecyclerView = view.findViewById(R.id.component_index_case_recyclerView);
        casesRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", 0,0, 0, 0));
        casesRecyclerView.setLayoutManager(layoutManager);
        casesRecyclerView.setHasFixedSize(true);

        casesEmptyTextView = view.findViewById(R.id.component_index_case_textView);

        casesProgressBar = view.findViewById(R.id.component_index_case_progressBar);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            casesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            casesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        casesSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!casesSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), casesSearchEditText);
                }
            }
            return false;
        });

        casesSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (casesSearchEditText.length() != 0) {
//                        getData("getCases", "", casesSearchEditText.getText().toString().trim());
//                    } else {
//                        casesRecyclerView.setAdapter(null);
//
//                        if (casesEmptyTextView.getVisibility() == View.VISIBLE) {
//                            casesEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        casesAddImageView.setOnClickListener(v -> {
            casesAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> casesAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createCaseFragment);
        });
    }

    private void setData() {
//        casesAdapter.setCenter(null);
        casesRecyclerView.setAdapter(casesAdapter);

        String dataSize = "15";
        casesCountTextView.setText("(" + dataSize + ")");
    }

}
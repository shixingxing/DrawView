package com.byox.drawviewproject.dialogs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.byox.drawviewproject.R;
import com.byox.drawviewproject.adapters.PhotoAdapter;
import com.byox.drawviewproject.behaviors.CustomBottomSheetBehavior;
import com.byox.drawviewproject.listeners.OnClickListener;
import com.byox.drawviewproject.utils.FileUtils;
import com.byox.drawviewproject.utils.LayoutUtils;
import com.byox.drawviewproject.utils.RxUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.ObservableEmitter;

/**
 * Created by Ing. Oscar G. Medina Cruz on 21/12/2016.
 */

public class SelectImageDialog extends BottomSheetDialogFragment {

    public static final String SELEC_IMAGE_DIALOG = "SELECT_IMAGE_DIALOG";

    // LISTENER
    private OnImageSelectListener onImageSelectListener;

    // VIEWS
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private PhotoAdapter photoAdapter;
    private LinearLayout mNoImages;

    // VARS
    private CustomBottomSheetBehavior mCustomBottomSheetBehavior;
    private int mRecyclerViewScrollAmount = 0;

    public SelectImageDialog() {
    }

    public static SelectImageDialog newInstance() {
        return new SelectImageDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_select_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_select_image);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_select_image);
        mNoImages = (LinearLayout) view.findViewById(R.id.ll_no_images);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(LayoutUtils.GetFlowLayoutManager(getContext()));

        photoAdapter =
                new PhotoAdapter(imageList,
                        new OnClickListener() {
                            @Override
                            public void onItemClickListener(View view, Object contentObject, int position) {
                                ByteArrayOutputStream byteArrayOutputStream =
                                        new ByteArrayOutputStream();
                                BitmapFactory.decodeFile(((File) contentObject).getPath())
                                        .compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                                if (onImageSelectListener != null) {
                                    onImageSelectListener.onSelectImage((File) contentObject);
                                    onImageSelectListener.onSelectImage(byteArrayOutputStream.toByteArray());
                                }
                                dismiss();
                            }
                        });
        mRecyclerView.setAdapter(photoAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        refresh();

        setListeners();


        mCustomBottomSheetBehavior = new CustomBottomSheetBehavior();
        mCustomBottomSheetBehavior.setLocked(false);
        mCustomBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mCustomBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        layoutParams.setBehavior(mCustomBottomSheetBehavior);
    }

    private void setListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mRecyclerViewScrollAmount += dy;

                if (mRecyclerViewScrollAmount > 0) {
                    mCustomBottomSheetBehavior.setLocked(true);
                } else {
                    mCustomBottomSheetBehavior.setLocked(false);
                    mRecyclerViewScrollAmount = 0;
                }
            }
        });
    }

    List<File> imageList;

    private void refresh() {

        mNoImages.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setRefreshing(true);

        RxUtil.io(new RxUtil.RxTask<Object>() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object value) {
                imageList = FileUtils.GetSortedFilesByDate(
                        FileUtils.GetImageList(Environment.getExternalStorageDirectory()));
                return new Object();
            }

            @Override
            public void onNext(Object value) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);

                photoAdapter.setFileList(imageList);
                photoAdapter.notifyDataSetChanged();
                if (imageList.size() == 0) {
                    mNoImages.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // INTERFACES
    public void setOnImageSelectListener(OnImageSelectListener onImageSelectListener) {
        this.onImageSelectListener = onImageSelectListener;
    }

    public interface OnImageSelectListener {
        void onSelectImage(File imageFile);

        void onSelectImage(byte[] imageBytes);
    }
}

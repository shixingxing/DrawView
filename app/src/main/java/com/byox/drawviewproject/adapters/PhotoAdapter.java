package com.byox.drawviewproject.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.byox.drawviewproject.R;
import com.byox.drawviewproject.listeners.OnClickListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 05/09/2016.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    // INTERFACE
    private OnClickListener mOnClickListener;

    // VARS
    private List<File> mFileList;

    public void setFileList(List<File> fileList) {
        this.mFileList = fileList;
    }

    public PhotoAdapter(List<File> fileList, OnClickListener onClickListener) {
        mFileList = fileList;
        mOnClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cv_photo_item);
            imageView = (ImageView) v.findViewById(R.id.iv_photo_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.get().load(mFileList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.color.colorBlackSemitrans).fit().centerCrop().into(holder.imageView);

        if (mOnClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onItemClickListener(view, mFileList.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mFileList == null) {
            return 0;
        } else {
            return mFileList.size();
        }
    }
}

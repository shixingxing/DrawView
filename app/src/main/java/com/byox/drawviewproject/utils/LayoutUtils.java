package com.byox.drawviewproject.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.byox.drawviewproject.R;

/**
 * Created by IngMedina on 28/03/2017.
 */

public class LayoutUtils {
    private static final int PHONE_PORTRAIT_COLUMNS_LAYOUT_COLUMNS = 3;
    private static final int PHONE_LANDSCAPE_COLUMNS_LAYOUT_COLUMNS = 5;

    private static final Object[] PORTRAIT_FLOW_LAYOUT_PATTERN =
            new Object[]{new Pair<>(3, 2), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(2, 2),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(3, 3), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 2), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(2, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 2),
                    new Pair<>(2, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1)};
    private static final Object[] LANDSCAPE_FLOW_LAYOUT_PATTERN =
            new Object[]{new Pair<>(3, 3), new Pair<>(2, 2), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(2, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(2, 2), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(3, 3), new Pair<>(1, 1), new Pair<>(1, 2),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(2, 2),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(2, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1),
                    new Pair<>(1, 1), new Pair<>(1, 1), new Pair<>(1, 1)};
    private static int mFlowLayoutPositionMultiplier = 0;


    public static RecyclerView.LayoutManager GetFlowLayoutManager(Context context) {
        mFlowLayoutPositionMultiplier = 0;
        RecyclerView.LayoutManager layoutManager = null;
        if (!context.getResources().getBoolean(R.bool.isTablet)) {
            switch (context.getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    layoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
                    break;

                default:
                    break;
            }
        }

        return layoutManager;
    }
}

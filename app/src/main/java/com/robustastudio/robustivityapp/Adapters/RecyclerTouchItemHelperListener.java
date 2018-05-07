package com.robustastudio.robustivityapp.Adapters;

import android.support.v7.widget.RecyclerView;

/**
 * Created by hp on 28/04/2018.
 */

public interface RecyclerTouchItemHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

}

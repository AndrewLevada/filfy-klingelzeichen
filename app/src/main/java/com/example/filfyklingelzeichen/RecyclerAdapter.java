package com.example.filfyklingelzeichen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is an extension of Recycler view adapter.
 * It simplifies work with it by implementing inflation
 * of items and there storing. You should only write how
 * to fill item with content.
 * !!! You must set itemLayout variable to your items resource int !!!
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BasicViewHolder> {
    @LayoutRes
    int itemLayout;

    private Context context;

    public RecyclerAdapter(RecyclerView recyclerView) {
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = SimpleInflater.inflate(parent, itemLayout, false);
        return new BasicViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final BasicViewHolder holder, final int position) {
        if (context == null) return;

        ViewGroup item = (ViewGroup) holder.itemView;
        fillItemWithData(item, position);
    }

    /**
     * Fill recycler item with your content here.
     * @param item Item to be filled.
     * @param position Index of item in recycler view.
     */
    abstract void fillItemWithData(ViewGroup item, int position);

    static class BasicViewHolder extends RecyclerView.ViewHolder {
        BasicViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnRecyclerItemClick {
        void onClick(int index);
    }

    void fadeAddAnimate(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        animation.setStartOffset(20 * position);
        view.startAnimation(animation);
    }
}

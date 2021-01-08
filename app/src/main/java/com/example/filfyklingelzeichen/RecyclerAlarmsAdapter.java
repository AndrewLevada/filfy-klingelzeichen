package com.example.filfyklingelzeichen;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAlarmsAdapter extends RecyclerAdapter {
    private final Context context;
    private List<Alarm> alarms;

    public RecyclerAlarmsAdapter(RecyclerView recyclerView, List<Alarm> alarms, Toolbox.CallbackOne<Integer> onclick) {
        super(recyclerView);
        this.alarms = alarms;
        itemLayout = R.layout.recyclable_alarm_template;

        context = recyclerView.getContext();
    }

    @Override
    void fillItemWithData(ViewGroup item, int position) {
        Alarm alarm = alarms.get(position);

//        ((TextView) item.findViewById(R.id.recycler_number)).setText(number.getPhone());
//        ((TextView) item.findViewById(R.id.recycler_label)).setText(number.getLabel());

        // Hide divider on last element
        // if (position == getItemCount() - 1)
        //     item.findViewById(R.id.recycler_divider).setVisibility(View.GONE);

        fadeAddAnimate(item, position);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }
}


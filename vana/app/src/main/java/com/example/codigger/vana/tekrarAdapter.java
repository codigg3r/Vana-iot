package com.example.codigger.vana;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class tekrarAdapter extends RecyclerView.Adapter<tekrarAdapter.tekrarViewHolder>{
    public ArrayList<tekrar> mTekrar;
    public static class tekrarViewHolder extends RecyclerView.ViewHolder{
        public TextView startTime;
        public TextView endTime;
        public TextView days;
        public Switch tg;

      public tekrarViewHolder(@NonNull View itemView) {
          super(itemView);

          startTime = itemView.findViewById(R.id.l_startTime);
          endTime = itemView.findViewById(R.id.l_endTime);
          days = itemView.findViewById(R.id.l_days);
          tg = itemView.findViewById(R.id.l_toggle);
      }

  }

    @NonNull
    @Override
    public tekrarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layour,viewGroup,false);
        tekrarViewHolder tvh = new tekrarViewHolder(v);
        return tvh;

    }

    public tekrarAdapter(ArrayList<tekrar> tekrars) {
        mTekrar = tekrars;
    }

    @Override
    public void onBindViewHolder(tekrarViewHolder tekrarViewHolder, int i) {
        tekrar cTekrar = mTekrar.get(i);
        tekrarViewHolder.startTime.setText(cTekrar.getStartTime());
        tekrarViewHolder.endTime.setText(cTekrar.getEndTime());
        tekrarViewHolder.days.setText(cTekrar.days);
        tekrarViewHolder.tg.setChecked(cTekrar.isTg());
    }

    @Override
    public int getItemCount() {
        return mTekrar.size();
    }
}

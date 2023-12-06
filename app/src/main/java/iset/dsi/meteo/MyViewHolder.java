package iset.dsi.meteo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textDate ;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textDate = itemView.findViewById(R.id.date_recycler);

    }
    public void bind(ForecastItem forecastItem){
        textDate.setText(forecastItem.getDtTxt());

    }
}

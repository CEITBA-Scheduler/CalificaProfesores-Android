package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

public class NoInfoViewHolder extends RecyclerView.ViewHolder {
    View view;
    public NoInfoViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String contentText, String buttonText, View.OnClickListener clickListener){
        TextView infoText = view.findViewById(R.id.NoInfoText);
        infoText.setText(contentText);

        Button button = view.findViewById(R.id.ButtonBeFirst);
        button.setOnClickListener(clickListener);

        button.setText(buttonText);
    }
}

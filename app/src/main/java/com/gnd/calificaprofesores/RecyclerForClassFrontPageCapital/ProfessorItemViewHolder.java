package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.gnd.calificaprofesores.R;


/** layout asociado: layout_item_professor.xml **/

public class ProfessorItemViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    public ProfessorItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(
            String ProfessorName,
            Float Conocimiento,
            Float Clases,
            Float Amabilidad,
            View.OnClickListener listener){

        RoundCornerProgressBar conocimiento = mView.findViewById(R.id.progress_2);
        RoundCornerProgressBar clases = mView.findViewById(R.id.progress_1);
        RoundCornerProgressBar amabilidad = mView.findViewById(R.id.progress_3);

        conocimiento.setMax(100);
        conocimiento.setProgress(Math.round(Conocimiento*20f));    // * 100 / 5

        clases.setMax(100);
        clases.setProgress(Math.round(Clases*20f));

        amabilidad.setMax(100);
        amabilidad.setProgress(Math.round(Amabilidad*20f));

        float average = (Conocimiento+Clases+Amabilidad)/3;

        RatingBar score = mView.findViewById(R.id.RatingBar);
        score.setRating(average);

        TextView titulo = mView.findViewById(R.id.ProfessorName);
        titulo.setText(ProfessorName);

        mView.findViewById(R.id.Content)
                .setOnClickListener(listener);
    }

}

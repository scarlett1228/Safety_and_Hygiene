package my.edu.utar.mobileappassignment2.fyp1.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import my.edu.utar.mobileappassignment2.fyp1.R;

public class Adapter extends RecyclerView.Adapter<Adapter.HolderAddress>{

    private Context context;
    private ArrayList<ResultHelperClass> resultList;

    public Adapter(Context context, ArrayList<ResultHelperClass> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @NotNull
    @Override
    public HolderAddress onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_result,parent,false);
        return new HolderAddress(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderAddress holder, int position) {


        //get data
        ResultHelperClass modelresult=resultList.get(position);

        int finalcorrect = Integer.parseInt(modelresult.getCorrect())*10;

        String correct= modelresult.getCorrect();
        String wrong=modelresult.getWrong();
        String finalscore= String.valueOf(finalcorrect) ;
        String type = modelresult.getType();
        String timestamp = modelresult.getTimestamp();


        holder.typeTV.setText(type);
        holder.correctTV.setText(correct);
        holder.wrongTV.setText(wrong);
        holder.finalscoreTV.setText(finalscore);
        holder.dateTV.setText(timestamp);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class HolderAddress extends RecyclerView.ViewHolder{
        private TextView correctTV,wrongTV,finalscoreTV,typeTV,dateTV;

        public HolderAddress(@NonNull View itemView) {
            super(itemView);

            correctTV=itemView.findViewById(R.id.correct);
            wrongTV=itemView.findViewById(R.id.wrong);
            finalscoreTV=itemView.findViewById(R.id.fnlscore);
            typeTV=itemView.findViewById(R.id.type);
            dateTV=itemView.findViewById(R.id.dateTv);

        }
    }
}


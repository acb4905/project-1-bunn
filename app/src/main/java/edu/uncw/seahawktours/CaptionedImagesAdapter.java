package edu.uncw.seahawktours;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    private String[] captions;
    private int[] imageIds;
    private Box<Building> buildingBox;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //Define the view to be used for each data item
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView=v;
        }
    }


    public CaptionedImagesAdapter(Box<Building> buildingBox){
        this. buildingBox=buildingBox;
    }

    @Override
    public int getItemCount(){
        return (int) buildingBox.count();
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv=  (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final CardView cardView=holder.cardView;
        ImageView imageView=(ImageView)cardView.findViewById(R.id.info_image);

        List<Building> buildings=buildingBox.getAll();
        Building buildingClicked=buildings.get(position);

        //use database
        Drawable drawable =(Drawable) ContextCompat.getDrawable(cardView.getContext(), buildingClicked.getImageResourceId());

        //ContextCompat.getDrawable(cardView.getContext(),

        imageView.setImageDrawable(drawable);

        imageView.setContentDescription(buildingClicked.getName());
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);

        //use database
        textView.setText(buildingClicked.getName());
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(cardView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_BUILDINGID, position);
                cardView.getContext().startActivity(intent);
            }
        });
    }

}

//Page 547
package app.android.mmauri.laboratorio2.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.android.mmauri.laboratorio2.R;
import app.android.mmauri.laboratorio2.models.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marc on 10/10/17.
 */

public class FruitAdapter extends BaseAdapter {

    // Atributos de la clase
    private Context context;
    private int layout;
    private List<Fruit> fruits;

    public FruitAdapter(Context context, int layout, List<Fruit> fruits) {
        this.context = context;
        this.layout = layout;
        this.fruits = fruits;
    }

    @Override
    public int getCount() {
        return this.fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return this.fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // Si es la primera vez
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.layout, null);
            holder = new ViewHolder();
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.textViewOrigin = (TextView) convertView.findViewById(R.id.textViewOrigin);
            holder.imageFruit = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Rellenamos los campos del layout con la info de la fruta actual
        final Fruit currentFruit = this.fruits.get(position);
        holder.textViewName.setText(currentFruit.getName());
        holder.textViewOrigin.setText(currentFruit.getOrigin());
        holder.imageFruit.setImageResource(currentFruit.getIconId());

        return convertView;
    }

    private static class ViewHolder {
        private TextView textViewName;
        private TextView textViewOrigin;
        private ImageView imageFruit;
    }
}

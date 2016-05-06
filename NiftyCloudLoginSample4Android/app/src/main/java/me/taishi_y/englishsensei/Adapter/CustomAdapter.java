package me.taishi_y.englishsensei.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.taishi_y.englishsensei.Model.CustomData;
import me.taishi_y.englishsensei.R;

/**
 * Created by yamasakitaishi on 2016/04/18.
 */
public class CustomAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater layoutInflater_;

    public CustomAdapter(Context context, int textViewResourceId, List<CustomData> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 特定の行(position)のデータを得る
        CustomData item = (CustomData)getItem(position);

        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.row_phrase, null);
        }

        // CustomDataのデータをViewの各Widgetにセットする
        ImageView imageView;
        imageView = (ImageView)convertView.findViewById(R.id.iv_user);
        imageView.setImageBitmap(item.getImageData());

        TextView textView;
        textView = (TextView)convertView.findViewById(R.id.tv_username);
        textView.setText(item.getTextData());

        TextView textView2;
        textView2 = (TextView)convertView.findViewById(R.id.tv_japanese);
        textView2.setText(item.getJapaneseTextData());

        TextView textView3;
        textView3 = (TextView)convertView.findViewById(R.id.tv_phrase);
        textView3.setText(item.getEnglishTextData());

        return convertView;
    }
}
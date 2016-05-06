package me.taishi_y.englishsensei.Fragment;

/**
 * Created by yamasakitaishi on 2016/04/23.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBObjectService;

import java.util.ArrayList;
import java.util.List;

import me.taishi_y.englishsensei.Adapter.CustomAdapter;
import me.taishi_y.englishsensei.Model.CustomData;
import me.taishi_y.englishsensei.R;

public class FragmentTabLearn extends Fragment {

    List<NCMBObject> list;

    private static final String NCMB_CLASSNAME_PHRASES = "Phrase";

    protected ListView lvPhrase;
    protected List<CustomData> phrases;
    protected CustomAdapter customAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_tab_learn, container, false);

        View v = inflater.inflate(R.layout.fragment_tab_learn, container, false);

        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
//        mSwipeRefreshLayout.setColorScheme(R.color.red, R.color.green, R.color.blue, R.color.yellow);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.yellow);

        phrases = new ArrayList<CustomData>();
        customAdapter = new CustomAdapter(getContext(), 0, phrases);
        lvPhrase = (ListView)v.findViewById(R.id.listView1);
        lvPhrase.setAdapter(customAdapter);


        // load exists messages
        loadPhrases();


        lvPhrase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                alert();
            }
        });

        return v;
    }

    public void alert (){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
//        alertDlg.setTitle("緊急地震速報");
        alertDlg.setMessage("Do you want to be a Sensei?(teacher)");
        alertDlg.setPositiveButton(
                "Sure!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        
                    }
                });
        alertDlg.setNegativeButton(
                "not now...",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // 3秒待機
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 3000);
        }
    };


    protected void loadPhrases() {
        NCMBObjectService service = (NCMBObjectService)NCMB.factory(NCMB.ServiceType.OBJECT);
        list = null;
        try {
            list = service.searchObject(NCMB_CLASSNAME_PHRASES, null);
        } catch (NCMBException e) {
//                Toast.makeText(MainActivity.this,
//                        "Failed loading messages",
//                        Toast.LENGTH_LONG)
//                        .show();
            return;
        }

        List<CustomData> objects = new ArrayList<CustomData>();
        for (int i=list.size()-1; i>=0; i--){
            Bitmap image_taishi = BitmapFactory.decodeResource(getResources(), R.drawable.neko);
            CustomData item = new CustomData();
            item.setImagaData(image_taishi);
            item.setTextData(list.get(i).getString("userName"));
            item.setJapaneseTextData(list.get(i).getString("postJapanese"));
            item.setEnglishTextData(list.get(i).getString("postEnglish"));
            item.setObjectId(list.get(i).getString("objectId"));
//                item.setTimestamp(obj.getUpdateDate());
            objects.add(item);
        }


        // update messages
        phrases.clear();
        phrases.addAll(objects);
        customAdapter.notifyDataSetChanged();
    }
}
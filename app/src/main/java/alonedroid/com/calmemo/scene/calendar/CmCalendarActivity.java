package alonedroid.com.calmemo.scene.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.Calendar;

import alonedroid.com.calmemo.R;
import hugo.weaving.DebugLog;

public class CmCalendarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 前後何年を表示するのか、初期値を設定する
        CmCalendarAdapter adapter = new CmCalendarAdapter(getSupportFragmentManager());
        adapter.setDisplayYears(getResources().getInteger(R.integer.display_years));

        // Exception回避のために「ID」を明示的にセットする
        ViewPager pager = new ViewPager(this);
        pager.setId(R.id.id_fragment_container);
        pager.setAdapter(adapter);
        setContentView(pager);

        // 初期位置を中央に持っていき、前後スワイプ可能にする
        pager.setCurrentItem(adapter.calculateFlatPosition());
    }
}

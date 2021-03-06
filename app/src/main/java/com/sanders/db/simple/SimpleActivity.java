package com.sanders.db.simple;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sanders.db.DBProxy;
import com.sanders.db.Table1;
import com.sanders.db.TableBean;
import com.sanders.db.Table_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sanders on 15/3/27.
 */
public class SimpleActivity extends Activity implements View.OnClickListener {

    public DBProxy db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setOnClickListener(this);
        }

        db = new DBProxy.DBBuilder()
                .builderDbName("test")
                .builderDbVersion(1)
                .builderTable(UserTable.class).builderTable(Table1.class).builderTable(Table_2.class).builderTable(TableBean.class)
                .build(this);
    }

    long id = 1;

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_insert:
                UserTable table = new UserTable("username", "password", 100);
                db.insert(table);
                break;
            case R.id.btn_insert_list:
                List<UserTable> list = new ArrayList<UserTable>();
                for (int i = 1; i <= 10; i++) {
                    list.add(new UserTable("username" + i, "password" + i, i));
                }
                db.insert(list);
                for (int i = 0; i < 10; i++) {
                    Log.e("ESA", "KEY_ID=" + list.get(i).getKeyId());
                }
                break;
            case R.id.btn_update:
                table = new UserTable("update", "update", 100);
                table.setKeyId(id);
                db.update(table);
                id++;
                break;
            case R.id.btn_update_list:
                list = new ArrayList<UserTable>();
                for (int i = 11; i <= 20; i++) {
                    table = new UserTable("UPDATE", "UPDATE", 0);
                    table.setKeyId(i);
                    list.add(table);
                }
                db.update(list);
                break;
            case R.id.btn_delete:
                db.delete(UserTable.class, "age=?", String.valueOf(12));
                break;
            case R.id.btn_query:
                Map<String, Object> map = db.query("SELECT * FROM user_table");
                Log.e("ESA", map.toString());
                break;
            case R.id.btn_query_list:
                list = db.queryList(UserTable.class, "age=?", String.valueOf(100));
                for (UserTable t : list) {
                    Log.e("ESA", "KEY_ID=" + t.getKeyId());
                }
                break;
        }
    }
}

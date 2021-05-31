package com.example.mydbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edDB, edTable;
    TextView textView;
    String tableName;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edDB=findViewById(R.id.edText1);
        edTable=findViewById(R.id.edText2);
        textView=findViewById(R.id.textView);

    }

    public void mOnClicked(View view){
        switch (view.getId()){
            case R.id.btnDB :
                createDB(edDB.getText().toString());
                break;
            case R.id.btnTable :
                createTable(edTable.getText().toString());
                break;
            case R.id.btnInsert :
                insertData();
                break;
            case R.id.btnSelect :
                selectData();
                break;
        }
    }

    private void createDB(String name){
        println("createDB 호출");
        database=openOrCreateDatabase(name,MODE_PRIVATE,null);
        println("데이터베이스 "+name+"생성됨");
    }
    private void createTable(String name){
        tableName=name;
        println("createTable 호출");
        if(database==null){
            println("데이터베이스를 먼저 생성하세요");
            return;
        }
        String sql="create table if not exists "+name+"("
                +" _id integer primary key autoincrement,"
                +" name text,"
                +" age integer,"
                +" mobile text)";
        database.execSQL(sql);
        println("테이블 "+name+"생성됨");
    }
    private void insertData(){
        println("insertData 호출");
        if(database==null){
            println("데이터베이스를 먼저 생성하세요");
            return;
        }
        if (tableName == null) {
            println("테이블을 먼저 생성하세요");
        }
        String sql="insert into "+tableName
                +" (name,age,mobile) "
                +"values('이순신',20,'010-1111-1111')";
        database.execSQL(sql);
        println("레코드 추가함");
    }
    private void selectData(){
        println("selectData 호출");
        if(database==null){
            println("데이터베이스를 먼저 생성하세요");
            return;
        }
        if (tableName == null) {
            println("테이블을 먼저 생성하세요");
        }
        String sql="select * from "+tableName;
        Cursor cursor=database.rawQuery(sql,null);
        for(int i=0;i<cursor.getCount();i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);
            println(id + ", " + name + ", " + age + ", " + mobile);
        }
    }
    private void println(String str){
        textView.append(str+"\n");
    }
}
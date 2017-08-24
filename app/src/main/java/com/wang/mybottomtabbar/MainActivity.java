package com.wang.mybottomtabbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wang.mybottomtabbar.Fragment.FragmentA;
import com.wang.mybottomtabbar.Fragment.FragmentB;
import com.wang.mybottomtabbar.Fragment.FragmentC;
import com.wang.mybottomtabbar.Fragment.FragmentD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((com.wang.supbottomtabbar.SupBottomTabBar) findViewById(R.id.BottomTabBar))
                .initFragmentorViewPager(getSupportFragmentManager(),R.id.fl_content)
                .addTabItem("草莓", getResources().getDrawable(R.drawable.icon01), getResources().getDrawable(R.drawable.icon_round), new FragmentA())
                .addTabItem("凤梨", getResources().getDrawable(R.drawable.icon02), getResources().getDrawable(R.drawable.icon_round), new FragmentB())
                .addTabItem("樱桃", getResources().getDrawable(R.drawable.icon03), getResources().getDrawable(R.drawable.icon_round), new FragmentC())
                .addTabItem("香蕉", getResources().getDrawable(R.drawable.icon04), getResources().getDrawable(R.drawable.icon_round), new FragmentD())
                .commit();
    }
}

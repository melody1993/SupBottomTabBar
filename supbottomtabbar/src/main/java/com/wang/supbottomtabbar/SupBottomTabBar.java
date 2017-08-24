package com.wang.supbottomtabbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class SupBottomTabBar extends LinearLayout implements View.OnClickListener{
    Context mContext;
    LinearLayout mLayout;
    //fragment集合
    List<Fragment> mFragment=new ArrayList<>();
    //选中图片集合
    private List<Drawable> selectdrawableList = new ArrayList<>();
    //未选中集合
    private List<Drawable> unselectdrawableList = new ArrayList<>();
    //tabId集合
    private List<String> tabIdList = new ArrayList<>();
    //FragmentManager绑定
    private FragmentManager mFragmentManager;
    private int mReplaceLayoutId;
    LinearLayout mTabContent;
    private int selectColor = Color.parseColor("#626262");
    private int unSelectColor = Color.parseColor("#F1453B");
    public SupBottomTabBar(Context context) {
        this(context,null);
    }
    public SupBottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_bottom, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        this.mTabContent = (LinearLayout)this.mLayout.findViewById(R.id.mTabContent);
        addView(mLayout);
    }
    /**
     * 初始化，主要是需要传入一个Fragment
     * <p>
     * 此方法必须在所有的方法之前调用
     *
     * @param FragmentManager
     * @return
     */
    public SupBottomTabBar initFragmentorViewPager(FragmentManager FragmentManager,int mReplaceLayoutId) {
        if (FragmentManager == null) {
            throw new IllegalStateException(
                    "Must input FragmentManager of initFragment");
        }
        this.mReplaceLayoutId=mReplaceLayoutId;
        mFragmentManager = FragmentManager;
        return this;
    }
    public SupBottomTabBar addTabItem(String name, Drawable selectdrawable, Drawable unselectdrawable, Fragment fragment){
        selectdrawableList.add(selectdrawable);
        unselectdrawableList.add(unselectdrawable);
        mFragment.add(fragment);
        LinearLayout TabItem = (LinearLayout) View.inflate(mContext, R.layout.item_tar, null);
        //设置TabItem标记
        TabItem.setTag(name);
        TabItem.setGravity(Gravity.CENTER);
        //添加标记至集合以作辨别
        tabIdList.add(String.valueOf(TabItem.getTag()));
        TabItem.setOnClickListener(this);
        ImageView tab_item_img = (ImageView)TabItem.findViewById(R.id.tab_img);
        TextView tab_item_tv = (TextView)TabItem.findViewById(R.id.tab_tv);
        tab_item_tv.setText(name);
        if(this.tabIdList.size() == 1) {
            tab_item_tv.setTextColor(this.selectColor);
            tab_item_img.setBackground(selectdrawable);
        } else {
            tab_item_tv.setTextColor(this.unSelectColor);
            tab_item_img.setBackground(unselectdrawable);
        }
        TabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
       mTabContent.addView(TabItem);

      return  this;
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < tabIdList.size(); i++) {
            if (tabIdList.get(i).equals(view.getTag())) {

                if (mFragmentManager != null) {
                    if (mReplaceLayoutId == 0) {
                        throw new IllegalStateException(
                                "Must input ReplaceLayout of mReplaceLayout");
                    }
                    relaceFrament(i);
                    changeTab(i);
                }
            }
        }
    }

    private void changeTab(int position) {
        if(position + 1 > this.mTabContent.getChildCount()) {
            throw new IndexOutOfBoundsException("onPageSelected:" + (position + 1) + "，of Max mTabContent ChildCount:" + this.mTabContent.getChildCount());
        } else {
            for(int i = 0; i < this.mTabContent.getChildCount(); ++i) {
                View TabItem = this.mTabContent.getChildAt(i);
                if(i == position) {
                    ((TextView)TabItem.findViewById(R.id.tab_tv)).setTextColor(this.selectColor);
                    if(!this.selectdrawableList.isEmpty()) {
                        TabItem.findViewById(R.id.tab_img).setBackground((Drawable)this.selectdrawableList.get(i));
                    }
                } else {
                    ((TextView)TabItem.findViewById(R.id.tab_tv)).setTextColor(this.unSelectColor);
                    if(!this.selectdrawableList.isEmpty()) {
                        TabItem.findViewById(R.id.tab_img).setBackground((Drawable)this.unselectdrawableList.get(i));
                    }
                }
            }

        }
    }
    private void relaceFrament(int i) {

        try {
            Fragment e = mFragment.get(i);
            FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
            for(int j=0;j<mFragment.size();j++){
                Fragment fragment=mFragment.get(j);
                if(j!=i&& fragment.isAdded()){
                    fragmentTransaction.hide(fragment);
                }
            }
            if(!e.isAdded()) {
                fragmentTransaction.add(this.mReplaceLayoutId, e);
            }else{
                fragmentTransaction.show(e);
            }

            fragmentTransaction.commit();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void commit() {
        if(this.mFragmentManager != null) {
            if(this.mReplaceLayoutId == 0) {
                throw new IllegalStateException("Must input ReplaceLayout of mReplaceLayout");
            }

            if(1 > this.mTabContent.getChildCount()) {
                throw new IndexOutOfBoundsException("onPageSelected:1，of Max mTabContent ChildCount:" + this.mTabContent.getChildCount());
            }

            this.relaceFrament(0);
        }

        if(this.tabIdList.isEmpty()) {
            throw new IllegalStateException("You Mast addTabItem before commit");
        }
    }
}

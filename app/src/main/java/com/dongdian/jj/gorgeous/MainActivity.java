package com.dongdian.jj.gorgeous;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dongdian.jj.gorgeous.category.CategoryFragment;
import com.dongdian.jj.gorgeous.home.HomeFragment;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.ib_home)
    RadioButton ibHome;
    @Bind(R.id.ib_category)
    RadioButton ibCategory;
    @Bind(R.id.ig_main)
    RadioGroup igMain;
    @Bind(R.id.iv_main)
    ImageView cvMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setWindowAnimation();
        initData();
    }

    private void setWindowAnimation() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Explode explode=new Explode();
            explode.setDuration(getResources().getInteger(R.integer.splash_fade_animation_time));
            getWindow().setEnterTransition(explode);
        }
    }

    private void initData() {
        repleasFragment(HomeFragment.class.getName());
    }

    @OnClick({R.id.ib_home, R.id.ib_category, R.id.iv_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_home:
                repleasFragment(HomeFragment.class.getName());
                break;
            case R.id.ib_category:
                repleasFragment(CategoryFragment.class.getName());
                break;
            case R.id.iv_main:
                break;
        }
    }

    //切换framgent
    private void repleasFragment(String fragmentClassName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment tempFramgnet = getSupportFragmentManager().findFragmentByTag(fragmentClassName);
        if (null == tempFramgnet) {
            try {
                tempFramgnet = (Fragment) Class.forName(fragmentClassName).newInstance();
                transaction.add(R.id.fl_main, tempFramgnet, fragmentClassName);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment.getTag().equals(fragmentClassName)) {
                    transaction.show(fragment);
                } else
                    transaction.hide(fragment);
            }
        }
        transaction.commit();
    }
}

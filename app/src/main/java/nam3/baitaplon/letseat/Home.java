package nam3.baitaplon.letseat;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;

public class Home extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Sử dụng FragmentTabHost để quản lý giao diện.
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //Chia các chức năng thành các Fragment: Home, Favorite, Add, User
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("", getResources().getDrawable(R.drawable.ic_home_black_24dp)), HomeFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator("", getResources().getDrawable(R.drawable.ic_favorite_black_24dp)), FavoriteFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("add").setIndicator("", getResources().getDrawable(R.drawable.ic_add_black_24dp)), AddFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("account").setIndicator("", getResources().getDrawable(R.drawable.ic_account_circle_black_24dp)), AccountFragment.class, null);
    }


}

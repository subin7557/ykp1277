package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    PlaceholderFragment p = null;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        p = PlaceholderFragment.create(position);
        return p;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "전체";
            case 1:
                return "연예";
            case 2:
                return "스포츠";
        }
        return null;
    }
    /*public void setStr(String s) {
        p.setStr(s);
    }*/
}

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
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "전체";
            case 1:
                return "정치";
            case 2:
                return "경제";
            case 3:
                return "사회";
            case 4:
                return "국제";
            case 5:
                return "연예·문화";
            case 6:
                return "스포츠";

        }
        return null;
    }
    /*public void setStr(String s) {
        p.setStr(s);
    }*/
}

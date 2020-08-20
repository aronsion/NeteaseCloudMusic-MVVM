package com.netease.music.ui.page.video;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.netease.music.BR;
import com.netease.music.R;
import com.netease.music.ui.page.drawer.DrawerFragment;
import com.netease.music.ui.state.VideoTabViewModel;

import org.jetbrains.annotations.NotNull;

public class VideoTabFragment extends BaseFragment {


    private VideoTabViewModel mViewModel;

    @Override
    protected void initViewModel() {
        mViewModel = getFragmentViewModel(VideoTabViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.delegate_video_tab, BR.vm, mViewModel);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //标题
        mViewModel.request.getTitleLiveData().observe(this, title -> {
            mViewModel.indicatorTitle.set(title);
        });
        //分页ID
        mViewModel.request.getVideoTabIdLiveData().observe(this, idArray -> {
            VideoTabAdapter mAdapter = new VideoTabAdapter(idArray, getChildFragmentManager());
            mViewModel.adapter.set(mAdapter);
        });

        mViewModel.request.requestVideoGroup();

    }


    static class VideoTabAdapter extends FragmentPagerAdapter {

        private long[] mTabIdArray;

        VideoTabAdapter(long[] tabArray, FragmentManager fm) {
            super(fm);
            mTabIdArray = tabArray;
        }

        @NotNull
        @Override
        public Fragment getItem(int i) {
            if (mTabIdArray[i] == 1000) {
                return new DrawerFragment();
            } else {
                return VideoFragment.newInstance(mTabIdArray[i]);
            }
        }

        @Override
        public int getCount() {
            return mTabIdArray.length;
        }
    }
}

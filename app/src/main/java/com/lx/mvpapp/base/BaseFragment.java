package com.lx.mvpapp.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by linxiao on 2018/10/30.
 */

public abstract class BaseFragment extends Fragment implements IBaseView{

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showProgress(String message) {
        //  mDialogLoad.setMessage(message);
        // mDialogLoad.showDialog();
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void cancelProgress() {

        //mDialogLoad.cancelDialog();
    }
}

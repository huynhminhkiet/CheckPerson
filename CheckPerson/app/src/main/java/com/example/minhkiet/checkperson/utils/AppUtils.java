package com.example.minhkiet.checkperson.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by tungle on 15/11/2016.
 */
public class AppUtils {

    public static void addNewFragment(FragmentActivity context, int placeHolder,
                                      Fragment newFragment) {
        try {
            android.support.v4.app.FragmentManager fragmentManager = context.getSupportFragmentManager();
            String fragmentTag = newFragment.getClass().getSimpleName();
            if (fragmentManager.findFragmentByTag(fragmentTag) == null
                    || !fragmentManager.findFragmentByTag(fragmentTag).isAdded()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(placeHolder, newFragment).addToBackStack(fragmentTag)
                        .commitAllowingStateLoss();
            }
        } catch (Exception ex) {
            Log.d("Failed", ex.getMessage());
        }
    }

    public static void showNewFragmentWithoutBackStack(FragmentActivity context, int placeHolder,
                                                   Fragment newFragment) {
        try {
            android.support.v4.app.FragmentManager fragmentManager = context.getSupportFragmentManager();
            String fragmentTag = newFragment.getClass().getSimpleName();
            if (fragmentManager.findFragmentByTag(fragmentTag) == null
                    || !fragmentManager.findFragmentByTag(fragmentTag).isAdded()) {
                fragmentManager.popBackStack();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(placeHolder, newFragment)
                        .commitAllowingStateLoss();
            }
        } catch (Exception ex) {
            Log.d("Failed", ex.getMessage());
        }
    }
}

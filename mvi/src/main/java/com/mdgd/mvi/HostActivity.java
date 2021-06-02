package com.mdgd.mvi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class HostActivity extends AppCompatActivity {

    protected abstract int getFragmentContainerId();

    // maybe use navigation components?
    protected FragmentTransaction getTransaction(boolean addToStack, String backStackTag, int enterAnim, int exitAnim, boolean sameAnimForPop) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToStack) {
            transaction.addToBackStack(backStackTag);
        }
        if (enterAnim != -1 && exitAnim != -1) {
            if (sameAnimForPop) {
                transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
            } else {
                transaction.setCustomAnimations(enterAnim, exitAnim);
            }
        }
        return transaction;
    }

    protected void addFragment(@Nullable Fragment fragment) {
        addFragment(fragment, false, null);
    }

    protected void addFragmentToBackStack(@Nullable Fragment fragment) {
        addFragment(fragment, true, null);
    }

    public void addFragment(@Nullable Fragment fragment, boolean addToStack, String backStackTag) {
        addFragment(fragment, addToStack, backStackTag, -1, -1);
    }

    public void addFragment(@Nullable Fragment fragment, boolean addToStack, String backStackTag, int enterAnim, int exitAnim) {
        if (fragment == null) {
            return;
        }
        if (fragment instanceof DialogFragment) {
            ((DialogFragment) fragment).show(getSupportFragmentManager(), backStackTag);
        } else {
            final FragmentTransaction transaction = getTransaction(addToStack, backStackTag, enterAnim, exitAnim, true)
                    .add(getFragmentContainerId(), fragment);
            transaction.commit();
        }
    }

    protected void replaceFragment(@Nullable Fragment fragment) {
        replaceFragment(fragment, false, null);
    }

    protected void replaceFragmentToBackStack(@Nullable Fragment fragment) {
        replaceFragment(fragment, true, null);
    }

    protected void replaceFragment(@Nullable Fragment fragment, boolean addToStack, String backStackTag) {
        if (fragment == null) {
            return;
        }
        getTransaction(addToStack, backStackTag, -1, -1, true).replace(getFragmentContainerId(), fragment).commit();
    }

    protected void removeFragment(@Nullable Fragment fragment, boolean addToStack, String backStackTag) {
        if (fragment == null) {
            return;
        }
        getTransaction(addToStack, backStackTag, -1, -1, true).remove(fragment).commit();
    }

    public void removeFragment(@Nullable Fragment fragment) {
        removeFragment(fragment, false, null);
    }
}

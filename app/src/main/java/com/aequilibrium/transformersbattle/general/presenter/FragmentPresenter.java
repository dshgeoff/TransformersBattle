package com.aequilibrium.transformersbattle.general.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.general.fragment.BaseFragment;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FragmentPresenter {
    private WeakReference<FragmentManager> mFragmentManager;
    private List<Control> mControl = new ArrayList<>();
    private boolean mClearAllBackStackFragments = false;
    private int mAddToBackStack = -1;

    static class Control {
        BaseFragment fragment, prevFragment;
        Class withoutFragment;
        int containerId = 0;
        boolean overlay;
        boolean rollback;
        boolean clearAllWithoutFragment;
        Bundle args = null;
        @AnimRes
        int enter = -1;
        @AnimRes
        int exit = -1;
        @AnimRes
        int popEnter = -1;
        @AnimRes
        int popExit = -1;
        boolean allowingStateLoss = false;
        boolean isCustomAnimationSet = false;
    }

    public FragmentPresenter(FragmentActivity activity, FragmentManager fragmentManager) {
        mFragmentManager = new WeakReference<>(fragmentManager);
    }

    public FragmentPresenter(FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public FragmentPresenter(FragmentManager fragmentManager) {
        this(null, fragmentManager);
    }

    Control lastPresentation() {
        return mControl.get(mControl.size() - 1);
    }

    public <F extends BaseFragment> FragmentPresenter push(Class<F> fragmentClass) {
        try {
            return push(fragmentClass.getConstructor().newInstance());
        } catch (InstantiationException e) {
            Log.e("Fragment", e.toString());
        } catch (IllegalAccessException e) {
            Log.e("Fragment", e.toString());
        } catch (NoSuchMethodException e) {
            Log.e("Fragment", e.toString());
        } catch (InvocationTargetException e) {
            Log.e("Fragment", e.toString());
        }
        return this;
    }

    public FragmentPresenter push(BaseFragment fragment, boolean allowingStateLoss) {
        Control control = new Control();
        control.fragment = fragment;
        control.allowingStateLoss = allowingStateLoss;
        mControl.add(control);
        return this;
    }

    public FragmentPresenter push(BaseFragment fragment) {
        Control control = new Control();
        control.fragment = fragment;
        mControl.add(control);
        return this;
    }

    public FragmentPresenter withArguments(Bundle args) {
        lastPresentation().args = args;
        return this;
    }

    public FragmentPresenter withDefaultAnimations() {
        return setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public FragmentPresenter setCustomAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        lastPresentation().enter = enter;
        lastPresentation().exit = exit;
        lastPresentation().popEnter = popEnter;
        lastPresentation().popExit = popExit;
        lastPresentation().isCustomAnimationSet = true;
        return this;
    }

    public FragmentPresenter setCustomAnimations(@AnimRes int enter, @AnimRes int exit) {
        lastPresentation().enter = enter;
        lastPresentation().exit = exit;
        lastPresentation().isCustomAnimationSet = true;
        return this;
    }

    public FragmentPresenter rollbackIfPossible() {
        lastPresentation().rollback = true;
        return this;
    }

    public FragmentPresenter in(int containerId) {
        lastPresentation().containerId = containerId;
        return this;
    }

    public FragmentPresenter inContainerOf(Fragment fragment) {
        return in(((ViewGroup) fragment.getView().getParent()).getId());
    }

    public FragmentPresenter allowingStateLoss() {
        lastPresentation().allowingStateLoss = true;
        return this;
    }

    public <F extends BaseFragment> FragmentPresenter clearAllWithoutFragment(Class<F> fragmentClass) {
        lastPresentation().clearAllWithoutFragment = true;
        lastPresentation().withoutFragment = fragmentClass;
        return this;
    }

    public void asMajorContent() {
        lastPresentation().overlay = false;
        mClearAllBackStackFragments = true;

        if (mAddToBackStack == -1) mAddToBackStack = 0;
        performPresent();
    }

    public void asSubContent() {
        lastPresentation().overlay = false;
        performPresent();
    }

    public void asRollbackContent() {
        lastPresentation().overlay = false;
        performPresent();
    }

    public void asOverlay() {
        lastPresentation().overlay = true;
        performPresent();
    }

    private void performPresent() {

        if (mAddToBackStack == -1) {
            mAddToBackStack = 1;
        }
        FragmentManager fragmentManager = mFragmentManager.get();
        if (fragmentManager == null) {
            return;
        }
        final String backStackEntryName = lastPresentation().fragment.getClass().getName();
        final boolean isAllowingStateLoss = lastPresentation().allowingStateLoss;

        if (mClearAllBackStackFragments) {
            if (lastPresentation().clearAllWithoutFragment) {
                for (int i = fragmentManager.getFragments().size() - 1; i >= 0; i--) {
                    BaseFragment f = (BaseFragment) fragmentManager.getFragments().get(i);
                    if (f != null) {
                        String tag = f.getClass().getName();
                        if (!tag.equals(lastPresentation().withoutFragment.getName()))
                            fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            } else {
                try {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } catch (Exception e) {
                }
            }
        } else {
            for (Control c : mControl) {
                String tag = c.fragment.getClass().getName();

                if (c.rollback) {
                    if (fragmentManager.getFragments() != null) {
                        for (int i = fragmentManager.getFragments().size() - 1; i >= 0; i--) {
                            BaseFragment f = (BaseFragment) fragmentManager.getFragments().get(i);
                            if (f != null) {
                                String popTag = f.getClass().getName();
                                if (popTag.equals(tag)) {
                                    lastPresentation().prevFragment = f;
                                    break;
                                }
                                fragmentManager.popBackStack(popTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                        }
                    }
                }
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = mFragmentManager.get();
                if (fragmentManager == null) {
                    return;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (mClearAllBackStackFragments) {
                    List<Fragment> activeFragments = fragmentManager.getFragments();
                    if (activeFragments != null)
                        for (Fragment activeFragment : activeFragments) {
                            if (activeFragment != null) {
                                if (lastPresentation().clearAllWithoutFragment) {
                                    if (!activeFragment.getClass().getName().equals(lastPresentation().withoutFragment.getName()))
                                        transaction.remove(activeFragment);
                                } else {
                                    transaction.remove(activeFragment);
                                }
                            }
                        }
                }

                if (!lastPresentation().rollback) {
                    for (Control c : mControl) {
                        String tag = c.fragment.getClass().getName();

                        if (c.containerId == 0) {
                            c.containerId = R.id.mainContainer;
                        }

                        if (c.args != null) {
                            c.fragment.setArguments(c.args);
                        }

                        if (c.isCustomAnimationSet) {
                            if (c.enter != -1 || c.exit != -1) {
                                if (c.popEnter != -1 || c.popExit != -1) {
                                    transaction.setCustomAnimations(c.enter, c.exit, c.popEnter, c.popExit);
                                } else {
                                    transaction.setCustomAnimations(c.enter, c.exit);
                                }
                            }
                        }

                        if (c.overlay) {
                            transaction.add(c.containerId, c.fragment, tag);
                        } else {
                            transaction.replace(c.containerId, c.fragment, tag);
                        }
                    }

                    if (mAddToBackStack == 1) {
                        transaction.addToBackStack(backStackEntryName);
                    }
                }

                try {
                    if (!isAllowingStateLoss)
                        transaction.commit();
                    else
                        transaction.commitAllowingStateLoss();
                } catch (IllegalStateException ex) {
                }
            }
        });
    }

}

package cc.rome753.activitytask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class ActivityTaskHelper2 {

    public static void init(Application app) {
        app.registerActivityLifecycleCallbacks(new ActivityTaskHelper2().activityLifecycleImpl);
    }

    private ActivityLifecycleImpl activityLifecycleImpl = new ActivityLifecycleImpl();

    private void handleFragment(Fragment fragment) {
        if(fragment == null || fragment.getActivity() == null) {
            Log.e("ActivityTaskHelper", "handleFragment null");
            return;
        }
        sendBroadcast(fragment.getActivity(), fragment);
    }

    private void handleFragment(Fragment fragment, Context context) {
        if(fragment == null || !(context instanceof Activity)) {
            Log.e("ActivityTaskHelper", "handleFragment null");
            return;
        }
        sendBroadcast((Activity) context, fragment);
    }

    private void handleActivity(Activity activity) {
        if(activity == null) {
            Log.e("ActivityTaskHelper", "handleActivity null");
            return;
        }
        sendBroadcast(activity, null);
    }

    private void sendBroadcast(Activity activity, Fragment fragment) {
        String lifecycle = Thread.currentThread().getStackTrace()[5].getMethodName();
        String packageName = "cc.rome753.activitytask";
        Intent intent = new Intent(packageName + ".ACTION_UPDATE_LIFECYCLE");
        intent.setPackage(packageName);
        intent.putExtra("lifecycle", lifecycle);
        intent.putExtra("task", "muduleinteract" + "@tid=" + activity.getTaskId());

        String code = String.valueOf(activity.hashCode());
        String substring = code.substring(0, 5);
        intent.putExtra("activity", substring +"#"+getSimpleName(activity));
        if(fragment != null) {
            intent.putStringArrayListExtra("fragments", getAllFragments(fragment));
        }
        activity.sendBroadcast(intent);
    }

    private ArrayList<String> getAllFragments(Fragment fragment){
        ArrayList<String> res = new ArrayList<>();
        while(fragment != null){
            res.add(getSimpleName(fragment));
            fragment = fragment.getParentFragment();
        }
        return res;
    }

    private String getSimpleName(Object obj){
        return obj.getClass().getSimpleName() + "@0x" + Integer.toHexString(obj.hashCode());
    }

    private class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if(activity instanceof FragmentActivity){
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(new FragmentLifecycleImpl(), true);
            }
            handleActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            handleActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            handleActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            handleActivity(activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            handleActivity(activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            handleActivity(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            handleActivity(activity);
        }
    }

    private class FragmentLifecycleImpl extends FragmentManager.FragmentLifecycleCallbacks{

        @Override
        public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
            handleFragment(f, context);
        }

        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            handleFragment(f, context);
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            handleFragment(f);
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            handleFragment(f);
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            handleFragment(f);
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
            handleFragment(f);
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            handleFragment(f);
        }
    }

}

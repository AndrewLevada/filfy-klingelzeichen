package com.example.filfyklingelzeichen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * This class contains all kinds of tools
 * that could be useful during development.
 */
public class Toolbox {
    private static final String PREFS_IS_FIRST_LAUNCH = "is_first_launch";

    /**
     * Finds the last child of given parent.
     * This child is ether last inflated or simply last in layout.
     *
     * @param parent {@link ViewGroup} which's child to find
     * @return Last child {@link View} or null if parent has no children.
     */
    @Nullable
    public static View getLastChild(@NonNull ViewGroup parent) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);

            if (child != null) return child;
        }
        return null;
    }

    public static void putInClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    public static <T> String arrayToString(T[] array) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < array.length; i++) string.append(array[i].toString()).append(i == array.length - 1 ? ", " : "");
        return string.toString();
    }

    public static boolean isFirstUserTypeOpen(Context context, int userType) {
        SharedPreferences preferences = context
                .getSharedPreferences(Config.appSharedPreferences, Context.MODE_PRIVATE);

        boolean result = preferences.getBoolean(PREFS_IS_FIRST_LAUNCH + userType, true);
        preferences.edit().putBoolean(PREFS_IS_FIRST_LAUNCH + userType, false).apply();
        return result;
    }

    // Dialogs

    public static void showSimpleDialog(Context context, @StringRes int title, @StringRes int body,
                                        @StringRes int buttonText) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(body)
                .setPositiveButton(buttonText, (dialog, which) -> {})
                .show();
    }

    // Sync thread used for syncing data

    public interface InSyncThread {
        void sync();
    }
    public static SyncThread getSyncThread(Fragment fragment, InSyncThread call) {
        return new SyncThread(fragment, call);
    }
    public static class SyncThread extends Thread {
        public Fragment fragment;
        private InSyncThread call;

        @Override
        public void run() {
            while (fragment != null && fragment.isAdded()) {
                try {
                    Thread.sleep(3400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                call.sync();
            }
        }

        public SyncThread(Fragment fragment, InSyncThread call) {
            this.fragment = fragment;
            this.call = call;
        }
    }

    // Several general callbacks

    public interface Callback {
        void invoke();
    }
    public interface CallbackOne<T> {
        void invoke(T arg);
    }
    public interface CallbackState {
        void invoke();
        void fail();
    }
    public interface CallbackStateOne<T> {
        void invoke(T arg);
        void fail();
    }

    // Internet connection check

    public static class InternetConnectionChecker {
        private static InternetConnectionChecker instance;

        private boolean hadInternet;

        public void hasInternet(@Nullable CallbackOne<Boolean> callback) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        int timeoutMs = 1500;
                        Socket sock = new Socket();
                        SocketAddress address = new InetSocketAddress("8.8.8.8", 53);

                        sock.connect(address, timeoutMs);
                        sock.close();

                        hadInternet = true;
                        if (callback != null) callback.invoke(true);
                    } catch (IOException e) {
                        hadInternet = false;
                        if (callback != null) callback.invoke(false);
                    }
                }
            };
            thread.start();
        }

        public boolean hasInternetSync() {
            hasInternet(hasInternet -> hadInternet = hasInternet);
            return hadInternet;
        }

        public static InternetConnectionChecker getInstance() {
            if (instance == null) instance = new InternetConnectionChecker();
            return instance;
        }
    }
}
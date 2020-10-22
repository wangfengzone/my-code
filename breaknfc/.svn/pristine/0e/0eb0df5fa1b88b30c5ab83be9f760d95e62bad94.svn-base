package com.kosmos.brushbreakfast.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;


import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static void requestAPermissionWithRx(FragmentActivity activity, String permission, PermissionRequestCallback callback) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(permission)
                .subscribe(granted -> {
                    if (granted) {
                        List<String> permissions = new ArrayList<>();
                        permissions.add(permission);
                        callback.onPermissionGranted(permissions);
                    } else {
                        List<String> permissions = new ArrayList<>();
                        permissions.add(permission);
                        callback.onPermissionRejected(permissions);
                    }
                });
    }

//    public static void requestAllPermissionsWithAndPermission(Context context, PermissionRequestCallback callback) {
//
//        Rationale rationale = (context1, data, executor) -> {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(context1);
//            builder.setTitle("权限申请").setMessage("为了提供更好的服务，该应用程序将申请如下权限：\n1.存储信息(头像等图片上传)\n2.位置信息(非必要)");
//            builder.setPositiveButton("我知道了", (dialog, which) -> {
//                executor.execute();
//            });
//            builder.show();
//            // When the user interrupts the request:
//            // executor.cancel();
//        };
//
//        AndPermission
//                .with(context)
//                .runtime()
//                .permission(
//                        Permission.Group.STORAGE,
//                        Permission.Group.LOCATION
//                )
//                .rationale(rationale)
//                .onGranted(callback::onPermissionGranted)
//                .onDenied(callback::onPermissionRejected)
//                .start();
//    }

    public static void requestAllPermissionWithRx(FragmentActivity activity, PermissionRequestCallback callback) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        List<String> permissions = new ArrayList<>();
                        permissions.add(permission.name);
                        callback.onPermissionGranted(permissions);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("权限申请").setMessage("为了提供更好的服务，该应用程序将申请如下权限：\n1.存储信息(APP更新等)");
                        builder.setPositiveButton("我知道了", (dialog, which) -> {
                        });
                        builder.show();
                    } else {
                        List<String> permissions = new ArrayList<>();
                        permissions.add(permission.name);
                        callback.onPermissionRejected(permissions);
                    }
                });
    }

    public static boolean hasThePermission(String permission, Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(context, permission);
    }
}



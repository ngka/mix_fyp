package com.router.Core.Permission;

import java.util.List;

/************************************************************
 * Description:已授權、未授權的接口回調
 ***********************************************************/

public interface PermissionListener {
    void onGranted(boolean isfirst);//已授權

    void onDenied(List<String> deniedPermission);//未授權

}

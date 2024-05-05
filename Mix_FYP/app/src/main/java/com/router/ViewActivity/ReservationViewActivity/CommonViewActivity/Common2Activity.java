package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.Base.Adapter.ItemClickListener;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.Permission.PermissionListener;
import com.router.Model.PhotoModel;
import com.router.R;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.utils.DialogUtils;
import com.router.utils.FileUtils;
import com.router.utils.SelectPhotoContract;
import com.router.utils.TakePhotoContract;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 回報路由器問題2頁面
 ***********************************************************/
public class Common2Activity extends BaseSimFragmentActivity<LoginViewModel> {

    private final String TAG = "Common2Activity";
    private ImageView imgBack;
    private Button btnNext;
    private EditText edtError;
    private GridView photoGridview;
    private ChooseImageAdapter photoAdapter;
    private List<PhotoModel> photoModels = new ArrayList<>();
    private String[] setHeadDialogCon;
    private ActivityResultLauncher selectPhoto;
    private ActivityResultLauncher takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        selectPhoto = registerForActivityResult(new SelectPhotoContract(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {
                    PhotoModel photoModel = new PhotoModel();
                    photoModel.setId(System.currentTimeMillis());
                    photoModel.setUri(uri);
                    String path = FileUtils.getRealPathFromUri(Common2Activity.this, uri);
                    photoModel.setPath(path);
                    photoModels.add(photoModel);
                    photoAdapter.addModel(photoModel);
                }
            }
        });
        takePhoto = registerForActivityResult(new TakePhotoContract(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {
                    PhotoModel photoModel = new PhotoModel();
                    photoModel.setId(System.currentTimeMillis());
                    photoModel.setUri(uri);
                    String path = FileUtils.getRealPathFromUri(Common2Activity.this, uri);
                    photoModel.setPath(path);
                    photoModels.add(photoModel);
                    photoAdapter.addModel(photoModel);
                }
            }
        });
        setHeadDialogCon = new String[]{"拍照", "選擇圖片"};
        imgBack = findViewById(R.id.img_back);
        btnNext = findViewById(R.id.btn_next);
        imgBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        edtError = findViewById(R.id.edt_error);
        photoGridview = findViewById(R.id.photo_gridview);
        photoAdapter = new ChooseImageAdapter(this, photoModels, R.layout.common_choose_image);
        photoGridview.setAdapter(photoAdapter);
        photoAdapter.setAddListener(new ItemClickListener<PhotoModel>() {
            @Override
            public void onClick(View view, PhotoModel model) {
                DialogUtils.ShowDialog(Common2Activity.this, setHeadDialogCon, new DialogUtils.DialogItemClickListener() {
                    @Override
                    public void confirm(String result) {
                        if (result.equals(setHeadDialogCon[0])) {
                            requestRunPermisssion(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {
                                @Override
                                public void onGranted(boolean isfirst) {
                                    takePhoto.launch(null);
                                }

                                @Override
                                public void onDenied(List<String> deniedPermission) {
                                    ToastUtil.getInstance().show("需要拍照許可權");
                                }
                            });

                        } else if (result.equals(setHeadDialogCon[1])) {
                            requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
                                @Override
                                public void onGranted(boolean isfirst) {
                                    selectPhoto.launch(null);
                                }

                                @Override
                                public void onDenied(List<String> deniedPermission) {
                                    ToastUtil.getInstance().show("需要文件許可權");
                                }
                            });

                        }
                    }
                });
            }
        });
        photoAdapter.setDelListener(new ItemClickListener<PhotoModel>() {
            @Override
            public void onClick(View view, PhotoModel model) {
                photoModels.remove(model);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common2;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registEventHandlerMsgType() {
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
    }

    @Override
    protected LoginViewModel bindViewModel() {
        return new LoginViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            CommonManager.getInstance().setCommon2("", null);
            finish();
        } else if (view.getId() == R.id.btn_next) {
            String msg = edtError.getText().toString().trim();
            if (TextUtils.isEmpty(msg)) {
                ToastUtil.getInstance().show("數據不能為空！");
                return;
            }
            CommonManager.getInstance().setCommon2(msg, photoModels);
            startActivity(new Intent(this, Common3Activity.class));
        }
    }

}

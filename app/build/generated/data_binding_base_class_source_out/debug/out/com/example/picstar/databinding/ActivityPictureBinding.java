// Generated by view binder compiler. Do not edit!
package com.example.picstar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.picstar.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPictureBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button buttonDownload;

  @NonNull
  public final ImageView photoDetail;

  @NonNull
  public final TextView photoTitleDetail;

  @NonNull
  public final Button premiumDownload;

  @NonNull
  public final TextView usernameDetail;

  private ActivityPictureBinding(@NonNull LinearLayout rootView, @NonNull Button buttonDownload,
      @NonNull ImageView photoDetail, @NonNull TextView photoTitleDetail,
      @NonNull Button premiumDownload, @NonNull TextView usernameDetail) {
    this.rootView = rootView;
    this.buttonDownload = buttonDownload;
    this.photoDetail = photoDetail;
    this.photoTitleDetail = photoTitleDetail;
    this.premiumDownload = premiumDownload;
    this.usernameDetail = usernameDetail;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPictureBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPictureBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_picture, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPictureBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_download;
      Button buttonDownload = rootView.findViewById(id);
      if (buttonDownload == null) {
        break missingId;
      }

      id = R.id.photo_detail;
      ImageView photoDetail = rootView.findViewById(id);
      if (photoDetail == null) {
        break missingId;
      }

      id = R.id.photo_title_detail;
      TextView photoTitleDetail = rootView.findViewById(id);
      if (photoTitleDetail == null) {
        break missingId;
      }

      id = R.id.premium_download;
      Button premiumDownload = rootView.findViewById(id);
      if (premiumDownload == null) {
        break missingId;
      }

      id = R.id.username_detail;
      TextView usernameDetail = rootView.findViewById(id);
      if (usernameDetail == null) {
        break missingId;
      }

      return new ActivityPictureBinding((LinearLayout) rootView, buttonDownload, photoDetail,
          photoTitleDetail, premiumDownload, usernameDetail);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
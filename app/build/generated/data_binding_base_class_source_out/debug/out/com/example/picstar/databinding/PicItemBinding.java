// Generated by view binder compiler. Do not edit!
package com.example.picstar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class PicItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView photo;

  @NonNull
  public final TextView photoTitle;

  @NonNull
  public final TextView statusText;

  @NonNull
  public final TextView username;

  private PicItemBinding(@NonNull LinearLayout rootView, @NonNull ImageView photo,
      @NonNull TextView photoTitle, @NonNull TextView statusText, @NonNull TextView username) {
    this.rootView = rootView;
    this.photo = photo;
    this.photoTitle = photoTitle;
    this.statusText = statusText;
    this.username = username;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PicItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PicItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.pic_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PicItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.photo;
      ImageView photo = rootView.findViewById(id);
      if (photo == null) {
        break missingId;
      }

      id = R.id.photo_title;
      TextView photoTitle = rootView.findViewById(id);
      if (photoTitle == null) {
        break missingId;
      }

      id = R.id.status_text;
      TextView statusText = rootView.findViewById(id);
      if (statusText == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = rootView.findViewById(id);
      if (username == null) {
        break missingId;
      }

      return new PicItemBinding((LinearLayout) rootView, photo, photoTitle, statusText, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

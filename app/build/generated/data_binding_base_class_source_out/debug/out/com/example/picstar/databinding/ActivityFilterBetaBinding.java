// Generated by view binder compiler. Do not edit!
package com.example.picstar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.picstar.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFilterBetaBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button applyFilter;

  @NonNull
  public final Button btnChoose;

  @NonNull
  public final EditText compressrate;

  @NonNull
  public final EditText filterType;

  @NonNull
  public final ImageView imgFilter;

  @NonNull
  public final EditText intensity;

  private ActivityFilterBetaBinding(@NonNull RelativeLayout rootView, @NonNull Button applyFilter,
      @NonNull Button btnChoose, @NonNull EditText compressrate, @NonNull EditText filterType,
      @NonNull ImageView imgFilter, @NonNull EditText intensity) {
    this.rootView = rootView;
    this.applyFilter = applyFilter;
    this.btnChoose = btnChoose;
    this.compressrate = compressrate;
    this.filterType = filterType;
    this.imgFilter = imgFilter;
    this.intensity = intensity;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFilterBetaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFilterBetaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_filter_beta, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFilterBetaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.apply_filter;
      Button applyFilter = rootView.findViewById(id);
      if (applyFilter == null) {
        break missingId;
      }

      id = R.id.btn_choose;
      Button btnChoose = rootView.findViewById(id);
      if (btnChoose == null) {
        break missingId;
      }

      id = R.id.compressrate;
      EditText compressrate = rootView.findViewById(id);
      if (compressrate == null) {
        break missingId;
      }

      id = R.id.filter_type;
      EditText filterType = rootView.findViewById(id);
      if (filterType == null) {
        break missingId;
      }

      id = R.id.img_filter;
      ImageView imgFilter = rootView.findViewById(id);
      if (imgFilter == null) {
        break missingId;
      }

      id = R.id.intensity;
      EditText intensity = rootView.findViewById(id);
      if (intensity == null) {
        break missingId;
      }

      return new ActivityFilterBetaBinding((RelativeLayout) rootView, applyFilter, btnChoose,
          compressrate, filterType, imgFilter, intensity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
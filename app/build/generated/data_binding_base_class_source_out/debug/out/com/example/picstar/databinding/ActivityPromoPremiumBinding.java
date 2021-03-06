// Generated by view binder compiler. Do not edit!
package com.example.picstar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.picstar.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPromoPremiumBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnSubscribe;

  @NonNull
  public final TextView textSubscribe;

  private ActivityPromoPremiumBinding(@NonNull RelativeLayout rootView,
      @NonNull Button btnSubscribe, @NonNull TextView textSubscribe) {
    this.rootView = rootView;
    this.btnSubscribe = btnSubscribe;
    this.textSubscribe = textSubscribe;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPromoPremiumBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPromoPremiumBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_promo_premium, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPromoPremiumBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_subscribe;
      Button btnSubscribe = rootView.findViewById(id);
      if (btnSubscribe == null) {
        break missingId;
      }

      id = R.id.text_subscribe;
      TextView textSubscribe = rootView.findViewById(id);
      if (textSubscribe == null) {
        break missingId;
      }

      return new ActivityPromoPremiumBinding((RelativeLayout) rootView, btnSubscribe,
          textSubscribe);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

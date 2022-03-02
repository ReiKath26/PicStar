// Generated by view binder compiler. Do not edit!
package com.example.picstar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.picstar.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDashboardBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnSubs;

  @NonNull
  public final ImageView imagePromo;

  @NonNull
  public final TextView textPromo;

  private FragmentDashboardBinding(@NonNull RelativeLayout rootView, @NonNull Button btnSubs,
      @NonNull ImageView imagePromo, @NonNull TextView textPromo) {
    this.rootView = rootView;
    this.btnSubs = btnSubs;
    this.imagePromo = imagePromo;
    this.textPromo = textPromo;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDashboardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDashboardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_dashboard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDashboardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_subs;
      Button btnSubs = rootView.findViewById(id);
      if (btnSubs == null) {
        break missingId;
      }

      id = R.id.image_promo;
      ImageView imagePromo = rootView.findViewById(id);
      if (imagePromo == null) {
        break missingId;
      }

      id = R.id.text_promo;
      TextView textPromo = rootView.findViewById(id);
      if (textPromo == null) {
        break missingId;
      }

      return new FragmentDashboardBinding((RelativeLayout) rootView, btnSubs, imagePromo,
          textPromo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
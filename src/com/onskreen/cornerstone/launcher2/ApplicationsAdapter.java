package com.onskreen.cornerstone.launcher2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * GridView adapter to show the list of all installed applications.
 */
public class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo> {
    private final Rect mOldBounds = new Rect();

    public ApplicationsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
        super(context, 0, apps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            if (this.getContext().getResources().getBoolean(R.bool.config_gridView)) {
                convertView = inflater.inflate(R.layout.item_application_grid, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.item_application_list, parent, false);
            }
        }

        final ApplicationInfo info = this.getItem(position);
        final TextView textView = (TextView) convertView.findViewById(R.id.label);
        textView.setText(info.getTitle());

        if (this.getContext().getResources().getBoolean(R.bool.config_listIcons)) {
            Drawable icon = info.getIcon();
            if (!info.isFiltered()) {
                final Resources resources = getContext().getResources();
                int width = (int) resources.getDimension(android.R.dimen.app_icon_size);
                int height = (int) resources.getDimension(android.R.dimen.app_icon_size);

                final int iconWidth = icon.getIntrinsicWidth();
                final int iconHeight = icon.getIntrinsicHeight();

                if (icon instanceof PaintDrawable) {
                    PaintDrawable painter = (PaintDrawable) icon;
                    painter.setIntrinsicWidth(width);
                    painter.setIntrinsicHeight(height);
                }

                if (width > 0 && height > 0 && (width < iconWidth || height < iconHeight)) {
                    final float ratio = (float) iconWidth / iconHeight;

                    if (iconWidth > iconHeight) {
                        height = (int) (width / ratio);
                    } else if (iconHeight > iconWidth) {
                        width = (int) (height * ratio);
                    }

                    final Bitmap.Config c =
                            icon.getOpacity() != PixelFormat.OPAQUE ?
                                    Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                    final Bitmap thumb = Bitmap.createBitmap(width, height, c);
                    final Canvas canvas = new Canvas(thumb);
                    canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, 0));
                    // Copy the old bounds to restore them later
                    // If we were to do oldBounds = icon.getBounds(),
                    // the call to setBounds() that follows would
                    // change the same instance and we would lose the
                    // old bounds
                    mOldBounds.set(icon.getBounds());
                    icon.setBounds(0, 0, width, height);
                    icon.draw(canvas);
                    icon.setBounds(mOldBounds);
                    icon = new BitmapDrawable(resources, thumb);
                    info.setFiltered(true);
                }
            }

            if (this.getContext().getResources().getBoolean(R.bool.config_gridView)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            }
        }

        return convertView;
    }
}

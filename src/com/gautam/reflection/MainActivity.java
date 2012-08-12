package com.gautam.reflection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
	Gallery gallery;
	Integer[] pics = { R.drawable.sample, R.drawable.sample, R.drawable.sample,
			R.drawable.sample, R.drawable.sample, R.drawable.sample,
			R.drawable.sample, R.drawable.sample, R.drawable.sample,
			R.drawable.sample };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bitmap originalImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.sample);

		// Gallery ga = (Gallery) findViewById(R.id.gallery1);
		// ga.setAdapter(new ImageAdapter(this));
		ImageView imageView = new ImageView(this);

		imageView.setImageBitmap(getRefelection(originalImage));
		// gallery = (Gallery) findViewById(R.id.gallery1);
		LinearLayout linLayout = new LinearLayout(this);
		linLayout.addView(imageView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setContentView(linLayout);

	}

	public class ImageAdapter extends BaseAdapter {
		Context mContext;
		int imageBackground;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pics.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			ImageView iv = new ImageView(mContext);
			iv.setImageResource(pics[position]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
			iv.setBackgroundResource(imageBackground);
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public Bitmap getRefelection(Bitmap image) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 1;

		// Get you bit map from drawable folder
		Bitmap originalImage = image;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		// Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70000000, 0x00ffffff,
				TileMode.MIRROR);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

}

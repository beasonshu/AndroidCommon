package tk.beason.noah.modules.sample.imageloader;

import android.os.Bundle;
import android.widget.ImageView;

import tk.beason.noah.R;
import tk.beason.noah.modules.base.AppBaseActivity;
import tk.beason.common.utils.image.loader.ImageLoader;

import tk.beason.noah.modules.base.AppBaseActivity;


public class SampleImageLoaderActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_imageloader);
    }


    @Override
    protected void initViews() {
        super.initViews();
        ImageView imageView = (ImageView) findViewById(R.id.test1);

        ImageLoader.with(this)
                .load("http://www.yuhaiyang.net/picture/1.webp")
                .into(imageView);

        imageView = (ImageView) findViewById(R.id.test2);

        ImageLoader.with(this)
                .load("http://www.yuhaiyang.net/picture/1.webp")
                .mode(ImageLoader.LoaderMode.CIRCLE_CROP)
                .into(imageView);
    }
}

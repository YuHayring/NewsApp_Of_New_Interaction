package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityMagicVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;

/**
 * @author hayring
 * @date 2020.11.26 16:00
 */
public class MagicVideoPlayActivity extends FullScreenActivity {



    ActivityMagicVideoPlayBinding videoPlayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(videoPlayBinding.getRoot());


    }
}
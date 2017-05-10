package cn.edu.gdmec.s07150808.myvideo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity implements SurfaceHolder.Callback{
    private MediaPlayer mMediaPlayer;
    private SurfaceView mSurfaceView01;
    /* SurfaceHolder���� */
    private SurfaceHolder mSurfaceHolder01;
    private ImageButton mPlay, mReset, mPause, mStop;
    /* 识别MediaPlayer是否已经释放资源 */
    private boolean bIsReleased = false;

    /* 识别MediaPlayer是否处于暂停状态 */
    private boolean bIsPaused = false;

    /* LogCat��输出�TAG filter */
    private static final String TAG = "HippoMediaPlayer";
    private String currentFilePath = "";
    private String currentTempFilePath = "";
    private String strVideoURL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strVideoURL ="http://192.168.191.1/videos/mov.3gp";
        mSurfaceView01 = (SurfaceView) findViewById(R.id.surfaceview);
        getWindow().setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder01 = mSurfaceView01.getHolder();
        mSurfaceHolder01.addCallback(this);
        mSurfaceHolder01.setFixedSize(160, 128);
        mSurfaceHolder01.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mPlay = (ImageButton) findViewById(R.id.play);
        mReset = (ImageButton) findViewById(R.id.reset);
        mPause = (ImageButton) findViewById(R.id.pause);
        mStop = (ImageButton) findViewById(R.id.stop);
        mPlay.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if(checkSDCard())
                {
                  /*  strVideoURL = mEditText01.getText().toString();*/
                    playVideo(strVideoURL);

                }
                else
                {

                }
            }
        });
        mPause.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if(checkSDCard())
                {
                    if (mMediaPlayer!= null)
                    {
                        if(bIsReleased == false)
                        {
                            if(bIsPaused==false)
                            {
                                mMediaPlayer.pause();
                                bIsPaused = true;
                              /*  mTextView01.setText(R.string.str_pause);*/
                            }
                            else if(bIsPaused==true)
                            {
                                mMediaPlayer.start();
                                bIsPaused = false;
                              /*  mTextView01.setText(R.string.str_play);*/
                            }
                        }
                    }
                }
                else
                {
                  /*  mTextView01.setText(R.string.str_err_nosd);*/
                }
            }
        });

    /* 停止的点击事件*/
        mStop.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if(checkSDCard())
                {
                    try
                    {
                        if (mMediaPlayer!= null)
                        {
                            if(bIsReleased==false)
                            {
                                mMediaPlayer.seekTo(0);
                                mMediaPlayer.pause();
                            /*    mTextView.setText(R.string.str_stop);*/
                            }
                        }
                    }
                    catch(Exception e)
                    {
                       /* mTextView01.setText(e.toString());*/
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                    }
                }
                else
                {
                 /*   mTextView01.setText(R.string.str_err_nosd);*/
                }
            }
        });
        /* 停止的点击事件*/
        mStop.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if(checkSDCard())
                {
                    try
                    {
                        if (mMediaPlayer != null)
                        {
                            if(bIsReleased==false)
                            {
                                mMediaPlayer.seekTo(0);
                                mMediaPlayer.pause();
                               /* mTextView.setText(R.string.str_stop);*/
                            }
                        }
                    }
                    catch(Exception e)
                    {
                      /*  mTextView01.setText(e.toString());*/
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                    }
                }
                else
                {
                  /*  mTextView01.setText(R.string.str_err_nosd);*/
                }
            }
        });
    }



    /* �播放视频的函数���� */
    private void playVideo(final String strPath)
    {
        try
        {
      /* �播放地址判断� */
            if (strPath.equals(currentFilePath) && mMediaPlayer != null)
            {
                mMediaPlayer.start();
                return;
            }
            else if(mMediaPlayer != null)
            {
                mMediaPlayer.stop();
            }

            currentFilePath = strPath;

      /* ���¹���MediaPlayer���� */
            mMediaPlayer = new MediaPlayer();
      /* ��设置音频类型��� */
            mMediaPlayer.setAudioStreamType(2);

      /* �设置SurfaceHolder */
            mMediaPlayer.setDisplay(mSurfaceHolder01);

            mMediaPlayer.setOnErrorListener
                    (new MediaPlayer.OnErrorListener()
                    {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra)
                        {
                            // TODO Auto-generated method stub
                            Log.i
                                    (
                                            TAG,
                                            "Error on Listener, what: " + what + "extra: " + extra
                                    );
                            return false;
                        }
                    });

            mMediaPlayer.setOnBufferingUpdateListener
                    (new MediaPlayer.OnBufferingUpdateListener()
                    {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent)
                        {
                            // TODO Auto-generated method stub
                            Log.i
                                    (
                                            TAG, "Update buffer: " +
                                                    Integer.toString(percent) + "%"
                                    );
                        }
                    });

            mMediaPlayer.setOnCompletionListener
                    (new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mp)
                        {
                            // TODO Auto-generated method stub
                            Log.i(TAG,"mMediaPlayer01 Listener Completed");
                            /*mTextView01.setText(R.string.str_done);*/
                        }
                    });

            mMediaPlayer.setOnPreparedListener
                    (new MediaPlayer.OnPreparedListener()
                    {
                        @Override
                        public void onPrepared(MediaPlayer mp)
                        {
                            // TODO Auto-generated method stub
                            Log.i(TAG,"Prepared Listener");
                        }
                    });

            Runnable r = new Runnable()
            {
                public void run()
                {
                    try
                    {
            /* �设置datasource */
                        setDataSource(strPath);
            /* ����调用�prepare */
                        mMediaPlayer.prepare();
                        Log.i
                                (
                                        TAG, "Duration: " + mMediaPlayer.getDuration()
                                );
                        mMediaPlayer.start();
                        bIsReleased = false;
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            };
            new Thread(r).start();
        }
        catch(Exception e)
        {
            if (mMediaPlayer != null)
            {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
    }

    /* ���setDataSource�����߳����� */
    private void setDataSource(String strPath) throws Exception
    {
        if (!URLUtil.isNetworkUrl(strPath))
        {
            mMediaPlayer.setDataSource(strPath);
        }
        else
        {
            if(bIsReleased == false)
            {
                URL myURL = new URL(strPath);
                URLConnection conn = myURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                if (is == null)
                {
                    throw new RuntimeException("stream is null");
                }
                File myFileTemp = File.createTempFile
                        ("hippoplayertmp", "."+getFileExtension(strPath));

                currentTempFilePath = myFileTemp.getAbsolutePath();

        /*currentTempFilePath = /sdcard/mediaplayertmp39327.dat */

                FileOutputStream fos = new FileOutputStream(myFileTemp);
                byte buf[] = new byte[128];
                do
                {
                    int numread = is.read(buf);
                    if (numread <= 0)
                    {
                        break;
                    }
                    fos.write(buf, 0, numread);
                }while (true);
                mMediaPlayer.setDataSource(currentTempFilePath);
                try
                {
                    is.close();
                }
                catch (Exception ex)
                {
                    Log.e(TAG, "error: " + ex.getMessage(), ex);
                }
            }
        }
    }

    private String getFileExtension(String strFileName)
    {
        File myFile = new File(strFileName);
        String strFileExtension=myFile.getName();
        strFileExtension=(strFileExtension.substring
                (strFileExtension.lastIndexOf(".")+1)).toLowerCase();

        if(strFileExtension=="")
        {
      /* ���޷�˳��ȡ����չ����Ĭ��Ϊ.dat */
            strFileExtension = "dat";
        }
        return strFileExtension;
    }

    private boolean checkSDCard()
    {
    /* �检测 sdcard�� */
        if(android.os.Environment.getExternalStorageState().equals
                (android.os.Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

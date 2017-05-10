# MyVideo
                                                     简单的视频

                  
                  使用MediaPlayer 结合SurfaceView 来播放，通过MediaPlayer来控制视频的播放、暂停、进度等；
                  通过SurfaceView 来显示视频内容；
                  优点：灵活性高，可以进行自定义；
                   缺点：难度比较大；
                 
                 
                 3）对播放器的主要控制方法：
                     prepare()和prepareAsync() :
                       提供了同步和异步两种方式设置播放器进入prepare状态，需要注意的是，如果MediaPlayer实例是由create方法创建的，
                       那么第一次启动播放前不需要再调用prepare（）了，因为create方法里已经调用过了。
                     start()是真正启动文件播放的方法，
                     pause()和stop(): 
                        起到暂停和停止播放的作用，
                     seekTo()是定位方法:
                        让播放器从指定的位置开始播放，需要注意的是该方法是个异步方法，也就是说该方法返回时并不意味着定位完成，尤其是播放的网络文件，
                        真正定位完成时会触发OnSeekComplete.onSeekComplete()，如果需要是可以调用setOnSeekCompleteListener(OnSeekCompleteListener)设置监听器来处理的。
                     release():
                        释放播放器占用的资源，一旦确定不再使用播放器时应当尽早调用它释放资源。
                     reset():
                         使播放器从Error状态中恢复过来，重新会到Idle状态。
                         
                         效果图：
                      
			![Image text](https://raw.github.com/  MyVideo/app/src/main/res/drawable/Video.png);

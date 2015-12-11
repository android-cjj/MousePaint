package com.cjj.mousepaint.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * 图片联播
 * @author cjj
 * 
 */
public class PlayViewHandler extends Handler {
	/**播放标志*/
	public boolean play = true;
	/**播放标志*/
	private int playingDirection;
	/**播放的时间*/
	private static final int DEFAULT_TIME = 6_000;

	/**构造函数*/
	public PlayViewHandler() {
	
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 0x123:
			final int count = msg.arg1;
			playingDirection = msg.arg2;
			final ViewPager viewPager = (ViewPager) msg.obj;
			int i = viewPager.getCurrentItem();
			if (playingDirection == 0) {
				if (i == count - 1) {
					playingDirection = -1;
					i--;
				} else {
					i++;
				}
			} else {
				if (i == count-1) {
					playingDirection = 1;
					i=0;
				} else {
					i++;
				}
			}

			viewPager.setCurrentItem(i, false);
			if (play) {
				postDelayed(new Runnable() {

					@Override
					public void run() {
						slidingPlayView(viewPager, count, playingDirection);
					}
				}, DEFAULT_TIME);
			}
			break;
		}
		super.handleMessage(msg);
	}

	/**
	 * 图片联播的方法
	 * 
	 * @param viewPager
	 * @param count
	 *            页数
	 * @param dir
	 *            方向 1正 0反
	 * @param handler
	 */
	public void slidingPlayView(ViewPager viewPager, int count, int dir) {
		Message message = new Message();
		message.obj = viewPager;
		message.arg1 = count;
		message.arg2 = dir;
		message.what = 0x123;
		sendMessage(message);
	}

	public void slidingPlayView(ViewPager viewPager, int count) {
		Message message = new Message();
		message.obj = viewPager;
		message.arg1 = count;
		message.arg2 = 1;
		message.what = 0x123;
		sendMessage(message);
	}

}

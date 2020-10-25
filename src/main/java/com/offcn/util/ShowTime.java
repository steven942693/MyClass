package com.offcn.util;

import com.offcn.view.OmnibusView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowTime extends Thread {
    @Override
    public void run() {
        showTime();
    }

    //显示时间的线程
    public void showTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        while (true) {
            int dayOfWeek_Int = calendar.get(Calendar.DAY_OF_WEEK);
            String dayOfWeek_Str = "";
            switch (dayOfWeek_Int) {
                case 1:
                    dayOfWeek_Str = "星期日";
                    break;
                case 2:
                    dayOfWeek_Str = "星期一";
                    break;
                case 3:
                    dayOfWeek_Str = "星期二";
                    break;
                case 4:
                    dayOfWeek_Str = "星期三";
                    break;
                case 5:
                    dayOfWeek_Str = "星期四";
                    break;
                case 6:
                    dayOfWeek_Str = "星期五";
                    break;
                case 7:
                    dayOfWeek_Str = "星期六";
                    break;
                default:
                    dayOfWeek_Str = "日期错误";
                    break;
            }
            String format = sdf.format(new Date());
            OmnibusView.jLabel_nowTime.setText("现在时间：  " + format + "  " + dayOfWeek_Str+"   ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OmnibusView.jLabel_nowTime.repaint();
        }
    }
}

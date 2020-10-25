package com.offcn.util;

import com.eltima.components.ui.DatePicker;

import java.awt.*;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtil {
    public static DatePicker getDatePicker() {
        DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd HH:mm";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("楷体", Font.BOLD, 16);

        Dimension dimension = new Dimension(150, 30);

        //int[] disabledDays = { 1, 3, 5, 7 };

        //int[] disabledDays = { 4, 6, 31, 30 };



        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setTimePanleVisible(true);
        datepick.setPreferredSize(new Dimension(150,30));
        //datepick.setLocation(30, 260);
        //datepick.setBounds(30, 260, 160, 30);
        // 设置一个月份中需要高亮显示的日子
        //datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        //datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CHINA);
        // 设置时钟面板可见
        datepick.setTimePanleVisible(true);
        return datepick;
    }
}

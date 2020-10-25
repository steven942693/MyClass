package com.offcn.dialog;

import com.eltima.components.ui.DatePicker;
import com.offcn.bean.Student;
import com.offcn.service.impl.GetStuInfoImpl;
import com.offcn.util.DatePickerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Att_and_Dis_Dialog extends JDialog {
    private JLabel jLabel_id;
    private JLabel jLabel_name;
    private JLabel jLabel_att;
    private JLabel jLabel_score;
    private JLabel jLabel_dis_reason;
    private JButton jLabel_presee;
    private JButton jLabel_clear;
    private JRadioButton jrb_qj, jrb_cd;
    private ButtonGroup buttonGroup;
    private JTextArea jta_today;
    private JTextArea jta_presee_all;

    private JRadioButton jrb_t1, jrb_t2, jrb_t3;
    private ButtonGroup buttonGroup_t;

    private JButton record_sure;
    private JButton record_cancel;
    private JButton btn_getTime;

    private Student student;
    private JTextField jtf_score = new JTextField();
    private JTextField jtf_dis = new JTextField();
    DatePicker datePicker = DatePickerUtil.getDatePicker();
    private JFrame jFrame;
    private GetStuInfoImpl stuInfo = new GetStuInfoImpl();
    private String mode;

    //创建容器
    Container container = getContentPane();

    public Att_and_Dis_Dialog(JFrame jFrame, String mode, Student student) {
        super(jFrame, true);
        setBackground(Color.WHITE);
        this.jFrame = jFrame;
        this.student = student;
        this.mode = mode;
        setLayout(null);
        if ("record_att".equals(mode)) {
            handleDialog("考勤");
        } else if ("record_dis".equals(mode)) {
            handleDialog("违纪");
        }

    }

    private void handleDialog(String mode) {
        this.setTitle(" 记录【 " + student.getStu_name() + " 】的" + mode + "信息");
        jLabel_id = new JLabel();
        jLabel_id.setForeground(Color.BLUE);
        jLabel_id.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_id.setBounds(30, 10, 200, 30);
        jLabel_id.setText("编号：" + student.getStu_id());
        container.add(jLabel_id);

        jLabel_name = new JLabel();
        jLabel_name.setForeground(Color.BLUE);
        jLabel_name.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_name.setBounds(30, 60, 200, 30);
        jLabel_name.setText("姓名：" + student.getStu_name());
        container.add(jLabel_name);

        jLabel_att = new JLabel();
        jLabel_att.setForeground(Color.BLUE);
        jLabel_att.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_att.setBounds(30, 110, 200, 30);
        jLabel_att.setText("今日" + mode);
        container.add(jLabel_att);


        jrb_t1 = new JRadioButton("上");
        jrb_t2 = new JRadioButton("下");
        jrb_t3 = new JRadioButton("晚");

        jrb_t1.setForeground(Color.RED);
        jrb_t1.setFont(new Font("楷体", Font.BOLD, 18));
        jrb_t1.setBounds(30, 210, 60, 30);

        jrb_t2.setForeground(Color.RED);
        jrb_t2.setFont(new Font("楷体", Font.BOLD, 18));
        jrb_t2.setBounds(120, 210, 60, 30);

        jrb_t3.setForeground(Color.RED);
        jrb_t3.setFont(new Font("楷体", Font.BOLD, 18));
        jrb_t3.setBounds(210, 210, 60, 30);

        buttonGroup_t = new ButtonGroup();
        buttonGroup_t.add(jrb_t1);
        buttonGroup_t.add(jrb_t2);
        buttonGroup_t.add(jrb_t3);
        buttonGroup_t.setSelected(jrb_t1.getModel(), true);
        container.add(jrb_t1);
        container.add(jrb_t2);
        container.add(jrb_t3);


        jLabel_score = new JLabel();
        jLabel_score.setForeground(Color.BLUE);
        jLabel_score.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_score.setBounds(30, 260, 200, 30);
        jLabel_score.setText("扣分");
        container.add(jLabel_score);

        jtf_score.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_score.setText("2");
        jtf_score.setBounds(80, 260, 50, 30);
        container.add(jtf_score);
        if ("考勤".equals(mode)) {
            jrb_qj = new JRadioButton("请假");
            jrb_cd = new JRadioButton("迟到");
            buttonGroup = new ButtonGroup();
            buttonGroup.add(jrb_cd);
            buttonGroup.setSelected(jrb_cd.getModel(), true);
            buttonGroup.add(jrb_qj);

            jrb_cd.setForeground(Color.ORANGE);
            jrb_cd.setFont(new Font("楷体", Font.BOLD, 18));
            jrb_cd.setBounds(30, 160, 100, 30);

            jrb_qj.setForeground(Color.ORANGE);
            jrb_qj.setFont(new Font("楷体", Font.BOLD, 18));
            jrb_qj.setBounds(150, 160, 150, 30);
            container.add(jrb_cd);
            container.add(jrb_qj);
        } else if ("违纪".equals(mode)) {
            jLabel_dis_reason = new JLabel();
            jLabel_dis_reason.setForeground(Color.BLUE);
            jLabel_dis_reason.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_dis_reason.setBounds(30, 160, 100, 30);
            jLabel_dis_reason.setText("违纪原因");
            container.add(jLabel_dis_reason);

            jtf_dis.setFont(new Font("楷体", Font.BOLD, 18));
            jtf_dis.setText("");
            jtf_dis.setBounds(130, 160, 220, 30);
            container.add(jtf_dis);
        }

        datePicker.setBounds(30, 310, 180, 30);
        container.add(datePicker);

        btn_getTime = new JButton("设置时间");
        btn_getTime.setForeground(Color.RED);
        btn_getTime.setFont(new Font("楷体", Font.BOLD, 18));
        btn_getTime.setBounds(230, 310, 120, 30);
        container.add(btn_getTime);

        jLabel_presee = new JButton("预览");
        jLabel_presee.setForeground(Color.BLUE);
        jLabel_presee.setFont(new Font("楷体", Font.BOLD, 16));
        jLabel_presee.setBounds(80, 360, 80, 30);
        container.add(jLabel_presee);

        jLabel_clear = new JButton("清除");
        jLabel_clear.setForeground(Color.BLUE);
        jLabel_clear.setFont(new Font("楷体", Font.BOLD, 16));
        jLabel_clear.setBounds(200, 360, 80, 30);
        container.add(jLabel_clear);

        jta_presee_all = new JTextArea();
        jta_today = new JTextArea();
        jta_presee_all.setForeground(Color.BLUE);
        jta_presee_all.setBackground(Color.white);
        jta_presee_all.setFont(new Font("楷体", Font.BOLD, 15));
        jta_presee_all.setBounds(30, 410, 330, 80);
        jta_presee_all.setEditable(false);
        container.add(jta_presee_all);

        record_sure = new JButton("确认修改");
        record_sure.setForeground(Color.BLACK);
        record_sure.setFont(new Font("楷体", Font.BOLD, 18));
        record_sure.setBounds(50, 520, 120, 30);
        container.add(record_sure);

        record_cancel = new JButton("取消");
        record_cancel.setForeground(Color.RED);
        record_cancel.setFont(new Font("楷体", Font.BOLD, 18));
        record_cancel.setBounds(220, 520, 120, 30);
        container.add(record_cancel);
        myEvent();
    }

    private String time = "";
    private String record_info = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private void myEvent() {

        btn_getTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTime();
            }
        });

        jLabel_presee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTime();
                String stu_name = student.getStu_name();
                String t_att = "";
                if (jrb_t1.isSelected()) {
                    t_att = "上";
                }
                if (jrb_t2.isSelected()) {
                    t_att = "下";
                }
                if (jrb_t3.isSelected()) {
                    t_att = "晚";
                }
                if ("record_att".equals(mode)) {
                    String reson_att = jrb_qj.isSelected() ? "请假" : "迟到";
                    jta_today.setText(" " + stu_name + " " + t_att + " " + reson_att + " [" + time.substring(0, 10) + "] " + time.substring(11, 16));
                    jta_presee_all.setText(jta_today.getText()+"\n\n 扣分:"+jtf_score.getText());
                } else if ("record_dis".equals(mode)) {
                    String disText = jtf_dis.getText();
                    jta_today.setText(" " + stu_name + " " + t_att + " " + disText + " [" + time.substring(0, 10) + "] " + time.substring(11, 16));
                    jta_presee_all.setText(jta_today.getText()+"\n\n 扣分:"+jtf_score.getText());
                }
            }
        });
        jLabel_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.setEnabled(true);
                jta_today.setText("");
                time = "";
            }
        });

        record_sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String todayText = jta_today.getText();
                if ("".equals(todayText) || todayText == null){
                    JOptionPane.showMessageDialog(jFrame,"请先预览信息！");
                    return;
                }
                Student one = stuInfo.getOne(student.getStu_id());
                if ("record_att".equals(mode)) {
                    String stu_attendance = one.getStu_attendance();
                    Student handleStu = handleStu(one, stu_attendance);
                    handleStu.setStu_attendance(record_info);
                    int res = stuInfo.updateAttendence_and_Score(handleStu);
                    if (res >0){
                        JOptionPane.showMessageDialog(jFrame,"考勤信息更新成功！");
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(jFrame,"更新失败，检查数据");
                    }

                } else if ("record_dis".equals(mode)) {
                    String stu_discipline = one.getStu_discipline();
                    Student handleStu = handleStu(one, stu_discipline);
                    handleStu.setStu_discipline(record_info);
                    int res = stuInfo.updateDispline_and_Score(handleStu);
                    if (res >0){
                        JOptionPane.showMessageDialog(jFrame,"违纪信息更新成功！");
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(jFrame,"更新失败，检查数据");
                    }
                }

            }
        });

        record_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "未保存,操作取消！");
                dispose();
            }
        });
    }

    private void setTime() {
        Date value = (Date) datePicker.getValue();
        time = value.toString();
        time = sdf.format(value);
        datePicker.setEnabled(false);
    }


    private Student handleStu(Student one,String info) {
        Student newStu = new Student();
        newStu.setStu_id(student.getStu_id());

        if (info == null || "".equals(info)) {
            record_info = jta_today.getText();
        } else {
            int start = info.lastIndexOf('[');
            int end = info.lastIndexOf(']');
            String res_time = info.substring(start + 1, end);
            String today = sdf.format(new Date()).substring(0, 10);
            if(today.equals(res_time)){
                record_info = info + " | "+ jta_today.getText();
            }else {
                record_info = info + " & "+ jta_today.getText();
            }
        }

        String scoreText = jtf_score.getText();
        if(scoreText == null || "".equals(scoreText) || "0".equals(scoreText)){
            newStu.setStu_totalScore(student.getStu_totalScore());
        }else {
            try {
                int score = Integer.parseInt(scoreText);
                int nowScore = Integer.parseInt(one.getStu_totalScore()) - score;
                newStu.setStu_totalScore(nowScore+"");
            }catch (Exception e){
                JOptionPane.showMessageDialog(jFrame,"分数输入异常,请重新输入!");
            }
        }
        return newStu;
    }

}

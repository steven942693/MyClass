package com.offcn.dialog;

import com.offcn.bean.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDisciplineDialog extends JDialog {
    private JLabel jLabel_id;
    private JLabel jLabel_name;
    private JLabel jLabel_att;
    private JLabel jLabel_score;
    private JButton jButton_show_calenda;
    private JTextArea jta_today;
    private JTextArea jta_history;
    private JScrollPane jsp;
    private Student student;
    private StringBuilder sb_today = new StringBuilder();
    private StringBuilder sb_history = new StringBuilder();
    private String stu__discipline;
    //创建容器
    Container container = getContentPane();
    public ShowDisciplineDialog(JFrame jFrame, String mode, Student student){
        super(jFrame,true);
        setLayout(null);
        this.student = student;
        this.setTitle("【 "+student.getStu_name()+" 】的违纪信息");
        setBackground(Color.WHITE);

        jLabel_id = new JLabel();
        jLabel_id.setForeground(Color.BLUE);
        jLabel_id.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_id.setBounds(30, 10, 200, 30);
        jLabel_id.setText("编号："+student.getStu_id());
        container.add(jLabel_id);

        jLabel_name = new JLabel();
        jLabel_name.setForeground(Color.BLUE);
        jLabel_name.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_name.setBounds(30, 60, 200, 30);
        jLabel_name.setText("姓名："+student.getStu_name());
        container.add(jLabel_name);

        jLabel_att = new JLabel();
        jLabel_att.setForeground(Color.BLUE);
        jLabel_att.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_att.setBounds(30, 110, 200, 30);
        jLabel_att.setText("今日违纪");
        container.add(jLabel_att);

        jta_today = new JTextArea();
        jta_today.setForeground(Color.BLUE);
        jta_today.setFont(new Font("楷体", Font.BOLD, 15));
        jta_today.setBounds(30, 160, 330, 120);
        jta_today.setEditable(false);
        container.add(jta_today);
        today_att();

        jLabel_score = new JLabel();
        jLabel_score.setForeground(Color.BLUE);
        jLabel_score.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_score.setBounds(30, 300, 200, 30);
        jLabel_score.setText("总分："+student.getStu_totalScore());
        container.add(jLabel_score);

        jButton_show_calenda = new JButton("历史违纪");
        jButton_show_calenda.setForeground(Color.BLUE);
        jButton_show_calenda.setFont(new Font("楷体", Font.BOLD, 18));
        jButton_show_calenda.setBounds(130, 350, 160, 30);
        container.add(jButton_show_calenda);


        jta_history = new JTextArea();
        jta_history.setForeground(Color.BLUE);
        jta_history.setFont(new Font("楷体", Font.BOLD, 15));
        jta_history.setEditable(false);

        jsp = new JScrollPane(jta_history);
        jsp.setForeground(Color.BLUE);
        jsp.setFont(new Font("楷体", Font.BOLD, 16));
        jsp.setBounds(20, 400, 340, 140);
        container.add(jsp);
        jta_history.setEditable(false);
        jsp.setVisible(false);
        myEvent();

    }

    private void myEvent(){
        jButton_show_calenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stu__discipline == null || "".equals(stu__discipline)){
                    return;
                }
                jta_history.setText("");
                jta_history.setText(sb_history.toString());
                jsp.setVisible(true);
            }
        });
    }

    private void today_att(){
        sb_history.append("====================================\n");
        boolean flag = false;
        Date td = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String real_td = sdf.format(td);
        stu__discipline = student.getStu_discipline();
        if (stu__discipline == null || "".equals(stu__discipline)){
            jta_today.setText("无违纪");
            return;
        }
        //按天分割
        String[] oneday = stu__discipline.split("\\&", -1);
        //按时段分割
        for (String t3s : oneday) {
            String[] t1 = t3s.split("\\|", -1);
            for (String t : t1) {
                int start = t.lastIndexOf('[');
                int end = t.lastIndexOf(']');
                String time = t.substring(start+1, end);
                if (real_td.equals(time)){
                    sb_today.append(t+"\n");
                    sb_history.append(t+"\n");
                    flag = true;
                }else{
                    sb_history.append(t+"\n");
                }
            }
            sb_history.append("====================================\n");
        }
        if(flag){
            jta_today.setText(sb_today.toString());
        }else{
            jta_today.setText("无违纪");
        }
    }
}

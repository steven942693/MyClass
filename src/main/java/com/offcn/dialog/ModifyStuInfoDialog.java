package com.offcn.dialog;

import com.offcn.bean.Student;
import com.offcn.service.impl.GetStuInfoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModifyStuInfoDialog extends JDialog {
    private JFrame jFrame;
    private Student student;
    private JLabel jLabel_id;
    private JLabel jLabel_name;
    private JLabel jLabel_score;
    private JLabel jLabel_state;
    private JLabel jLabel_phone;
    private JLabel jLabel_school;
    private JLabel jLabel_major;
    private JLabel jLabel_quali;
    private JLabel jLabel_class;
    private JLabel jLabel_reward;
    private JButton modi_sure;
    private JButton modi_cancel;
    private JScrollPane jsp;


    private JTextField jtf_score = new JTextField();
    private JTextField jtf_state = new JTextField();
    private JTextField jtf_phone = new JTextField();
    private JTextField jtf_school = new JTextField();
    private JTextField jtf_major = new JTextField();
    private JTextField jtf_quali = new JTextField();
    private JTextField jtf_reward = new JTextField();
    //创建容器
    Container container = getContentPane();

    public ModifyStuInfoDialog(JFrame frame, Student student) {
        super(frame, true);
        jFrame = frame;
        container.setLayout(null);
        this.student = student;
        this.setTitle("修改【 " + student.getStu_name() + " 】的信息");
        setBackground(Color.WHITE);

        jLabel_class = new JLabel();
        jLabel_class.setForeground(Color.BLUE);
        jLabel_class.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_class.setBounds(30, 10, 200, 30);
        jLabel_class.setText("班级：" + student.getStu_class());
        container.add(jLabel_class);

        jLabel_id = new JLabel();
        jLabel_id.setForeground(Color.BLUE);
        jLabel_id.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_id.setBounds(30, 60, 200, 30);
        jLabel_id.setText("编号：" + student.getStu_id());
        container.add(jLabel_id);

        jLabel_name = new JLabel();
        jLabel_name.setForeground(Color.BLUE);
        jLabel_name.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_name.setBounds(30, 110, 200, 30);
        jLabel_name.setText("姓名：" + student.getStu_name());
        container.add(jLabel_name);

        jLabel_score = new JLabel();
        jLabel_score.setForeground(Color.BLUE);
        jLabel_score.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_score.setBounds(30, 160, 200, 30);
        jLabel_score.setText("总分");
        container.add(jLabel_score);

        jLabel_state = new JLabel();
        jLabel_state.setForeground(Color.BLUE);
        jLabel_state.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_state.setBounds(30, 210, 200, 30);
        jLabel_state.setText("学习状态");
        container.add(jLabel_state);

        jLabel_phone = new JLabel();
        jLabel_phone.setForeground(Color.BLUE);
        jLabel_phone.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_phone.setBounds(30, 260, 200, 30);
        jLabel_phone.setText("手机号");
        container.add(jLabel_phone);

        jLabel_school = new JLabel();
        jLabel_school.setForeground(Color.BLUE);
        jLabel_school.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_school.setBounds(30, 310, 200, 30);
        jLabel_school.setText("学校");
        container.add(jLabel_school);

        jLabel_major = new JLabel();
        jLabel_major.setForeground(Color.BLUE);
        jLabel_major.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_major.setBounds(30, 360, 200, 30);
        jLabel_major.setText("专业");
        container.add(jLabel_major);


        jLabel_quali = new JLabel();
        jLabel_quali.setForeground(Color.BLUE);
        jLabel_quali.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_quali.setBounds(30, 410, 200, 30);
        jLabel_quali.setText("专/本");
        container.add(jLabel_quali);

        jLabel_reward = new JLabel();
        jLabel_reward.setForeground(Color.BLUE);
        jLabel_reward.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_reward.setBounds(30, 460, 200, 30);
        jLabel_reward.setText("加分原因");
        container.add(jLabel_reward);

        modi_sure = new JButton("确认修改");
        modi_sure.setForeground(Color.BLACK);
        modi_sure.setFont(new Font("楷体", Font.BOLD, 18));
        modi_sure.setBounds(50, 520, 120, 30);
        container.add(modi_sure);

        modi_cancel = new JButton("取消");
        modi_cancel.setForeground(Color.RED);
        modi_cancel.setFont(new Font("楷体", Font.BOLD, 18));
        modi_cancel.setBounds(220, 520, 120, 30);
        container.add(modi_cancel);

        jtf_score.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_score.setText(student.getStu_totalScore());
        jtf_score.setBounds(80, 160, 50, 30);
        container.add(jtf_score);

        jtf_state.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_state.setText(student.getStu_remark());
        jtf_state.setBounds(115, 210, 50, 30);
        container.add(jtf_state);

        jtf_phone.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_phone.setText(student.getStu_phone());
        jtf_phone.setBounds(90, 260, 150, 30);
        container.add(jtf_phone);

        jtf_school.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_school.setText(student.getStu_graduate_school());
        jtf_school.setBounds(80, 310, 280, 30);
        container.add(jtf_school);

        jtf_major.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_major.setText(student.getStu_major());
        jtf_major.setBounds(85, 360, 275, 30);
        container.add(jtf_major);

        jtf_quali.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_quali.setText(student.getStu_qualifications());
        jtf_quali.setBounds(90, 410, 50, 30);
        container.add(jtf_quali);

        jtf_reward.setFont(new Font("楷体", Font.BOLD, 18));
        jtf_reward.setText(null);
        jtf_reward.setBounds(110, 460, 250, 30);
        container.add(jtf_reward);
        myEvent();
    }

    private void myEvent() {
        modi_sure.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scoreText = jtf_score.getText();
                try {
                    Integer.parseInt(scoreText);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(jFrame, "分数只能是整数！");
                    return;
                }
                String stateText = jtf_state.getText();
                String phoneText = jtf_phone.getText();
                String schoolText = jtf_school.getText();
                String majorText = jtf_major.getText();
                String qualiText = jtf_quali.getText();
                String rewardText = jtf_reward.getText();
                {
                    GetStuInfoImpl stuInfo = new GetStuInfoImpl();
                    if ("".equals(rewardText) || rewardText == null) {
                        rewardText = null;
                    } else {
                        Student one = stuInfo.getOne(student.getStu_id());
                        String stu_reward = one.getStu_reward();
                        if (stu_reward == null || "".equals(stu_reward)) {

                        } else {
                            rewardText = stu_reward + " | " + rewardText;
                        }
                    }
                    Student newStu = new Student();
                    newStu.setStu_totalScore(scoreText);
                    newStu.setStu_phone(phoneText);
                    newStu.setStu_id(student.getStu_id());
                    newStu.setStu_graduate_school(schoolText);
                    newStu.setStu_major(majorText);
                    newStu.setStu_qualifications(qualiText);
                    newStu.setStu_remark(stateText);
                    newStu.setStu_reward(rewardText);

                    int res = stuInfo.updateStuInfo(newStu);
                    if (res > 0) {
                        JOptionPane.showMessageDialog(jFrame, "数据更新成功！");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(jFrame, "更新失败，请检查数据格式！");
                    }
                }
            }
        });

        modi_cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "未保存,操作取消！");
                dispose();
            }
        });
    }
}

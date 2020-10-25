package com.offcn.dialog;


import com.offcn.bean.Student;
import com.offcn.cache.JedisForCount;
import com.offcn.service.impl.GetStuInfoImpl;
import com.offcn.view.OmnibusView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.offcn.view.OmnibusView.fresh_seats;

public class Edit_Seats_Dialog extends JDialog {
    private JTextField jtf_input_name = new JTextField();
    private JButton edit_sure = new JButton("确认");
    private JButton edit_cancel = new JButton("取消");
    GetStuInfoImpl stuInfo = new GetStuInfoImpl();
    //创建容器
    Container container = getContentPane();
    private JFrame jFrame;
    private Integer index;

    private JLabel jLabel_id;
    private JLabel jLabel_name;
    private JLabel jLabel_score;
    private JLabel jLabel_state;
    private JLabel jLabel_phone;
    private JLabel jLabel_school;
    private JLabel jLabel_major;
    private JLabel jLabel_quali;
    private JLabel jLabel_class;

    public Edit_Seats_Dialog(final JFrame jFrame, String mode, String name, final int index) {
        super(jFrame, true);
        setBackground(Color.WHITE);
        container.setLayout(null);
        this.jFrame = jFrame;
        this.index = index;
        if ("edit".equals(mode)) {
            this.setTitle("编辑座位");
            jLabel_name = new JLabel();
            jLabel_name.setForeground(Color.BLUE);
            jLabel_name.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_name.setBounds(30, 20, 100, 30);
            jLabel_name.setText("输入姓名");
            container.add(jLabel_name);

            jtf_input_name.setFont(new Font("楷体", Font.BOLD, 18));
            jtf_input_name.setText(name);
            jtf_input_name.setBounds(30, 50, 200, 30);
            container.add(jtf_input_name);


            edit_sure.setForeground(Color.BLACK);
            edit_sure.setFont(new Font("楷体", Font.BOLD, 18));
            edit_sure.setBounds(50, 100, 80, 30);
            container.add(edit_sure);


            edit_cancel.setForeground(Color.RED);
            edit_cancel.setFont(new Font("楷体", Font.BOLD, 18));
            edit_cancel.setBounds(150, 100, 80, 30);
            container.add(edit_cancel);
        } else if ("ask".equals(mode)) {
            this.setTitle("选择清除模式");
            final JButton clear_one = new JButton("清除指定编号");
            clear_one.setForeground(Color.BLUE);
            clear_one.setFont(new Font("楷体", Font.BOLD, 18));
            clear_one.setBounds(50, 30, 200, 30);
            container.add(clear_one);

            JButton clearr_all = new JButton("清除全部座位");
            clearr_all.setForeground(Color.RED);
            clearr_all.setFont(new Font("楷体", Font.BOLD, 18));
            clearr_all.setBounds(50, 100, 200, 30);
            container.add(clearr_all);

            clear_one.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Edit_Seats_Dialog clearone = new Edit_Seats_Dialog(OmnibusView.frame, "clearone", "", 1001);
                    clearone.setSize(300,200);
                    clearone.setLocationRelativeTo(null);
                    clearone.setVisible(true);
                }
            });

            clearr_all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int ask = JOptionPane.showConfirmDialog(jFrame, "是否清除所有的座位信息？此操作不可恢复！", "确认清除？", 1);
                    if (ask == JOptionPane.YES_OPTION) {
                        Long clear_res = JedisForCount.clear_allseats();
                        if (clear_res > 0) {
                            JOptionPane.showMessageDialog(jFrame, "清除成功！");
                            fresh_seats();
                        } else {
                            JOptionPane.showMessageDialog(jFrame, "清除失败！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jFrame, "取消操作！");
                    }
                }
            });


        }else if("clearone".equals(mode)){
            this.setTitle("清除指定编号");

            JLabel title = new JLabel("输入自然编号");
            title.setForeground(Color.BLUE);
            title.setFont(new Font("楷体", Font.BOLD, 18));
            title.setBounds(30, 20, 200, 30);
            container.add(title);

            final JTextField jtf_index = new JTextField();
            jtf_index.setFont(new Font("楷体", Font.BOLD, 18));
            jtf_index.setBounds(30, 60, 100, 30);
            container.add(jtf_index);

            JButton one_sure = new JButton("确定");
            one_sure.setFont(new Font("楷体", Font.BOLD, 18));
            one_sure.setBounds(50, 110, 80, 30);
            container.add(one_sure);

            JButton one_cancle = new JButton("取消");
            one_cancle.setFont(new Font("楷体", Font.BOLD, 18));
            one_cancle.setBounds(150, 110, 80, 30);
            container.add(one_cancle);

            one_sure.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String index = jtf_index.getText();
                    try {
                        int parseInt = Integer.parseInt(index);
                        Long res = JedisForCount.clear_one(parseInt);
                        if (res>0){
                            JOptionPane.showMessageDialog(jFrame,"清除成功！");
                            fresh_seats();
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(jFrame,"清除失败,可能是没有该编号！");
                        }

                    }catch (Exception we){
                        JOptionPane.showMessageDialog(jFrame,"数据格式错误！");
                    }
                }
            });
            one_cancle.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(jFrame,"操作取消！");
                    dispose();
                }
            });


        } else {
            Student oneByName = stuInfo.getOneByName(name.trim());
            if (oneByName == null) {
                return;
            }

            this.setTitle("查看【" + name + "】的信息");

            jLabel_id = new JLabel();
            jLabel_id.setForeground(Color.BLUE);
            jLabel_id.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_id.setBounds(30, 60, 300, 30);
            jLabel_id.setText("编号：" + oneByName.getStu_id());
            container.add(jLabel_id);

            jLabel_name = new JLabel();
            jLabel_name.setForeground(Color.BLUE);
            jLabel_name.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_name.setBounds(30, 110, 300, 30);
            jLabel_name.setText("姓名：" + oneByName.getStu_name());
            container.add(jLabel_name);

            jLabel_score = new JLabel();
            jLabel_score.setForeground(Color.BLUE);
            jLabel_score.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_score.setBounds(30, 160, 300, 30);
            jLabel_score.setText("总分：" + oneByName.getStu_totalScore());
            container.add(jLabel_score);

            jLabel_state = new JLabel();
            jLabel_state.setForeground(Color.BLUE);
            jLabel_state.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_state.setBounds(30, 210, 300, 30);
            jLabel_state.setText("学习状态：" + oneByName.getStu_remark());
            container.add(jLabel_state);

            jLabel_phone = new JLabel();
            jLabel_phone.setForeground(Color.BLUE);
            jLabel_phone.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_phone.setBounds(30, 260, 300, 30);
            jLabel_phone.setText("手机号：" + oneByName.getStu_phone());
            container.add(jLabel_phone);

            jLabel_school = new JLabel();
            jLabel_school.setForeground(Color.BLUE);
            jLabel_school.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_school.setBounds(30, 310, 500, 30);
            jLabel_school.setText("学校：" + oneByName.getStu_graduate_school());
            container.add(jLabel_school);

            jLabel_major = new JLabel();
            jLabel_major.setForeground(Color.BLUE);
            jLabel_major.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_major.setBounds(30, 360, 500, 30);
            jLabel_major.setText("专业：" + oneByName.getStu_major());
            container.add(jLabel_major);


            jLabel_quali = new JLabel();
            jLabel_quali.setForeground(Color.BLUE);
            jLabel_quali.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_quali.setBounds(30, 410, 300, 30);
            jLabel_quali.setText("专/本：" + oneByName.getStu_qualifications());
            container.add(jLabel_quali);

            jLabel_class = new JLabel();
            jLabel_class.setForeground(Color.BLUE);
            jLabel_class.setFont(new Font("楷体", Font.BOLD, 18));
            jLabel_class.setBounds(30, 10, 300, 30);
            jLabel_class.setText("班级：" + oneByName.getStu_class());
            container.add(jLabel_class);
        }
        editEvent();
    }

    private void editEvent() {
        edit_sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameText = jtf_input_name.getText();
                Student oneByName = stuInfo.getOneByName(nameText.trim());
                if (oneByName == null) {
                    JOptionPane.showMessageDialog(jFrame, "该学员不存在，请检查姓名！");
                    return;
                }
                String seat_out = index + "=" + nameText;
                Long res = JedisForCount.writeSeat(index + "", seat_out);
                if (res > 0) {
                    JOptionPane.showMessageDialog(jFrame, "编辑成功");
                    fresh_seats();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(jFrame, "编辑保存失败！");
                }
            }
        });

        edit_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "未保存，操作取消");
                dispose();
            }
        });
    }
}

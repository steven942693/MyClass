package com.offcn.view;

import com.offcn.bean.Student;
import com.offcn.cache.JedisForCount;
import com.offcn.dialog.Att_and_Dis_Dialog;
import com.offcn.dialog.Edit_Seats_Dialog;
import com.offcn.dialog.ModifyStuInfoDialog;
import com.offcn.service.impl.GetStuInfoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.sun.deploy.util.DeployUIManager.setLookAndFeel;


public class OmnibusView {


    private static String edit_mode;
    private JPanel jp_up;
    private JPanel jp_down;
    private JPanel jp_left;
    private JPanel jp_right;
    public static JPanel jp_center;
    private JPanel jp_nav_btn;
    public static JFrame frame;
    private JButton btn_AllStu;
    private JButton btn_record;
    private JButton btn_seats;
    private JButton btn_roll;
    private JButton btn_export;
    private JButton btn_modify;
    private JButton btn_fresh;
    private JButton record_att;
    private JButton record_dis;

    private JScrollPane jsp;
    private JScrollPane jsp_modi;
    private List<Student> allStu;
    private JTable jTable_allStu;
    private String[] columnNames;
    private Object[][] stuInfo;
    private Dimension frameSize;
    private DefaultTableModel dtm;
    private GetStuInfoImpl stu;
    private String fresh_flag = "ffff";
    private String allStu_fresh_flag = "ffff";
    private JLabel jLabel_welcome = new JLabel("中公优就业考勤系统");
    private JLabel jLabel_roll_stuName;
    public static JLabel jLabel_nowTime = new JLabel();

    private JLabel roll_name;
    private JLabel roll_age;
    private JLabel roll_school;
    private JLabel roll_major;
    private JLabel roll_welcome;
    private JLabel roll_welcome_value;
    private JLabel roll_times;
    private JButton roll_start;
    private boolean roll_flag = false;
    private SimpleDateFormat sdf;
    public static Map<Integer,String> seat_map;
    private String notice_text  = "";

    int screenHeight;
    int screenWidth;

    public OmnibusView(List<Student> allStu) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        this.allStu = allStu;
        //设置皮肤
        try {
//            去除顶部设置按钮
            UIManager.put("RootPane.setupButtonVisible",false);
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            //TODO exception
        }
//        setLookAndFeel();
//        javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
        frame = new JFrame("考勤管理系统");
        frame.setFont(new Font("楷体", Font.PLAIN, 16));
        frame.setLayout(new BorderLayout());
        //OmnibusView.class.getClassLoader().getResource("logo.jpg")
        Image image = Toolkit.getDefaultToolkit().getImage("E:\\MyProject\\MyClass\\src\\main\\resources\\logo.jpg");
        frame.setIconImage(image);
        Toolkit kit = Toolkit.getDefaultToolkit();
        //获取系统的分辨率
        Dimension screenSize = kit.getScreenSize();
        //获取系统分辨率的高，768
        screenHeight = screenSize.height;
        //获取系统分辨率的宽，1366
        screenWidth = screenSize.width;
        //设置窗口的大小,以比例进显示
        frame.setSize((int) (screenWidth * 0.75) + 20, (int) (screenHeight * 0.85) + 140);
        //设置窗口的最小尺寸
        frame.setMinimumSize(new Dimension((int) (screenWidth * 0.6) + 20, (int) (screenHeight * 0.7) + 140));
        //设置窗口课件
        frame.setVisible(true);
        //设置窗口大小不可改变
        frame.setResizable(true);
        //设置窗口居中,注意写的顺序，先设置窗口的大小
        frame.setLocationRelativeTo(null);
        frameSize = frame.getSize();
        jp_up = new JPanel();
        jp_down = new JPanel();
        jp_left = new JPanel();
        jp_right = new JPanel();
        jp_center = new JPanel();
        jp_nav_btn = new JPanel();
        jp_center.setBorder(BorderFactory.createEtchedBorder());
        jLabel_welcome.setFont(new Font("楷体", Font.ITALIC, (int) (frameSize.width * 0.1)));
        jLabel_welcome.setAutoscrolls(true);
        //0为居中显示
        jLabel_welcome.setHorizontalAlignment(0);

        jLabel_nowTime.setFont(new Font("楷体", Font.BOLD, 18));
        jLabel_nowTime.setAutoscrolls(true);


        jp_center.setLayout(new BorderLayout());
        jp_center.add(jLabel_welcome, BorderLayout.CENTER);


        btn_AllStu = new JButton("信息总览");
        btn_AllStu.setForeground(Color.BLUE);
        btn_AllStu.setFont(new Font("楷体", Font.BOLD, 16));
        btn_seats = new JButton("座位表");
        btn_seats.setForeground(Color.BLUE);
        btn_seats.setFont(new Font("楷体", Font.BOLD, 16));
        btn_record = new JButton("考勤/违纪");
        btn_record.setForeground(Color.BLUE);
        btn_record.setFont(new Font("楷体", Font.BOLD, 16));
        btn_roll = new JButton("点名提问");
        btn_roll.setForeground(Color.BLUE);
        btn_roll.setFont(new Font("楷体", Font.BOLD, 16));

        btn_export = new JButton("导出记录");
        btn_export.setForeground(Color.magenta);
        btn_export.setFont(new Font("楷体", Font.BOLD, 16));
        btn_modify = new JButton("修改信息");
        btn_modify.setForeground(Color.RED);
        btn_modify.setFont(new Font("楷体", Font.BOLD, 16));
        btn_fresh = new JButton("刷新数据");
        btn_fresh.setForeground(Color.GREEN);
        btn_fresh.setFont(new Font("楷体", Font.BOLD, 16));
        stu = new GetStuInfoImpl();

        record_att = new JButton("考勤记录");
        record_att.setFont(new Font("楷体", Font.BOLD, 16));
        record_att.setForeground(Color.magenta);
        record_dis = new JButton("违纪记录");
        record_dis.setForeground(Color.RED);
        record_dis.setFont(new Font("楷体", Font.BOLD, 16));

        frame.add(jp_up, BorderLayout.NORTH);
//        jp_up.setLayout(new FlowLayout((int) (frameSize.width * 0.8), 20, 5));
        jp_up.setLayout(new BorderLayout());
        jp_up.add(jp_nav_btn, BorderLayout.WEST);
        jp_nav_btn.setLayout(new FlowLayout((int) (frameSize.width * 0.8), 20, 5));
        jp_nav_btn.add(btn_AllStu);
        jp_nav_btn.add(btn_seats);
        jp_nav_btn.add(btn_record);
        jp_nav_btn.add(btn_roll);
        jp_up.add(jLabel_nowTime);
        jLabel_nowTime.setHorizontalAlignment(4);


        frame.add(jp_center, BorderLayout.CENTER);
        jp_left.setBorder(BorderFactory.createEtchedBorder());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        myEvent();
    }

    private void myEvent() {
        btn_seats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fresh_flag = "btn_seats";
                frame.remove(jp_center);
                frame.remove(jp_left);
                frame.remove(jp_down);
                frame.remove(jp_right);
                show_seats();
                frame.repaint();
            }
        });

        btn_roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fresh_flag = "btn_roll";
                jp_center.removeAll();
                jp_center.repaint();
                jp_left.removeAll();
                jp_left.repaint();
                frame.remove(jp_center);
                frame.repaint();
                frame.remove(jp_left);
                frame.repaint();
                frame.remove(jp_down);
                jp_down.removeAll();
                frame.remove(jp_right);
                jp_right.removeAll();
                show_rollcaller();
            }
        });
        btn_record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fresh_flag = "btn_record";
                allStu_fresh_flag = "record_att";
                jp_center.removeAll();
                jp_center.repaint();
                jp_left.removeAll();
                jp_left.repaint();
                frame.remove(jp_center);
                frame.remove(jp_left);
                frame.remove(jp_down);
                frame.remove(jp_right);
                frame.repaint();
                att_and_dis();
            }
        });

        record_dis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "记录违纪";
                jLabel_notice.setText(notice_text);
                allStu_fresh_flag = "record_dis";
            }
        });

        record_att.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "记录考勤";
                jLabel_notice.setText(notice_text);
                allStu_fresh_flag = "record_att";
            }
        });

        btn_modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "修改信息";
                jLabel_notice.setText(notice_text);
                btn_modify.setForeground(Color.BLUE);
                allStu_fresh_flag = "modi_stu";
                fresh_method_modiStuInfo();
            }
        });


        btn_export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        notice_text = "导出记录";
                        jLabel_notice.setText(notice_text);
                        JFileChooser jfc = new JFileChooser();
                        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        jfc.setMultiSelectionEnabled(false);
                        jfc.showDialog(frame, "选择保存路径");
                        File selectedFile = jfc.getSelectedFile();
                        if (selectedFile == null) {
                            JOptionPane.showMessageDialog(frame, "未选择任何文件夹");
                            return;
                        }
                        File[] files = selectedFile.listFiles();
                        if (files.length != 0) {
                            JOptionPane.showMessageDialog(frame, "文件夹非空！");
                            return;
                        }
                        if (selectedFile != null) {
//                            JOptionPane.showMessageDialog(frame,"正在导出······请稍等！");
                            String path = selectedFile.getPath();
                            try {
                                exportRecord(path);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        btn_fresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "刷新信息";
                jLabel_notice.setText(notice_text);
                if ("all_stu".equals(allStu_fresh_flag)) {
                    fresh_method_allStuInfo();
                } else if ("modi_stu".equals(allStu_fresh_flag)) {
                    fresh_method_modiStuInfo();
                }
            }
        });


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frameSize = frame.getSize();
                jLabel_notice.setBounds(20, (int) frameSize.height/2 + frameSize.height/3-20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
                /*if ("all_stu".equals(allStu_fresh_flag)) {
                    fresh_method_allStuInfo();
                } else if ("modi_stu".equals(allStu_fresh_flag)) {
                    fresh_method_modiStuInfo();
                }*/
                switch (fresh_flag) {
                    case "all_stu":
                        if (jsp != null) {
                            fresh_method_allStuInfo();
                            fresh();
//                            jLabel_welcome.repaint();
                            jp_center.repaint();
                            frame.repaint();
                            frame.setVisible(true);
                        }
                        break;
                    case "btn_record": {
                        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.12)));
                        //jp_center.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.84), (int) (frameSize.getHeight() * 0.85)));
                        record_att.setBounds(5, (int) frameSize.height / 2 - 60, (int) (frameSize.getWidth() * 0.2), (int) (frameSize.getHeight() * 0.06));
                        record_dis.setBounds(5, (int) frameSize.height / 2 + 40, (int) (frameSize.getWidth() * 0.2), (int) (frameSize.getHeight() * 0.06));
                        jp_up.repaint();
                        jp_center.repaint();
                        frame.repaint();
                        frame.setVisible(true);
                    }
                    break;
                    case "btn_roll": {
                        Dimension xxx = frame.getSize();
                        jp_left.setPreferredSize(new Dimension((int) (xxx.width * 0.65), (int) (xxx.height * 0.12)));
                        jp_left.repaint();
                        jp_down.setPreferredSize(new Dimension((int) (xxx.width * 0.13), (int) (xxx.height * 0.2)));
                        jp_down.repaint();
                        int height = (int) (xxx.height * 0.2);
                        int down_siz_width = jp_down.getSize().width;
                        roll_start.setFont(new Font("楷体", Font.BOLD, (int) (xxx.getWidth() * 0.04)));
                        roll_start.setSize((int) (down_siz_width * 0.25), (int) (down_siz_width * 0.06));
                        int roll_startHeight = roll_start.getHeight();
                        roll_start.setLocation(down_siz_width / 2 - roll_start.getWidth() / 2, height / 2 - roll_startHeight / 2);
                        roll_start.setHorizontalAlignment(0);

                        jLabel_roll_stuName.setFont(new Font("楷体", Font.ITALIC, (int) (xxx.width * 0.65 * 0.18)));
                        frame.setVisible(true);

                    }
                    break;
                    case "btn_seats": {
                        frame.repaint();
                        Dimension xxx = frame.getSize();
                        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.12)));
                        jp_left.repaint();
//                        jp_center.repaint();
//                        jp_center.repaint();
                        jp_down.setPreferredSize(new Dimension((int) (frameSize.width * 0.13), (int) (frameSize.height * 0.07)));
                        seat_fresh.setBounds(5, (int) frameSize.height / 2 - 80, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
                        seat_fresh.setAlignmentX(0);
                        seat_edit.setBounds(5, (int) frameSize.height / 2 - 20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
                        seat_edit.setAlignmentX(0);
                        seat_clear.setBounds(5, (int) frameSize.height / 2 + 40, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
                        seat_clear.setAlignmentX(0);

                        jp_left.repaint();
//                        jp_center.repaint();
                        jp_down.repaint();
                        frame.repaint();
                        frame.setVisible(true);
                    }
                    break;
                    default: {
                        jLabel_welcome.setFont(new Font("楷体", Font.ITALIC, (int) (frameSize.width * 0.09)));
                    }
                    break;
                }
            }
        });


        btn_AllStu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(jp_center);
                frame.remove(jp_left);
                frame.remove(jp_down);
                frame.remove(jp_right);
                frame.repaint();
                jp_center.removeAll();
                frame.add(jp_center);
                frame.add(jp_left, BorderLayout.WEST);
                fresh_flag = "all_stu";
                allStu_fresh_flag = "all_stu";
                jp_center.removeAll();
                jp_center.setLayout(new FlowLayout());
                fresh_method_allStuInfo();
                frame.repaint();
                frame.setVisible(true);
                fresh();
            }
        });
    }

    private void show_Btn_AllStu() {
        notice_text = "全部信息";
        jLabel_notice.setText(notice_text);
        //添加之前先移除所有组件
        jp_center.removeAll();
        jp_left.removeAll();
        jLabel_notice.setForeground(Color.RED);
        jLabel_notice.setText(notice_text);
        jLabel_notice.setFont(new Font("楷体", Font.BOLD, 16));
        jLabel_notice.setBounds(20, (int) frameSize.height/2 + frameSize.height/3-20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        jLabel_notice.setAlignmentX(0);
        jp_left.add(jLabel_notice);
        jp_right.removeAll();
        jp_down.removeAll();
        columnNames = new String[]{
                "编号",
                "班级",
                "姓名",
                "性别",
                "手机号",
                "生日",
                "毕业学校",
                "专业",
                "专/本",
                "考勤",
                "违纪",
                "总分"
        };

        int validCount = stu.getValidCount();
        stuInfo = new Object[validCount][columnNames.length];
        int row = 0;
        for (Student student : allStu) {
            String stu_remark = student.getStu_remark();
            if ("休学".equals(stu_remark)) {
                continue;
            }
            stuInfo[row][0] = student.getStu_id();
            stuInfo[row][1] = student.getStu_class();
            stuInfo[row][2] = student.getStu_name();
            stuInfo[row][3] = student.getStu_sex();
            stuInfo[row][4] = student.getStu_phone();
            stuInfo[row][5] = student.getStu_birthday().substring(0, 10);
            stuInfo[row][6] = student.getStu_graduate_school();
            stuInfo[row][7] = student.getStu_major();
            stuInfo[row][8] = student.getStu_qualifications();
            stuInfo[row][9] = "考勤";
            stuInfo[row][10] = "违纪";
            stuInfo[row][11] = student.getStu_totalScore();
            row++;
        }
        dtm = new DefaultTableModel(stuInfo, columnNames);
        jTable_allStu = new JTable(dtm);
        TableColumn column_att = jTable_allStu.getColumnModel().getColumn(9);
        TableColumn column_dis = jTable_allStu.getColumnModel().getColumn(10);
        ActionPanelEditorRenderer_att att_Renderer = new ActionPanelEditorRenderer_att(dtm, jTable_allStu);
        ActionPanelEditorRenderer_dis dis_Renderer = new ActionPanelEditorRenderer_dis(dtm, jTable_allStu);
        column_att.setCellRenderer(att_Renderer);
        column_att.setCellEditor(att_Renderer);
        column_dis.setCellRenderer(dis_Renderer);
        column_dis.setCellEditor(dis_Renderer);

        jTable_allStu.setFont(new Font("楷体", Font.PLAIN, 16));
        jTable_allStu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JTableHeader header = jTable_allStu.getTableHeader();
        header.setFont(new Font("楷体", Font.PLAIN, 16));
        jTable_allStu.setRowHeight(30);

        jTable_allStu.getColumnModel().getColumn(0).setPreferredWidth((int) (jp_center.getWidth()*0.05));//50
        jTable_allStu.getColumnModel().getColumn(1).setPreferredWidth((int) (jp_center.getWidth()*0.09));//90
        jTable_allStu.getColumnModel().getColumn(2).setPreferredWidth((int) (jp_center.getWidth()*0.07));//80
        jTable_allStu.getColumnModel().getColumn(3).setPreferredWidth((int) (jp_center.getWidth()*0.05));//50
        jTable_allStu.getColumnModel().getColumn(4).setPreferredWidth((int) (jp_center.getWidth()*0.10));//120
        jTable_allStu.getColumnModel().getColumn(5).setPreferredWidth((int) (jp_center.getWidth()*0.10));//120
        jTable_allStu.getColumnModel().getColumn(6).setPreferredWidth((int) (jp_center.getWidth()*0.17));//200
        jTable_allStu.getColumnModel().getColumn(7).setPreferredWidth((int) (jp_center.getWidth()*0.14));//150
        jTable_allStu.getColumnModel().getColumn(8).setPreferredWidth((int) (jp_center.getWidth()*0.05));//60
        jTable_allStu.getColumnModel().getColumn(9).setPreferredWidth((int) (jp_center.getWidth()*0.06));//80
        jTable_allStu.getColumnModel().getColumn(10).setPreferredWidth((int) (jp_center.getWidth()*0.06));//80
        jTable_allStu.getColumnModel().getColumn(11).setPreferredWidth((int) (jp_center.getWidth()*0.05));//50

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        jTable_allStu.getColumn("编号").setCellRenderer(render);
        jTable_allStu.getColumn("班级").setCellRenderer(render);
        jTable_allStu.getColumn("姓名").setCellRenderer(render);
        jTable_allStu.getColumn("性别").setCellRenderer(render);
        jTable_allStu.getColumn("手机号").setCellRenderer(render);
        jTable_allStu.getColumn("生日").setCellRenderer(render);
        jTable_allStu.getColumn("毕业学校").setCellRenderer(render);
        jTable_allStu.getColumn("专业").setCellRenderer(render);
        jTable_allStu.getColumn("专/本").setCellRenderer(render);
        jTable_allStu.getColumn("考勤").setCellRenderer(render);
        jTable_allStu.getColumn("违纪").setCellRenderer(render);
        jTable_allStu.getColumn("总分").setCellRenderer(render);

        jsp = new JScrollPane(jTable_allStu);
        jp_left.setLayout(null);
        jp_left.add(btn_export);
        jp_left.add(btn_modify);
        jp_left.add(btn_fresh);
        frameSize = frame.getSize();
        fresh();
        jp_center.repaint();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jp_center.add(jsp);
        frame.setVisible(true);

    }


    private void fresh() {
        frameSize = frame.getSize();
        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.12)));
        btn_export.setBounds(5, (int) frameSize.height / 2 - 80, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        btn_modify.setBounds(5, (int) frameSize.height / 2 - 20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        btn_fresh.setBounds(5, (int) frameSize.height / 2 + 40, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        btn_fresh.setAlignmentX(0);
        btn_export.setAlignmentX(0);
        btn_modify.setAlignmentX(0);

        jsp.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.84), (int) (frameSize.getHeight() * 0.85)));
        jp_center.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.84), (int) (frameSize.getHeight() * 0.85)));
    }

    private void exportRecord(final String path) throws Exception {
        final List<Student> allStu = stu.getAllStu();
        new Thread() {
            @Override
            public void run() {
                for (Student student : allStu) {
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "/" + student.getStu_name() + ".txt"), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("编号: " + student.getStu_id() + "\n");
                    sb.append("班级: " + student.getStu_class() + "\n");
                    sb.append("姓名: " + student.getStu_name() + "\n");
                    sb.append("总分: " + student.getStu_totalScore() + "\n");
                    sb.append("学习状态: " + student.getStu_remark() + "\n");
                    sb.append("性别: " + student.getStu_sex() + "\n");
                    sb.append("手机号: " + student.getStu_phone() + "\n");
                    sb.append("出生日期: " + student.getStu_birthday().substring(0, 10) + "\n");
                    sb.append("专业: " + student.getStu_major() + "\n");
                    sb.append("专/本: " + student.getStu_qualifications() + "\n");
                    sb.append("============================考勤信息============================\n");
                    String stu_attendance = student.getStu_attendance();
                    if (stu_attendance == null) {
                        sb.append("考勤正常\n");
                    } else {
                        String[] _1day = stu_attendance.split("\\&", -1);
                        out(sb, _1day);
                    }
                    sb.append("============================违纪信息============================\n");
                    String stu_discipline = student.getStu_discipline();
                    if (stu_discipline == null) {
                        sb.append("无违纪\n");
                    } else {
                        String[] _1day = stu_discipline.split("\\&", -1);
                        out(sb, _1day);
                    }
                    sb.append("============================加分信息============================\n");
                    String stu_reward = student.getStu_reward();
                    if (stu_reward == null) {
                        sb.append("无加分信息\n");
                    } else {
                        String[] rewards = stu_reward.split("\\|", -1);
                        for (String reward : rewards) {
                            sb.append(reward + "\n");
                        }
                    }
                    try {
                        bw.write(sb.toString());
                        bw.flush();
                        bw.close();
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(frame, "导出数据完成！");
            }
        }.start();

    }

    private void out(StringBuilder sb, String[] _1day) {
        int x = 1;
        for (String _t3 : _1day) {
            if (x == 1)
                sb.append("*******************************************\n");
            x++;
            String[] t3s = _t3.split("\\|", -1);
            for (String t3 : t3s) {
                sb.append(t3 + "\n");
            }
            sb.append("*******************************************\n");
        }
    }

    private void fresh_method_allStuInfo() {
        stu = new GetStuInfoImpl();
        allStu = stu.getAllStu();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        show_Btn_AllStu();
    }

    private void fresh_method_modiStuInfo() {
        //移除所有已经存在的组件
        jp_center.removeAll();
        frame.repaint();

        jp_center.setLayout(new FlowLayout((int) (frameSize.width * 0.65), 50, 30));
        List<Student> allStu = stu.getAllStu();
        for (final Student student : allStu) {
            String remark = student.getStu_remark();
            if ("休学".equals(remark)) {
                continue;
            }
            final JButton jButton = new JButton(student.getStu_name());
            jButton.setFont(new Font("楷体", Font.PLAIN, 18));
            jButton.setPreferredSize(new Dimension(120, 30));
            jp_center.add(jButton);
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ("modi_stu".equals(allStu_fresh_flag)) {
                        ModifyStuInfoDialog modi_Stuinfo = new ModifyStuInfoDialog(OmnibusView.frame, student);
                        modi_Stuinfo.setSize(500, 700);
                        modi_Stuinfo.setLocationRelativeTo(null);
                        modi_Stuinfo.setVisible(true);
                    } else if ("record_att".equals(allStu_fresh_flag)) {
                        Att_and_Dis_Dialog record_att = new Att_and_Dis_Dialog(frame, "record_att", student);
                        record_att.setSize(500, 700);
                        record_att.setLocationRelativeTo(null);
                        record_att.setVisible(true);
                    } else if ("record_dis".equals(allStu_fresh_flag)) {
                        Att_and_Dis_Dialog record_dis = new Att_and_Dis_Dialog(frame, "record_dis", student);
                        record_dis.setSize(500, 700);
                        record_dis.setLocationRelativeTo(null);
                        record_dis.setVisible(true);
                    }

                }
            });
        }
        jp_center.repaint();
        jp_center.setVisible(false);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        jp_center.setVisible(true);
    }

    private void att_and_dis() {
        frame.add(jp_center);
        frame.add(jp_left, BorderLayout.WEST);
        jp_left.setLayout(null);
        frame.add(jp_center);
        jp_center.setLayout(new FlowLayout());
        jp_left.add(record_att);
        jp_left.add(record_dis);

        notice_text = "记录考勤";
        jLabel_notice.setForeground(Color.RED);
        jLabel_notice.setText(notice_text);
        jLabel_notice.setFont(new Font("楷体", Font.BOLD, 16));
        jLabel_notice.setBounds(20, (int)frameSize.height / 2+ frameSize.height / 3 - 20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        jLabel_notice.setAlignmentX(0);
        jp_left.add(jLabel_notice);


        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.12)));
        record_att.setBounds(5, (int) frameSize.height / 2 - 60, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        record_dis.setBounds(5, (int) frameSize.height / 2 + 40, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));

        record_dis.setAlignmentX(0);
        record_att.setAlignmentX(0);
        fresh_method_modiStuInfo();

        frame.repaint();
        frame.setVisible(true);
    }

    private void show_rollcaller() {
        frame.add(jp_left, BorderLayout.WEST);
        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.6), (int) (frameSize.getHeight() * 0.12)));
        jp_left.repaint();
        frame.add(jp_right);
        jp_right.setBorder(BorderFactory.createEtchedBorder());
        //jp_right.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.29), (int) (frameSize.getHeight() * 0.12)));
        jp_right.repaint();
        frame.add(jp_down, BorderLayout.SOUTH);
        jp_down.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.2)));
        jp_down.setBorder(BorderFactory.createEtchedBorder());
        jp_down.repaint();
        frame.repaint();
        frame.setVisible(true);
        roll();
    }

    private void roll() {
        frameSize = frame.getSize();
        jp_left.setLayout(new BorderLayout());
        Dimension leftSize = jp_left.getSize();
        jLabel_roll_stuName = new JLabel("开始点名");
        jLabel_roll_stuName.setFont(new Font("楷体", Font.ITALIC, (int) (leftSize.width * 0.18)));
        jLabel_roll_stuName.setAutoscrolls(true);
        //0为居中显示
        jLabel_roll_stuName.setHorizontalAlignment(0);
        jp_left.add(jLabel_roll_stuName, BorderLayout.CENTER);
        jp_left.repaint();
        jp_left.setVisible(true);

        final List<String> namelists = new ArrayList<>();
        List<Student> allStu = stu.getAllStu();
        for (Student student : allStu) {
            if ("休学".equals(student.getStu_remark())) {
                continue;
            }
            namelists.add(student.getStu_name());
        }
        final Random random = new Random(System.currentTimeMillis());
        roll_name = new JLabel();
        roll_name.setForeground(Color.BLUE);
        roll_name.setFont(new Font("楷体", Font.BOLD, 18));
        roll_name.setBounds(30, 10, 300, 30);
        roll_name.setText("姓名：");

        roll_age = new JLabel();
        roll_age.setForeground(Color.BLUE);
        roll_age.setFont(new Font("楷体", Font.BOLD, 18));
        roll_age.setBounds(30, 60, 300, 30);
        roll_age.setText("年龄：");

        roll_school = new JLabel();
        roll_school.setForeground(Color.BLUE);
        roll_school.setFont(new Font("楷体", Font.BOLD, 18));
        roll_school.setBounds(30, 110, 300, 30);
        roll_school.setText("学校：");

        roll_major = new JLabel();
        roll_major.setForeground(Color.BLUE);
        roll_major.setFont(new Font("楷体", Font.BOLD, 18));
        roll_major.setBounds(30, 160, 300, 30);
        roll_major.setText("专业：");

        roll_welcome = new JLabel();
        roll_welcome.setForeground(Color.BLUE);
        roll_welcome.setFont(new Font("楷体", Font.BOLD, 18));
        roll_welcome.setBounds(30, 210, 300, 30);
        roll_welcome.setText("运气指数");


        roll_welcome_value = new JLabel();
        roll_welcome_value.setForeground(Color.RED);
        roll_welcome_value.setFont(new Font("楷体", Font.BOLD, 18));
        roll_welcome_value.setBounds(30, 260, 600, 30);
        roll_welcome_value.setText("");

        roll_times = new JLabel();
        roll_times.setForeground(Color.BLUE);
        roll_times.setFont(new Font("楷体", Font.BOLD, 18));
        roll_times.setBounds(30, 310, 300, 30);
        roll_times.setText("今日被点到次数：");

        jp_right.setLayout(null);

        jp_right.add(roll_name);
        jp_right.add(roll_age);
        jp_right.add(roll_school);
        jp_right.add(roll_major);
        jp_right.add(roll_welcome);
        jp_right.add(roll_times);
        jp_right.add(roll_welcome_value);

        jp_right.repaint();

        JPanel jp = new JPanel();

        jp_down.setLayout(new BorderLayout());
        roll_start = new JButton("开始点名");
        roll_start.setForeground(Color.GREEN);
        jp_down.add(jp, BorderLayout.CENTER);
        jp.setLayout(null);
        jp.add(roll_start);

        jp_down.setPreferredSize(new Dimension((int) (frameSize.width * 0.13), (int) (frameSize.height * 0.2)));
        jp_down.repaint();
        int down_siz_width = jp_down.getSize().width;
        int down_siz_height = jp_down.getSize().height;
        roll_start.setFont(new Font("楷体", Font.BOLD, (int) (frameSize.getWidth() * 0.04)));
        roll_start.setBackground(Color.white);
        roll_start.setSize((int) (down_siz_width * 0.25), (int) (down_siz_width * 0.06));
        roll_start.setLocation(down_siz_width / 2 - roll_start.getWidth() / 2, down_siz_height / 2 - roll_start.getHeight() / 2);
        roll_start.setHorizontalAlignment(0);
        jp_down.repaint();

        roll_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {

                        String startText = roll_start.getText();
                        if ("开始点名".equals(startText)) {
                            roll_flag = true;
                            roll_start.setText("停   止");
                            roll_start.setForeground(Color.RED);
                            roll_age.setText("年龄:");
                            roll_school.setText("学校:");
                            roll_major.setText("专业:");
                            roll_welcome_value.setText("");
                            roll_times.setText("今日被点到次数：");

                        } else if ("停   止".equals(startText)) {
                            String[] welcomStr = {
                                    "恭喜你，中奖啦！",
                                    "运气这么好，可以买彩票啦！",
                                    "你的运气无与伦比！",
                                    "奖励你回答一个问题！",
                                    "真的是缘分！",
                                    "还有比你更6的吗？",
                                    "又是运气满满的一天！"
                            };
                            Student oneByName = stu.getOneByName(jLabel_roll_stuName.getText().trim());
                            roll_flag = false;
                            roll_start.setText("开始点名");
                            roll_start.setForeground(Color.GREEN);
                            String agestr = oneByName.getStu_birthday().substring(0, 4);
                            sdf = new SimpleDateFormat("yyyy");
                            String yearstr = sdf.format(new Date());
                            int year = Integer.parseInt(yearstr);
                            int age = Integer.parseInt(agestr);
                            roll_age.setText("年龄:" + (year - age));
                            roll_school.setText("学校:" + oneByName.getStu_graduate_school());
                            roll_major.setText("专业:" + oneByName.getStu_major());
                            roll_welcome_value.setText(welcomStr[random.nextInt(welcomStr.length)]);

                            String rName = oneByName.getStu_name();
                            String count = JedisForCount.readCount(rName);
                            Date now = new Date();
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String nowDate = sdf.format(now);
                            int anInt = 0;
                            if (count == null || "".equals(count)) {
                                anInt = 1;
                            } else {
                                String[] splits = count.split("\\|", -1);
                                anInt = Integer.parseInt(splits[1]);
                                if (!nowDate.equals(splits[0])) {
                                    anInt = 1;
                                } else {
                                    anInt++;
                                }
                            }
                            JedisForCount.writeCount(rName, nowDate + "|" + anInt);
                            roll_times.setText("今日被点到次数：" + anInt);
                        }
                        while (roll_flag) {
                            //随机抽取一个数字
                            int anInt = random.nextInt(namelists.size());
                            jLabel_roll_stuName.setText(namelists.get(anInt));
                            roll_name.setText("姓名:" + namelists.get(anInt));
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    private JButton seat_fresh;
    private JButton seat_edit;
    private JButton seat_clear;
    private JLabel jLabel_notice = new JLabel();
//    private String edit_mode = "ffff";
    private JButton seat_save = new JButton("目前是编辑模式,完成后点击此处保存编辑！");

    private void show_seats() {
        notice_text = "座位信息";
        frameSize = frame.getSize();
        jp_center.removeAll();
        frame.add(jp_center);
        jp_left.removeAll();
        frame.add(jp_left, BorderLayout.WEST);
        jp_left.setLayout(null);
        jp_left.setPreferredSize(new Dimension((int) (frameSize.getWidth() * 0.13), (int) (frameSize.getHeight() * 0.12)));

        jLabel_notice.setForeground(Color.RED);
        jLabel_notice.setText(notice_text);
        jLabel_notice.setFont(new Font("楷体", Font.BOLD, 16));
        jLabel_notice.setBounds(20, (int) frameSize.height / 2+frameSize.height / 3 - 20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        jLabel_notice.setAlignmentX(0);
        jp_left.add(jLabel_notice);

        seat_fresh = new JButton("刷新座位");
        seat_fresh.setForeground(Color.magenta);
        seat_fresh.setFont(new Font("楷体", Font.BOLD, 16));
        seat_fresh.setBounds(5, (int) frameSize.height / 2 - 80, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        seat_fresh.setAlignmentX(0);
        jp_left.add(seat_fresh);

        seat_edit = new JButton("编辑座位");
        seat_edit.setForeground(Color.BLUE);
        seat_edit.setFont(new Font("楷体", Font.BOLD, 16));
        seat_edit.setBounds(5, (int) frameSize.height / 2 - 20, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        seat_edit.setAlignmentX(0);
        jp_left.add(seat_edit);

        seat_clear = new JButton("清除座位");
        seat_clear.setForeground(Color.RED);
        seat_clear.setFont(new Font("楷体", Font.BOLD, 16));
        seat_clear.setBounds(5, (int) frameSize.height / 2 + 40, (int) (frameSize.getWidth() * 0.12), (int) (frameSize.getHeight() * 0.06));
        seat_clear.setAlignmentX(0);
        jp_left.add(seat_clear);

        jp_left.repaint();
        fresh_seats();
        seat_event();
        frame.repaint();
    }

    private static void show_button(String stu_Name, JPanel jp_cent, GridBagLayout gb, GridBagConstraints gc, int btn_w, int btn_h, final int index) {
        final JButton stu_btn = new JButton(stu_Name);    //创建Button对象
        stu_btn.setForeground(Color.BLUE);
        stu_btn.setFont(new Font("楷体", Font.BOLD, 16));
        stu_btn.setPreferredSize(new Dimension(btn_w, btn_h));
        gb.setConstraints(stu_btn, gc);
        final String name_text = stu_btn.getText().trim();
        if("讲  师".equals(name_text)){
            stu_btn.setEnabled(false);
            stu_btn.setForeground(Color.magenta);
            stu_btn.setBackground(Color.magenta);
        }else if("".equals(name_text) || name_text == null){
            if (!"edit".equals(edit_mode)){
                //不是编辑模式的时候，不显示空白按钮
                stu_btn.setUI(null);
                stu_btn.setOpaque(false);
                stu_btn.setBorderPainted(false);
                stu_btn.setContentAreaFilled(false);
            }else{
                stu_btn.setOpaque(true);
                stu_btn.setBorderPainted(true);
                stu_btn.setContentAreaFilled(true);
            }
        }
        stu_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(123);
                if("edit".equals(edit_mode)){
                    Edit_Seats_Dialog edit_seats = new Edit_Seats_Dialog(OmnibusView.frame, "edit", name_text, index);
                    edit_seats.setSize(350,250);
                    edit_seats.setLocationRelativeTo(null);
                    edit_seats.setVisible(true);
                }else{
                    if ("".equals(name_text) || name_text == null){
                        return;
                    }
                    Edit_Seats_Dialog edit_seats = new Edit_Seats_Dialog(OmnibusView.frame, "", name_text, index);
                    edit_seats.setSize(400,500);
                    edit_seats.setLocationRelativeTo(null);
                    edit_seats.setVisible(true);
                }
            }
        });
        jp_cent.add(stu_btn);

    }

    private void seat_event() {
        frameSize = frame.getSize();
        seat_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                notice_text = "编辑座位";
                jLabel_notice.setText(notice_text);
                edit_mode = "edit";
                jp_down.removeAll();
                frame.add(jp_down, BorderLayout.SOUTH);
                jp_down.setLayout(new BorderLayout());
                jp_down.setPreferredSize(new Dimension((int) (frameSize.width * 0.13), (int) (frameSize.height * 0.07)));
                jp_down.repaint();
                frame.repaint();

                seat_save.setForeground(Color.RED);
                seat_save.setBackground(Color.YELLOW);
                seat_save.setFont(new Font("楷体", Font.BOLD, 20));
                jp_down.add(seat_save, BorderLayout.CENTER);
                jp_down.repaint();
                fresh_seats();
                frame.repaint();
            }
        });


        seat_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "座位信息";
                jLabel_notice.setText(notice_text);
                edit_mode = "save";
                jp_down.removeAll();
                frame.remove(jp_down);
                fresh_seats();
                frame.repaint();
                frame.setVisible(true);
            }
        });

        seat_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notice_text = "清除座位";
                jLabel_notice.setText(notice_text);
                edit_mode = "clear";
                Edit_Seats_Dialog ask_for_sure = new Edit_Seats_Dialog(frame, "ask", "", 1000);
                ask_for_sure.setSize(300,200);
                ask_for_sure.setLocationRelativeTo(null);
                ask_for_sure.setVisible(true);


            }
        });

        seat_fresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                notice_text = "座位信息";
                jLabel_notice.setText(notice_text);
                fresh_seats();
            }
        });
    }

    public static void fresh_seats(){
        jp_center.removeAll();
        jp_center.repaint();
        GridBagLayout gbaglayout = new GridBagLayout();
        jp_center.setLayout(gbaglayout);
        GridBagConstraints constraints = new GridBagConstraints();

        seat_map = JedisForCount.getSeatIndex();
        int index = 0;
        for (int i = 0; i < 8; i++) {
            constraints.fill = GridBagConstraints.CENTER;    //组件填充显示区域
            constraints.weightx = 0.5;    // 指定组件的分配区域
            constraints.weighty = 0.2;
            constraints.gridwidth = 1;
            for (int j = 0; j < 5; j++) {
                show_button(seat_map.get(index) == null?" ":seat_map.get(index), jp_center, gbaglayout, constraints, 100, 30,index);
                index++;
                if (j == 3) {
                    constraints.gridwidth = GridBagConstraints.REMAINDER;
                }
            }
        }

        GridBagConstraints c_teacher = new GridBagConstraints();
        c_teacher.gridwidth = GridBagConstraints.REMAINDER;
        c_teacher.weightx = 0.5;    // 指定组件的分配区域
        c_teacher.weighty = 0.2;
        c_teacher.fill = GridBagConstraints.CENTER;
        c_teacher.gridwidth = 2;
        show_button("讲  师", jp_center, gbaglayout, c_teacher, 200, 30,999);
        jp_center.repaint();
    }
}

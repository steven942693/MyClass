package com.offcn.view;

//import com.eltima.components.ui.DatePicker;
import com.offcn.bean.Student;
import com.offcn.dialog.ShowAttendenceDialog;
import com.offcn.service.impl.GetStuInfoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanelEditorRenderer_att extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel jp = new JPanel();
    private JButton btn_att = new JButton("考勤");
    private static DefaultTableModel dtm;
    private static JTable table;


    public ActionPanelEditorRenderer_att(final DefaultTableModel dtm, final JTable table){
        this.dtm = dtm;
        this.table = table;
        jp.add(btn_att);
        btn_att.setFont(new Font("楷体", Font.PLAIN, 16));
        btn_att.setVisible(true);
        jp.setVisible(true);
        btn_att.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                //String name = (String)dtm.getValueAt(row, 2);
                String stu_id = (String)dtm.getValueAt(row, 0);
                //从数据库中查看选中的学生
                GetStuInfoImpl stuInfo = new GetStuInfoImpl();
                Student one = stuInfo.getOne(stu_id);
                {
                    ShowAttendenceDialog show_att = new ShowAttendenceDialog(OmnibusView.frame, "", one);
                    show_att.setSize(500,600);
                    show_att.setLocationRelativeTo(null);
                    show_att.setVisible(true);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return btn_att;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        btn_att.setForeground(table.getSelectionForeground());

        btn_att.setBackground(table.getSelectionBackground());
        return btn_att ;
    }

    @Override
    public Object getCellEditorValue() {
        int column = table.getSelectedColumn();
        int row = table.getSelectedRow();
        return dtm.getValueAt(row,column).toString();
    }
}

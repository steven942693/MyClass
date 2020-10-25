package com.offcn.view;

import com.offcn.bean.Student;
import com.offcn.dialog.ShowDisciplineDialog;
import com.offcn.service.impl.GetStuInfoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanelEditorRenderer_dis extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel jp = new JPanel();
    private JButton btn_dis = new JButton("违纪");
    DefaultTableModel dtm;
    JTable table;
    public ActionPanelEditorRenderer_dis(final DefaultTableModel dtm, final JTable table){
        this.dtm = dtm;
        this.table = table;
        jp.add(btn_dis);
        btn_dis.setFont(new Font("楷体", Font.PLAIN, 16));
        btn_dis.setVisible(true);
        jp.setVisible(true);
        btn_dis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                //String name = (String)dtm.getValueAt(row, 2);
                String stu_id = (String)dtm.getValueAt(row, 0);
                //从数据库中查看选中的学生
                GetStuInfoImpl stuInfo = new GetStuInfoImpl();
                Student one = stuInfo.getOne(stu_id);
                {
                    ShowDisciplineDialog discipline = new ShowDisciplineDialog(OmnibusView.frame, "", one);
                    discipline.setSize(500,600);
                    discipline.setLocationRelativeTo(null);
                    discipline.setVisible(true);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return btn_dis;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        btn_dis.setForeground(table.getSelectionForeground());

        btn_dis.setBackground(table.getSelectionBackground());
        return btn_dis ;
    }

    @Override
    public Object getCellEditorValue() {
        int column = table.getSelectedColumn();
        int row = table.getSelectedRow();
        return dtm.getValueAt(row,column).toString();
    }
}

package view;

import bean.Student;
import event.MenuEvent;
import util.SqlUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

public class Manage extends JFrame {
    public static String text;//保存当前登录的账户
    private final int WIDTH = 800;
    private final int HEIGHT = 725;
    JLabel background;
    JMenuBar menuBar;//菜单条
    JMenu menu, menu1;//一个菜单
    JMenuItem item1, item2, item3, item4;//项目
    JMenuItem item_1, item_2;//项目
    JPanel jPanel_1, jPanel_2;

    ActionListener listener_1;//事件监听
    JLabel name;
    JTextField nameText;

    JRadioButton Men, Women;
    JLabel age;
    JTextField ageText;
    JLabel grade;
    JTextField gradeText;
    JLabel number;
    JTextField numberText;
    JButton add, delete, update, select, reset,selectall;
    public static JTextArea resultText;//显示结果
    private Student student=new Student();
    JTable table = null;
    JScrollPane jScrollPane;
    Object[] columns = {"姓名", "性别", "年龄", "班级", "学号"};//标题信息数组
    static Vector rwo;
    static DefaultTableModel model;
    static TableColumnModel columnModel;
    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);

    public Manage() {
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
        //selectAllStudent();
    }

    public Manage(String str) {
        text = str;
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
//        selectAllStudent();
    }

    private void init() {

        this.setLayout(null);
        this.setTitle("学生信息管理系统");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        //获取屏幕的高度和宽度
        int width = dimension.width;
        int height = dimension.height;
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;
        //设置位置和窗口的大小
        this.setBounds(x, y, WIDTH, HEIGHT);
        //点击X关闭把当前账户设置为离线
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SqlUtils.setStart(text,0);
            }
        });
        ImageIcon img = new ImageIcon("graduation\\src\\img\\4.jpg");
        background = new JLabel(img);
        background.setBounds(0, 0, img.getIconWidth(), 100);
        menuBar = new JMenuBar();//构造菜单条
        menu = new JMenu("管理");
        menu1 = new JMenu("账号");
        item1 = new JMenuItem("查看在线人数", new ImageIcon("graduation\\src\\img\\i1.jpg"));
        item2 = new JMenuItem("查看所有账号", new ImageIcon("graduation\\src\\img\\i1.jpg"));
        item3 = new JMenuItem("更改员工账号", new ImageIcon("graduation\\src\\img\\i1.jpg"));
        item4 = new JMenuItem("更改当前账号", new ImageIcon("graduation\\src\\img\\i1.jpg"));
        item_1 = new JMenuItem("退出", new ImageIcon("graduation\\src\\img\\i2.jpg"));
        item_2 = new JMenuItem("注销", new ImageIcon("graduation\\src\\img\\i3.jpg"));

        item4.addActionListener(e -> new ChangeOne(text));
        item_2.addActionListener(e -> new DropAccount(text));

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menu1.add(item_1);
        menu1.add(item_2);
        menuBar.add(menu);
        menuBar.add(menu1);

        jPanel_1 = new JPanel();
        jPanel_1.setLayout(flowLayout);//居中对齐
        jPanel_1.setBorder(BorderFactory.createTitledBorder("基本信息处理"));
        jPanel_1.setBounds(0, 100, WIDTH - 14, 100);
        //添加按钮
        name = new JLabel("姓名");
        nameText = new JTextField(12);
        Men = new JRadioButton("男");
        Women = new JRadioButton("女");
        Men.addActionListener(e -> student.setSex("男"));
        Women.addActionListener(e -> student.setSex("女"));
        age = new JLabel("年龄");
        ageText = new JTextField(12);
        grade = new JLabel("班级");
        gradeText = new JTextField(12);
        number = new JLabel("学号");
        numberText = new JTextField(12);

        add = new JButton("增加数据");
        delete = new JButton("删除数据");
        update = new JButton("修改数据");
        select = new JButton("查询数据");
        selectall = new JButton("查询所有数据");
        reset = new JButton("重置数据");
        add.addActionListener(e -> addStudent());
        delete.addActionListener(e -> deleteStudent());
        update.addActionListener(e -> updateStudent());
        select.addActionListener(e->selectOne());
        selectall.addActionListener(e->selectAllStudent());
        reset.addActionListener(e->resetAll());
        //第二个盘子的布局
        jPanel_2 = new JPanel();
        jPanel_2.setLayout(flowLayout);
        jPanel_2.setBounds(0, 200, WIDTH - 14, 300);
        jPanel_2.setBorder(BorderFactory.createTitledBorder("学生数据信息显示"));
        table();
        resultText = new JTextArea();
        resultText.setBounds(10, 510, WIDTH - 30, 150);
        resultText.setBorder(BorderFactory.createTitledBorder("账号信息显示"));
        resultText.setEditable(false);
        jPanel_1.add(name);
        jPanel_1.add(nameText);
        jPanel_1.add(Men);
        jPanel_1.add(Women);
        jPanel_1.add(age);
        jPanel_1.add(ageText);
        jPanel_1.add(grade);
        jPanel_1.add(gradeText);
        jPanel_1.add(number);
        jPanel_1.add(numberText);
        jPanel_1.add(add);
        jPanel_1.add(delete);
        jPanel_1.add(update);
        jPanel_1.add(select);
        jPanel_1.add(selectall);
        jPanel_1.add(reset);
        jPanel_2.add(jScrollPane);
        this.add(jPanel_1);
//        this.add(menuBar);
        this.add(jPanel_2);
        this.add(background);
        this.setJMenuBar(menuBar);
        this.add(resultText);
        setAllName();
        allEvent();
    }
    /**
     * 清空文本框
     */
    private void resetAll() {
        nameText.setText("");
        ageText.setText("");
        gradeText.setText("");
        numberText.setText("");
    }

    /**
     * 将学生数据添加到表格中
     * @param student 学生数据
     */
    private void set(Student student){
        Object[] data=new Object[5];
        data[0]=student.getName();
        data[1]=student.getSex();
        data[2]=student.getAge();
        data[3]=student.getGrade();
        data[4]=student.getNumber();
        model.addRow(data);

    }

    /**
     * 删除表格内的数据
     */
    private void deleteRow(){
        for (int i = model.getRowCount(); i>0; i--) {
            model.removeRow(i-1);

        }

    }

    /**
     * 把数据库内所有的数据添加到表格中
     */
    private void selectAllStudent() {
        List<Student> students = SqlUtils.selectStudent();
        for (Student value : students) {
            set(value);
        }
    }

    /**
     * 查询一条学生数据
     */
    private void selectOne(){
        if("".equals(numberText.getText())){
            JOptionPane.showMessageDialog(null,"学号不能为空","提示信息", JOptionPane.ERROR_MESSAGE);
        }else {
//            Student student = SqlUtils.selectStudent(numberText.getText());
            Student student = SqlUtils.callProcedure(Student.class);
            resetAll();
            deleteRow();
            if (student != null) {
                set(student);
            }
        }
    }

    private void updateStudent() {
        if (setStudent()) {
            boolean b = SqlUtils.updateStudent(student);
            if (b) {
                JOptionPane.showMessageDialog(null, "修改成功", "提示信息", JOptionPane.WARNING_MESSAGE);
                resetAll();
                deleteRow();
                selectAllStudent();
            } else {
                JOptionPane.showMessageDialog(null, "修改失败,该学号不存在", "提示信息", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void deleteStudent() {
        if ("".equals(numberText.getText())) {
            JOptionPane.showMessageDialog(null,"学号不能为空","提示信息",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object[] op={"确定","取消"};
        int i = JOptionPane.showOptionDialog(this, "确定要删除此学生吗?", "提示消息", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        if(i==0) {
            boolean b = SqlUtils.deleteStudent(numberText.getText());
            if (b){
                JOptionPane.showMessageDialog(null,"删除成功","提示信息",JOptionPane.WARNING_MESSAGE);
                resetAll();
                deleteRow();
                selectAllStudent();
            }
        }

    }

    private void addStudent() {

        if(setStudent()) {
            boolean b = SqlUtils.insertStudent(student);
            if (b) {
                JOptionPane.showMessageDialog(null, "添加成功", "提示信息", JOptionPane.WARNING_MESSAGE);
                resetAll();
                deleteRow();
                selectAllStudent();
            } else {
                JOptionPane.showMessageDialog(null, "添加失败,该学号已存在", "提示信息", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean setStudent() {
        if("".equals(nameText.getText())){
            JOptionPane.showMessageDialog(null,"姓名不能为空","提示信息",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if("".equals(ageText.getText())){
            JOptionPane.showMessageDialog(null,"年龄不能为空","提示信息",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if("".equals(gradeText.getText())){
            JOptionPane.showMessageDialog(null,"班级不能为空","提示信息",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if("".equals(numberText.getText())){
            JOptionPane.showMessageDialog(null,"学号不能为空","提示信息",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        student.setName(nameText.getText());
        int a=Integer.parseInt(ageText.getText());
        student.setAge(a);
        student.setGrade(gradeText.getText());
        student.setNumber(numberText.getText());
        return true;
    }

    /**
     * 菜单栏中的menu设置名字
     */
    private void setAllName() {
        item1.setName("item1");
        item2.setName("item2");
        item3.setName("item3");
        item4.setName("item4");
        item_1.setName("item_1");
    }

    /**
     * 菜单栏中前三个按钮的事件
     */
    private void allEvent() {
        listener_1 = new MenuEvent();
        item1.addActionListener(listener_1);
        item2.addActionListener(listener_1);
        item3.addActionListener(listener_1);
        item_1.addActionListener(listener_1);
    }


    /**
     * 创建表格
     */
    private void table() {
        table = getTable();
        jScrollPane = new JScrollPane(table);//添加一个浏览条
        jScrollPane.setPreferredSize(new Dimension(WIDTH - 30, 250));//窗格大小
        table.setPreferredSize(new Dimension(WIDTH - 30, 1000));//表格大小
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//将滑动组件显示在窗格中
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable();
            int[] columnWidth = {70, 70, 70, 70, 70, 70};//列宽
            model = new DefaultTableModel() {//让表格不能编辑
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columns);
            table.setModel(model);//设置为表格的模式
            columnModel = table.getColumnModel();//获取表格的控制
            table.getTableHeader().setReorderingAllowed(false);//让表格不可拖动
            table.getTableHeader().setResizingAllowed(false);//让表格不可拖动
            int count = columnModel.getColumnCount();//返回列数和行数
            for (int i = 0; i < count; i++) {
                TableColumn column = columnModel.getColumn(i);//返列表的对象
                column.setPreferredWidth(columnWidth[i]);
            }
            rwo = new Vector(5);
        }
        return table;
    }
}

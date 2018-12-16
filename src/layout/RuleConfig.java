package layout;

import utils.CacheUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RuleConfig extends JFrame implements ActionListener{

    private JLabel jLabel1 = new JLabel("奖项名称");
    private JTextField jTextField1 = new JTextField();
    private JButton saveButton = new JButton("添加抽奖项");
    private JLabel jLabel2 = new JLabel("抽取个数");
    private JTextField jTextField2 = new JTextField();
    private List list = new List();
    private JButton nextButton = new JButton("下一步");

    public RuleConfig(){
        super("抽奖规则配置");
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(1000,700));
        this.setFont(new Font("宋体",Font.BOLD,15));
        Container c = this.getContentPane();
        GridBagConstraints gridJLabel1 = getGridJLabel1();
        c.add(jLabel1,gridJLabel1);
        GridBagConstraints gridJTextField1 = getGridJTextField1();
        c.add(jTextField1,gridJTextField1);
        GridBagConstraints gridSaveButton = getSaveButton();
        saveButton.addActionListener(this);
        c.add(saveButton,gridSaveButton);
        GridBagConstraints gridJLabel2 = getGridJLabel2();
        c.add(jLabel2,gridJLabel2);
        GridBagConstraints gridJTextField2 = getGridJTextField2();
        c.add(jTextField2,gridJTextField2);
        GridBagConstraints gridList = getGridList();
        c.add(list,gridList);
        GridBagConstraints gridNextButton = getNextButton();
        nextButton.addActionListener(this);
        c.add(nextButton,gridNextButton);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    private GridBagConstraints getGridJLabel1(){
        final GridBagConstraints gridJLabel1 = new GridBagConstraints();
        gridJLabel1.gridx = 0;
        gridJLabel1.gridy = 0;
        gridJLabel1.weightx = 15;
        gridJLabel1.insets = new Insets(5,5,5,5);
        gridJLabel1.fill = GridBagConstraints.HORIZONTAL;
        return gridJLabel1;
    }

    private GridBagConstraints getGridJTextField1(){
        final GridBagConstraints gridJTextField1 = new GridBagConstraints();
        gridJTextField1.gridx = 1;
        gridJTextField1.gridy = 0;
        gridJTextField1.insets = new Insets(5,5,5,5);
        gridJTextField1.weightx = 55;
        gridJTextField1.fill = GridBagConstraints.HORIZONTAL;
        return gridJTextField1;
    }

    private GridBagConstraints getSaveButton(){
        final GridBagConstraints gridSaveButton = new GridBagConstraints();
        gridSaveButton.gridx = 2;
        gridSaveButton.gridy = 0;
        gridSaveButton.gridheight = 2;
        gridSaveButton.insets = new Insets(5,5,5,5);
        gridSaveButton.weightx = 30;
        gridSaveButton.fill = GridBagConstraints.BOTH;
        return gridSaveButton;
    }

    private GridBagConstraints getGridJLabel2(){
        final GridBagConstraints gridJLabel2 = new GridBagConstraints();
        gridJLabel2.gridx = 0;
        gridJLabel2.gridy = 1;
        gridJLabel2.weightx = 15;
        gridJLabel2.insets = new Insets(5,5,5,5);
        gridJLabel2.fill = GridBagConstraints.HORIZONTAL;
        return gridJLabel2;
    }

    private GridBagConstraints getGridJTextField2(){
        final GridBagConstraints gridJTextField2 = new GridBagConstraints();
        gridJTextField2.gridx = 1;
        gridJTextField2.gridy = 1;
        gridJTextField2.insets = new Insets(5,5,5,5);
        gridJTextField2.weightx = 55;
        gridJTextField2.fill = GridBagConstraints.HORIZONTAL;
        return gridJTextField2;
    }

    private GridBagConstraints getGridList(){
        final GridBagConstraints gridGridList = new GridBagConstraints();
        gridGridList.gridx = 0;
        gridGridList.gridy = 2;
        gridGridList.gridwidth = 3;
        gridGridList.gridheight = 5;
        gridGridList.insets = new Insets(5,5,5,5);
//        gridGridJLabel3.weightx = 100;
        gridGridList.fill = GridBagConstraints.BOTH;
        return gridGridList;
    }

    private GridBagConstraints getNextButton(){
        final GridBagConstraints gridNextButton = new GridBagConstraints();
        gridNextButton.gridx = 0;
        gridNextButton.gridy = 7;
        gridNextButton.gridwidth = 3;
        gridNextButton.gridheight = 2;
        gridNextButton.insets = new Insets(5,5,5,5);
//        gridNextButton.weightx = 100;
        gridNextButton.fill = GridBagConstraints.BOTH;
        return gridNextButton;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton){
            String value1 = jTextField1.getText();
            if(value1 == null || value1.length() == 0){
                JOptionPane.showMessageDialog(null,"奖项名称不能为空");
                return;
            }
            String value2 = jTextField2.getText();
            if(value2 == null || value2.length() == 0){
                JOptionPane.showMessageDialog(null,"个数不能为空");
                return;
            }
            Integer valueInt;
            try{
                valueInt = Integer.parseInt(value2);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null,"请输入合法的个数(数字)");
                return;
            }

            if(valueInt <= 0) {
                JOptionPane.showMessageDialog(null, "奖项个数需大于0");
                return;
            }


            int temp = CacheUtil.allNumber + valueInt;
            if(temp > CacheUtil.allPeople ){
                JOptionPane.showMessageDialog(null, "抽奖名单的人数小于被抽的个数");
                return;
            }
            CacheUtil.allNumber = CacheUtil.allNumber + valueInt;
            boolean result = CacheUtil.ruleStack.add(new RuleItem(value1, valueInt));
            if(!result){
                JOptionPane.showMessageDialog(null, "奖项个数达上线,无法继续添加");
                return;
            }
            list.add("奖项: " + value1 + "  个数: " + valueInt);
            jTextField1.setText("");
            jTextField2.setText("");

        }else if(e.getSource() == nextButton){
            this.dispose();
            new Lucky();
        }
    }
}

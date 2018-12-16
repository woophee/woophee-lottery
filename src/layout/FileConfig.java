package layout;

import utils.CacheUtil;
import utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class FileConfig extends JFrame implements ActionListener{

    private JLabel jLabel = new JLabel("文件路径");
    private JTextField jTextField = new JTextField();
    private JButton readButton = new JButton("浏览");
    private JButton nextButton = new JButton("下一步");

    public static String filePath;

    public FileConfig(){
        super("请选择抽奖名单");
        Container c = this.getContentPane();
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(1000,700));
        this.setFont(new Font("宋体",Font.BOLD,15));
        GridBagConstraints gridJLabel = getGridJLabel();
        c.add(jLabel,gridJLabel);
        GridBagConstraints gridJTextField = getGridJTextField();
        c.add(jTextField,gridJTextField);
        GridBagConstraints gridReadButton = getReadButton();
        readButton.addActionListener(this);
        c.add(readButton,gridReadButton);
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

    private GridBagConstraints getGridJLabel(){
        final GridBagConstraints gridJLabel1 = new GridBagConstraints();
        gridJLabel1.gridx = 0;
        gridJLabel1.gridy = 0;
        gridJLabel1.weightx = 15;
        gridJLabel1.insets = new Insets(5,5,5,5);
        gridJLabel1.fill = GridBagConstraints.HORIZONTAL;
        return gridJLabel1;
    }

    private GridBagConstraints getGridJTextField(){
        final GridBagConstraints gridJTextField1 = new GridBagConstraints();
        gridJTextField1.gridx = 1;
        gridJTextField1.gridy = 0;
        gridJTextField1.insets = new Insets(5,5,5,5);
        gridJTextField1.weightx = 55;
        gridJTextField1.fill = GridBagConstraints.HORIZONTAL;
        return gridJTextField1;
    }

    private GridBagConstraints getReadButton(){
        final GridBagConstraints gridReadButton = new GridBagConstraints();
        gridReadButton.gridx = 2;
        gridReadButton.gridy = 0;
        gridReadButton.gridheight = 1;
        gridReadButton.insets = new Insets(5,5,5,5);
        gridReadButton.weightx = 30;
        gridReadButton.fill = GridBagConstraints.BOTH;
        return gridReadButton;
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
        if(e.getSource() == readButton){
            JFileChooser fileChooser = new JFileChooser();
            int value = fileChooser.showOpenDialog(this.getContentPane());
            if(value == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getPath();
                jTextField.setText(selectedFile.getPath());
            }
        }else if(e.getSource() == nextButton){
            //判断路径是否为空或者假路径则提示
            if(filePath != null) {
                File file = new File(filePath);
                if(!file.exists()){
                    JOptionPane.showMessageDialog(null,"文件不存在");
                    return;
                }
                try {
                    HashSet<String> set = new HashSet<String>();
                    CacheUtil.lotteryFile = FileUtil.readLogByList(filePath,set);
                    CacheUtil.allPeople = set.size();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"读取文件出现问题");
                    return;
                }
                String fileName = "result " + System.currentTimeMillis() + ".txt";
                CacheUtil.fileName = fileName;
                try {
                    FileUtil.createNewFile(fileName);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null,"创建结果文件失败");
                    return;
                }
                this.dispose();
                new RuleConfig();
            }else{
                //弹窗警告
                JOptionPane.showMessageDialog(null,"请通过浏览按钮选择路径");

            }
        }
    }
}

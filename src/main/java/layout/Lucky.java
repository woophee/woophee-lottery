package layout;

import utils.CacheUtil;
import utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class Lucky extends JFrame implements ActionListener{

    private JLabel title = new JLabel();
    private JLabel screen = new JLabel();
    private JButton button = new JButton("开始抽奖");
    private int[] indexResult;
    private static boolean flag = true;

    public Lucky(){
        super("抽奖界面");
        this.setBounds(100,100,1000,650);

        JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon ii = new ImageIcon("background.jpg");
                g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
            }
        };
        this.add(p);
//        setSize(300, 300);
//        setVisible(true);

        p.setLayout(new BorderLayout());
        button.setFont(new Font("Courier",Font.PLAIN,32));
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.setForeground(Color.white);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        p.add(BorderLayout.SOUTH, button);

        screen.setFont(new Font("Courier",Font.PLAIN,22));
        screen.setHorizontalAlignment(JLabel.CENTER);
        screen.setVerticalAlignment(JLabel.CENTER);
        screen.setForeground(Color.white);
        JScrollPane sp = new JScrollPane(screen);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        p.add(BorderLayout.CENTER, sp);

        title.setFont(new Font("Courier",Font.BOLD,42));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setOpaque(false);
        p.add(BorderLayout.NORTH, title);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if("停止".equals(button.getText())) {
            flag = false;
            RuleItem ruleItem = CacheUtil.ruleStack.pop();
            String name = ruleItem.getName();
            title.setText("========= " + name + " =========");
            FileUtil.writeFile(CacheUtil.fileName,"\r\n******" + name + "******\r\n");
            for (int i = 0;i < indexResult.length; i++) {
                CacheUtil.processlist.add(CacheUtil.lotteryFile.get(indexResult[i]));
                FileUtil.writeFile(CacheUtil.fileName,CacheUtil.lotteryFile.get(indexResult[i]) + "\r\n");
            }
            List<String> temp = new ArrayList<>();
            Set<Integer> tempset = new HashSet<>();
            for (int i = 0;i < indexResult.length; i++) {
                tempset.add(indexResult[i]);
            }
            for(int i = 0; i < CacheUtil.lotteryFile.size(); i++){
                if(!tempset.contains(i)){
                    temp.add(CacheUtil.lotteryFile.get(i));
                }
            }
            CacheUtil.lotteryFile = temp;

            FileUtil.writeFile(CacheUtil.fileName,"\r\n");
            if(CacheUtil.ruleStack.size() == 0){
                button.setText("结束抽奖");
                return;
            }
            button.setText("开始抽奖");
        }else if("开始抽奖".equals(button.getText())){
            button.setText("停止");
            RuleItem ruleItem = CacheUtil.ruleStack.peek();
            final String name = ruleItem.getName();
            title.setText(name);
            final Integer number = ruleItem.getNumber();
            flag = true;
            new Thread(){
                public void run(){
                    while(Lucky.flag){
                        HashSet<String> temp = new HashSet<String>();
                        int[] index = new int[0];
                        //保证本次抽奖不重复
                        boolean tain = false;
                        while(temp.size() != number || tain){
                            tain = false;
                            temp.clear();
                            index = randomArray(0, CacheUtil.lotteryFile.size() - 1, number);
                            for (int i = 0;i < index.length; i++) {
//                                System.out.println(CacheUtil.lotteryFile.get(index[i]));
                                temp.add(CacheUtil.lotteryFile.get(index[i]));
                                if(CacheUtil.processlist.size() < CacheUtil.allPeople && CacheUtil.processlist.contains(CacheUtil.lotteryFile.get(index[i])) ){
                                    tain = true;
                                }
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("<html><body>");
                        for (int i = 0;i < index.length; i++) {
                            sb.append(CacheUtil.lotteryFile.get(index[i]));
                            sb.append("<br/>");
                        }
                        sb.append("</body></html>");
                        screen.setText(sb.toString());
                        indexResult = index;
                    }
                }
            }.start();
        }else if("结束抽奖".equals(button.getText())){
//            this.dispose();
            System.exit(0);
        }
    }

    void JlabelSetText(List<String> content, Integer number){
        int size = content.size() - 1;
        int num = number;
        StringBuilder builder = new StringBuilder("<html>");
        while(num > 0){
            Random r = new Random();
            int randomInt = r.nextInt(size);
            builder.append(content.get(randomInt));
            builder.append("<br/>");
            num--;
        }
        builder.append("</html>");
        screen.setText(builder.toString());
    }

    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }


    public static int[] randomArray(int min,int max,int n){
        int len = max-min+1;

        if(max < min || n > len){
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }
    public static void main(String[] args) {
        int[] reult1 = randomCommon(20,50,10);
        for (int i : reult1) {
            System.out.println(i);
        }

    }
}

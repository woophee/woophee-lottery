import layout.FileConfig;
import utils.Constant;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LotteryMain {



    public static void main(String[] args) {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JOptionPane.showMessageDialog(null, "[For Your Information]" + "\n" + "The software is valid until " + "\n" + sd.format(Constant.EXPIRED_TIME));
        long currentTime = System.currentTimeMillis();
        if(currentTime > Constant.EXPIRED_TIME){
            JOptionPane.showMessageDialog(null,"The software has expired.");
            System.exit(0);
        }
        if(currentTime < Constant.REGISTER_TIME){
            JOptionPane.showMessageDialog(null,"The software has expired.");
            System.exit(0);
        }
        new FileConfig();
    }
}

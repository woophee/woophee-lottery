import layout.FileConfig;
import utils.Constant;

import javax.swing.*;
import java.io.File;

public class LotteryMain {



    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        if(currentTime > Constant.EXPIRED_TIME){
            JOptionPane.showMessageDialog(null,"您的软件已过期，请续费");
            System.exit(0);
        }
        if(currentTime < Constant.REGISTER_TIME){
            JOptionPane.showMessageDialog(null,"您的软件已过期，请续费");
            System.exit(0);
        }
        new FileConfig();
    }
}

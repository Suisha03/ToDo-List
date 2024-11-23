package Unused;
import javax.swing.JFrame;

import FXTest.TestFX;

public class App {
    public static void main(String[] args){
        try{
            String[] params = {"arg1", "arg2"};
            TestFX.main(params);
        } catch (Exception e){
            e.printStackTrace();
        }
        /*
        JFrame frame = new JFrame("MyTitle");
        frame.setBounds(100,100,728,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        */
    }
}

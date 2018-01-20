/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycomputer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author tritu
 */
public class DemoGridLayout extends JFrame {

    private JLabel lblScreen;
    String key1 = "";
    String key2 = "";
    String result = "";
    boolean test = true;
    ArrayList<JButton> buttons = null;
    String[] buttonText = {"AC", "+/-", "%", "+",
                           "1", "2", "3", "-",
                           "4", "5", "6", "*",
                           "7", "8", "9", "/",
                           "0", "=", ".",""
    };
    StringBuffer buffer = new StringBuffer();

    public DemoGridLayout() {
        super("Máy Tính Bỏ Túi");
        buttons = new ArrayList<JButton>();
        this.setSize(400, 400);
        this.setLocation(ABORT, ABORT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.controlUI();
        this.eventUI();

    }

    private void controlUI() {
        // Tạo 1 khoang chưa 
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c = this.getContentPane();

        // Tạo 1 bảng dành cho Screen
        JPanel paneScreen = new JPanel();
        // Screen có thể kéo dãn tại bên phải
        paneScreen.setLayout(new FlowLayout(FlowLayout.RIGHT));
        // Đưa vào kích thước 
        paneScreen.setPreferredSize(new Dimension(0, 110));

        // Tạo 1 bảng dành cho Button
        JPanel paneButton = new JPanel();
        // Tạo lưới các ô cho bảng Button 
        paneButton.setLayout(new GridLayout(5, 4));

        // Tạo các nút bấm có các kí tự 
        for (String text : buttonText) {
            JButton button = new JButton(text);
            button.setFont(new Font("Calibri", Font.BOLD, 20));

            // Các nút này sẽ được thêm vào trong 1 mảng buttons
            buttons.add(button);
            // Thêm vào Pane
            paneButton.add(button);
        }

        // tạo nhãn số 0 xuất hiện trên màn 
        lblScreen = new JLabel("0");
        lblScreen.setFont(new Font("Calibri", Font.BOLD, 45));
        paneScreen.add(lblScreen);

        // thêm bảng Screen vào phía trên UI
        c.add(paneScreen, BorderLayout.NORTH);
        c.add(paneButton, BorderLayout.CENTER);

    }

    private void eventUI() {
        
        // Ta đi xử lí sự kiên cho các nút bấm
        for (JButton bt : buttons) {
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Để biết chính xác nút nào được bấm
                    JButton btnSource = (JButton) e.getSource();
                    if (btnSource.getText().equals("+/-") && buffer.toString().length() == 0) {
                        buffer.append("-");
                        lblScreen.setText(buffer.toString());
                    }
                    if (btnSource.getText().matches("[^0-9=.]")) {
                        result = btnSource.getText();
                        key1 = lblScreen.getText();
                        test = false;
                    }
                    else if(btnSource.getText().matches("[\\d.]")){
                        if(test){
                        hienThi(btnSource);
                        }else{
                        buffer.delete(0, buffer.toString().length());
                            hienThi(btnSource);
                            test = true;
                        }
                    
                    }else if(btnSource.getText().equals("AC")){
                     reSet();
                    }
                    else if(btnSource.getText().equals("=")){
                    if(result.equals(" ")){
                    return;
                    }
                     try {
                            Caculator();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "LỖI! HÃY NHẤN AC ĐỂ TRỞ LẠI");
                        }

                    }
                   
                }
               
            });

        }

    }
    private void hienThi(JButton source){
        String text = source.getText();
        if(lblScreen.getText().equals("0") && text.equals("0")){
        return;
        }
    buffer.append(text);
    lblScreen.setText(buffer.toString());
    
    }
    private void reSet(){
    buffer.delete(0, buffer.toString().length());
    lblScreen.setText("0");
    key1 ="";
    key2 ="";
    result = "";
       
    }
    private void Caculator(){
    key2 = buffer.toString();
    float Operation = 0;
    if(result.equals("+")){
    Operation = Float.parseFloat(key1) + Float.parseFloat(key2);
    }
    else if(result.equals("-")){
        Operation = Float.parseFloat(key1) - Float.parseFloat(key2);    
    }
    else if(result.equals("*")){
    Operation = Float.parseFloat(key1) * Float.parseFloat(key2);
    }
    else if(result.equals("/")){
    Operation = Float.parseFloat(key1) / Float.parseFloat(key2);
    }
    if(Operation == (int) Operation ){
        lblScreen.setText( (int)Operation + "");    
    }else {
    String str = Operation + "";
    char[] stringAr = str.toCharArray();
    if(stringAr.length >7){
    lblScreen.setFont(new Font("Calibri", Font.BOLD, 50));
    }
    lblScreen.setText(str);
    }
    if(result.equals("%")){
    int ret = Integer.parseInt(key1) % Integer.parseInt(key2);
    lblScreen.setText(ret + "");
    }
    buffer.delete(0, buffer.toString().length());
    }

}

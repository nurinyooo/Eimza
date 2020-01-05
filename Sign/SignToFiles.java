package com.ders.Sign;

import com.ders.Pades.PadesSign;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
public class SignToFiles extends JFrame implements ActionListener {
    static JLabel l;
    static JComboBox c1;
    static JFileChooser j,j2;
    PadesSign padesSign = new PadesSign();
    SignToFiles()
    {
    }
    public static void main(String args[])
    {
        SignToFiles f1 = new SignToFiles();

        JFrame f = new JFrame("file chooser");

        f.setSize(400, 400);

        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String s1[] = { "","Pades", "Cades", "Xades" };

        c1 = new JComboBox(s1);


        JButton button1 = new JButton("Aç");

        JButton button2 = new JButton("Kaydet");

        JButton button3 = new JButton("İmzala");


        button1.addActionListener(f1);
        button2.addActionListener(f1);
        button3.addActionListener(f1);
        c1.addActionListener(f1);

        JPanel p = new JPanel();

        p.add(button1);
        p.add(button2);
        p.add(button3);
        p.add(c1);

        l = new JLabel("no file selected");

        p.add(l);
        f.add(p);

        f.show();
    }
    public void actionPerformed(ActionEvent evt)
    {
        String com = evt.getActionCommand();

        if(com.equals("Aç")){
            j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                System.out.println(j.getSelectedFile().toURI());
                l.setText(j.getSelectedFile().getAbsolutePath());
            } else
                l.setText("the user cancelled the operation");
    }else if(com.equals("Kaydet")){
            j2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j2.showSaveDialog(null);

            if(r == JFileChooser.APPROVE_OPTION){
                l.setText(j2.getSelectedFile().getAbsolutePath());
            }
        }else if(com.equals("İmzala")){
            if(c1.getItemAt(c1.getSelectedIndex()) == "Pades"){
                try {
                padesSign.signPades(j,j2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
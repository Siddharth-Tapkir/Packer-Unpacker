import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Packer
{
    private String PackName;
    private String DirName;
    private char Key;

    public Packer(String A, String B, char Key)
    {
        this.PackName = A;
        this.DirName = B;
        this.Key = Key;
    }

    public int PackingActivity()
    {
        try
        {
           int i = 0, j = 0, iRet = 0;
           
           File fobj = new File(DirName);

           if((fobj.exists()) && (fobj.isDirectory()))
           {
                File Packobj = new File(PackName);
                boolean bRet = Packobj.createNewFile();

                if(false == bRet)
                {
                    JOptionPane.showMessageDialog(null, "File already exists! Choose another name.");
                    return -2;
                }
                
                File Arr[] = fobj.listFiles();
                FileOutputStream foobj = new FileOutputStream(Packobj);
                byte Buffer[] = new byte[1024];
                String Header = null;

                for(i = 0; i < Arr.length; i++)
                {
                    Header = Arr[i].getName() +" "+ Arr[i].length();
                    for(j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }
                    foobj.write(Header.getBytes());
                    FileInputStream fiobj = new FileInputStream(Arr[i]);

                    while((iRet = fiobj.read(Buffer)) != -1)
                    {
                        for(int k = 0; k < iRet; k++)
                        {
                            Buffer[k] = (byte)(Buffer[k] ^ Key);
                        }
                        foobj.write(Buffer,0,iRet);
                    }
                    fiobj.close();
                }
           }
           else
           {
                return -1;
           }
        }
        catch(Exception eobj)
        {}
        return 0;
    }
}

class Unpacker
{
    private String PackName;
    private char Key;

    public Unpacker(String A, char Key)
    {
        this.PackName = A;
        this.Key = Key;
    }

    public int UnpackingActivity()
    {
        try
        {
            String Header = null;
            File fobjnew = null;
            int FileSize = 0,iRet = 0;

            File fobj = new File(PackName);

            if(!fobj.exists())
            {
                return -1;
            }

            FileInputStream fiobj = new FileInputStream(fobj);
            byte HeaderBuffer[] = new byte[100];

            while((iRet = fiobj.read(HeaderBuffer,0,100)) != -1)
            {
                Header = new String(HeaderBuffer);
                Header = Header.trim();
                String Tockens[] = Header.split(" ");

                fobjnew = new File(Tockens[0]);
                fobjnew.createNewFile();

                FileSize = Integer.parseInt(Tockens[1]);
                byte Buffer[] = new byte[FileSize];
                FileOutputStream foobj = new FileOutputStream(fobjnew);
                fiobj.read(Buffer,0,FileSize);

                for(int k = 0; k < FileSize; k++)
                {
                    Buffer[k] = (byte)(Buffer[k] ^ Key);
                }

                foobj.write(Buffer,0,FileSize);
                foobj.close();
            }
            
            fiobj.close();
        }
        catch(Exception eobj)
        {}

        return 0;
    }
}

class GUIPack implements ActionListener
{
    public JFrame fobj;
    public JButton bobj, Back;
    public JTextField tobj1, tobj2;
    public JLabel DirName, PackName, heading;

    public GUIPack(int Width, int Height)
    {
        fobj = new JFrame();
        fobj.setTitle("Packer");
        fobj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient background panel
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(30, 30, 60);
                Color color2 = new Color(80, 120, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);

        heading = new JLabel("Pack Your Files");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 55));
        heading.setForeground(Color.WHITE);
        heading.setBounds(740, 200, 600, 80);

        DirName = new JLabel("Directory Name:");
        DirName.setFont(new Font("Segoe UI", Font.PLAIN, 35));
        DirName.setForeground(Color.WHITE);
        DirName.setBounds(550, 400, 300, 50);

        tobj1 = new JTextField();
        tobj1.setFont(new Font("Consolas", Font.PLAIN, 25));
        tobj1.setBounds(880, 400, 400, 50);

        PackName = new JLabel("Output File Name:");
        PackName.setFont(new Font("Segoe UI", Font.PLAIN, 35));
        PackName.setForeground(Color.WHITE);
        PackName.setBounds(550, 470, 300, 50);

        tobj2 = new JTextField();
        tobj2.setFont(new Font("Consolas", Font.PLAIN, 25));
        tobj2.setBounds(880, 470, 400, 50);

        bobj = new JButton("Start Packing");
        bobj.setFont(new Font("Segoe UI", Font.BOLD, 35));
        bobj.setBackground(new Color(60, 150, 80));
        bobj.setForeground(Color.WHITE);
        bobj.setFocusPainted(false);
        bobj.setBounds(700, 580, 500, 70);

        Back = new JButton("← Go Back");
        Back.setFont(new Font("Segoe UI", Font.BOLD, 25));
        Back.setBackground(new Color(120, 40, 40));
        Back.setForeground(Color.WHITE);
        Back.setFocusPainted(false);
        Back.setBounds(30, 30, 180, 50);

        bgPanel.add(heading);
        bgPanel.add(DirName);
        bgPanel.add(PackName);
        bgPanel.add(tobj1);
        bgPanel.add(tobj2);
        bgPanel.add(bobj);
        bgPanel.add(Back);

        fobj.add(bgPanel);
        bobj.addActionListener(this);
        Back.addActionListener(this);

        fobj.setVisible(true);
    }

    public void actionPerformed(ActionEvent aobj)
    {
        if(aobj.getSource() == bobj)
        {
            String dname = tobj1.getText();
            String pname = tobj2.getText();

            Packer mobj = new Packer(pname,dname,'x');
            int iRet = mobj.PackingActivity();

            if(0 == iRet)
                JOptionPane.showMessageDialog(fobj,"✅ Packing Successful!\nPacked items are in: "+pname);
            else if(-1 == iRet)
                JOptionPane.showMessageDialog(fobj,"❌ Directory not found!");
            else if(-2 == iRet)
                JOptionPane.showMessageDialog(fobj,"⚠️ File already exists!");
        }
        else if(aobj.getSource() == Back)
        {
            fobj.dispose();
            new GUI();
        }
    }
}

class GUIUnpack implements ActionListener
{
    public JFrame fobj;
    public JButton unpack, Back;
    public JTextField tobj1;
    public JLabel FileName, heading;

    public GUIUnpack(int Width, int Height)
    {
        fobj = new JFrame();
        fobj.setTitle("Unpacker");
        fobj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(40, 20, 70);
                Color color2 = new Color(100, 60, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);

        heading = new JLabel("Unpack Your Files");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 55));
        heading.setForeground(Color.WHITE);
        heading.setBounds(700, 200, 600, 80);

        FileName = new JLabel("Packed File Name:");
        FileName.setFont(new Font("Segoe UI", Font.PLAIN, 35));
        FileName.setForeground(Color.WHITE);
        FileName.setBounds(550, 470, 350, 50);

        tobj1 = new JTextField();
        tobj1.setFont(new Font("Consolas", Font.PLAIN, 25));
        tobj1.setBounds(900, 470, 400, 50);

        unpack = new JButton("Start Unpacking");
        unpack.setFont(new Font("Segoe UI", Font.BOLD, 35));
        unpack.setBackground(new Color(50, 120, 200));
        unpack.setForeground(Color.WHITE);
        unpack.setFocusPainted(false);
        unpack.setBounds(700, 580, 500, 70);

        Back = new JButton("← Go Back");
        Back.setFont(new Font("Segoe UI", Font.BOLD, 25));
        Back.setBackground(new Color(120, 40, 40));
        Back.setForeground(Color.WHITE);
        Back.setFocusPainted(false);
        Back.setBounds(30, 30, 180, 50);

        bgPanel.add(heading);
        bgPanel.add(FileName);
        bgPanel.add(tobj1);
        bgPanel.add(unpack);
        bgPanel.add(Back);

        fobj.add(bgPanel);
        unpack.addActionListener(this);
        Back.addActionListener(this);

        fobj.setVisible(true);
    }

    public void actionPerformed(ActionEvent aobj)
    {
        if(aobj.getSource() == unpack)
        {    
            String packname = tobj1.getText();

            Unpacker mobj = new Unpacker(packname,'x');
            int iRet = mobj.UnpackingActivity();

            if(0 == iRet)
                JOptionPane.showMessageDialog(fobj,"✅ Unpacking Successful!");
            else if(-1 == iRet)
                JOptionPane.showMessageDialog(fobj,"❌ Unable to access file!");
        }
        else if(aobj.getSource() == Back)
        {
            fobj.dispose();
            new GUI();
        }
    }
}

class GUI extends JFrame implements ActionListener
{
    public JButton pack;
    public JButton unpack;
    public JLabel heading;

    public GUI()
    {   
        super("Packer-Unpacker");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(10, 10, 40);
                Color color2 = new Color(60, 80, 140);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);

        heading = new JLabel("Packer & Unpacker");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 60));
        heading.setForeground(Color.WHITE);
        heading.setBounds(710, 200, 700, 80);

        pack = new JButton("Pack Files");
        unpack = new JButton("Unpack Files");

        pack.setFont(new Font("Segoe UI", Font.BOLD, 40));
        unpack.setFont(new Font("Segoe UI", Font.BOLD, 40));

        pack.setBackground(new Color(60, 160, 80));
        unpack.setBackground(new Color(80, 120, 200));

        pack.setForeground(Color.WHITE);
        unpack.setForeground(Color.WHITE);

        pack.setFocusPainted(false);
        unpack.setFocusPainted(false);

        pack.setBounds(650, 420, 300, 100);
        unpack.setBounds(1000, 420, 300, 100);

        bgPanel.add(heading);
        bgPanel.add(pack);
        bgPanel.add(unpack);

        add(bgPanel);

        pack.addActionListener(this);
        unpack.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent aobj)
    {
        if(aobj.getSource() == pack)
        {
            dispose();
            new GUIPack(400,300);
        }
        else if(aobj.getSource() == unpack)
        {
            dispose();
            new GUIUnpack(400,300);
        }
    }
}

class PackerUnpacker
{
    public static void main(String A[])
    {
        new GUI();
    }
}

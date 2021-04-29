/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablepane;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */



public class tablepane extends javax.swing.JFrame {

    public void my_initial(){
      setLocationRelativeTo(null);
    }
    
    /**
     * Creates new form tablepane
     */
    public tablepane() {
        initComponents();
        my_initial();
        getConnection();
        tab1.setBackground(new Color(204,0,204));
        jp1.setVisible(true);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
        ShowTable();
    }
    String ImgPath = null;
    int pos = 0;
    public Connection getConnection(){
        Connection con = null;
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/flashcards", "root", "");
            //JOptionPane.showMessageDialog(null, "Connected");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(tablepane.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "Not Connected");
            return null;
        }
    }
    // Check Input Fields
    public boolean checkInputs(){
        if(
              txt_english.getText() != null
           && txt_vietnamese.getText() != null
           && txt_pronunciation.getText() != null
           && txt_note.getText() != null
          ){
          return true;
        }
        return false;
    }
    //resize imagetab2
    public ImageIcon ResizeImage(String imagePath, byte [] pic){
        ImageIcon myImage = null;
        if (imagePath!= null){
            myImage = new ImageIcon(imagePath);
        }
        else{
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 =img.getScaledInstance(lb_image.getWidth(), lb_image.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }       
    //resize imagetab3
    public ImageIcon ResizeImageTab3(String imagePath, byte [] pic){
        ImageIcon myImage = null;
        if (imagePath!= null){
            myImage = new ImageIcon(imagePath);
        }
        else{
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 =img.getScaledInstance(lb_image3.getWidth(), lb_image3.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }       
    //resize imagetab4
    public ImageIcon ResizeImageTab4E(String imagePath, byte [] pic){
        ImageIcon myImage = null;
        if (imagePath!= null){
            myImage = new ImageIcon(imagePath);
        }
        else{
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 =img.getScaledInstance(lb_ansIMG.getWidth(), lb_ansIMG.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }       
    //Display Data in table
    //ArrayList With Data
    public ArrayList<table> getTableList(){
        ArrayList<table> tableList = new ArrayList<table>();
        Connection con = getConnection();
        String query = "SELECT * FROM flashcard";
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            table tb;
            while (rs.next()){
                tb = new table(rs.getInt("id"), rs.getString("english"),
                                rs.getString("vietnamese"), rs.getString("pronunciation"),
                                rs.getString("note"), rs.getBytes("image"));
                tableList.add(tb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(tablepane.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableList;
    }
    // show table => jtable
    public void ShowTable(){
        ArrayList<table> list = getTableList();
        DefaultTableModel model = (DefaultTableModel)jTable_table.getModel();
        // clear jtable content
        model.setRowCount(0);
        Object[] row = new Object[6];
        for (int i=0; i< list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getEnglish();
            row[2] = list.get(i).getVietnamese();
            row[3] = list.get(i).getPronunciation();
            row[4] = list.get(i).getNote();
            row[5] = list.get(i).getImage();
            
            //ImageIcon pic = new ImageIcon(new ImageIcon list.get(i).getImage());                  
            model.addRow(row);
        }
    }
    
      // Show Data In Inputs
    public void ShowItemInTab1(int item){
            txt_id.setText(Integer.toString(getTableList().get(item).getId()));
            txt_english.setText(getTableList().get(item).getEnglish());
            txt_vietnamese.setText(getTableList().get(item).getVietnamese());
            txt_pronunciation.setText(getTableList().get(item).getPronunciation());
            txt_note.setText(getTableList().get(item).getNote());
            lb_image.setIcon(ResizeImage(null, getTableList().get(item).getImage()));
    }
    public void ShowItemInTab3(int item){
            //txt_id.setText(Integer.toString(getTableList().get(item).getId()));
            txt_english3.setText(getTableList().get(item).getEnglish());
            txt_vietnamese3.setText(getTableList().get(item).getVietnamese());
            txt_pronunciation3.setText(getTableList().get(item).getPronunciation());
            txt_note3.setText(getTableList().get(item).getNote());
            lb_image3.setIcon(ResizeImageTab3(null, getTableList().get(item).getImage()));
    }
    //Show question in tab 4_1
    public void ShowTab4(int item){
            lb_ques.setText(getTableList().get(item).getEnglish());
            lb_pro.setText(getTableList().get(item).getPronunciation());
    }
    //Show question in tab 4_2
    public void ShowTab4_2(int item){
            lb_ques1.setText(getTableList().get(item).getVietnamese());
    }
    public void  ShowTaskVocabularyTab4_2(int item){
            lb_ansV.setText(getTableList().get(item).getVietnamese());
            lb_ansPro1.setText(getTableList().get(item).getPronunciation());
            lb_ansE1.setText(getTableList().get(item).getEnglish());
            lb_ansNote1.setText(getTableList().get(item).getNote());
            lb_ansIMG1.setIcon(ResizeImageTab4E(null, getTableList().get(item).getImage()));
    }
    public void  ShowTaskVocabularyTab4(int item){
            lb_ansAns.setText(getTableList().get(item).getVietnamese());
            lb_ansPro.setText(getTableList().get(item).getPronunciation());
            lb_ansE.setText(getTableList().get(item).getEnglish());
            lb_ansNote.setText(getTableList().get(item).getNote());
            lb_ansIMG.setIcon(ResizeImageTab4E(null, getTableList().get(item).getImage()));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        bg = new javax.swing.JPanel();
        topjp = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_exit = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        leftjp = new javax.swing.JPanel();
        tab1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        bottomjp = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        middle = new javax.swing.JPanel();
        jp1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jppp2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        label2 = new java.awt.Label();
        jpppp1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jppp3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        label3 = new java.awt.Label();
        jp2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_english = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_vietnamese = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_pronunciation = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_note = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        lb_image = new javax.swing.JLabel();
        btn_browse = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_table = new javax.swing.JTable();
        jp3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lb_image3 = new javax.swing.JLabel();
        txt_vietnamese3 = new javax.swing.JTextField();
        txt_english3 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_note3 = new javax.swing.JTextArea();
        txt_pronunciation3 = new javax.swing.JTextField();
        btn_back3 = new javax.swing.JButton();
        btn_next3 = new javax.swing.JButton();
        jp4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jb_left4 = new javax.swing.JPanel();
        tab1_4 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        tab2_4 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jp_right4 = new javax.swing.JPanel();
        jp1_4 = new javax.swing.JPanel();
        jp_quesition = new javax.swing.JPanel();
        btn_sub = new javax.swing.JButton();
        lb_ques = new javax.swing.JLabel();
        txt_ans = new javax.swing.JTextField();
        lb_pro = new javax.swing.JLabel();
        jp_assiitaint = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        bn_showPic = new javax.swing.JButton();
        btn_showNote = new javax.swing.JButton();
        btn_showAns = new javax.swing.JButton();
        jp_answer = new javax.swing.JPanel();
        lb_ansE = new javax.swing.JLabel();
        lb_ansPro = new javax.swing.JLabel();
        lb_ansIMG = new javax.swing.JLabel();
        lb_ansAns = new javax.swing.JLabel();
        lb_ansNote = new javax.swing.JTextArea();
        btn_nextTab4 = new javax.swing.JButton();
        btn_backTab4 = new javax.swing.JButton();
        jp2_4 = new javax.swing.JPanel();
        jp1_5 = new javax.swing.JPanel();
        jp_quesition1 = new javax.swing.JPanel();
        btn_sub1 = new javax.swing.JButton();
        lb_ques1 = new javax.swing.JLabel();
        txt_ans1 = new javax.swing.JTextField();
        jp_assiitaint1 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        bn_showPic1 = new javax.swing.JButton();
        btn_showNote1 = new javax.swing.JButton();
        btn_showAns1 = new javax.swing.JButton();
        jp_answer1 = new javax.swing.JPanel();
        lb_ansE1 = new javax.swing.JLabel();
        lb_ansPro1 = new javax.swing.JLabel();
        lb_ansIMG1 = new javax.swing.JLabel();
        lb_ansV = new javax.swing.JLabel();
        lb_ansNote1 = new javax.swing.JTextArea();
        btn_nextTab5 = new javax.swing.JButton();
        btn_backTab5 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Flashcard ");
        setFocusTraversalPolicyProvider(true);
        setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        setIconImages(null);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topjp.setBackground(new java.awt.Color(102, 0, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("My Flashcard - Professional English");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("v1.0.n");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_48px.png"))); // NOI18N

        lb_exit.setBackground(new java.awt.Color(102, 0, 102));
        lb_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_delete_32px_1.png"))); // NOI18N
        lb_exit.setOpaque(true);
        lb_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_exitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_exitMouseEntered(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(102, 0, 102));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_subtract_32px.png"))); // NOI18N
        jLabel14.setOpaque(true);

        javax.swing.GroupLayout topjpLayout = new javax.swing.GroupLayout(topjp);
        topjp.setLayout(topjpLayout);
        topjpLayout.setHorizontalGroup(
            topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topjpLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addGap(32, 32, 32)
                .addGroup(topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topjpLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(topjpLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 379, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))))
        );
        topjpLayout.setVerticalGroup(
            topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topjpLayout.createSequentialGroup()
                .addGroup(topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(topjpLayout.createSequentialGroup()
                        .addGroup(topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(topjpLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(topjpLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        bg.add(topjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 812, 60));

        leftjp.setBackground(new java.awt.Color(153, 0, 153));
        leftjp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tab1.setBackground(new java.awt.Color(153, 0, 153));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_home_48px.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(153, 153, 153));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Infomation");

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addContainerGap(29, Short.MAX_VALUE))
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        leftjp.add(tab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -4, 110, 110));

        tab2.setBackground(new java.awt.Color(153, 0, 153));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_new_ticket_48px.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(" Edit Vocabulary");

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        leftjp.add(tab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 110, 110));

        tab3.setBackground(new java.awt.Color(153, 0, 153));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_create_48px.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(" Practice");

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(26, 26, 26))
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        leftjp.add(tab3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 110, 110));

        tab4.setBackground(new java.awt.Color(153, 0, 153));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_game_controller_48px_1.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Play Game");

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        leftjp.add(tab4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 110, 110));

        bg.add(leftjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 110, 550));

        bottomjp.setBackground(new java.awt.Color(102, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("@thuthao");

        javax.swing.GroupLayout bottomjpLayout = new javax.swing.GroupLayout(bottomjp);
        bottomjp.setLayout(bottomjpLayout);
        bottomjpLayout.setHorizontalGroup(
            bottomjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomjpLayout.createSequentialGroup()
                .addContainerGap(640, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        bottomjpLayout.setVerticalGroup(
            bottomjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomjpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        bg.add(bottomjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 700, 40));

        middle.setLayout(new javax.swing.OverlayLayout(middle));

        jp1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel31.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel31.setText("Hope you guys enjoy it!");

        jLabel16.setFont(new java.awt.Font("Vladimir Script", 0, 24)); // NOI18N
        jLabel16.setText("Author:  Thu Thao");

        jLabel32.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel32.setText("My Flashcard is an application supporting you guys to improve your English skills. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addComponent(jLabel16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel8.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 680, 100));

        jppp2.setBackground(new java.awt.Color(255, 255, 255));
        jppp2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jppp2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jppp2MouseClicked(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(153, 0, 153));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_create_120px_2.png"))); // NOI18N
        jLabel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel17.setOpaque(true);

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label2.setText("Practice");

        javax.swing.GroupLayout jppp2Layout = new javax.swing.GroupLayout(jppp2);
        jppp2.setLayout(jppp2Layout);
        jppp2Layout.setHorizontalGroup(
            jppp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jppp2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jppp2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(43, 43, 43))
        );
        jppp2Layout.setVerticalGroup(
            jppp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jppp2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jPanel8.add(jppp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 220, 370));

        jpppp1.setBackground(new java.awt.Color(255, 255, 255));
        jpppp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jpppp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpppp1MouseClicked(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(153, 0, 153));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_new_ticket_120px.png"))); // NOI18N
        jLabel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel23.setOpaque(true);

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label1.setText("Edit Vocabulary");

        javax.swing.GroupLayout jpppp1Layout = new javax.swing.GroupLayout(jpppp1);
        jpppp1.setLayout(jpppp1Layout);
        jpppp1Layout.setHorizontalGroup(
            jpppp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addGroup(jpppp1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpppp1Layout.setVerticalGroup(
            jpppp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpppp1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jPanel8.add(jpppp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 220, 370));

        jppp3.setBackground(new java.awt.Color(255, 255, 255));
        jppp3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jppp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jppp3MouseClicked(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(153, 0, 153));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_game_controller_120px.png"))); // NOI18N
        jLabel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel24.setOpaque(true);

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label3.setText("Play Game");

        javax.swing.GroupLayout jppp3Layout = new javax.swing.GroupLayout(jppp3);
        jppp3.setLayout(jppp3Layout);
        jppp3Layout.setHorizontalGroup(
            jppp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jppp3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(33, 33, 33))
        );
        jppp3Layout.setVerticalGroup(
            jppp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jppp3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jPanel8.add(jppp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 210, 370));

        javax.swing.GroupLayout jp1Layout = new javax.swing.GroupLayout(jp1);
        jp1.setLayout(jp1Layout);
        jp1Layout.setHorizontalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp1Layout.setVerticalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        middle.add(jp1);

        jp2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setText("ID:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 16, 23, -1));

        txt_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_idMouseClicked(evt);
            }
        });
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        jPanel3.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 13, 232, -1));

        jLabel19.setText("English:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 47, -1, -1));

        txt_english.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_englishMouseClicked(evt);
            }
        });
        jPanel3.add(txt_english, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 44, 232, -1));

        jLabel20.setText("Vietnamese:");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 78, -1, -1));

        txt_vietnamese.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_vietnameseMouseClicked(evt);
            }
        });
        jPanel3.add(txt_vietnamese, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 75, 232, -1));

        jLabel21.setText("Pronunciation:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 106, -1, -1));

        txt_pronunciation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_pronunciationMouseClicked(evt);
            }
        });
        jPanel3.add(txt_pronunciation, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 106, 232, -1));

        jLabel22.setText("Note:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 154, -1, -1));

        txt_note.setColumns(20);
        txt_note.setRows(5);
        txt_note.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_noteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txt_note);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 132, 232, 80));

        jLabel18.setText("Picture:");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 51, 40, -1));

        lb_image.setBackground(new java.awt.Color(255, 255, 255));
        lb_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        lb_image.setOpaque(true);
        jPanel3.add(lb_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 11, 176, 163));

        btn_browse.setBackground(new java.awt.Color(153, 0, 153));
        btn_browse.setForeground(new java.awt.Color(255, 255, 255));
        btn_browse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_folder_24px.png"))); // NOI18N
        btn_browse.setText("Browse");
        btn_browse.setIconTextGap(10);
        btn_browse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_browseMouseClicked(evt);
            }
        });
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });
        jPanel3.add(btn_browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 25, -1, -1));

        btn_add.setBackground(new java.awt.Color(153, 0, 153));
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_plus_24px_4.png"))); // NOI18N
        btn_add.setText("Add");
        btn_add.setIconTextGap(10);
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel3.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 185, 89, -1));

        btn_update.setBackground(new java.awt.Color(153, 0, 153));
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_update_left_rotation_24px.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.setIconTextGap(10);
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel3.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 185, -1, -1));

        btn_delete.setBackground(new java.awt.Color(153, 0, 153));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_delete_24px_2.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.setIconTextGap(10);
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel3.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 185, -1, -1));

        jTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "English", "Vietnamese", "Pronunciation", "Note"
            }
        ));
        jTable_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_table);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 229, 703, 281));

        javax.swing.GroupLayout jp2Layout = new javax.swing.GroupLayout(jp2);
        jp2.setLayout(jp2Layout);
        jp2Layout.setHorizontalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp2Layout.setVerticalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        middle.add(jp2);

        jp3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lb_image3.setBackground(new java.awt.Color(204, 204, 255));
        lb_image3.setOpaque(true);

        txt_vietnamese3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_vietnamese3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_vietnamese3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txt_english3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_english3.setForeground(new java.awt.Color(102, 0, 153));
        txt_english3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_english3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_english3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_english3ActionPerformed(evt);
            }
        });

        txt_note3.setColumns(20);
        txt_note3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_note3.setRows(5);
        txt_note3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_note3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane4.setViewportView(txt_note3);

        txt_pronunciation3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        txt_pronunciation3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pronunciation3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btn_back3.setBackground(new java.awt.Color(153, 0, 153));
        btn_back3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_back_24px_2.png"))); // NOI18N
        btn_back3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back3ActionPerformed(evt);
            }
        });

        btn_next3.setBackground(new java.awt.Color(153, 0, 153));
        btn_next3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_forward_24px.png"))); // NOI18N
        btn_next3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_next3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(btn_back3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(lb_image3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btn_next3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(txt_vietnamese3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_english3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_pronunciation3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(110, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txt_english3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_pronunciation3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_image3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_back3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_next3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(txt_vietnamese3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jp3Layout = new javax.swing.GroupLayout(jp3);
        jp3.setLayout(jp3Layout);
        jp3Layout.setHorizontalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp3Layout.setVerticalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        middle.add(jp3);

        jp4.setBackground(new java.awt.Color(255, 255, 255));

        tab1_4.setBackground(new java.awt.Color(102, 0, 102));
        tab1_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1_4MouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Play Game E - V");

        javax.swing.GroupLayout tab1_4Layout = new javax.swing.GroupLayout(tab1_4);
        tab1_4.setLayout(tab1_4Layout);
        tab1_4Layout.setHorizontalGroup(
            tab1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab1_4Layout.setVerticalGroup(
            tab1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab2_4.setBackground(new java.awt.Color(102, 0, 102));
        tab2_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2_4MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Play Game V - E");

        javax.swing.GroupLayout tab2_4Layout = new javax.swing.GroupLayout(tab2_4);
        tab2_4.setLayout(tab2_4Layout);
        tab2_4Layout.setHorizontalGroup(
            tab2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );
        tab2_4Layout.setVerticalGroup(
            tab2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jb_left4Layout = new javax.swing.GroupLayout(jb_left4);
        jb_left4.setLayout(jb_left4Layout);
        jb_left4Layout.setHorizontalGroup(
            jb_left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab2_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jb_left4Layout.setVerticalGroup(
            jb_left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jb_left4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(tab1_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab2_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );

        jp_right4.setLayout(new javax.swing.OverlayLayout(jp_right4));

        jp1_4.setBackground(new java.awt.Color(255, 255, 255));

        jp_quesition.setBackground(new java.awt.Color(255, 255, 255));
        jp_quesition.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_sub.setBackground(new java.awt.Color(102, 0, 102));
        btn_sub.setForeground(new java.awt.Color(255, 255, 255));
        btn_sub.setText("Submit");
        btn_sub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_subActionPerformed(evt);
            }
        });

        lb_ques.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_ques.setForeground(new java.awt.Color(0, 51, 153));
        lb_ques.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txt_ans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ansActionPerformed(evt);
            }
        });

        lb_pro.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lb_pro.setForeground(new java.awt.Color(0, 51, 153));
        lb_pro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jp_quesitionLayout = new javax.swing.GroupLayout(jp_quesition);
        jp_quesition.setLayout(jp_quesitionLayout);
        jp_quesitionLayout.setHorizontalGroup(
            jp_quesitionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_ques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_pro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesitionLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jp_quesitionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesitionLayout.createSequentialGroup()
                        .addComponent(btn_sub, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesitionLayout.createSequentialGroup()
                        .addComponent(txt_ans, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jp_quesitionLayout.setVerticalGroup(
            jp_quesitionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesitionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_ques, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_ans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_sub, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jp_assiitaint.setBackground(new java.awt.Color(255, 255, 255));
        jp_assiitaint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel12.setBackground(new java.awt.Color(153, 0, 153));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Help");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        bn_showPic.setBackground(new java.awt.Color(102, 0, 102));
        bn_showPic.setForeground(new java.awt.Color(255, 255, 255));
        bn_showPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        bn_showPic.setText("Picture");
        bn_showPic.setIconTextGap(8);
        bn_showPic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bn_showPicActionPerformed(evt);
            }
        });

        btn_showNote.setBackground(new java.awt.Color(102, 0, 102));
        btn_showNote.setForeground(new java.awt.Color(255, 255, 255));
        btn_showNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        btn_showNote.setText("Note");
        btn_showNote.setIconTextGap(17);
        btn_showNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showNoteActionPerformed(evt);
            }
        });

        btn_showAns.setBackground(new java.awt.Color(102, 0, 102));
        btn_showAns.setForeground(new java.awt.Color(255, 255, 255));
        btn_showAns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        btn_showAns.setText("Answer");
        btn_showAns.setIconTextGap(8);
        btn_showAns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showAnsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_assiitaintLayout = new javax.swing.GroupLayout(jp_assiitaint);
        jp_assiitaint.setLayout(jp_assiitaintLayout);
        jp_assiitaintLayout.setHorizontalGroup(
            jp_assiitaintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bn_showPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_showNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_showAns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp_assiitaintLayout.setVerticalGroup(
            jp_assiitaintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_assiitaintLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bn_showPic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_showNote)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_showAns)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jp_answer.setBackground(new java.awt.Color(255, 255, 255));
        jp_answer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jp_answer.setForeground(new java.awt.Color(204, 204, 204));
        jp_answer.setToolTipText("");

        lb_ansE.setBackground(new java.awt.Color(255, 255, 255));
        lb_ansE.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_ansE.setForeground(new java.awt.Color(0, 0, 102));
        lb_ansE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansPro.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lb_ansPro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansAns.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_ansAns.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansNote.setColumns(20);
        lb_ansNote.setRows(5);

        javax.swing.GroupLayout jp_answerLayout = new javax.swing.GroupLayout(jp_answer);
        jp_answer.setLayout(jp_answerLayout);
        jp_answerLayout.setHorizontalGroup(
            jp_answerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_ansAns, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp_answerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_answerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_answerLayout.createSequentialGroup()
                        .addGap(0, 49, Short.MAX_VALUE)
                        .addComponent(lb_ansIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addComponent(lb_ansNote))
                .addContainerGap())
            .addComponent(lb_ansPro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_ansE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp_answerLayout.setVerticalGroup(
            jp_answerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_answerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_ansE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ansPro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ansIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_ansAns, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_ansNote, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        btn_nextTab4.setBackground(new java.awt.Color(102, 0, 102));
        btn_nextTab4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_chevron_right_24px.png"))); // NOI18N
        btn_nextTab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextTab4ActionPerformed(evt);
            }
        });

        btn_backTab4.setBackground(new java.awt.Color(102, 0, 102));
        btn_backTab4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_chevron_left_24px.png"))); // NOI18N
        btn_backTab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backTab4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp1_4Layout = new javax.swing.GroupLayout(jp1_4);
        jp1_4.setLayout(jp1_4Layout);
        jp1_4Layout.setHorizontalGroup(
            jp1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp1_4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_backTab4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1_4Layout.createSequentialGroup()
                        .addComponent(jp_quesition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_nextTab4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jp_answer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jp_assiitaint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp1_4Layout.setVerticalGroup(
            jp1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp1_4Layout.createSequentialGroup()
                .addGroup(jp1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1_4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jp1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jp1_4Layout.createSequentialGroup()
                                .addComponent(btn_nextTab4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addComponent(jp_quesition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jp1_4Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btn_backTab4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addGap(18, 18, 18)
                .addComponent(jp_answer, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(jp1_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jp_assiitaint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
        );

        jp_right4.add(jp1_4);

        jp2_4.setBackground(new java.awt.Color(255, 204, 255));

        jp1_5.setBackground(new java.awt.Color(255, 255, 255));

        jp_quesition1.setBackground(new java.awt.Color(255, 255, 255));
        jp_quesition1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_sub1.setBackground(new java.awt.Color(102, 0, 102));
        btn_sub1.setForeground(new java.awt.Color(255, 255, 255));
        btn_sub1.setText("Submit");
        btn_sub1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sub1ActionPerformed(evt);
            }
        });

        lb_ques1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_ques1.setForeground(new java.awt.Color(0, 51, 153));
        lb_ques1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txt_ans1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ans1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_quesition1Layout = new javax.swing.GroupLayout(jp_quesition1);
        jp_quesition1.setLayout(jp_quesition1Layout);
        jp_quesition1Layout.setHorizontalGroup(
            jp_quesition1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_ques1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesition1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jp_quesition1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesition1Layout.createSequentialGroup()
                        .addComponent(btn_sub1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesition1Layout.createSequentialGroup()
                        .addComponent(txt_ans1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        jp_quesition1Layout.setVerticalGroup(
            jp_quesition1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_quesition1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_ques1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_ans1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btn_sub1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jp_assiitaint1.setBackground(new java.awt.Color(255, 255, 255));
        jp_assiitaint1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel14.setBackground(new java.awt.Color(153, 0, 153));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Help");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addContainerGap())
        );

        bn_showPic1.setBackground(new java.awt.Color(102, 0, 102));
        bn_showPic1.setForeground(new java.awt.Color(255, 255, 255));
        bn_showPic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        bn_showPic1.setText("Picture");
        bn_showPic1.setIconTextGap(8);
        bn_showPic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bn_showPic1ActionPerformed(evt);
            }
        });

        btn_showNote1.setBackground(new java.awt.Color(102, 0, 102));
        btn_showNote1.setForeground(new java.awt.Color(255, 255, 255));
        btn_showNote1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        btn_showNote1.setText("Note");
        btn_showNote1.setIconTextGap(17);
        btn_showNote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showNote1ActionPerformed(evt);
            }
        });

        btn_showAns1.setBackground(new java.awt.Color(102, 0, 102));
        btn_showAns1.setForeground(new java.awt.Color(255, 255, 255));
        btn_showAns1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_idea_24px.png"))); // NOI18N
        btn_showAns1.setText("Answer");
        btn_showAns1.setIconTextGap(8);
        btn_showAns1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showAns1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_assiitaint1Layout = new javax.swing.GroupLayout(jp_assiitaint1);
        jp_assiitaint1.setLayout(jp_assiitaint1Layout);
        jp_assiitaint1Layout.setHorizontalGroup(
            jp_assiitaint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bn_showPic1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_showNote1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_showAns1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp_assiitaint1Layout.setVerticalGroup(
            jp_assiitaint1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_assiitaint1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bn_showPic1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_showNote1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_showAns1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jp_answer1.setBackground(new java.awt.Color(255, 255, 255));
        jp_answer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jp_answer1.setForeground(new java.awt.Color(204, 204, 204));
        jp_answer1.setToolTipText("");

        lb_ansE1.setBackground(new java.awt.Color(255, 255, 255));
        lb_ansE1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_ansE1.setForeground(new java.awt.Color(0, 0, 102));
        lb_ansE1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansPro1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lb_ansPro1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_ansV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_ansNote1.setColumns(20);
        lb_ansNote1.setRows(5);

        javax.swing.GroupLayout jp_answer1Layout = new javax.swing.GroupLayout(jp_answer1);
        jp_answer1.setLayout(jp_answer1Layout);
        jp_answer1Layout.setHorizontalGroup(
            jp_answer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_ansV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp_answer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_answer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_answer1Layout.createSequentialGroup()
                        .addGap(0, 49, Short.MAX_VALUE)
                        .addComponent(lb_ansIMG1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addComponent(lb_ansNote1))
                .addContainerGap())
            .addComponent(lb_ansPro1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_ansE1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp_answer1Layout.setVerticalGroup(
            jp_answer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_answer1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_ansE1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ansPro1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ansIMG1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_ansV, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_ansNote1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        btn_nextTab5.setBackground(new java.awt.Color(102, 0, 102));
        btn_nextTab5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_chevron_right_24px.png"))); // NOI18N
        btn_nextTab5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextTab5ActionPerformed(evt);
            }
        });

        btn_backTab5.setBackground(new java.awt.Color(102, 0, 102));
        btn_backTab5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_chevron_left_24px.png"))); // NOI18N
        btn_backTab5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backTab5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp1_5Layout = new javax.swing.GroupLayout(jp1_5);
        jp1_5.setLayout(jp1_5Layout);
        jp1_5Layout.setHorizontalGroup(
            jp1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp1_5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_backTab5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1_5Layout.createSequentialGroup()
                        .addComponent(jp_quesition1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_nextTab5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jp_answer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jp_assiitaint1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp1_5Layout.setVerticalGroup(
            jp1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp1_5Layout.createSequentialGroup()
                .addGroup(jp1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1_5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jp1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jp1_5Layout.createSequentialGroup()
                                .addComponent(btn_nextTab5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addComponent(jp_quesition1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jp1_5Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btn_backTab5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addGap(18, 18, 18)
                .addComponent(jp_answer1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(jp1_5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jp_assiitaint1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp2_4Layout = new javax.swing.GroupLayout(jp2_4);
        jp2_4.setLayout(jp2_4Layout);
        jp2_4Layout.setHorizontalGroup(
            jp2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
            .addGroup(jp2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp2_4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jp1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jp2_4Layout.setVerticalGroup(
            jp2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
            .addGroup(jp2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jp2_4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jp1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jp_right4.add(jp2_4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jb_left4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jp_right4, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jb_left4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jp_right4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jp4Layout = new javax.swing.GroupLayout(jp4);
        jp4.setLayout(jp4Layout);
        jp4Layout.setHorizontalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp4Layout.setVerticalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        middle.add(jp4);

        bg.add(middle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 700, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
                                                  
    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        jp1.setVisible(true);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
        tab1.setBackground(new Color(204,0,204));
        tab2.setBackground(new Color(153,0,153));
        tab3.setBackground(new Color(153,0,153));
        tab4.setBackground(new Color(153,0,153));
    }//GEN-LAST:event_tab1MouseClicked

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        // 
        jp2.setVisible(true);
        jp1.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
        tab2.setBackground(new Color(204,0,204));
        tab1.setBackground(new Color(153,0,153));
        tab3.setBackground(new Color(153,0,153));
        tab4.setBackground(new Color(153,0,153));
        //ShowItemInTab1(0);
    }//GEN-LAST:event_tab2MouseClicked

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
           // TODO add your handling code here:
        jp3.setVisible(true);
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp4.setVisible(false);
        tab3.setBackground(new Color(204,0,204));
        tab2.setBackground(new Color(153,0,153));
        tab1.setBackground(new Color(153,0,153));
        tab4.setBackground(new Color(153,0,153));
        ShowItemInTab3(0);
    }//GEN-LAST:event_tab3MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        // TODO add your handling code here:
        jp4.setVisible(true);
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(false);
        tab4.setBackground(new Color(204,0,204));
        tab2.setBackground(new Color(153,0,153));
        tab3.setBackground(new Color(153,0,153));
        tab1.setBackground(new Color(153,0,153));
        jp1_4.setVisible(true);
        jp2_4.setVisible(false);
        jp_quesition.setVisible(true);
        jp_assiitaint.setVisible(true);
        jp_answer.setVisible(false);
        ShowTab4(0);
        tab1_4.setBackground(new Color(204,0,204));
    }//GEN-LAST:event_tab4MouseClicked

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images","jpg","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            lb_image.setIcon(ResizeImage(path, null));
            ImgPath = path;
        }
        else{
            System.out.print("No file selected");
        }
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
         if(checkInputs() && ImgPath != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO flashcard(english,vietnamese,pronunciation,note,image)"
                        + "values(?,?,?,?,?) ");
                ps.setString(1, txt_english.getText());
                ps.setString(2, txt_vietnamese.getText());
                ps.setString(3, txt_pronunciation.getText());
                ps.setString(4, txt_note.getText());         
                InputStream img = new FileInputStream(new File(ImgPath));
                ps.setBlob(5, img);
                ps.executeUpdate();
                ShowTable();
               
                JOptionPane.showMessageDialog(null, "Your Vocabulary Inserted");
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
        }
        
    }//GEN-LAST:event_btn_addActionPerformed
    //update
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        if(checkInputs()&&txt_id.getText()!=null){
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
           
            // update without image
            if(ImgPath == null)
            {
                try {
                    UpdateQuery = "UPDATE flashcard SET english = ?, vietnamese = ?"
                            + ", pronunciation = ?, note = ? WHERE id = ?";
                    ps = con.prepareStatement(UpdateQuery);
                   
                    ps.setString(1, txt_english.getText());
                    ps.setString(2, txt_vietnamese.getText());
                    ps.setString(3, txt_pronunciation.getText());
                    ps.setString(4, txt_note.getText());
                    ps.setInt(5, Integer.parseInt(txt_id.getText()));
      
                   
                    ps.executeUpdate();
                    ShowTable();
                    JOptionPane.showMessageDialog(null, "Your Vocabulary Updated");
                   
                } catch (SQLException ex) {
                    Logger.getLogger(tablepane.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            // update With Image
            else{
                try{
                    InputStream img = new FileInputStream(new File(ImgPath));
                    UpdateQuery = "UPDATE flashcard SET english = ?, vietnamese = ?"
                            + ", pronunciation = ?, note = ?, image = ? WHERE id = ?";
               
                    ps = con.prepareStatement(UpdateQuery);
                   
                    ps.setString(1, txt_english.getText());
                    ps.setString(2, txt_vietnamese.getText());
                    ps.setString(3, txt_pronunciation.getText());
                    ps.setString(4, txt_note.getText());
                    ps.setBlob(5, img);
                    
                    ps.setInt(6, Integer.parseInt(txt_id.getText()));
                   
                    ps.executeUpdate();
                    ShowTable();
                    JOptionPane.showMessageDialog(null, "Your Vocabulary updated");
               
                }catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "One of fields is empty or wrong");
        }
    }//GEN-LAST:event_btn_updateActionPerformed
    //Delete
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (!txt_id.getText().equals("")){
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM flashcard WHERE id = ?");
                int id = Integer.parseInt(txt_id.getText());
                ps.setInt(1, id);
                ps.executeUpdate();
                ShowTable();
                JOptionPane.showMessageDialog(null, "Your Vocabulary deleted");
            } catch (SQLException ex) {
                Logger.getLogger(tablepane.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Your Vocabulary not deleted");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Your Vocabulary not deleted: no Id to Delete");
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void jTable_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_tableMouseClicked
        // TODO add your handling code here:
        int index = jTable_table.getSelectedRow();
        ShowItemInTab1(index);
    }//GEN-LAST:event_jTable_tableMouseClicked

    private void txt_english3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_english3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_english3ActionPerformed

    private void btn_back3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back3ActionPerformed
        // TODO add your handling code here:
        pos--;
        if(pos < 0)
        pos = 0;
        ShowItemInTab3(pos);
    }//GEN-LAST:event_btn_back3ActionPerformed

    private void btn_next3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_next3ActionPerformed
        // TODO add your handling code here:
        pos++;
        if(pos >= getTableList().size())
            pos = getTableList().size()-1;
        ShowItemInTab3(pos);
    }//GEN-LAST:event_btn_next3ActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_idMouseClicked
        // TODO add your handling code here:\
        txt_id.setText("");
        txt_english.setText("");
        txt_vietnamese.setText("");
        txt_pronunciation.setText("");
        txt_note.setText("");
        lb_image.setIcon(null);
    }//GEN-LAST:event_txt_idMouseClicked

    private void txt_englishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_englishMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_englishMouseClicked

    private void txt_vietnameseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_vietnameseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_vietnameseMouseClicked

    private void txt_pronunciationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_pronunciationMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pronunciationMouseClicked

    private void txt_noteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_noteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noteMouseClicked

    private void btn_browseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_browseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_browseMouseClicked

    private void lb_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_exitMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_lb_exitMouseClicked

    private void lb_exitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_exitMouseEntered
        // TODO add your handling code here:
        lb_exit.setBackground(new Color(153,0,153));
    }//GEN-LAST:event_lb_exitMouseEntered

    private void tab1_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1_4MouseClicked
        // TODO add your handling code here:
        jp1_4.setVisible(true);
        jp2_4.setVisible(false);
        tab1_4.setBackground(new Color(204,0,204));
        tab2_4.setBackground(new Color(153,0,153));
        jp_quesition.setVisible(true);
        jp_assiitaint.setVisible(true);
        jp_answer.setVisible(false);
        pos=0;
    }//GEN-LAST:event_tab1_4MouseClicked

    private void tab2_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2_4MouseClicked
        // TODO add your handling code here:
        jp2_4.setVisible(true);
        jp1_4.setVisible(false);
        tab2_4.setBackground(new Color(204,0,204));
        tab1_4.setBackground(new Color(153,0,153));
        ShowTab4_2(0);
        jp_quesition1.setVisible(true);
        jp_assiitaint1.setVisible(true);
        jp_answer1.setVisible(false);
        pos=0;
    }//GEN-LAST:event_tab2_4MouseClicked

    private void btn_backTab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backTab4ActionPerformed
        // TODO add your handling code here:
        pos--;
        if(pos < 0)
        pos = 0;
        ShowTab4(pos);
        jp_answer.setVisible(false);
        txt_ans.setText("");
        lb_ansIMG.setIcon(null);
        lb_ansNote.setText("");
        lb_ansAns.setText("");
    }//GEN-LAST:event_btn_backTab4ActionPerformed

    private void btn_nextTab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextTab4ActionPerformed
        // TODO add your handling code here:
        pos++;
        if(pos >= getTableList().size())
            pos = getTableList().size()-1;
        ShowTab4(pos);
        jp_answer.setVisible(false);
        txt_ans.setText("");
        lb_ansIMG.setIcon(null);
        lb_ansNote.setText("");
        lb_ansAns.setText("");
    }//GEN-LAST:event_btn_nextTab4ActionPerformed
    
    private void btn_subActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_subActionPerformed
        // TODO add your handling code here:
        if (txt_ans.getText().equalsIgnoreCase(getTableList().get(pos).getVietnamese())){
            JOptionPane.showMessageDialog(null, "Your anser is correct!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Your anser is incorrect!");
        }
        jp_answer.setVisible(true);
        ShowTaskVocabularyTab4(pos);
    }//GEN-LAST:event_btn_subActionPerformed

    private void bn_showPicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bn_showPicActionPerformed
        // TODO add your handling code here:
        jp_answer.setVisible(true);
        lb_ansE.setText(getTableList().get(pos).getEnglish());
        lb_ansPro.setText(getTableList().get(pos).getPronunciation());
        lb_ansIMG.setIcon(ResizeImageTab4E(null, getTableList().get(pos).getImage()));
    }//GEN-LAST:event_bn_showPicActionPerformed

    private void btn_showNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showNoteActionPerformed
        // TODO add your handling code here:
        jp_answer.setVisible(true);
        lb_ansE.setText(getTableList().get(pos).getEnglish());
        lb_ansPro.setText(getTableList().get(pos).getPronunciation());
        lb_ansNote.setText(getTableList().get(pos).getNote());
    }//GEN-LAST:event_btn_showNoteActionPerformed

    private void btn_showAnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showAnsActionPerformed
        // TODO add your handling code here:
        jp_answer.setVisible(true);
        lb_ansE.setText(getTableList().get(pos).getEnglish());
        lb_ansPro.setText(getTableList().get(pos).getPronunciation());
        lb_ansAns.setText(getTableList().get(pos).getVietnamese());
        lb_ansIMG.setIcon(ResizeImageTab4E(null, getTableList().get(pos).getImage()));
        lb_ansNote.setText(getTableList().get(pos).getNote());
    }//GEN-LAST:event_btn_showAnsActionPerformed

    private void txt_ansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ansActionPerformed
        // TODO add your handling code here:
        txt_ans.setText("");
    }//GEN-LAST:event_txt_ansActionPerformed

    private void btn_sub1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sub1ActionPerformed
        // TODO add your handling code here:
        if (txt_ans1.getText().equalsIgnoreCase(getTableList().get(pos).getEnglish())){
            JOptionPane.showMessageDialog(null, "Your anser is correct!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Your anser is incorrect!");
        }
        jp_answer1.setVisible(true);
        ShowTaskVocabularyTab4_2(pos);
    }//GEN-LAST:event_btn_sub1ActionPerformed

    private void txt_ans1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ans1ActionPerformed
        // TODO add your handling code here:
        txt_ans1.setText("");
    }//GEN-LAST:event_txt_ans1ActionPerformed

    private void bn_showPic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bn_showPic1ActionPerformed
        // TODO add your handling code here:
        jp_answer1.setVisible(true);
        lb_ansV.setText(getTableList().get(pos).getVietnamese());
        lb_ansIMG1.setIcon(ResizeImageTab4E(null, getTableList().get(pos).getImage()));
    }//GEN-LAST:event_bn_showPic1ActionPerformed

    private void btn_showNote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showNote1ActionPerformed
        // TODO add your handling code here:
        jp_answer1.setVisible(true);
        lb_ansV.setText(getTableList().get(pos).getVietnamese());
        //lb_ansPro1.setText(getTableList().get(pos).getPronunciation());
        lb_ansNote1.setText(getTableList().get(pos).getNote());
    }//GEN-LAST:event_btn_showNote1ActionPerformed

    private void btn_showAns1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showAns1ActionPerformed
        // TODO add your handling code here:
        jp_answer1.setVisible(true);
        lb_ansE1.setText(getTableList().get(pos).getEnglish());
        lb_ansPro1.setText(getTableList().get(pos).getPronunciation());
        lb_ansV.setText(getTableList().get(pos).getVietnamese());
        lb_ansIMG1.setIcon(ResizeImageTab4E(null, getTableList().get(pos).getImage()));
        lb_ansNote1.setText(getTableList().get(pos).getNote());
    }//GEN-LAST:event_btn_showAns1ActionPerformed

    private void btn_nextTab5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextTab5ActionPerformed
        // TODO add your handling code here:
        pos++;
        if(pos >= getTableList().size())
            pos = getTableList().size()-1;
        ShowTab4_2(pos);
        jp_answer1.setVisible(false);
        txt_ans1.setText("");
        lb_ansIMG1.setIcon(null);
        lb_ansNote1.setText("");
        lb_ansE1.setText("");
        lb_ansPro1.setText("");
    }//GEN-LAST:event_btn_nextTab5ActionPerformed

    private void btn_backTab5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backTab5ActionPerformed
        // TODO add your handling code here:
        pos--;
        if(pos < 0)
        pos = 0;
        ShowTab4_2(pos);
        jp_answer1.setVisible(false);
        txt_ans1.setText("");
        lb_ansIMG1.setIcon(null);
        lb_ansNote1.setText("");
        lb_ansE1.setText("");
    }//GEN-LAST:event_btn_backTab5ActionPerformed

    private void jpppp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpppp1MouseClicked
        // TODO add your handling code here:
        tab2.setBackground(new Color(204,0,204));
        tab1.setBackground(new Color(153,0,153));
        tab3.setBackground(new Color(153,0,153));
        tab4.setBackground(new Color(153,0,153));
        jp2.setVisible(true);
        jp1.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
    }//GEN-LAST:event_jpppp1MouseClicked

    private void jppp2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jppp2MouseClicked
        // TODO add your handling code here:
        tab3.setBackground(new Color(204,0,204));
        tab1.setBackground(new Color(153,0,153));
        tab2.setBackground(new Color(153,0,153));
        tab4.setBackground(new Color(153,0,153));
        jp3.setVisible(true);
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp4.setVisible(false);
        ShowItemInTab3(0);
    }//GEN-LAST:event_jppp2MouseClicked

    private void jppp3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jppp3MouseClicked
        // TODO add your handling code here:
        tab4.setBackground(new Color(204,0,204));
        tab1.setBackground(new Color(153,0,153));
        tab2.setBackground(new Color(153,0,153));
        tab3.setBackground(new Color(153,0,153));
        jp4.setVisible(true);
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(false);
        ShowTab4(0);
        jp2_4.setVisible(false);
    }//GEN-LAST:event_jppp3MouseClicked
                                        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tablepane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tablepane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tablepane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tablepane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tablepane().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton bn_showPic;
    private javax.swing.JButton bn_showPic1;
    private javax.swing.JPanel bottomjp;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_back3;
    private javax.swing.JButton btn_backTab4;
    private javax.swing.JButton btn_backTab5;
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_next3;
    private javax.swing.JButton btn_nextTab4;
    private javax.swing.JButton btn_nextTab5;
    private javax.swing.JButton btn_showAns;
    private javax.swing.JButton btn_showAns1;
    private javax.swing.JButton btn_showNote;
    private javax.swing.JButton btn_showNote1;
    private javax.swing.JButton btn_sub;
    private javax.swing.JButton btn_sub1;
    private javax.swing.JButton btn_update;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable_table;
    private javax.swing.JPanel jb_left4;
    private javax.swing.JPanel jp1;
    private javax.swing.JPanel jp1_4;
    private javax.swing.JPanel jp1_5;
    private javax.swing.JPanel jp2;
    private javax.swing.JPanel jp2_4;
    private javax.swing.JPanel jp3;
    private javax.swing.JPanel jp4;
    private javax.swing.JPanel jp_answer;
    private javax.swing.JPanel jp_answer1;
    private javax.swing.JPanel jp_assiitaint;
    private javax.swing.JPanel jp_assiitaint1;
    private javax.swing.JPanel jp_quesition;
    private javax.swing.JPanel jp_quesition1;
    private javax.swing.JPanel jp_right4;
    private javax.swing.JPanel jppp2;
    private javax.swing.JPanel jppp3;
    private javax.swing.JPanel jpppp1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private javax.swing.JLabel lb_ansAns;
    private javax.swing.JLabel lb_ansE;
    private javax.swing.JLabel lb_ansE1;
    private javax.swing.JLabel lb_ansIMG;
    private javax.swing.JLabel lb_ansIMG1;
    private javax.swing.JTextArea lb_ansNote;
    private javax.swing.JTextArea lb_ansNote1;
    private javax.swing.JLabel lb_ansPro;
    private javax.swing.JLabel lb_ansPro1;
    private javax.swing.JLabel lb_ansV;
    private javax.swing.JLabel lb_exit;
    private javax.swing.JLabel lb_image;
    private javax.swing.JLabel lb_image3;
    private javax.swing.JLabel lb_pro;
    private javax.swing.JLabel lb_ques;
    private javax.swing.JLabel lb_ques1;
    private javax.swing.JPanel leftjp;
    private javax.swing.JPanel middle;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab1_4;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab2_4;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JPanel topjp;
    private javax.swing.JTextField txt_ans;
    private javax.swing.JTextField txt_ans1;
    private javax.swing.JTextField txt_english;
    private javax.swing.JTextField txt_english3;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextArea txt_note;
    private javax.swing.JTextArea txt_note3;
    private javax.swing.JTextField txt_pronunciation;
    private javax.swing.JTextField txt_pronunciation3;
    private javax.swing.JTextField txt_vietnamese;
    private javax.swing.JTextField txt_vietnamese3;
    // End of variables declaration//GEN-END:variables
}

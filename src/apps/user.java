/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;
import asset.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Date;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author User
 */
public class user extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model;
    /**
     * Creates new form user
     */
    public user() {
        initComponents();
        connection=koneksi.connection();
        Tampil_Jam();
        Tampil_Tanggal();
        tNama.requestFocus();
        getDataTable();
        
    }
    
    private void getDataTable(){
        model = (DefaultTableModel) tabData.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM user, pengguna where user.id=pengguna.id";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[8];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("user");
                obj[3] = res.getString("jk");
                obj[4] = res.getString("tempatLahir");
                obj[5] = res.getString("tglLahir");
                obj[6] = res.getString("alamat");
                obj[7] = res.getString("pass");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    public void Tampil_Jam(){
        ActionListener taskPerformer = new ActionListener() {
 
        @Override
            public void actionPerformed(ActionEvent evt) {
            String nol_jam = "", nol_menit = "",nol_detik = "";
 
            java.util.Date dateTime = new java.util.Date();
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();
 
            if(nilai_jam <= 9) nol_jam= "0";
            if(nilai_menit <= 9) nol_menit= "0";
            if(nilai_detik <= 9) nol_detik= "0";
 
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);
 
            textJam.setText(jam+":"+menit+":"+detik+"");
            }
        };
    new Timer(1000, taskPerformer).start();
    }   
 
    public void Tampil_Tanggal() {
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    texTgl.setText(tanggal);
    }
    
     private void reset(){
        tUser.setText("");
        tNama.setText("");
        tAlamat.setText("");
        tPass.setText("");
        //tTanggal.setDate();
        tLahir.setText("");
        tId.setText("");
        tRegis.setEnabled(true);
        buttonGroup1.clearSelection();
    }
     
    private void insUser(){
    PreparedStatement statement = null;
    String sql = "INSERT INTO user (user, pass) VALUES(?,?);";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tUser.getText());
            statement.setString(2, tPass.getText());
            statement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
        try{
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        }
     }
    
    private void insPengguna(){
    String jenkel="";
    if(rbLaki.isSelected()){
        jenkel=rbLaki.getText();
    }else{jenkel=rbPerempuan.getText();}
   // String tangal=((JTextField)tTanggal.getDateEditor().getUiComponent()).getText();
    String dt = "yyyy-MM-dd";
    SimpleDateFormat fd = new SimpleDateFormat (dt);
    String tangal=String.valueOf(fd.format(tTanggal.getDate()));
    PreparedStatement statement = null;
    String sql = "INSERT INTO pengguna (nama, tempatLahir, tglLahir, alamat, jk) VALUES(?,?,?,?,?);";
    try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, tLahir.getText());
            statement.setString(3, tangal);
            statement.setString(4, tAlamat.getText());
            statement.setString(5, jenkel);
            statement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
        try{
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        }
    }
    
    private void updateUser(){
    PreparedStatement statement = null;
    String sql = "Update user set user=?, pass=? Where id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tUser.getText());
            statement.setString(2, tPass.getText());
            statement.setString(3, tId.getText());
            statement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
        try{
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        }
     }
    
    private void updatePengguna(){
    String jenkel="";
    if(rbLaki.isSelected()){
        jenkel=rbLaki.getText();
    }else{jenkel=rbPerempuan.getText();}
   // String tangal=((JTextField)tTanggal.getDateEditor().getUiComponent()).getText();
    String dt = "yyyy-MM-dd";
    SimpleDateFormat fd = new SimpleDateFormat (dt);
    String tangal=String.valueOf(fd.format(tTanggal.getDate()));
    PreparedStatement statement = null;
    String sql = "update pengguna set nama=?, tempatLahir=?, tglLahir=?, alamat=?, jk=? where id=?;";
    try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, tLahir.getText());
            statement.setString(3, tangal);
            statement.setString(4, tAlamat.getText());
            statement.setString(5, jenkel);
            statement.setString(6, tId.getText());
            statement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
        try{
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        }
    }
    
    private void delete(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM user WHERE id=?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, tId.getText());
            statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace(); 
       } finally{
        try {
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        }
    }
    
    private void refresh(){
        model = (DefaultTableModel) tabData.getModel();
        model.setRowCount(0);
        getDataTable();
    }
    
    private void search(){
        model = (DefaultTableModel) tabData.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from user, pengguna where user.id=pengguna.id and nama like ?"; 
                    //"SELECT * FROM user,pengguna WHERE "
                    //+"mhs.idProdi=prodi.idProdi AND mhs.idKonsen=konsentrasi.idKonsen AND nama like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCari.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("user");
                obj[3] = res.getString("jk");
                obj[4] = res.getString("tempatLahir");
                obj[5] = res.getString("tglLahir");
                obj[6] = res.getString("alamat");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        texTgl = new javax.swing.JLabel();
        textJam = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAlamat = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tLahir = new javax.swing.JTextField();
        tTanggal = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tNama = new javax.swing.JTextField();
        tUser = new javax.swing.JTextField();
        tPass = new javax.swing.JPasswordField();
        rbPerempuan = new javax.swing.JRadioButton();
        rbLaki = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        tId = new javax.swing.JTextField();
        bUpdate = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        tRegis = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabData = new javax.swing.JTable();
        bRefresh = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        mBeranda = new javax.swing.JMenuItem();
        mTransaksi = new javax.swing.JMenuItem();
        mKota = new javax.swing.JMenuItem();
        mPengguna = new javax.swing.JMenuItem();
        laporan = new javax.swing.JMenu();
        mLaporan = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pengguna/USER");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(51, 255, 255));

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Pengguna");

        texTgl.setText("Tanggal");

        textJam.setText("Jam");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("FORM");

        tAlamat.setColumns(20);
        tAlamat.setRows(5);
        jScrollPane1.setViewportView(tAlamat);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Alamat");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Tempat Lahir");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Tanggal Lahir");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Jenis Kelamin");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Username");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Nama");

        buttonGroup1.add(rbPerempuan);
        rbPerempuan.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        rbPerempuan.setText("Perempuan");

        buttonGroup1.add(rbLaki);
        rbLaki.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        rbLaki.setText("Laki-Laki");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("ID User");

        tId.setEditable(false);

        bUpdate.setText("Update");
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        tRegis.setText("Tambah Pengguna");
        tRegis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tRegisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(rbLaki)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbPerempuan))
                    .addComponent(tUser)
                    .addComponent(tNama)
                    .addComponent(tPass)
                    .addComponent(tLahir)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(tTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(71, 71, 71)
                .addComponent(tId))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tRegis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bReset, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rbLaki)
                    .addComponent(rbPerempuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(tTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tRegis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bUpdate)
                    .addComponent(bReset)
                    .addComponent(bHapus))
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Pencarian");

        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        tabData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Username", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabDataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabData);
        if (tabData.getColumnModel().getColumnCount() > 0) {
            tabData.getColumnModel().getColumn(0).setResizable(false);
            tabData.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabData.getColumnModel().getColumn(1).setResizable(false);
            tabData.getColumnModel().getColumn(1).setPreferredWidth(5);
            tabData.getColumnModel().getColumn(2).setResizable(false);
            tabData.getColumnModel().getColumn(2).setPreferredWidth(25);
            tabData.getColumnModel().getColumn(3).setResizable(false);
            tabData.getColumnModel().getColumn(3).setPreferredWidth(9);
            tabData.getColumnModel().getColumn(4).setResizable(false);
            tabData.getColumnModel().getColumn(4).setPreferredWidth(30);
            tabData.getColumnModel().getColumn(5).setResizable(false);
            tabData.getColumnModel().getColumn(5).setPreferredWidth(12);
            tabData.getColumnModel().getColumn(6).setResizable(false);
            tabData.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabData.getColumnModel().getColumn(7).setMinWidth(0);
            tabData.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });

        menu.setText("Menu");

        mBeranda.setText("Beranda");
        mBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBerandaActionPerformed(evt);
            }
        });
        menu.add(mBeranda);

        mTransaksi.setText("Transaksi");
        mTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTransaksiActionPerformed(evt);
            }
        });
        menu.add(mTransaksi);

        mKota.setText("Kota");
        mKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKotaActionPerformed(evt);
            }
        });
        menu.add(mKota);

        mPengguna.setText("Kendaraan");
        mPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPenggunaActionPerformed(evt);
            }
        });
        menu.add(mPengguna);

        jMenuBar1.add(menu);

        laporan.setText("Laporan");

        mLaporan.setText("Laporan");
        mLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLaporanActionPerformed(evt);
            }
        });
        laporan.add(mLaporan);

        jMenuBar1.add(laporan);

        jMenu9.setText("User");

        jMenuItem10.setText("Exit");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem10);

        jMenuBar1.add(jMenu9);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(texTgl)
                        .addGap(18, 18, 18)
                        .addComponent(textJam))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(26, 26, 26)
                                .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRefresh)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(texTgl)
                    .addComponent(textJam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCari)
                            .addComponent(bRefresh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
        // TODO add your handling code here:
        if(!tUser.getText().trim().isEmpty()&&!tNama.getText().trim().isEmpty()
                &&!tAlamat.getText().trim().isEmpty()&&!tLahir.getText().trim().isEmpty()
                &&!tPass.getText().trim().isEmpty()&&!tTanggal.getCalendar().toString().trim().isEmpty()){
            updateUser();
            updatePengguna();
            JOptionPane.showMessageDialog(this,"Data Sudah Diperbaharui",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            reset();
            refresh();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdateActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        if(!tCari.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabData.getModel();
            model.setRowCount(0);
            search();
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Nama Mahasiswa yang ingin di cari!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void tRegisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tRegisActionPerformed
        // TODO add your handling code here:
        if(!tUser.getText().trim().isEmpty()&&!tNama.getText().trim().isEmpty()
                &&!tAlamat.getText().trim().isEmpty()&&!tLahir.getText().trim().isEmpty()
                &&!tPass.getText().trim().isEmpty()&&!tTanggal.getCalendar().toString().trim().isEmpty()){
            insUser();
            insPengguna();
            JOptionPane.showMessageDialog(this,"Selamat Data Berhasil Ditambahkan",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"Lengkapi form terlebih dahulu!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tRegisActionPerformed

    private void tabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabDataMouseClicked
        int row=tabData.getSelectedRow();
        String uId=tabData.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM user WHERE id="+uId+"";
        //cId=connection.prepareStatement(query);
        // TODO add your handling code here:
        tId.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),0).toString());
        tNama.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),1).toString());
        tAlamat.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),6).toString());
        tUser.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),2).toString());
        tPass.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),7).toString());
        //buttonGroup1.setSelectedItem(tabData.getModel().getValueAt(tabData.));
        String jenkel = tabData.getModel().getValueAt(tabData.getSelectedRow(),3).toString();
            if(jenkel.equals("Laki-Laki")){
                rbLaki.setSelected(true);
            }else{
                rbPerempuan.setSelected(true);
            }
        tLahir.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),4).toString());
        //tTanggal.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),5).)toString());
        //DefaultTableModel tanggal = (DefaultTableModel)tabData.getModel();
        try{
            int tgl = tabData.getSelectedRow();
            Date tgl_1 = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(tgl, 5));
            tTanggal.setDate(tgl_1);
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
        }
        tId.setEditable(false);
        tRegis.setEnabled(false);
    //    tPass.setText(query);
    }//GEN-LAST:event_tabDataMouseClicked

    private void mBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBerandaActionPerformed
        // TODO add your handling code here:
            new dashboard().setVisible(true);
            this.dispose();
    }//GEN-LAST:event_mBerandaActionPerformed

    private void mTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTransaksiActionPerformed
        // TODO add your handling code here:
        new transaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mTransaksiActionPerformed

    private void mKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKotaActionPerformed
        // TODO add your handling code here:
        new kota().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mKotaActionPerformed

    private void mPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPenggunaActionPerformed
        // TODO add your handling code here:
        new kendaraan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mPenggunaActionPerformed

    private void mLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLaporanActionPerformed
        // TODO add your handling code here:
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mLaporanActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_bResetActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        if(!tId.getText().trim().isEmpty()){
        int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Pengguna ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (alert == JOptionPane.YES_OPTION){
        delete();
        refresh();
        reset();
        JOptionPane.showMessageDialog(this, "Pengguna berhassil dihapus",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

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
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new user().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu laporan;
    private javax.swing.JMenuItem mBeranda;
    private javax.swing.JMenuItem mKota;
    private javax.swing.JMenuItem mLaporan;
    private javax.swing.JMenuItem mPengguna;
    private javax.swing.JMenuItem mTransaksi;
    private javax.swing.JMenu menu;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextArea tAlamat;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tId;
    private javax.swing.JTextField tLahir;
    private javax.swing.JTextField tNama;
    private javax.swing.JPasswordField tPass;
    private javax.swing.JButton tRegis;
    private com.toedter.calendar.JDateChooser tTanggal;
    private javax.swing.JTextField tUser;
    private javax.swing.JTable tabData;
    private javax.swing.JLabel texTgl;
    private javax.swing.JLabel textJam;
    // End of variables declaration//GEN-END:variables
}

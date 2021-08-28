/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;
import asset.koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class kota extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model;   
    /**
     * Creates new form kota
     */
    public kota() {
        initComponents();
        Tampil_Jam();
        Tampil_Tanggal();
        connection=koneksi.connection();
        tKota.requestFocus();
        tableKota();
        tableTerminal();
        cmbBox();
    }
    
   //isi data combo box
    public void cmbBox(){
        try {
        String query = "SELECT * FROM kota";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbKota.addItem(res.getString("kota"));
        }
        
        res.last();
        int jumKota = res.getRow();
        res.first();
    } catch (SQLException e){}
    }
    
    private void tableKota(){
        model = (DefaultTableModel) tabKota.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM kota";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[2];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kota");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void tableTerminal(){
        model = (DefaultTableModel) tabTerminal.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM terminal";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[4];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kota");
                obj[2] = res.getString("terminal");
                //obj[3] = res.getString("harga");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void tambahKota(){
        PreparedStatement statement = null;
    String sql = "INSERT INTO kota (kota) VALUES (?);";
    try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tKota.getText());
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
    
    private void tambahTerminal(){
        PreparedStatement statement = null;
    String sql = "INSERT INTO terminal (kota,terminal) VALUES (?,?);";
    try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, cbKota.getSelectedItem().toString());
            statement.setString(2, tTerminal.getText());
            
            //statement.setString(3, tHarga.getText());
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
    
    private void deleteKota(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM kota WHERE id=?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, tIdKota.getText());
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
    
    private void deleteTerminal(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM terminal WHERE id=?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, tIdTerminal.getText());
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
    
    private void refreshKota(){
        model = (DefaultTableModel) tabKota.getModel();
        model.setRowCount(0);
        tableKota();
    }
    
    private void refreshTerminal(){
        model = (DefaultTableModel) tabTerminal.getModel();
        model.setRowCount(0);
        tableTerminal();
        tCariTerminal.setText("");
    }
    
    private void resetKota(){
        tIdKota.setText("");
        tKota.setText("");
    }
    
    private void resetTerminal(){
        tIdTerminal.setText("");
        tTerminal.setText("");
        //tHarga.setText("");
        cbKota.setSelectedItem("--- Pilih Kota ---");
    }
    
    private void updateKota(){
    PreparedStatement statement = null;
    String sql = "Update kota set kota=? Where id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tKota.getText());
            statement.setString(2, tIdKota.getText());
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
    
    private void updateTerminal(){
    PreparedStatement statement = null;
    String sql = "Update terminal set terminal=?, kota=? Where id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tTerminal.getText());
            statement.setString(2, cbKota.getSelectedItem().toString());
            //statement.setString(3, tHarga.getText());
            statement.setString(3, tIdTerminal.getText());
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
    
    private void searchKota(){
        model = (DefaultTableModel) tabKota.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from kota where kota like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCariKota.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kota");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    private void searchTerminal(){
        model = (DefaultTableModel) tabTerminal.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from terminal where terminal like ?"; 
                    //"SELECT * FROM user,pengguna WHERE "
                    //+"mhs.idProdi=prodi.idProdi AND mhs.idKonsen=konsentrasi.idKonsen AND kota like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCariTerminal.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kota");
                obj[2] = res.getString("terminal");
               // obj[3] = res.getString("harga");
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tIdKota = new javax.swing.JTextField();
        tKota = new javax.swing.JTextField();
        bTambahKota = new javax.swing.JButton();
        bUbahKota = new javax.swing.JButton();
        bHapusKota = new javax.swing.JButton();
        bResetKota = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tCariKota = new javax.swing.JTextField();
        bCariKota = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabKota = new javax.swing.JTable();
        texTgl = new javax.swing.JLabel();
        textJam = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tTerminal = new javax.swing.JTextField();
        cbKota = new javax.swing.JComboBox<>();
        tIdTerminal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bTambahTerm = new javax.swing.JButton();
        bResetTerm = new javax.swing.JButton();
        bUbahTerm = new javax.swing.JButton();
        bHapusTerm = new javax.swing.JButton();
        bRefreshTerminal = new javax.swing.JButton();
        bCariTerminal = new javax.swing.JButton();
        tCariTerminal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabTerminal = new javax.swing.JTable();
        bRefreshKota = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        mBeranda = new javax.swing.JMenuItem();
        mTransaksi = new javax.swing.JMenuItem();
        mPengguna = new javax.swing.JMenuItem();
        mKendaraan = new javax.swing.JMenuItem();
        laporan = new javax.swing.JMenu();
        mLaporan = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        mLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Daerah");

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Data Daerah Tujuan");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("FORM KOTA");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("ID");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Kota");

        tIdKota.setEditable(false);

        bTambahKota.setText("Tambah");
        bTambahKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahKotaActionPerformed(evt);
            }
        });

        bUbahKota.setText("Update");
        bUbahKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahKotaActionPerformed(evt);
            }
        });

        bHapusKota.setText("Hapus");
        bHapusKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusKotaActionPerformed(evt);
            }
        });

        bResetKota.setText("Reset");
        bResetKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetKotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bTambahKota)
                            .addComponent(bHapusKota, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bUbahKota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bResetKota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tIdKota, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tKota, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(43, 43, 43))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tIdKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambahKota)
                    .addComponent(bResetKota))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapusKota)
                    .addComponent(bUbahKota))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Pencarian");

        bCariKota.setText("Cari");
        bCariKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariKotaActionPerformed(evt);
            }
        });

        tabKota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Kota"
            }
        ));
        tabKota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabKotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabKota);

        texTgl.setText("Tanggal");

        textJam.setText("Jam");

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("FORM TERMINAL");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Kota");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Terminal");

        cbKota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih Kota ---" }));

        tIdTerminal.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("ID");

        bTambahTerm.setText("Tambah");
        bTambahTerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahTermActionPerformed(evt);
            }
        });

        bResetTerm.setText("Reset");
        bResetTerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetTermActionPerformed(evt);
            }
        });

        bUbahTerm.setText("Update");
        bUbahTerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahTermActionPerformed(evt);
            }
        });

        bHapusTerm.setText("Hapus");
        bHapusTerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusTermActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tIdTerminal)
                                    .addComponent(tTerminal)
                                    .addComponent(cbKota, 0, 137, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bHapusTerm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bTambahTerm))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bUbahTerm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bResetTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tIdTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambahTerm)
                    .addComponent(bResetTerm))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapusTerm)
                    .addComponent(bUbahTerm))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        bRefreshTerminal.setText("Refresh");
        bRefreshTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshTerminalActionPerformed(evt);
            }
        });

        bCariTerminal.setText("Cari");
        bCariTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariTerminalActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Pencarian");

        tabTerminal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Kota", "Terminal"
            }
        ));
        tabTerminal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTerminalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabTerminal);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(26, 26, 26)
                        .addComponent(tCariTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bCariTerminal)
                        .addGap(18, 18, 18)
                        .addComponent(bRefreshTerminal)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tCariTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCariTerminal)
                    .addComponent(bRefreshTerminal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        bRefreshKota.setText("Refresh");
        bRefreshKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshKotaActionPerformed(evt);
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

        mPengguna.setText("Pengguna");
        mPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPenggunaActionPerformed(evt);
            }
        });
        menu.add(mPengguna);

        mKendaraan.setText("Kendaraan");
        mKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKendaraanActionPerformed(evt);
            }
        });
        menu.add(mKendaraan);

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

        mLogout.setText("Exit");
        mLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLogoutActionPerformed(evt);
            }
        });
        jMenu9.add(mLogout);

        jMenuBar1.add(jMenu9);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(texTgl)
                .addGap(18, 18, 18)
                .addComponent(textJam)
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addComponent(tCariKota, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bCariKota)
                        .addGap(18, 18, 18)
                        .addComponent(bRefreshKota)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textJam)
                    .addComponent(texTgl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tCariKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCariKota)
                            .addComponent(bRefreshKota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCariKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariKotaActionPerformed
        // TODO add your handling code here:
        if(!tCariKota.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabKota.getModel();
            model.setRowCount(0);
            searchKota();
            tCariKota.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Kota yang ingin di cari!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariKotaActionPerformed

    private void bUbahKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahKotaActionPerformed
        // TODO add your handling code here:
         if(!tIdKota.getText().trim().isEmpty()&&!tKota.getText().trim().isEmpty()){
            updateKota();
            JOptionPane.showMessageDialog(this,"Kota Sudah Diperbaharui",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetKota();
            refreshKota();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUbahKotaActionPerformed

    private void mTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTransaksiActionPerformed
        // TODO add your handling code here:
        new transaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mTransaksiActionPerformed

    private void bUbahTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahTermActionPerformed
        // TODO add your handling code here:
        if(!tIdTerminal.getText().trim().isEmpty()){
            updateTerminal();
            JOptionPane.showMessageDialog(this,"Data Terminal Sudah Diperbaharui",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetTerminal();
            refreshTerminal();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUbahTermActionPerformed

    private void bRefreshKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshKotaActionPerformed
        // TODO add your handling code here:
        refreshKota();
        tCariKota.setText("");
    }//GEN-LAST:event_bRefreshKotaActionPerformed

    private void bRefreshTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshTerminalActionPerformed
        // TODO add your handling code here:
        refreshTerminal();
    }//GEN-LAST:event_bRefreshTerminalActionPerformed

    private void bCariTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariTerminalActionPerformed
        // TODO add your handling code here:
        if(!tCariTerminal.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabTerminal.getModel();
            model.setRowCount(0);
            searchTerminal();
            tCariTerminal.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Terminal yang ingin di cari!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariTerminalActionPerformed

    private void bTambahKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahKotaActionPerformed
        // TODO add your handling code here:
        if(!tKota.getText().trim().isEmpty()){
            tambahKota();
            resetKota();
            refreshKota();
            JOptionPane.showMessageDialog(this,"Selamat Kota Berhasil Ditambahkan",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"Nama Kota Belum diisi!!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bTambahKotaActionPerformed

    private void bHapusKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusKotaActionPerformed
        // TODO add your handling code here:
        if(!tIdKota.getText().trim().isEmpty()){
        int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Kota ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (alert == JOptionPane.YES_OPTION){
        deleteKota();
        refreshKota();
        resetKota();
        JOptionPane.showMessageDialog(this, "Kota berhassil dihapus",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih Kota terlebih dahulu!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusKotaActionPerformed

    private void bHapusTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusTermActionPerformed
        // TODO add your handling code here:
        if(!tIdTerminal.getText().trim().isEmpty()){
        int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Terminal ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (alert == JOptionPane.YES_OPTION){
        deleteTerminal();
        refreshTerminal();
        resetTerminal();
        JOptionPane.showMessageDialog(this, "Terminal berhassil dihapus",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih Terminal terlebih dahulu!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusTermActionPerformed

    private void bTambahTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahTermActionPerformed
        // TODO add your handling code here:
        String cmb = "--- Pilih Kota ---";
        if(cbKota.getSelectedItem()==cmb){
            JOptionPane.showMessageDialog(this,"Pilih Kota Terlebih Dahulu","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else
        if(//!tIdTerminal.getText().trim().isEmpty()
                //&&!cbKota.getText().trim().isEmpty()
                !tTerminal.getText().trim().isEmpty()){
            tambahTerminal();
            resetTerminal();
            refreshTerminal();
            JOptionPane.showMessageDialog(this,"Selamat Terminal Tujuan Bertambah",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"GAGAL MENAMBAHKAN!! Harap lengkapi form",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bTambahTermActionPerformed

    private void bResetKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetKotaActionPerformed
        // TODO add your handling code here:
        resetKota();
    }//GEN-LAST:event_bResetKotaActionPerformed

    private void bResetTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetTermActionPerformed
        // TODO add your handling code here:
        resetTerminal();
    }//GEN-LAST:event_bResetTermActionPerformed

    private void tabKotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKotaMouseClicked
        // TODO add your handling code here:
        int row=tabKota.getSelectedRow();
        String uId=tabKota.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM kota WHERE id="+uId+"";
        tIdKota.setText(tabKota.getModel().getValueAt(tabKota.getSelectedRow(),0).toString());
        tKota.setText(tabKota.getModel().getValueAt(tabKota.getSelectedRow(),1).toString());
    }//GEN-LAST:event_tabKotaMouseClicked

    private void mBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBerandaActionPerformed
        // TODO add your handling code here:
        new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mBerandaActionPerformed

    private void mPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPenggunaActionPerformed
        // TODO add your handling code here:
        new user().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mPenggunaActionPerformed

    private void mKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKendaraanActionPerformed
        // TODO add your handling code here:
        new kendaraan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mKendaraanActionPerformed

    private void tabTerminalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTerminalMouseClicked
        // TODO add your handling code here:
        int row=tabTerminal.getSelectedRow();
        String uId=tabTerminal.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM terrminal WHERE id="+uId+"";
        String cbkt = "Select kota from terminal where id="+uId+"";
        tIdTerminal.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),0).toString());
        tTerminal.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),2).toString());
        //tHarga.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),3).toString());
        cbKota.setSelectedItem(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),1).toString());
//        String cmbox = model.getValueAt(tabTerminal.getSelectedRow(),1).toString();
//            switch(cmbox){
//                case "+"cbkt"+";
//                cbKota.setSelectedItem(cbkt);
//                break;
//            }
    }//GEN-LAST:event_tabTerminalMouseClicked

    private void mLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLaporanActionPerformed
        // TODO add your handling code here:
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mLaporanActionPerformed

    private void mLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLogoutActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(kota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCariKota;
    private javax.swing.JButton bCariTerminal;
    private javax.swing.JButton bHapusKota;
    private javax.swing.JButton bHapusTerm;
    private javax.swing.JButton bRefreshKota;
    private javax.swing.JButton bRefreshTerminal;
    private javax.swing.JButton bResetKota;
    private javax.swing.JButton bResetTerm;
    private javax.swing.JButton bTambahKota;
    private javax.swing.JButton bTambahTerm;
    private javax.swing.JButton bUbahKota;
    private javax.swing.JButton bUbahTerm;
    private javax.swing.JComboBox<String> cbKota;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu laporan;
    private javax.swing.JMenuItem mBeranda;
    private javax.swing.JMenuItem mKendaraan;
    private javax.swing.JMenuItem mLaporan;
    private javax.swing.JMenuItem mLogout;
    private javax.swing.JMenuItem mPengguna;
    private javax.swing.JMenuItem mTransaksi;
    private javax.swing.JMenu menu;
    private javax.swing.JTextField tCariKota;
    private javax.swing.JTextField tCariTerminal;
    private javax.swing.JTextField tIdKota;
    private javax.swing.JTextField tIdTerminal;
    private javax.swing.JTextField tKota;
    private javax.swing.JTextField tTerminal;
    private javax.swing.JTable tabKota;
    private javax.swing.JTable tabTerminal;
    private javax.swing.JLabel texTgl;
    private javax.swing.JLabel textJam;
    // End of variables declaration//GEN-END:variables
}

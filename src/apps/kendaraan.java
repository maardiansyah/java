/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;
import asset.koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class kendaraan extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model; 
    /**
     * Creates new form kendaraan
     */
    
    public kendaraan() {
        initComponents();
        Tampil_Jam();
        Tampil_Tanggal();
        connection=koneksi.connection();
        tableData();
        cmbko();
        cmbter();
        cmbBr();
        cmbKls();
        
    }
    
    private void tableData(){
        model = (DefaultTableModel) tabKen.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM kendaraan";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[9];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("brand");
                obj[6] = res.getString("fasilitas");
                obj[5] = res.getString("kota");
                obj[4] = res.getString("terminal");
                obj[3] = res.getString("noperasi");
                obj[7] = res.getString("status");
                obj[8] = res.getString("tarif");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    public void cmbKls(){
        cbFasilitas.removeAllItems();
        String kot = cbBrand.getSelectedItem().toString();
        try {
        String query = "SELECT * FROM kelas where kBrand = '"+kot+"'";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbFasilitas.addItem(res.getString("kelas"));    
        }
        //res.last();
       // int jumKota = res.getRow();
       // res.first();
       //st.close();
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    //isi data combobox Brand
    public void cmbBr(){
        //cbBrand.removeAllItems();
        try {
        String query = "SELECT *FROM brand";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbBrand.addItem(res.getString("brand"));
        }
        
        //cmbter();
        //st.close();
    } catch (SQLException e){
        e.printStackTrace();}
    };
    
    //isi data combo box jurusan
    public void cmbko(){
        //cbKota.removeAllItems();
        try {
        String query = "SELECT kota FROM kota";
        Statement st = (Statement) connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbKota.addItem(res.getString("kota"));
        }
        
        //cmbter();
       // st.close();
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    //isi data combo box terminal
    public void cmbter(){
        cbTerminal.removeAllItems();
        String kot = cbKota.getSelectedItem().toString();
        try {
        String query = "SELECT * FROM terminal where kota = '"+kot+"'";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbTerminal.addItem(res.getString("terminal"));    
        }
        //res.last();
       // int jumKota = res.getRow();
       // res.first();
      // st.close();
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    private void tambahKendaraan(){
        PreparedStatement statement = null;
        String sql="INSERT INTO `kendaraan`(nama,brand,noperasi,terminal,kota,fasilitas,status,tarif) "
                + "VALUES (?,?,?,?,?,?,?,?);";
        try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, cbBrand.getSelectedItem().toString());
            statement.setString(3, cbFasilitas.getSelectedItem().toString());
            statement.setString(4, cbKota.getSelectedItem().toString());
            statement.setString(5, cbTerminal.getSelectedItem().toString());
            statement.setString(6, tOperasi.getText());
            statement.setString(7, tStatus.getText());
            statement.setString(8, tTarif.getText());
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
    
    private void resetKendaraan(){
        cbBrand.setSelectedItem("--- Pilih Brand ---");
        cbKota.setSelectedItem("--- Pilih Kota ---");
        tStatus.setText("");
        tNama.setText("");
        tOperasi.setText("");
        tTarif.setText("");
    }
    
    private void refreshKendaraan(){
        model = (DefaultTableModel) tabKen.getModel();
        model.setRowCount(0);
        tableData();
        tCari.setText("");
    }
    
    
    private void deleteKendaraan(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM kendaraan WHERE id=?";
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
    
    private void updateKendaraan(){
    PreparedStatement statement = null;
    String sql = "UPDATE `kendaraan` SET `nama`=?,`brand`=?,`noperasi`=?,`terminal`=?,`kota`=?,`fasilitas`=?,`status`=?,tarif=? "
            + "WHERE id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, cbBrand.getSelectedItem().toString());
            statement.setString(3, cbFasilitas.getSelectedItem().toString());
            statement.setString(4, cbKota.getSelectedItem().toString());
            statement.setString(5, cbTerminal.getSelectedItem().toString());
            statement.setString(6, tOperasi.getText());
            statement.setString(7, tStatus.getText());
            statement.setString(8, tTarif.getText());
            statement.setString(9, tId.getText());
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
    
    private void searchKendaraan(){
        model = (DefaultTableModel) tabKen.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from kendaraan where nama like ? or brand like ? or tarif like ? "
                    + "or fasilitas like ? or kota like ? or terminal like ? or noperasi like ? or status like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCari.getText()+"%");
            statement.setString(2,"%"+ tCari.getText()+"%");
            statement.setString(3,"%"+ tCari.getText()+"%");
            statement.setString(4,"%"+ tCari.getText()+"%");
            statement.setString(5,"%"+ tCari.getText()+"%");
            statement.setString(6,"%"+ tCari.getText()+"%");
            statement.setString(7,"%"+ tCari.getText()+"%");
            statement.setString(8,"%"+ tCari.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[9];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("brand");
                obj[3] = res.getString("fasilitas");
                obj[4] = res.getString("kota");
                obj[5] = res.getString("terminal");
                obj[6] = res.getString("noperasi");
                obj[7] = res.getString("status");
                obj[8] = res.getString("tarif");
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
        texTgl = new javax.swing.JLabel();
        textJam = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tId = new javax.swing.JTextField();
        tNama = new javax.swing.JTextField();
        bTambah = new javax.swing.JButton();
        bUpdate = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tOperasi = new javax.swing.JTextField();
        cbBrand = new javax.swing.JComboBox<>();
        cbFasilitas = new javax.swing.JComboBox<>();
        cbKota = new javax.swing.JComboBox<>();
        cbTerminal = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        tStatus = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tTarif = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabKen = new javax.swing.JTable();
        bRefresh = new javax.swing.JButton();
        bk = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        mBeranda = new javax.swing.JMenuItem();
        mTransaksi = new javax.swing.JMenuItem();
        mKota = new javax.swing.JMenuItem();
        mPengguna = new javax.swing.JMenuItem();
        laporan = new javax.swing.JMenu();
        mLaporan = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        Close = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kelola Kendaraan");
        setLocationByPlatform(true);

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Kendaraan");

        texTgl.setText("Tanggal");

        textJam.setText("Jam");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("FORM");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("ID Kendaraan");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Nama Kendaraan");

        tId.setEditable(false);

        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bUpdate.setText("Update");
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setText("Brand");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Kelas Fasilitas");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Jurusan");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Terminal");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("No Operasi");

        cbBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Brand" }));
        cbBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBrandActionPerformed(evt);
            }
        });

        cbFasilitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Fasilitas" }));

        cbKota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kota" }));
        cbKota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbKotaMouseClicked(evt);
            }
        });
        cbKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKotaActionPerformed(evt);
            }
        });

        cbTerminal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Terminal" }));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel11.setText("Status");

        jLabel12.setText("Tarif ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bTambah))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bReset, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tId)
                            .addComponent(tNama)
                            .addComponent(cbBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbFasilitas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbKota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTerminal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tOperasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tTarif)
                                .addComponent(tStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFasilitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambah)
                    .addComponent(bReset))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapus)
                    .addComponent(bUpdate))
                .addGap(27, 27, 27))
        );

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Pencarian");

        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        tabKen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Brand", "Fasilitas", "Kota", "Terminal", "No Operasi", "Status", "Tarif"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabKen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabKenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabKen);
        if (tabKen.getColumnModel().getColumnCount() > 0) {
            tabKen.getColumnModel().getColumn(0).setResizable(false);
            tabKen.getColumnModel().getColumn(0).setPreferredWidth(25);
            tabKen.getColumnModel().getColumn(1).setResizable(false);
            tabKen.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabKen.getColumnModel().getColumn(2).setResizable(false);
            tabKen.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabKen.getColumnModel().getColumn(3).setResizable(false);
            tabKen.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabKen.getColumnModel().getColumn(4).setResizable(false);
            tabKen.getColumnModel().getColumn(4).setPreferredWidth(125);
            tabKen.getColumnModel().getColumn(5).setResizable(false);
            tabKen.getColumnModel().getColumn(5).setPreferredWidth(175);
            tabKen.getColumnModel().getColumn(6).setResizable(false);
            tabKen.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabKen.getColumnModel().getColumn(7).setResizable(false);
            tabKen.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabKen.getColumnModel().getColumn(8).setResizable(false);
            tabKen.getColumnModel().getColumn(8).setPreferredWidth(125);
        }

        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });

        bk.setBackground(new java.awt.Color(51, 51, 255));
        bk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bk.setForeground(new java.awt.Color(255, 255, 255));
        bk.setText("BRAND DAN KELAS");
        bk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkActionPerformed(evt);
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

        mPengguna.setText("Pengguna");
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

        Close.setText("Close");
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });
        jMenu9.add(Close);

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
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bRefresh)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(texTgl)
                        .addComponent(textJam)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCari)
                            .addComponent(bRefresh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bk, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        if(!tCari.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabKen.getModel();
            model.setRowCount(0);
            searchKendaraan();
            tCari.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Terminal yang ingin di cari!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        // TODO add your handling code here:
       refreshKendaraan();
       tId.setText("");
    }//GEN-LAST:event_bRefreshActionPerformed

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
        new user().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mPenggunaActionPerformed

    private void bkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkActionPerformed
        // TODO add your handling code here:
        new kelas().setVisible(true);
    }//GEN-LAST:event_bkActionPerformed

    private void cbKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKotaActionPerformed
        // TODO add your handling code here:
        cmbter();
    }//GEN-LAST:event_cbKotaActionPerformed

    private void cbKotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbKotaMouseClicked
        // TODO add your handling code here:
        //cmbter();
    }//GEN-LAST:event_cbKotaMouseClicked

    private void cbBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBrandActionPerformed
        // TODO add your handling code here:
        cmbKls();
    }//GEN-LAST:event_cbBrandActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        resetKendaraan();
        tId.setText("");
    }//GEN-LAST:event_bResetActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        if(!tNama.getText().trim().isEmpty()){
            int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Data ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (alert == JOptionPane.YES_OPTION){
                deleteKendaraan();
                refreshKendaraan();
                resetKendaraan();
                JOptionPane.showMessageDialog(this, "Data berhassil dihapus",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih Data terlebih dahulu!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
        tId.setText("");
    }//GEN-LAST:event_bHapusActionPerformed

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
        // TODO add your handling code here:
        if(!tId.getText().trim().isEmpty()&&!tNama.getText().trim().isEmpty()
            &&!tStatus.getText().trim().isEmpty()&&!tOperasi.getText().trim().isEmpty()){
            updateKendaraan();
            JOptionPane.showMessageDialog(this,"Data Terminal Sudah Diperbaharui",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetKendaraan();
            refreshKendaraan();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
        tId.setText("");
    }//GEN-LAST:event_bUpdateActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        String cmb = "Pilih Brand", kta = "Pilih Kota";
        String fsl = null, trm = null;
        if(cbBrand.getSelectedItem()==cmb||cbKota.getSelectedItem()==kta){
            JOptionPane.showMessageDialog(this,"Data Bus/Tujuan Belum Ditetapkan","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else if(cbFasilitas.getSelectedItem()==fsl||cbTerminal.getSelectedItem()==trm){
            JOptionPane.showMessageDialog(this,"Data Bus/Tujuan Belum Tersedia!","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else
        if(!tNama.getText().trim().isEmpty()&&!tOperasi.getText().trim().isEmpty()&&!tStatus.getText().trim().isEmpty()){
            tambahKendaraan();
            resetKendaraan();
            refreshKendaraan();
            tId.setText("");
            JOptionPane.showMessageDialog(this,"Selamat kelas Tujuan Bertambah",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"GAGAL MENAMBAHKAN!! Harap lengkapi form",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bTambahActionPerformed

    private void tabKenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKenMouseClicked
        // TODO add your handling code here:
        int row=tabKen.getSelectedRow();
        String uId=tabKen.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
        tId.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),0).toString());
        tNama.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),1).toString());
        cbBrand.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),2).toString());
        cbFasilitas.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),3).toString());
        cbKota.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),4).toString());
        cbTerminal.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),5).toString());
        tOperasi.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),6).toString());
        tStatus.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),7).toString());
        tTarif.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),8).toString());
        
    }//GEN-LAST:event_tabKenMouseClicked

    private void mLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLaporanActionPerformed
        // TODO add your handling code here:
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mLaporanActionPerformed

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CloseActionPerformed

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
            java.util.logging.Logger.getLogger(kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kendaraan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Close;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUpdate;
    private javax.swing.JButton bk;
    private javax.swing.JComboBox<String> cbBrand;
    private javax.swing.JComboBox<String> cbFasilitas;
    private javax.swing.JComboBox<String> cbKota;
    private javax.swing.JComboBox<String> cbTerminal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu laporan;
    private javax.swing.JMenuItem mBeranda;
    private javax.swing.JMenuItem mKota;
    private javax.swing.JMenuItem mLaporan;
    private javax.swing.JMenuItem mPengguna;
    private javax.swing.JMenuItem mTransaksi;
    private javax.swing.JMenu menu;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tId;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tOperasi;
    private javax.swing.JTextField tStatus;
    private javax.swing.JTextField tTarif;
    private javax.swing.JTable tabKen;
    private javax.swing.JLabel texTgl;
    private javax.swing.JLabel textJam;
    // End of variables declaration//GEN-END:variables
}

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */
public class kelas extends javax.swing.JFrame {
    DefaultTableModel model;
    Connection connection = koneksi.connection();
    /**
     * Creates new form kelas
     */
    public kelas() {
        initComponents();
        Tampil_Jam();
        Tampil_Tanggal();
        connection=koneksi.connection();
        tableBrand();
        tableKelas();
        cmbBox();
    }
    
    public void cmbBox(){
        try {
        String query = "SELECT * FROM brand";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbBrand.addItem(res.getString("brand"));
        }
        
        res.last();
        int jumBrand = res.getRow();
        res.first();
    } catch(SQLException err){
            err.printStackTrace();}
    }
    
    private void tableBrand(){
        model = (DefaultTableModel) tabBrand.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM brand";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[2];
                obj[0] = res.getString("id");
                obj[1] = res.getString("brand");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void tableKelas(){
        model = (DefaultTableModel) tabKelas.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM kelas";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[4];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kBrand");
                obj[2] = res.getString("kelas");
                obj[3] = res.getString("fasilitas");
//                obj[4] = res.getString("kHarga");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void tambahBrand(){
        PreparedStatement statement = null;
        String sql="Insert Into brand (brand) values (?);";
        
        try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, brand.getText());
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
    
    private void tambahKelas(){
        PreparedStatement statement = null;
        String sql="Insert Into kelas (kBrand, kelas, fasilitas) values (?,?,?)";
        
        try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, cbBrand.getSelectedItem().toString());
            statement.setString(2, tKelas.getText());
            statement.setString(3, tFasilitas.getText());
            //statement.setString(4, tHarga.getText());
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
    
    private void resetBrand(){
        tIdBrand.setText("");
        brand.setText("");
    }
    
    private void resetKelas(){
        cbBrand.setSelectedItem("--- Pilih Brand ---");
        tKelas.setText("");
        tFasilitas.setText("");
//        tHarga.setText("");
        idText.setText("0");
    }
    
    private void refreshBrand(){
        model = (DefaultTableModel) tabBrand.getModel();
        model.setRowCount(0);
        tableBrand();
        tCariBrand.setText("");
    }
    
    private void refreshKelas(){
        model = (DefaultTableModel) tabKelas.getModel();
        model.setRowCount(0);
        tableKelas();
        tCariKelas.setText("");
    }
    
    private void deleteBrand(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM brand WHERE id=?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, tIdBrand.getText());
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
    
    private void deleteKelas(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM kelas WHERE id=?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, idText.getText());
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
    
    private void updateBrand(){
    PreparedStatement statement = null;
    String sql = "UPDATE `brand` SET `brand`=? WHERE id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(2, tIdBrand.getText());
            statement.setString(1, brand.getText());
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
    
     private void updateKelas(){
    PreparedStatement statement = null;
    String sql = "Update kelas set kBrand=?,kelas=?,fasilitas=? Where id=? ;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(4, idText.getText());
            statement.setString(1, cbBrand.getSelectedItem().toString());
            statement.setString(2, tKelas.getText());
            statement.setString(3, tFasilitas.getText());
            //statement.setString(4, tHarga.getText());
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
     
     private void searchBrand(){
        model = (DefaultTableModel) tabBrand.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from brand where brand like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCariBrand.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("brand");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
     private void searchKelas(){
        model = (DefaultTableModel) tabKelas.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from kelas where kelas like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCariKelas.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kBrand");
                obj[2] = res.getString("kelas");
                obj[3] = res.getString("fasilitas");
                obj[4] = res.getString("kHarga");
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        texTgl = new javax.swing.JLabel();
        textJam = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        brand = new javax.swing.JTextField();
        bTambahKota = new javax.swing.JButton();
        bUbahKota = new javax.swing.JButton();
        bHapusKota = new javax.swing.JButton();
        bResetKota = new javax.swing.JButton();
        tIdBrand = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabBrand = new javax.swing.JTable();
        tCariBrand = new javax.swing.JTextField();
        bCariKota = new javax.swing.JButton();
        bRefreshKota = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tKelas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bTambahTerm = new javax.swing.JButton();
        bResetTerm = new javax.swing.JButton();
        bUbahTerm = new javax.swing.JButton();
        bHapusTerm = new javax.swing.JButton();
        cbBrand = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tFasilitas = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        idText = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabKelas = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        tCariKelas = new javax.swing.JTextField();
        bCariTerminal = new javax.swing.JButton();
        bRefreshTerminal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kelas dan Fasilitas");

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Data Brand dan Fasilitas");

        texTgl.setText("Tanggal");

        textJam.setText("Jam");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("FORM BRAND");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("ID");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Brand");

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

        tIdBrand.setEditable(false);

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
                        .addContainerGap(92, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(brand, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(tIdBrand))
                        .addGap(43, 43, 43))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tIdBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tabBrand.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Kota"
            }
        ));
        tabBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabBrandMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabBrand);

        bCariKota.setText("Cari");
        bCariKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariKotaActionPerformed(evt);
            }
        });

        bRefreshKota.setText("Refresh");
        bRefreshKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshKotaActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("FORM FASILITAS");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Kelas");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Brand");

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

        cbBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih Brand ---" }));

        jLabel7.setText("Fasilitas");

        tFasilitas.setColumns(20);
        tFasilitas.setRows(5);
        jScrollPane3.setViewportView(tFasilitas);

        jLabel12.setText("ID  :");

        idText.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bHapusTerm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bTambahTerm))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bUbahTerm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bResetTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(143, 143, 143))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addComponent(tKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(idText))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambahTerm)
                    .addComponent(bResetTerm))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapusTerm)
                    .addComponent(bUbahTerm))
                .addContainerGap())
        );

        tabKelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Kelas", "Fasilitas"
            }
        ));
        tabKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabKelasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabKelas);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Pencarian");

        bCariTerminal.setText("Cari");
        bCariTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariTerminalActionPerformed(evt);
            }
        });

        bRefreshTerminal.setText("Refresh");
        bRefreshTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshTerminalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(texTgl)
                .addGap(18, 18, 18)
                .addComponent(textJam)
                .addGap(47, 47, 47))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 47, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(tCariBrand)
                                .addGap(18, 18, 18)
                                .addComponent(bCariKota)
                                .addGap(18, 18, 18)
                                .addComponent(bRefreshKota))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tCariKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bCariTerminal)
                                .addGap(18, 18, 18)
                                .addComponent(bRefreshTerminal))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textJam)
                    .addComponent(texTgl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tCariBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCariKota)
                            .addComponent(bRefreshKota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bRefreshTerminal)
                            .addComponent(bCariTerminal)
                            .addComponent(tCariKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(0, 42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bTambahKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahKotaActionPerformed
        // TODO add your handling code here:
        if(!brand.getText().trim().isEmpty()){
            tambahBrand();
            resetBrand();
            refreshBrand();
            JOptionPane.showMessageDialog(this,"Selamat Brand Berhasil Ditambahkan",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"Masuikan nama Brand!!",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bTambahKotaActionPerformed

    private void bUbahKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahKotaActionPerformed
        // TODO add your handling code here:
        if(!brand.getText().trim().isEmpty()&&!tIdBrand.getText().trim().isEmpty()){
            updateBrand();
            JOptionPane.showMessageDialog(this,"Brand Sudah Diperbaharui",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetBrand();
            refreshBrand();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUbahKotaActionPerformed

    private void bHapusKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusKotaActionPerformed
        // TODO add your handling code here:
        if(!brand.getText().trim().isEmpty()){
            int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Kota ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (alert == JOptionPane.YES_OPTION){
                deleteBrand();
                refreshBrand();
                resetBrand();
                JOptionPane.showMessageDialog(this, "Brand berhassil dihapus",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih Brand terlebih dahulu!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusKotaActionPerformed

    private void bResetKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetKotaActionPerformed
        // TODO add your handling code here:
        resetBrand();
    }//GEN-LAST:event_bResetKotaActionPerformed

    private void tabBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabBrandMouseClicked
        // TODO add your handling code here:
        int row=tabBrand.getSelectedRow();
        String uId=tabBrand.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM kota WHERE id="+uId+"";
        brand.setText(tabBrand.getModel().getValueAt(tabBrand.getSelectedRow(),1).toString());
        tIdBrand.setText(tabBrand.getModel().getValueAt(tabBrand.getSelectedRow(),0).toString());
    }//GEN-LAST:event_tabBrandMouseClicked

    private void bCariKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariKotaActionPerformed
        // TODO add your handling code here:
        if(!tCariBrand.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabBrand.getModel();
            model.setRowCount(0);
            searchBrand();
            tCariBrand.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Brand yang ingin di cari!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariKotaActionPerformed

    private void bRefreshKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshKotaActionPerformed
        // TODO add your handling code here:
        refreshBrand();
        tCariBrand.setText("");
    }//GEN-LAST:event_bRefreshKotaActionPerformed

    private void bTambahTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahTermActionPerformed
        // TODO add your handling code here:
        String cmb = "--- Pilih Brand ---";
        if(cbBrand.getSelectedItem()==cmb){
            JOptionPane.showMessageDialog(this,"Pilih Brand Terlebih Dahulu","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else
        if(//!tIdTerminal.getText().trim().isEmpty()
            //&&!cbKota.getText().trim().isEmpty()
             !tKelas.getText().trim().isEmpty()){
            tambahKelas();
            resetKelas();
            refreshKelas();
            JOptionPane.showMessageDialog(this,"Selamat kelas Tujuan Bertambah",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"GAGAL MENAMBAHKAN!! Harap lengkapi form",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bTambahTermActionPerformed

    private void bResetTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetTermActionPerformed
        // TODO add your handling code here:
        resetKelas();
    }//GEN-LAST:event_bResetTermActionPerformed

    private void bUbahTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahTermActionPerformed
        // TODO add your handling code here:
        String cmb = "--- Pilih Brand ---";
        if(cbBrand.getSelectedItem()==cmb){
            JOptionPane.showMessageDialog(this,"Pilih Brand Terlebih Dahulu","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else
        if(!tKelas.getText().trim().isEmpty()){
            updateKelas();
            JOptionPane.showMessageDialog(this,"Data Kelas Sudah Diperbaharui",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetKelas();
            refreshKelas();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUbahTermActionPerformed

    private void bHapusTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusTermActionPerformed
        // TODO add your handling code here:
        if(!tKelas.getText().trim().isEmpty()){
            int alert = JOptionPane.showConfirmDialog(this,"Anda Yakin ingin menghapus Kelas ini?",
                "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (alert == JOptionPane.YES_OPTION){
                deleteKelas();
                refreshKelas();
                resetKelas();
                JOptionPane.showMessageDialog(this, "Kelass berhassil dihapus",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih Kelas terlebih dahulu!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusTermActionPerformed

    private void tabKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKelasMouseClicked
        // TODO add your handling code here:
        int row=tabKelas.getSelectedRow();
        String uId=tabKelas.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
        idText.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),0).toString());
        tKelas.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),2).toString());
        tFasilitas.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),3).toString());
        //tHarga.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),4).toString());
        cbBrand.setSelectedItem(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),1).toString());
        //        String cmbox = model.getValueAt(tabTerminal.getSelectedRow(),1).toString();
        //            switch(cmbox){
            //                case "+"cbkt"+";
            //                cbKota.setSelectedItem(cbkt);
            //                break;
            //            }
    }//GEN-LAST:event_tabKelasMouseClicked

    private void bCariTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariTerminalActionPerformed
        // TODO add your handling code here:
        if(!tCariKelas.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabKelas.getModel();
            model.setRowCount(0);
            searchKelas();
            tCariKelas.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Nama Kelas yang ingin di cari!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariTerminalActionPerformed

    private void bRefreshTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshTerminalActionPerformed
        // TODO add your handling code here:
        refreshKelas();
    }//GEN-LAST:event_bRefreshTerminalActionPerformed

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
            java.util.logging.Logger.getLogger(kelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kelas().setVisible(true);
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
    private javax.swing.JTextField brand;
    private javax.swing.JComboBox<String> cbBrand;
    private javax.swing.JLabel idText;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tCariBrand;
    private javax.swing.JTextField tCariKelas;
    private javax.swing.JTextArea tFasilitas;
    private javax.swing.JTextField tIdBrand;
    private javax.swing.JTextField tKelas;
    private javax.swing.JTable tabBrand;
    private javax.swing.JTable tabKelas;
    private javax.swing.JLabel texTgl;
    private javax.swing.JLabel textJam;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;
import asset.koneksi;
import apps.kendaraan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.lang.NumberFormatException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class transaksi extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model; 
    /**
     * Creates new form transaksi
     */
    public transaksi() {
        initComponents();
        connection=koneksi.connection();
        cmbKls();
        cmbter();
        cmbko();
        cmbBr();
        tableData();
        tableData1();
        Tampil_Jam();
        Tampil_Tanggal();
        cbKota.setEnabled(false);
        cbFasilitas.setEnabled(false);
        cbTerminal.setEnabled(false);
        cbBrand.setEnabled(false);
        tPesan.requestFocus();
        tTarif.setEnabled(false);
        tTotal.setEnabled(false);
        bTambah.setEnabled(false);
        bPrint.setEnabled(false);
    }
    
    private void tambahKendaraan(){
        PreparedStatement statement = null;
        String dt = "yyyy-MM-dd";
        SimpleDateFormat fd = new SimpleDateFormat (dt);
        String tgl = String.valueOf(fd.format(tTanggal.getDate()));
        String sql="INSERT INTO `booking`(nama,brand,noperasi,terminal,kota,fasilitas,jumlah,tarif,tanggal,nmPesan,total) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        try{ 
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, cbBrand.getSelectedItem().toString());
            statement.setString(3, tOperasi.getText());
            statement.setString(4, cbTerminal.getSelectedItem().toString());
            statement.setString(5, cbKota.getSelectedItem().toString());
            statement.setString(6, cbFasilitas.getSelectedItem().toString());
            statement.setString(7, tJum.getText());
            statement.setString(8, tTarif.getText());
            statement.setString(9, tgl);
            statement.setString(10, tPesan.getText());
            statement.setString(11,tTotal.getText());
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
    
    private void tableData(){
        model = (DefaultTableModel) tabPesan.getModel();
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
    
    private void tableData1(){
        model = (DefaultTableModel) tabPesan1.getModel();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM booking";
            ResultSet res = stat.executeQuery(sql);
            while(res.next ()){
                Object[ ] obj = new Object[12];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nmPesan");
                obj[2] = res.getString("tanggal");
                obj[3] = res.getString("nama");
                obj[4] = res.getString("brand");
                obj[5] = res.getString("fasilitas");
                obj[6] = res.getString("kota");
                obj[7] = res.getString("terminal");
                obj[8] = res.getString("noperasi");
                obj[9] = res.getString("tarif");
                obj[10] = res.getString("jumlah");
                obj[11] = res.getString("total");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    //isi data combobox Brand
    public void cmbBr(){
        cbBrand.removeAllItems();
        try {
        String query = "SELECT *FROM brand";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbBrand.addItem(res.getString("brand"));
        }
    } catch (SQLException e){
        e.printStackTrace();}
    };
    
    public void cmbKls(){
        cbFasilitas.removeAllItems();
        //String kot = cbBrand.getSelectedItem().toString();
        try {
        String query = "SELECT * FROM kelas ";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbFasilitas.addItem(res.getString("kelas"));    
        }
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    //isi data combo box jurusan
    public void cmbko(){
        //cbKota.removeAllItems();
        cbKota.removeAllItems();
        //String kot = cbFasilitas.getSelectedItem().toString();
        try {
        String query = "SELECT * FROM kota";
        Statement st = (Statement) connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbKota.addItem(res.getString("kota"));
        }
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    //isi data combo box terminal
    public void cmbter(){
        cbTerminal.removeAllItems();
        //String kot = cbKota.getSelectedItem().toString();
        try {
        String query = "SELECT * FROM terminal";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbTerminal.addItem(res.getString("terminal"));    
        }
    } catch (SQLException e){
        e.printStackTrace();}
    }
    
    private void resetKendaraan(){
        cbBrand.setSelectedItem("Pilih Brand");
        cbKota.setSelectedItem("Pilih Kota");
        cbTerminal.setSelectedItem("Pilih Terminal");
        cbFasilitas.setSelectedItem("Pilih Fasilitas");
        tJum.setText("");
        tNama.setText("");
        tOperasi.setText("");
        tTarif.setText("0");
        tPesan.setText("");
        tTotal.setText("");
        idText.setText("Pesanan Baru");
        tTanggal.cleanup();
    }
    
    private void searchKendaraan(){
        model = (DefaultTableModel) tabPesan.getModel();
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
    
    private void searchKendaraan1(){
        model = (DefaultTableModel) tabPesan1.getModel();
        PreparedStatement statement = null;
        try{
            String sql = "select*from booking where nmPesan like ? or tanggal like ? or nama like ? or brand like ? or tarif like ? "
                    + "or fasilitas like ? or kota like ? or terminal like ? or noperasi like ? or tarif like ? or jumlah like ? or total like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+ tCari1.getText()+"%");
            statement.setString(2,"%"+ tCari1.getText()+"%");
            statement.setString(3,"%"+ tCari1.getText()+"%");
            statement.setString(4,"%"+ tCari1.getText()+"%");
            statement.setString(5,"%"+ tCari1.getText()+"%");
            statement.setString(6,"%"+ tCari1.getText()+"%");
            statement.setString(7,"%"+ tCari1.getText()+"%");
            statement.setString(8,"%"+ tCari1.getText()+"%");
            statement.setString(9,"%"+ tCari1.getText()+"%");
            statement.setString(10,"%"+ tCari1.getText()+"%");
            statement.setString(11,"%"+ tCari1.getText()+"%");
            statement.setString(12,"%"+ tCari1.getText()+"%");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Object[ ] obj = new Object[12];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nmPesan");
                obj[2] = res.getString("tanggal");
                obj[3] = res.getString("nama");
                obj[4] = res.getString("brand");
                obj[5] = res.getString("fasilitas");
                obj[6] = res.getString("kota");
                obj[7] = res.getString("terminal");
                obj[8] = res.getString("noperasi");
                obj[9] = res.getString("tarif");
                obj[10] = res.getString("jumlah");
                obj[11] = res.getString("total");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void cektarif(){
        int s = Integer.parseInt(tJum.getText());
        int a = Integer.parseInt(tTarif.getText());
        String d = null;
        
        if(tJum.getText() == d){
        tTarif.setText("Belum Dijumlah");
        }else{
            //String uId=tOperasi.getText();
            //String query = "SELECT * FROM Kendaraan WHERE fasilitas="+uId+"";
            //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
            int c = a*s;
            tTotal.setText(c+"");
        }
    }
    private void refreshKendaraan1(){
        model = (DefaultTableModel) tabPesan1.getModel();
        model.setRowCount(0);
        tableData1();
        tCari1.setText("");
    }
    private void refreshKendaraan(){
        model = (DefaultTableModel) tabPesan.getModel();
        model.setRowCount(0);
        tableData();
        tCari.setText("");
    }
    
    private void deleteKendaraan(){
        PreparedStatement statement = null;
        String sql = "DELETE FROM pesanan WHERE id=?";
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
   
    private void updateKendaraan(){
    PreparedStatement statement = null;
    String dt = "yyyy-MM-dd";
    SimpleDateFormat fd = new SimpleDateFormat (dt);
    String tgl = String.valueOf(fd.format(tTanggal.getDate()));
    String sql = "UPDATE `booking` SET `nama`=?,`brand`=?,`noperasi`=?,`terminal`=?,"
            + "`kota`=?,`fasilitas`=?,`jumlah`=?,`tarif`=?,"
            + "`tanggal`=?,`nmPesan`=?,`total`=? WHERE id = ?;";
    try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tNama.getText());
            statement.setString(2, cbBrand.getSelectedItem().toString());
            statement.setString(3, tOperasi.getText());
            statement.setString(4, cbTerminal.getSelectedItem().toString());
            statement.setString(5, cbKota.getSelectedItem().toString());
            statement.setString(6, cbFasilitas.getSelectedItem().toString());
            statement.setString(7, tJum.getText());
            statement.setString(8, tTarif.getText());
            statement.setString(9, tgl);
            statement.setString(10,tPesan.getText());
            statement.setString(11,tTotal.getText());
            statement.setString(12, idText.getText());
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
    
    private void cetak(){
        String z = "dd MMMMMMMMM yyyy";
        SimpleDateFormat zz = new SimpleDateFormat (z);
        String pa = texTgl.getText().toString(); 
        String pb = textJam.getText().toString(); String pf = tNama.getText();
        String pc = idText.getText(); String pd = tPesan.getText(); String pe = String.valueOf(zz.format(tTanggal.getDate()));
        String pg = cbBrand.getSelectedItem().toString(); String ph = cbFasilitas.getSelectedItem().toString(); 
        String pi; String pj = cbKota.getSelectedItem().toString(); String pk = cbTerminal.getSelectedItem().toString();
        pi = null; String pl = tOperasi.getText(); String pm = tTarif.getText(); String pn = tJum.getText(); String po = tTotal.getText();
        
        try {
        String query = "SELECT * FROM kelas where kBrand = '"+pg+"' and kelas = '"+ph+"'";
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while (res.next()){
            cbFasilitas.addItem(res.getString("kelas"));
            pi = res.getString("fasilitas");
        }
    } catch (SQLException e){
        e.printStackTrace();}
    
       taPrint.setText(""
               + "\r +++++++++++ Struk Booking ++++++++++++"
               + "\n      ======== ALDI-Transport ========"+"\n"
               +"\n Tanggal : "+pa+
               "\n Jam : "+pb+"\n"+
               "\n No. Booking \t : "+pc+
               "\n Nama \t : "+pd+
               "\n Tanggal \t : "+ pe +
               "\n Jenis Bus \t : "+ pf+
               "\n Bus \t : "+pg+
               "\n Kelas \t : "+ph+
               "\n Fasilitas \t : "+pi+
               "\n Kota Tujuan \t : "+pj+
               "\n Terminal \t : "+pk+
               "\n No. Operasi \t : "+pl+
               "\n Jumlah Pesan : "+pn+" Orang"+
               "\n Harga/Orang \t : Rp. "+pm +",-"+
               "\n Total Bayar \t: Rp. "+po+",-"
               +"\n\n  --- Semoga Perjalanan Anda Menyenangkan ---"
       );
    }
    
    private void print(){
        try {
                boolean complete = taPrint.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
           
            }
    }
     private void printBook(){
        try {
                boolean complete = tabPesan1.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
           
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
        jLabel4 = new javax.swing.JLabel();
        tPesan = new javax.swing.JTextField();
        tNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbBrand = new javax.swing.JComboBox<>();
        cbFasilitas = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbKota = new javax.swing.JComboBox<>();
        cbTerminal = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tOperasi = new javax.swing.JTextField();
        bTambah = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        idText = new javax.swing.JLabel();
        tTanggal = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        tTarif = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tJum = new javax.swing.JTextField();
        bCek = new javax.swing.JButton();
        bPrint = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        taPrint = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        bRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPesan = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabPesan1 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        tCari1 = new javax.swing.JTextField();
        bCari1 = new javax.swing.JButton();
        bRefresh1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        bPrint1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        mBeranda = new javax.swing.JMenuItem();
        mKendaraan = new javax.swing.JMenuItem();
        mKota = new javax.swing.JMenuItem();
        mPengguna = new javax.swing.JMenuItem();
        laporan = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaksi");

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Kendaraan");

        texTgl.setText("Tanggal");

        textJam.setText("Jam");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("Pemesan");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Nama Kendaraan");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setText("Brand");

        cbBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Brand" }));
        cbBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBrandActionPerformed(evt);
            }
        });

        cbFasilitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Fasilitas" }));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Kelas Fasilitas");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Jurusan");

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

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Terminal");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("No Operasi");

        tOperasi.setEditable(false);

        bTambah.setText("Pesan");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        jButton5.setText("Batal");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setText("Ubah");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("FORM TRANSAKSI");

        jLabel12.setText("ID  :");

        idText.setText("Pesanan Baru");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel13.setText("Tgl Berangkat");

        tTarif.setEditable(false);
        tTarif.setText("0");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel14.setText("Harga/Orang");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel15.setText("Total");

        tTotal.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel11.setText("Jumlah");

        tJum.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tJumCaretUpdate(evt);
            }
        });

        bCek.setText("Cek");
        bCek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCekActionPerformed(evt);
            }
        });

        bPrint.setText("Print");
        bPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrintActionPerformed(evt);
            }
        });

        taPrint.setColumns(20);
        taPrint.setRows(5);
        jScrollPane6.setViewportView(taPrint);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idText, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel11))
                                                .addGap(30, 30, 30))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(tTarif, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tOperasi, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbTerminal, javax.swing.GroupLayout.Alignment.LEADING, 0, 125, Short.MAX_VALUE)
                                            .addComponent(cbKota, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbFasilitas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbBrand, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tNama, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tPesan, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tJum, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tTotal)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(bHapus)
                                                    .addComponent(bTambah))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(bPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(bCek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(97, 97, 97))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(idText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tPesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbFasilitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cbKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tJum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambah)
                    .addComponent(jButton5)
                    .addComponent(bCek))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(bHapus)
                    .addComponent(bPrint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText(" Cari Kendaraan");

        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });

        tabPesan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabPesan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPesanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabPesan);

        tabPesan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Pemesan", "Tanggal", "Nama_BUS", "BUS", "Kelas", "KOTA", "Terminal", "No. Operasi", "Harga Satuan", "Jumlah Pesan", "Total bayar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabPesan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPesan1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabPesan1);

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText(" Cari Booking");

        bCari1.setText("Cari");
        bCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari1ActionPerformed(evt);
            }
        });

        bRefresh1.setText("Refresh");
        bRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefresh1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Data Booking  ||");

        bPrint1.setText("Print Data Booking");
        bPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrint1ActionPerformed(evt);
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

        mKendaraan.setText("Kendaraan");
        mKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKendaraanActionPerformed(evt);
            }
        });
        menu.add(mKendaraan);

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
        laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanActionPerformed(evt);
            }
        });
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
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(texTgl)
                .addGap(18, 18, 18)
                .addComponent(textJam)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRefresh))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(tCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bCari1)
                                .addGap(18, 18, 18)
                                .addComponent(bRefresh1)
                                .addGap(18, 18, 18)
                                .addComponent(bPrint1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(texTgl)
                    .addComponent(textJam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCari)
                            .addComponent(bRefresh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(bCari1)
                            .addComponent(bRefresh1)
                            .addComponent(tCari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(bPrint1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBerandaActionPerformed
        // TODO add your handling code here:
        new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mBerandaActionPerformed

    private void mKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKendaraanActionPerformed
        // TODO add your handling code here:
        new kendaraan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mKendaraanActionPerformed

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

    private void cbBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBrandActionPerformed
        // TODO add your handling code here:
        cmbKls();
    }//GEN-LAST:event_cbBrandActionPerformed

    private void cbKotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbKotaMouseClicked
        // TODO add your handling code here:
        //cmbter();
    }//GEN-LAST:event_cbKotaMouseClicked

    private void cbKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKotaActionPerformed
        // TODO add your handling code here:
        cmbter();
    }//GEN-LAST:event_cbKotaActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        String cmb = "Pilih Brand", kta = "Pilih Kota";
        String fsl = null, trm = null;
        if(cbBrand.getSelectedItem()==cmb||cbKota.getSelectedItem()==kta){
            JOptionPane.showMessageDialog(this,"Data Bus/Tujuan Belum Ditetapkan","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else if(cbFasilitas.getSelectedItem()==fsl||cbTerminal.getSelectedItem()==trm){
            JOptionPane.showMessageDialog(this,"Data Bus/Tujuan Belum Tersedia!","Notifikasi", JOptionPane.WARNING_MESSAGE);
        }else
        if(!tPesan.getText().trim().isEmpty()&&!tOperasi.getText().trim().isEmpty()
                &&!tTotal.getText().trim().isEmpty()&&!tTanggal.getCalendar().toString().trim().isEmpty()){
            tambahKendaraan();
            resetKendaraan();
            refreshKendaraan();
            refreshKendaraan1();
            JOptionPane.showMessageDialog(this,"Selamat kelas Tujuan Bertambah",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else  {JOptionPane.showMessageDialog(this,"GAGAL MENAMBAHKAN!! Harap lengkapi form",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
        print();
    }//GEN-LAST:event_bTambahActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        resetKendaraan();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(!tPesan.getText().trim().isEmpty()&&!tNama.getText().trim().isEmpty()
            &&!tJum.getText().trim().isEmpty()&&!tOperasi.getText().trim().isEmpty()){
            updateKendaraan();
            JOptionPane.showMessageDialog(this,"Data Terminal Sudah Diperbaharui",
                "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            resetKendaraan();
            refreshKendaraan1();
        }else  {JOptionPane.showMessageDialog(this,"Pembaharuan gagal. Harap Periksa Kembali kelengkapan Data!",
            "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        if(!tNama.getText().trim().isEmpty()&&!tPesan.getText().trim().isEmpty()&&!idText.getText().trim().isEmpty()){
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
    }//GEN-LAST:event_bHapusActionPerformed

    private void tJumCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tJumCaretUpdate
        // TODO add your handling code here:
         
        
    }//GEN-LAST:event_tJumCaretUpdate

    private void bCekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCekActionPerformed
        // TODO add your handling code here:
       cektarif();
       cetak();
       bPrint.setEnabled(false);
       bTambah.setEnabled(true);
    }//GEN-LAST:event_bCekActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        if(!tCari.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabPesan.getModel();
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
        tCari.setText("");
    }//GEN-LAST:event_bRefreshActionPerformed

    private void tabPesanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPesanMouseClicked
        // TODO add your handling code here:
        int row=tabPesan.getSelectedRow();
        String uId=tabPesan.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
        //idText.setText(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),0).toString());
        tNama.setText(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),1).toString());
        cbBrand.setSelectedItem(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),2).toString());
        cbFasilitas.setSelectedItem(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),3).toString());
        cbKota.setSelectedItem(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),4).toString());
        cbTerminal.setSelectedItem(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),5).toString());
        tOperasi.setText(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),6).toString());
        //tJum.setText(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),7).toString());
        tTarif.setText(tabPesan.getModel().getValueAt(tabPesan.getSelectedRow(),8).toString());
    }//GEN-LAST:event_tabPesanMouseClicked

    private void bCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari1ActionPerformed
        // TODO add your handling code here:
        if(!tCari1.getText().trim().isEmpty()){
            model=(DefaultTableModel) tabPesan1.getModel();
            model.setRowCount(0);
            searchKendaraan1();
            tCari.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan Booking yang ingin di cari!",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCari1ActionPerformed

    private void bRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefresh1ActionPerformed
        // TODO add your handling code here:
        refreshKendaraan1();
        tCari1.setText("");
    }//GEN-LAST:event_bRefresh1ActionPerformed

    private void tabPesan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPesan1MouseClicked
        // TODO add your handling code here:
         int row=tabPesan1.getSelectedRow();
        String uId=tabPesan1.getModel().getValueAt(row,0).toString();
        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
        idText.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),0).toString());
        tPesan.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),1).toString());
        try{
            int tgl = tabPesan1.getSelectedRow();
            Date tgl_1 = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(tgl, 2));
            tTanggal.setDate(tgl_1);
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
        }
        tNama.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),3).toString());
        cbBrand.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),4).toString());
        cbFasilitas.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),5).toString());
        cbKota.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),6).toString());
        cbTerminal.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),7).toString());
        tOperasi.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),8).toString());
        tTarif.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),9).toString());
        tJum.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),10).toString());
        tTotal.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),11).toString());
        
        bPrint.setEnabled(true);
        cetak();
    }//GEN-LAST:event_tabPesan1MouseClicked

    private void laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanActionPerformed
        // TODO add your handling code here:
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_laporanActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void bPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrintActionPerformed
        // TODO add your handling code here:
        print();
        
    }//GEN-LAST:event_bPrintActionPerformed

    private void bPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrint1ActionPerformed
        // TODO add your handling code here:
        printBook();
    }//GEN-LAST:event_bPrint1ActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bCari1;
    private javax.swing.JButton bCek;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bPrint;
    private javax.swing.JButton bPrint1;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bRefresh1;
    private javax.swing.JButton bTambah;
    private javax.swing.JComboBox<String> cbBrand;
    private javax.swing.JComboBox<String> cbFasilitas;
    private javax.swing.JComboBox<String> cbKota;
    private javax.swing.JComboBox<String> cbTerminal;
    private javax.swing.JLabel idText;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu laporan;
    private javax.swing.JMenuItem mBeranda;
    private javax.swing.JMenuItem mKendaraan;
    private javax.swing.JMenuItem mKota;
    private javax.swing.JMenuItem mPengguna;
    private javax.swing.JMenu menu;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tCari1;
    private javax.swing.JTextField tJum;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tOperasi;
    private javax.swing.JTextField tPesan;
    private com.toedter.calendar.JDateChooser tTanggal;
    private javax.swing.JTextField tTarif;
    private javax.swing.JTextField tTotal;
    private javax.swing.JTextArea taPrint;
    private javax.swing.JTable tabPesan;
    private javax.swing.JTable tabPesan1;
    private javax.swing.JLabel texTgl;
    private javax.swing.JLabel textJam;
    // End of variables declaration//GEN-END:variables

    private Object hrg1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

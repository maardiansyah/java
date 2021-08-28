/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import asset.koneksi;
import java.awt.print.PrinterException;
//import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import java.awt.print.PrinterException;

/**
 *
 * @author User
 */
public class laporan extends javax.swing.JFrame {
    DefaultTableModel model;
    Connection connection;
    /**
     * Creates new form NewJFrame
     */
    public laporan() {
        initComponents();
        connection=koneksi.connection();
        tableUser();
        tableKota();
        tableTerminal();
        tableKendaraan();
        tableBooking();
        tableBrand();
        tableKelas();
        print();
    }
    private void tableUser(){
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
                Object[ ] obj = new Object[3];
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
    private void tableKendaraan(){
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
    private void tableBooking(){
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
               // obj[4] = res.getString("kHarga");
                model.addRow(obj);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
    }
    
    private void print(){
        String a = jComboBox1.getSelectedItem().toString();
        if(a == "Transaksi/Booking"){
            try {
                boolean complete = tabPesan1.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
           
            }
        }else if(a == "User"){
            try {
                boolean complete = tabData.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else if(a == "Kota"){
            try {
                boolean complete = tabKota.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else if(a == "Terminal"){
            try {
                boolean complete = tabTerminal.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else if(a == "Kendaraan"){
            try {
                boolean complete = tabKen.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else if(a == "Brand"){
            try {
                boolean complete = tabBrand.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else if(a == "Fasilitas"){
            try {
                boolean complete = tabKelas.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{JOptionPane.showMessageDialog(null,"Pilih Data Yang Ingin Diprint!!");}
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
        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabData = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabKota = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabTerminal = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabBrand = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabKelas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabKen = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabPesan1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laporan");
        setAlwaysOnTop(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("LAPORAN");

        jButton1.setText("HOME");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("DATA USER/ADMIN");

        tabData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Username", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat"
            }
        ));
        tabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabDataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabData);
        if (tabData.getColumnModel().getColumnCount() > 0) {
            tabData.getColumnModel().getColumn(0).setResizable(false);
            tabData.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabData.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabData.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabData.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabData.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabData.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabData.getColumnModel().getColumn(6).setResizable(false);
            tabData.getColumnModel().getColumn(6).setPreferredWidth(250);
        }

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("DATA KOTA");

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
        if (tabKota.getColumnModel().getColumnCount() > 0) {
            tabKota.getColumnModel().getColumn(0).setResizable(false);
            tabKota.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabKota.getColumnModel().getColumn(1).setResizable(false);
            tabKota.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("DATA Terminal");

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
        jScrollPane3.setViewportView(tabTerminal);
        if (tabTerminal.getColumnModel().getColumnCount() > 0) {
            tabTerminal.getColumnModel().getColumn(0).setResizable(false);
            tabTerminal.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabTerminal.getColumnModel().getColumn(1).setResizable(false);
            tabTerminal.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabTerminal.getColumnModel().getColumn(2).setResizable(false);
            tabTerminal.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("DATA Brand");

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
        jScrollPane5.setViewportView(tabBrand);
        if (tabBrand.getColumnModel().getColumnCount() > 0) {
            tabBrand.getColumnModel().getColumn(0).setResizable(false);
            tabBrand.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabBrand.getColumnModel().getColumn(1).setResizable(false);
            tabBrand.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("DATA FASILITAS");

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
        jScrollPane6.setViewportView(tabKelas);
        if (tabKelas.getColumnModel().getColumnCount() > 0) {
            tabKelas.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabKelas.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabKelas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabKelas.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("DATA Kendaraan");

        tabKen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Brand", "Fasilitas", "Kota", "Terminal", "No Operasi", "Status", "Tarif"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        jScrollPane7.setViewportView(tabKen);
        if (tabKen.getColumnModel().getColumnCount() > 0) {
            tabKen.getColumnModel().getColumn(0).setResizable(false);
            tabKen.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabKen.getColumnModel().getColumn(1).setResizable(false);
            tabKen.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabKen.getColumnModel().getColumn(2).setResizable(false);
            tabKen.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabKen.getColumnModel().getColumn(3).setResizable(false);
            tabKen.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabKen.getColumnModel().getColumn(4).setResizable(false);
            tabKen.getColumnModel().getColumn(4).setPreferredWidth(75);
            tabKen.getColumnModel().getColumn(5).setResizable(false);
            tabKen.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabKen.getColumnModel().getColumn(6).setResizable(false);
            tabKen.getColumnModel().getColumn(6).setPreferredWidth(25);
            tabKen.getColumnModel().getColumn(7).setResizable(false);
            tabKen.getColumnModel().getColumn(7).setPreferredWidth(25);
            tabKen.getColumnModel().getColumn(8).setResizable(false);
            tabKen.getColumnModel().getColumn(8).setPreferredWidth(200);
        }

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("DATA BOOKING");

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
        jScrollPane8.setViewportView(tabPesan1);
        if (tabPesan1.getColumnModel().getColumnCount() > 0) {
            tabPesan1.getColumnModel().getColumn(0).setResizable(false);
            tabPesan1.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabPesan1.getColumnModel().getColumn(1).setResizable(false);
            tabPesan1.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(2).setResizable(false);
            tabPesan1.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(3).setResizable(false);
            tabPesan1.getColumnModel().getColumn(3).setPreferredWidth(25);
            tabPesan1.getColumnModel().getColumn(4).setResizable(false);
            tabPesan1.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(5).setResizable(false);
            tabPesan1.getColumnModel().getColumn(5).setPreferredWidth(15);
            tabPesan1.getColumnModel().getColumn(6).setResizable(false);
            tabPesan1.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(7).setResizable(false);
            tabPesan1.getColumnModel().getColumn(7).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(8).setResizable(false);
            tabPesan1.getColumnModel().getColumn(8).setPreferredWidth(15);
            tabPesan1.getColumnModel().getColumn(9).setResizable(false);
            tabPesan1.getColumnModel().getColumn(9).setPreferredWidth(50);
            tabPesan1.getColumnModel().getColumn(10).setResizable(false);
            tabPesan1.getColumnModel().getColumn(10).setPreferredWidth(10);
            tabPesan1.getColumnModel().getColumn(11).setResizable(false);
            tabPesan1.getColumnModel().getColumn(11).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(357, 357, 357)))
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                            .addComponent(jScrollPane6))
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1273, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel1);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Print Data");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Data", "Transaksi/Booking", "User", "Kota", "Terminal", "Kendaraan", "Brand", "Fasilitas" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabDataMouseClicked
//        int row=tabData.getSelectedRow();
//        String uId=tabData.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM user WHERE id="+uId+"";
//        //cId=connection.prepareStatement(query);
//        // TODO add your handling code here:
//        tId.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),0).toString());
//        tNama.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),1).toString());
//        tAlamat.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),6).toString());
//        tUser.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),2).toString());
//        tPass.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),7).toString());
//        //buttonGroup1.setSelectedItem(tabData.getModel().getValueAt(tabData.));
//        String jenkel = tabData.getModel().getValueAt(tabData.getSelectedRow(),3).toString();
//        if(jenkel.equals("Laki-Laki")){
//            rbLaki.setSelected(true);
//        }else{
//            rbPerempuan.setSelected(true);
//        }
//        tLahir.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),4).toString());
//        //tTanggal.setText(tabData.getModel().getValueAt(tabData.getSelectedRow(),5).)toString());
//        //DefaultTableModel tanggal = (DefaultTableModel)tabData.getModel();
//        try{
//            int tgl = tabData.getSelectedRow();
//            Date tgl_1 = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(tgl, 5));
//            tTanggal.setDate(tgl_1);
//        }
//        catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
//        }
//        tId.setEditable(false);
//        tRegis.setEnabled(false);
        //    tPass.setText(query);
    }//GEN-LAST:event_tabDataMouseClicked

    private void tabKotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKotaMouseClicked
        // TODO add your handling code here:
//        int row=tabKota.getSelectedRow();
//        String uId=tabKota.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM kota WHERE id="+uId+"";
//        tIdKota.setText(tabKota.getModel().getValueAt(tabKota.getSelectedRow(),0).toString());
//        tKota.setText(tabKota.getModel().getValueAt(tabKota.getSelectedRow(),1).toString());
    }//GEN-LAST:event_tabKotaMouseClicked

    private void tabTerminalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTerminalMouseClicked
        // TODO add your handling code here:
//        int row=tabTerminal.getSelectedRow();
//        String uId=tabTerminal.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM terrminal WHERE id="+uId+"";
//        String cbkt = "Select kota from terminal where id="+uId+"";
//        tIdTerminal.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),0).toString());
//        tTerminal.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),2).toString());
//        tHarga.setText(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),3).toString());
//        cbKota.setSelectedItem(tabTerminal.getModel().getValueAt(tabTerminal.getSelectedRow(),1).toString());
        //        String cmbox = model.getValueAt(tabTerminal.getSelectedRow(),1).toString();
        //            switch(cmbox){
            //                case "+"cbkt"+";
            //                cbKota.setSelectedItem(cbkt);
            //                break;
            //            }
    }//GEN-LAST:event_tabTerminalMouseClicked

    private void tabBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabBrandMouseClicked
        // TODO add your handling code here:
//        int row=tabBrand.getSelectedRow();
//        String uId=tabBrand.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM kota WHERE id="+uId+"";
//        brand.setText(tabBrand.getModel().getValueAt(tabBrand.getSelectedRow(),1).toString());
//        tIdBrand.setText(tabBrand.getModel().getValueAt(tabBrand.getSelectedRow(),0).toString());
    }//GEN-LAST:event_tabBrandMouseClicked

    private void tabKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKelasMouseClicked
        // TODO add your handling code here:
//        int row=tabKelas.getSelectedRow();
//        String uId=tabKelas.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
//        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
//        idText.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),0).toString());
//        tKelas.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),2).toString());
//        tFasilitas.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),3).toString());
//        tHarga.setText(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),4).toString());
//        cbBrand.setSelectedItem(tabKelas.getModel().getValueAt(tabKelas.getSelectedRow(),1).toString());
        //        String cmbox = model.getValueAt(tabTerminal.getSelectedRow(),1).toString();
        //            switch(cmbox){
            //                case "+"cbkt"+";
            //                cbKota.setSelectedItem(cbkt);
            //                break;
            //            }
    }//GEN-LAST:event_tabKelasMouseClicked

    private void tabKenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKenMouseClicked
        // TODO add your handling code here:
//        int row=tabKen.getSelectedRow();
//        String uId=tabKen.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
//        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
//        tId.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),0).toString());
//        tNama.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),1).toString());
//        cbBrand.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),2).toString());
//        cbFasilitas.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),3).toString());
//        cbKota.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),4).toString());
//        cbTerminal.setSelectedItem(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),5).toString());
//        tOperasi.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),6).toString());
//        tStatus.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),7).toString());
//        tTarif.setText(tabKen.getModel().getValueAt(tabKen.getSelectedRow(),8).toString());

    }//GEN-LAST:event_tabKenMouseClicked

    private void tabPesan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPesan1MouseClicked
        // TODO add your handling code here:
//        int row=tabPesan1.getSelectedRow();
//        String uId=tabPesan1.getModel().getValueAt(row,0).toString();
//        String query = "SELECT * FROM fasilitas WHERE id="+uId+"";
//        //String cbkt = "Select kBrand from fasilitas where id="+uId+"";
//        idText.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),0).toString());
//        tPesan.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),1).toString());
//        try{
//            int tgl = tabPesan1.getSelectedRow();
//            Date tgl_1 = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(tgl, 2));
//            tTanggal.setDate(tgl_1);
//        }
//        catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
//        }
//        tNama.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),3).toString());
//        cbBrand.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),4).toString());
//        cbFasilitas.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),5).toString());
//        cbKota.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),6).toString());
//        cbTerminal.setSelectedItem(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),7).toString());
//        tOperasi.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),8).toString());
//        tTarif.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),9).toString());
//        tJum.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),10).toString());
//        tTotal.setText(tabPesan1.getModel().getValueAt(tabPesan1.getSelectedRow(),11).toString());

    }//GEN-LAST:event_tabPesan1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        print();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        //print();
    }//GEN-LAST:event_formMouseClicked

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
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable tabBrand;
    private javax.swing.JTable tabData;
    private javax.swing.JTable tabKelas;
    private javax.swing.JTable tabKen;
    private javax.swing.JTable tabKota;
    private javax.swing.JTable tabPesan1;
    private javax.swing.JTable tabTerminal;
    // End of variables declaration//GEN-END:variables
}

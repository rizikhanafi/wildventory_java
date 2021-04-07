/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wildventory_admin;

import hal_utama.halaman_utama;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author lenovo
 */
public class datainventori extends javax.swing.JFrame {
private Connection koneksi;
private koneksi db = new koneksi();
private DefaultTableModel tbl;
 public datainventori() {
        initComponents();
        setIcon();
       tbl=new DefaultTableModel();
        tabel.setModel(tbl);
        tbl.addColumn("ID");
        tbl.addColumn("Nama");
        tbl.addColumn("Tanggal");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Jenis");
        tbl.addColumn("Harga");
        getData();
        id_auto();
        id.setEnabled(false);
        update.setEnabled(false);
        
    }
    
    public void getData(){
    tbl.getDataVector().removeAllElements();
    tbl.fireTableDataChanged();
    try{
        db.getkoneksi();
        Statement stat=(Statement) db.getkoneksi().createStatement();
        String sql="select * from masuk";
        ResultSet res =stat.executeQuery(sql);
        while(res.next()){
            Object[] obj=new Object[6];
            obj[0] = res.getString("id_barang");
            obj[1] = res.getString("nama_barang");
            obj[2] = res.getString("tanggal_masuk");
            obj[3] = res.getString("stok_barang");
            obj[4] = res.getString("jenis_barang");
            obj[5] = res.getString("harga_barang");
            tbl.addRow(obj);
        }      
    } catch(SQLException err){
        JOptionPane.showMessageDialog(null,err.getMessage());
    }

}
   public void bersih(){
    nama_barang.setText("");
   
    jumlah.setText("");
    harga.setText("");
    getData();
   }
    public void simpan(){
    String r,h,c,m,p,k;
    k=id.getText();
    r=nama_barang.getText();
    c=jumlah.getText();
    m=cb_jenis.getSelectedItem().toString();
    p=harga.getText();
    SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
    h=simple.format(tanggal.getDate());
    try{
        if(r.equals("") || h.equals("") || c.equals("") || p.equals("")){
                   JOptionPane.showMessageDialog(this,"Data tidak boleh kosong.");
                   bersih();
                } else {
        db.getkoneksi();
        Statement stat=(Statement) db.getkoneksi().createStatement();
        
        stat.executeUpdate(
        "insert into masuk"
                + "(id_barang,nama_barang,tanggal_masuk,stok_barang,jenis_barang,harga_barang) values ('"+k+"','"+r+"','"+h+"','"+c+"','"+m+"','"+p+"')");
        JOptionPane.showMessageDialog(this,"Data tersimpan.");
       bersih();
       getData();
       id_auto();
       update.setEnabled(false);
        }
    } catch(SQLException err){
        JOptionPane.showMessageDialog(this,"Error, tidak bisa disimpan, masukan data dengan benar.");
    }
}
    
    public void cari(){
    DefaultTableModel tabelTampil1 = new DefaultTableModel();
    tabelTampil1.addColumn("ID");
    tabelTampil1.addColumn("Nama");
    tabelTampil1.addColumn("Tanggal");
    tabelTampil1.addColumn("Jumlah");
    tabelTampil1.addColumn("Jenis");
    tabelTampil1.addColumn("Harga");
        try{
            db.getkoneksi();
        Statement stat=(Statement) db.getkoneksi().createStatement();
            String sql = "select * from MASUK where nama_barang like '%" + cari_barang.getText() + "%'";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
            tabelTampil1.addRow(new Object[]{
            rs.getString(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5),
            rs.getString(6),
            });
            }
            tabel.setModel(tabelTampil1);

                }catch (Exception e){
            }
        
    }
    public void tampil() {
        
        try {
        id.setText(tabel.getValueAt(tabel.getSelectedRow(), 0).toString());
        nama_barang.setText(tabel.getValueAt(tabel.getSelectedRow(), 1).toString());
        //String t = (tabel.getValueAt(tabel.getSelectedRow(), 2).toString());
        
        jumlah.setText(tabel.getValueAt(tabel.getSelectedRow(), 3).toString());
        String s = tabel.getValueAt(tabel.getSelectedRow(), 4).toString();
        cb_jenis.setSelectedItem(s);
        harga.setText(tabel.getValueAt(tabel.getSelectedRow(), 5).toString());
    }
    catch(Exception c) {
    }
        
    }
    public void hapus() {
    String r,h,c,m,p;
    r=nama_barang.getText();

    c=jumlah.getText();
    m=cb_jenis.getSelectedItem().toString();
    p=harga.getText();
    try{
        if(r.equals("")||  c.equals("") || p.equals("")){
                   JOptionPane.showMessageDialog(this,"Silahkan pilih data yang akan dihapus..");
                   bersih();
                } else {
        db.getkoneksi();
        Statement stat=(Statement) db.getkoneksi().createStatement();
        stat.executeUpdate(
                "delete from masuk where "
                        + "id_barang='"+id.getText()+"'");
        JOptionPane.showMessageDialog(this,"Data terhapus.");
        bersih();
        getData();
        id_auto();
        update.setEnabled(false);
        }
    } catch(SQLException err){
        JOptionPane.showMessageDialog(null,err.getMessage());
    }
}
    private void id_auto() {
       try {
            Statement stat=(Statement) db.getkoneksi().createStatement();
        String sql="select * from masuk order by id_barang desc";
        ResultSet rs =stat.executeQuery(sql);
            if (rs.next()) {
                String ids = rs.getString("id_barang").substring(3);
                String AN = "" + (Integer.parseInt(ids) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               id.setText("1" + Nol + AN);
            } else {
               id.setText("10001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
     }

public void update(){
    String r,h,c,p,k,x;
    x=id.getText();
    r=nama_barang.getText();
    c=jumlah.getText();
    k=cb_jenis.getSelectedItem().toString();
    p=harga.getText();
    SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
    h=simple.format(tanggal.getDate());
    try{if(r.equals("") || x.equals("") || c.equals("") || p.equals("")){
                   JOptionPane.showMessageDialog(this,"Data yang di update tidak boleh kosong.");
                   bersih();
                   id_auto();
                } else {
        db.getkoneksi();
        Statement stat=(Statement) db.getkoneksi().createStatement();
        stat.executeUpdate(
                "update masuk SET "
                        + "id_barang='"+id.getText()+"',"
                        + "nama_barang='"+nama_barang.getText()+"',"
                        + "tanggal_masuk='"+h+"',"
                        + "stok_barang='"+jumlah.getText()+"',"
                        + "jenis_barang='"+k+"',"
                        + "harga_barang='"+harga.getText()+"'WHERE id_barang='"+id.getText()+"'");
        JOptionPane.showMessageDialog(this,"Data berhasil di update.");
        bersih();
        getData();
        id_auto();
        update.setEnabled(false);
    }
    } catch(SQLException err){
        JOptionPane.showMessageDialog(this,"Error, tidak bisa disimpan, masukan data dengan benar.");
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

        bg = new javax.swing.JPanel();
        sidepanel = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        copyright = new javax.swing.JLabel();
        berandaa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        hover_inventori = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tentang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        keluar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panel_menu = new javax.swing.JPanel();
        beranda = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nama_barang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jumlah = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cb_jenis = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        hapus = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cari_barang = new javax.swing.JTextField();
        cari = new javax.swing.JButton();
        submit = new javax.swing.JButton();
        tanggal = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setMinimumSize(new java.awt.Dimension(862, 540));
        bg.setPreferredSize(new java.awt.Dimension(862, 540));
        bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgMouseClicked(evt);
            }
        });
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidepanel.setBackground(new java.awt.Color(207, 241, 239));
        sidepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Artboard 1.png"))); // NOI18N
        sidepanel.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 11, -1, -1));

        copyright.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        copyright.setForeground(new java.awt.Color(63, 63, 63));
        copyright.setText("RH ©2020 1.0.0");
        sidepanel.add(copyright, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 511, -1, -1));

        berandaa.setBackground(new java.awt.Color(207, 241, 239));
        berandaa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                berandaaMouseClicked(evt);
            }
        });
        berandaa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home (1).png"))); // NOI18N
        jLabel2.setText("  Beranda");
        berandaa.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 90, -1));

        sidepanel.add(berandaa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 240, 40));

        hover_inventori.setBackground(new java.awt.Color(167, 211, 208));
        hover_inventori.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/work.png"))); // NOI18N
        jLabel3.setText("  Data Inventori");
        hover_inventori.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 140, -1));

        sidepanel.add(hover_inventori, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 240, 40));

        tentang.setBackground(new java.awt.Color(207, 241, 239));
        tentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tentangMouseClicked(evt);
            }
        });
        tentang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/warning.png"))); // NOI18N
        jLabel6.setText("  Tentang Aplikasi");
        tentang.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 170, -1));

        sidepanel.add(tentang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 240, 40));

        keluar.setBackground(new java.awt.Color(207, 241, 239));
        keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarMouseClicked(evt);
            }
        });
        keluar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jLabel4.setText("  Keluar");
        keluar.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 170, -1));

        sidepanel.add(keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 240, 40));

        bg.add(sidepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 580));

        panel_menu.setBackground(new java.awt.Color(207, 241, 239));

        beranda.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        beranda.setForeground(new java.awt.Color(63, 63, 63));
        beranda.setText("Data Inventori");

        javax.swing.GroupLayout panel_menuLayout = new javax.swing.GroupLayout(panel_menu);
        panel_menu.setLayout(panel_menuLayout);
        panel_menuLayout.setHorizontalGroup(
            panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_menuLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(beranda)
                .addContainerGap(451, Short.MAX_VALUE))
        );
        panel_menuLayout.setVerticalGroup(
            panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_menuLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(beranda)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        bg.add(panel_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 640, 90));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Disini kamu bisa melihat data.");
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama Barang", "Tanggal Masuk", "Jumlah Barang", "Jenis", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 150, 300, 320));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("ID Barang :");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, -1, 30));
        bg.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 90, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Nama Barang :");
        bg.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, 30));
        bg.add(nama_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 140, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal :");
        bg.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, -1, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Jumlah :");
        bg.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, -1, 30));
        bg.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 110, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Jenis Barang :");
        bg.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, -1, 30));

        cb_jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Non Food" }));
        bg.add(cb_jenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, 120, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Harga :");
        bg.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, -1, 30));
        bg.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 250, 30));

        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        bg.add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 120, 30));

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        bg.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 120, 30));

        jLabel12.setText("Cari Barang :");
        bg.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, -1, 30));
        bg.add(cari_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, 120, 30));

        cari.setText("Cari");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        bg.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 480, 90, 30));

        submit.setText("Tambah");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        bg.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, 250, 30));
        bg.add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 250, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(878, 579));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void berandaaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berandaaMouseClicked
        this.dispose();
        new halaman_admin().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_berandaaMouseClicked

    private void keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarMouseClicked
int jawab = JOptionPane.showOptionDialog(this, "Ingin Keluar?", "Keluar",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE, null, null, null);
if (jawab == JOptionPane.YES_OPTION) {
    System.exit(0);
}          // TODO add your handling code here:
    }//GEN-LAST:event_keluarMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
update();
submit.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_updateActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
hapus(); 

submit.setEnabled(true);
getData();// TODO add your handling code here:
    }//GEN-LAST:event_hapusActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
cari();
               // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void tentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tentangMouseClicked
this.dispose();
        new tentang().setVisible(true);         // TODO add your handling code here:
    }//GEN-LAST:event_tentangMouseClicked

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
tampil();
update.setEnabled(true);
submit.setEnabled(false);

// TODO add your handling code here:
    }//GEN-LAST:event_tabelMouseClicked

    private void bgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseClicked
    nama_barang.setEnabled(true);
tanggal.setEnabled(true);
cb_jenis.setEnabled(true);
jumlah.setEnabled(true);
harga.setEnabled(true);
submit.setEnabled(true);
update.setEnabled(false);

id_auto();
getData();
bersih();// TODO add your handling code here:
    }//GEN-LAST:event_bgMouseClicked

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_submitActionPerformed

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
            java.util.logging.Logger.getLogger(datainventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datainventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datainventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datainventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datainventori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel beranda;
    private javax.swing.JPanel berandaa;
    private javax.swing.JPanel bg;
    private javax.swing.JButton cari;
    private javax.swing.JTextField cari_barang;
    private javax.swing.JComboBox<String> cb_jenis;
    private javax.swing.JLabel copyright;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga;
    private javax.swing.JPanel hover_inventori;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JPanel keluar;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField nama_barang;
    private javax.swing.JPanel panel_menu;
    private javax.swing.JPanel sidepanel;
    private javax.swing.JButton submit;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JPanel tentang;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icons.png")));
    }
}

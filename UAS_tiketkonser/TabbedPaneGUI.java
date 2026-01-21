/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketkonser;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class TabbedPaneGUI extends javax.swing.JFrame {

    /**
     * Creates new form TabbedPaneGUI
     */
    private int nextId = 1;
    
    public TabbedPaneGUI() {
        initComponents();
    startMarqueeAnimation();
    jPanel1.setLayout(null);
        // Ambil lebar teks dari preferred size
        int width1 = jLabel1.getPreferredSize().width;
        int width2 = jLabel2.getPreferredSize().width;
            jLabel1.setBounds(-width1, 20, width1, 30);
            jLabel2.setBounds(-width2, 750, width2, 30);
    
    // Kosongkan tabel saat aplikasi dibuka
    ((DefaultTableModel) jTable3.getModel()).setRowCount(0);
    ((DefaultTableModel) jTable1.getModel()).setRowCount(0);
    
    jTable2.getColumnModel().getColumn(0).setPreferredWidth(30);  // No
    jTable2.getColumnModel().getColumn(1).setPreferredWidth(100); // ID_PEMBELI
    
    CardLayout cardLayout = new CardLayout();
    getContentPane().setLayout(cardLayout);
    getContentPane().add(jPanel1, "beranda");
    getContentPane().add(jPanel4, "daftar_tiket");
    getContentPane().add(jPanel5, "daftar_peserta");

    cardLayout.show(getContentPane(), "beranda"); // tampilkan beranda pertama kali
    
    jSpinner1.setModel(new SpinnerNumberModel(1, 1, 10, 1));
    
    loadTanggalKonser();
    
    }
    
    private void hitungTotal() {
    try {
        // Ambil harga dari jTextField1 (HARGA PER TIKET)
        String hargaText = jTextField1.getText();
        if (!hargaText.startsWith("Rp.")) {
            jTextField3.setText("Rp. 0"); // total di jTextField1
            return;
        }

        // Bersihkan format "Rp. " dan titik ribuan
        String hargaBersih = hargaText.replace("Rp. ", "").replace(".", "");
        double hargaPerTiket = Double.parseDouble(hargaBersih);

        // Ambil jumlah dari jSpinner1
        int jumlah = (int) jSpinner1.getValue(); // ← ini kunci utama!

        // Hitung total
        double total = hargaPerTiket * jumlah;

        // Tampilkan di jTextField1 (TOTAL HARGA)
        jTextField3.setText("Rp. " + (int)total);
    } catch (Exception e) {
        jTextField3.setText("Rp. 0");
    }
}
    
 private void startMarqueeAnimation() {
    Timer timer = new Timer(30, e -> {
        // Gerakkan jLabel1 (atas)
        int x1 = jLabel1.getX() + 2;
        if (x1 > getWidth()) {
            x1 = -jLabel1.getWidth();
        }
        jLabel1.setLocation(x1, 20); // y = 20 (posisi atas)

        // Gerakkan jLabel2 (bawah)
        int x2 = jLabel2.getX() + 2;
        if (x2 > getWidth()) {
            x2 = -jLabel2.getPreferredSize().width;
        }
        jLabel2.setLocation(x2, 750); 
    });
    timer.start();
}
    
    private String generateIdPembeli() {
    return String.format("P%03d", nextId); // P001, P002, P003...
    }
    
    private void simpanDataKeDuaTabel() {
    
    String namaPembeli = jTextField4.getText().trim();
    if (namaPembeli.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama pembeli belum diisi!");
        return;
    }

    String idPembeli = jTextField6.getText();
    String namaKonser = jTextField2.getText();
    int idKonser = Integer.parseInt(jTextField5.getText());
    String tanggal = jComboBox1.getSelectedItem().toString(); // ← ambil dari combo box
    int jumlah = (int) jSpinner1.getValue();
    double hargaPerTiket = Double.parseDouble(jTextField1.getText().replace("Rp. ", "").replace(".", ""));
    double totalHarga = Double.parseDouble(jTextField3.getText().replace("Rp. ", "").replace(".", ""));

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO pembeli (id_pembeli, nama_pembeli, id_konser, jumlah_tiket, total_harga) VALUES (?, ?, ?, ?, ?)"
        );
        stmt.setString(1, idPembeli);
        stmt.setString(2, namaPembeli);
        stmt.setInt(3, idKonser);
        stmt.setInt(4, jumlah);
        stmt.setDouble(5, totalHarga);
        stmt.executeUpdate();

        // Tambahkan ke tabel GUI (opsional, karena nanti bisa load dari DB)
        DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
        Object[] row3 = {model3.getRowCount() + 1, namaPembeli, idPembeli};
        model3.addRow(row3);

        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        Object[] row1 = {
            model1.getRowCount() + 1,
            namaKonser,
            idKonser,
            tanggal,
            jumlah,
            "Rp. " + (int)hargaPerTiket,
            "Rp. " + (int)totalHarga
        };
        model1.addRow(row1);

        nextId++;
        refreshForm();
        isiComboBoxIdPembeli();
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke database!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saat menyimpan: " + e.getMessage());
    }
    
}

    private void refreshForm() {
    jTextField4.setText(""); // NAMA PEMBELI
    jTextField6.setText(""); // ID_PEMBELI
    jTextField2.setText(""); // NAMA KONSER
    jTextField5.setText(""); // ID_KONSER
    jComboBox1.setSelectedIndex(0);
    jSpinner1.setValue(1);
    jTextField1.setText(""); // HARGA PER TIKET
    jTextField3.setText(""); // TOTAL HARGA
    
    jComboBox2.setSelectedIndex(-1);
}

    private int cariBarisBerdasarkanId(String id) {
    DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
    for (int i = 0; i < model3.getRowCount(); i++) {
        String idTabel = model3.getValueAt(i, 2).toString(); // kolom ID_PEMBELI
        if (idTabel.equals(id)) {
            return i;
        }
    }
    return -1; // tidak ditemukan
}
    
    private void isiComboBoxIdPembeli() {
    jComboBox2.removeAllItems(); // kosongkan dulu
    
    DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
    for (int i = 0; i < model3.getRowCount(); i++) {
        String idPembeli = model3.getValueAt(i, 2).toString(); // kolom ID_PEMBELI
        jComboBox2.addItem(idPembeli);
    }
}
    
    private void pilihDataBerdasarkanId() {
    System.out.println("=== DEBUG: Memulai Pencarian ===");

    if (jComboBox2.getItemCount() == 0) {
        JOptionPane.showMessageDialog(this, "Belum ada data pembeli!");
        return;
    }

    String idPilih = jComboBox2.getSelectedItem().toString();
    System.out.println("ID yang dipilih: " + idPilih);

    // Cari baris di jTable3
    DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
    int index = -1;
    for (int i = 0; i < model3.getRowCount(); i++) {
        Object idTabelObj = model3.getValueAt(i, 2);
        System.out.println("Baris " + i + ": ID_TABEL = " + (idTabelObj != null ? idTabelObj.toString() : "null"));
        if (idTabelObj != null && idTabelObj.toString().equals(idPilih)) {
            index = i;
            System.out.println("✅ Ditemukan di baris: " + i);
            break;
        }
    }

    if (index == -1) {
        JOptionPane.showMessageDialog(this, "ID tidak ditemukan!");
        System.out.println("❌ ID tidak ditemukan");
        return;
    }

    // Ambil data dari jTable3
    Object namaPembeliObj = model3.getValueAt(index, 1);
    Object idPembeliObj = model3.getValueAt(index, 2);
    System.out.println("Nama Pembeli: " + (namaPembeliObj != null ? namaPembeliObj.toString() : "null"));
    System.out.println("ID Pembeli: " + (idPembeliObj != null ? idPembeliObj.toString() : "null"));

    // Ambil data dari jTable1
    DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
    Object namaKonserObj = model1.getValueAt(index, 1);
    Object idKonserObj = model1.getValueAt(index, 2);
    System.out.println("Nama Konser: " + (namaKonserObj != null ? namaKonserObj.toString() : "null"));
    System.out.println("ID Konser: " + (idKonserObj != null ? idKonserObj.toString() : "null"));

    // Isi field input
    jTextField4.setText(namaPembeliObj != null ? namaPembeliObj.toString() : "");
    jTextField6.setText(idPembeliObj != null ? idPembeliObj.toString() : "");

    jTextField2.setText(namaKonserObj != null ? namaKonserObj.toString() : "");
    jTextField5.setText(idKonserObj != null ? idKonserObj.toString() : "");

    if (model1.getValueAt(index, 3) != null) {
        jComboBox1.setSelectedItem(model1.getValueAt(index, 3).toString());
    }

    if (model1.getValueAt(index, 4) != null) {
        try {
            jSpinner1.setValue(Integer.parseInt(model1.getValueAt(index, 4).toString()));
        } catch (NumberFormatException e) {
            jSpinner1.setValue(1);
        }
    }

    jTextField1.setText(model1.getValueAt(index, 5) != null ? model1.getValueAt(index, 5).toString() : "");
    jTextField3.setText(model1.getValueAt(index, 6) != null ? model1.getValueAt(index, 6).toString() : "");

    System.out.println("✅ Data berhasil dimuat ke form input");
}
    
    private void loadTanggalKonser() {
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT tanggal FROM konser ORDER BY tanggal");
        ResultSet rs = stmt.executeQuery();
        jComboBox1.removeAllItems();
        while (rs.next()) {
            jComboBox1.addItem(rs.getString("tanggal"));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
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

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonBeranda = new javax.swing.JButton();
        jButtonDaftarTiket = new javax.swing.JButton();
        jButtonDaftarPeserta = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButtonBack1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButtonSimpan = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonPilih = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonEksport = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButtonBlackPink = new javax.swing.JButton();
        jButtonGDragon = new javax.swing.JButton();
        jButtonJKT48 = new javax.swing.JButton();
        jButtonJHope = new javax.swing.JButton();
        jButtonSeventeen = new javax.swing.JButton();
        jButtonNCTWISH = new javax.swing.JButton();
        jButtonYoasobi = new javax.swing.JButton();
        jButtonTaeyon = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButtonBack2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/menu input.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(null);

        jButtonBeranda.setBackground(new java.awt.Color(204, 204, 255));
        jButtonBeranda.setFont(new java.awt.Font("Trajan Pro", 1, 18)); // NOI18N
        jButtonBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/panggung.png"))); // NOI18N
        jButtonBeranda.setText("  BERANDA");
        jButtonBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBerandaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonBeranda);
        jButtonBeranda.setBounds(420, 200, 270, 70);

        jButtonDaftarTiket.setBackground(new java.awt.Color(204, 204, 255));
        jButtonDaftarTiket.setFont(new java.awt.Font("Trajan Pro", 1, 18)); // NOI18N
        jButtonDaftarTiket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiket.png"))); // NOI18N
        jButtonDaftarTiket.setText("   DAFTAR TIKET DAN HARGA");
        jButtonDaftarTiket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDaftarTiketActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDaftarTiket);
        jButtonDaftarTiket.setBounds(360, 340, 400, 70);

        jButtonDaftarPeserta.setBackground(new java.awt.Color(204, 204, 255));
        jButtonDaftarPeserta.setFont(new java.awt.Font("Trajan Pro", 1, 18)); // NOI18N
        jButtonDaftarPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/menu.png"))); // NOI18N
        jButtonDaftarPeserta.setText("DAFTAR PESERTA");
        jButtonDaftarPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDaftarPesertaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDaftarPeserta);
        jButtonDaftarPeserta.setBounds(390, 490, 330, 70);

        jLabel1.setFont(new java.awt.Font("Castellar", 1, 24)); // NOI18N
        jLabel1.setText("<<<<<<<<<<WAR TIKET SERU>>>>>>>>>>");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(270, 0, 530, 40);

        jLabel2.setFont(new java.awt.Font("Castellar", 1, 24)); // NOI18N
        jLabel2.setText("<<<<<<<<<<Dapatkan Tiketnya dan Ikuti Konsernya>>>>>>>>>>");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(110, 750, 970, 29);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/konser.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 1080, 800);

        getContentPane().add(jPanel1, "card2");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButtonBack1.setBackground(new java.awt.Color(204, 204, 255));
        jButtonBack1.setFont(new java.awt.Font("Trajan Pro", 0, 16)); // NOI18N
        jButtonBack1.setText("KEMBALI");
        jButtonBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBack1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Castellar", 1, 24)); // NOI18N
        jLabel11.setText("INPUT DATA");

        jLabel12.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel12.setText("NAMA PEMBELI");

        jTextField4.setBackground(new java.awt.Color(204, 204, 255));
        jTextField4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel13.setText("ID_KONSER");

        jTextField5.setBackground(new java.awt.Color(204, 204, 255));
        jTextField5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel14.setText("TANGGAL KONSER");

        jTable3.setBackground(new java.awt.Color(204, 204, 255));
        jTable3.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No. ", "NAMA PEMBELI", "ID_PEMBELI"
            }
        ));
        jScrollPane6.setViewportView(jTable3);

        jButtonSimpan.setBackground(new java.awt.Color(204, 204, 255));
        jButtonSimpan.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        jButtonSimpan.setText("SIMPAN");
        jButtonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimpanActionPerformed(evt);
            }
        });

        jButtonUpdate.setBackground(new java.awt.Color(204, 204, 255));
        jButtonUpdate.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        jButtonUpdate.setText("UPDATE");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setBackground(new java.awt.Color(204, 204, 255));
        jButtonDelete.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonDelete.setText("DELETE");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonPilih.setBackground(new java.awt.Color(204, 204, 255));
        jButtonPilih.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        jButtonPilih.setText("Pilih");
        jButtonPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPilihActionPerformed(evt);
            }
        });

        jComboBox2.setBackground(new java.awt.Color(204, 204, 255));
        jComboBox2.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(204, 204, 255));
        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel15.setText("JUMLAH TIKET");

        jLabel16.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel16.setText("TOTAL HARGA");

        jSpinner1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        jSpinner1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jSpinner1KeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel17.setText("HARGA PER TIKET");

        jTextField1.setBackground(new java.awt.Color(204, 204, 255));
        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel18.setText("NAMA KONSER");

        jTextField2.setBackground(new java.awt.Color(204, 204, 255));
        jTextField2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jTextField3.setBackground(new java.awt.Color(204, 204, 255));
        jTextField3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "NAMA KONSER", "ID_KONSER", "TANGGAL KONSER", "JUMLAH TIKET", "HARGA PERTIKET", "TOTAL HARGA"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButtonEksport.setBackground(new java.awt.Color(204, 204, 255));
        jButtonEksport.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonEksport.setText("EKSPORT");
        jButtonEksport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEksportActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel5.setText("ID_PEMBELI");

        jTextField6.setBackground(new java.awt.Color(204, 204, 255));
        jTextField6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel11))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jButtonEksport))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel13))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel15))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButtonPilih))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel17))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButtonUpdate))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(jButtonSimpan))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonBack1)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButtonDelete))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel11)
                        .addGap(113, 113, 113)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(554, 554, 554)
                        .addComponent(jButtonEksport))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(jLabel13))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(344, 344, 344)
                        .addComponent(jLabel15))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jLabel14))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(557, 557, 557)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(444, 444, 444)
                        .addComponent(jLabel16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(557, 557, 557)
                        .addComponent(jButtonPilih))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(599, 599, 599)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(393, 393, 393)
                        .addComponent(jLabel17))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(486, 486, 486)
                        .addComponent(jButtonUpdate))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(441, 441, 441)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabel18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(486, 486, 486)
                        .addComponent(jButtonSimpan))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(344, 344, 344)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonBack1)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(518, 518, 518)
                        .addComponent(jButtonDelete)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));

        jLabel9.setFont(new java.awt.Font("Castellar", 1, 24)); // NOI18N
        jLabel9.setText("DAFTAR TIKET DAN HARGA");

        jButtonBlackPink.setBackground(new java.awt.Color(255, 255, 255));
        jButtonBlackPink.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonBlackPink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketblack.png"))); // NOI18N
        jButtonBlackPink.setText("<html><div align='center'>01. KONSER BLACKPINK<br>JAKARTA<br>Rp. 3.800.000</html> ");
        jButtonBlackPink.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonBlackPink.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonBlackPink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBlackPinkMouseClicked(evt);
            }
        });
        jButtonBlackPink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBlackPinkActionPerformed(evt);
            }
        });

        jButtonGDragon.setBackground(new java.awt.Color(255, 255, 255));
        jButtonGDragon.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonGDragon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketG.png"))); // NOI18N
        jButtonGDragon.setText("<html><div align='center'>02. KONSER G-DRAGON<br>JAKARTA<br>Rp. 3.850.000</div></html>");
        jButtonGDragon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGDragon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGDragon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGDragonMouseClicked(evt);
            }
        });

        jButtonJKT48.setBackground(new java.awt.Color(255, 255, 255));
        jButtonJKT48.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonJKT48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketJKT.png"))); // NOI18N
        jButtonJKT48.setText("<html><div align='center'>04. KONSER JKT48<br>ARENA JAKARTA<br>Rp. 4.800.000 </div></html>");
        jButtonJKT48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonJKT48.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonJKT48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonJKT48MouseClicked(evt);
            }
        });

        jButtonJHope.setBackground(new java.awt.Color(255, 255, 255));
        jButtonJHope.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonJHope.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketJ.png"))); // NOI18N
        jButtonJHope.setText("<html><div align='center'>03. KONSER J-HOPE<br>JAKARTA<br>Rp. 4.550.000</div></html>");
        jButtonJHope.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonJHope.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonJHope.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonJHopeMouseClicked(evt);
            }
        });
        jButtonJHope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJHopeActionPerformed(evt);
            }
        });

        jButtonSeventeen.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSeventeen.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonSeventeen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketseven.png"))); // NOI18N
        jButtonSeventeen.setText("<html><div align='center'>06. KONSER SEVENTEEN<br>JAKARTA<br>Rp. 3.800.000</div></html>");
        jButtonSeventeen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSeventeen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSeventeen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSeventeenMouseClicked(evt);
            }
        });

        jButtonNCTWISH.setBackground(new java.awt.Color(255, 255, 255));
        jButtonNCTWISH.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonNCTWISH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketnct.png"))); // NOI18N
        jButtonNCTWISH.setText("<html><div align='center'>05. KONSER NCTWISH<br>JAKARTA<br>Rp. 3.000.000</div></html>");
        jButtonNCTWISH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNCTWISH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNCTWISH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonNCTWISHMouseClicked(evt);
            }
        });
        jButtonNCTWISH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNCTWISHActionPerformed(evt);
            }
        });

        jButtonYoasobi.setBackground(new java.awt.Color(255, 255, 255));
        jButtonYoasobi.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonYoasobi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketY.png"))); // NOI18N
        jButtonYoasobi.setText("<html><div align='center'>08. KONSER YOASOBI<br>JAKARTA<br>Rp. 2.000.000</div></html>\"");
        jButtonYoasobi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonYoasobi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonYoasobi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonYoasobiMouseClicked(evt);
            }
        });

        jButtonTaeyon.setBackground(new java.awt.Color(255, 255, 255));
        jButtonTaeyon.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButtonTaeyon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/tiketT.png"))); // NOI18N
        jButtonTaeyon.setText("<html><div align='center'>07. KONSER TAEYON<br>JAKARTA<br>Rp. 3.100.000</div></html>");
        jButtonTaeyon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonTaeyon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonTaeyon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonTaeyonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonBlackPink)
                    .addComponent(jButtonJHope)
                    .addComponent(jButtonTaeyon)
                    .addComponent(jButtonNCTWISH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonGDragon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonSeventeen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonJKT48, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonYoasobi, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(76, 76, 76))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel9)
                .addGap(52, 52, 52)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBlackPink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGDragon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonJKT48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonJHope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSeventeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNCTWISH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonYoasobi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonTaeyon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel9);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, "card3");

        jPanel5.setLayout(null);

        jButtonBack2.setBackground(new java.awt.Color(204, 204, 255));
        jButtonBack2.setFont(new java.awt.Font("Trajan Pro", 0, 16)); // NOI18N
        jButtonBack2.setText("KEMBALI");
        jButtonBack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBack2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonBack2);
        jButtonBack2.setBounds(0, 0, 109, 25);

        jLabel10.setFont(new java.awt.Font("Castellar", 1, 36)); // NOI18N
        jLabel10.setText("DAFTAR PESERTA");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(353, 67, 370, 43);

        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO.", "NAMA PEMBELI", "ID_PEMBELI", "NAMA KONSER", "ID_KONSER", "TANGGAL KONSER", "JUMLAH TIKET", "HARGA PERTIKET", "Title 9"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jPanel5.add(jScrollPane4);
        jScrollPane4.setBounds(76, 154, 910, 320);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiketkonser/peserta.png"))); // NOI18N
        jPanel5.add(jLabel4);
        jLabel4.setBounds(0, 0, 1080, 800);

        getContentPane().add(jPanel5, "card4");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDaftarTiketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDaftarTiketActionPerformed
        
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");

    }//GEN-LAST:event_jButtonDaftarTiketActionPerformed

    private void jButtonBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBerandaActionPerformed
        refreshForm(); // kosongkan field
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "beranda");
        
    }//GEN-LAST:event_jButtonBerandaActionPerformed

    private void jButtonBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack1ActionPerformed

    CardLayout cl = (CardLayout) getContentPane().getLayout();
    cl.show(getContentPane(), "beranda");

    }//GEN-LAST:event_jButtonBack1ActionPerformed

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        simpanDataKeDuaTabel();
        isiComboBoxIdPembeli(); 
    }//GEN-LAST:event_jButtonSimpanActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButtonBack2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack2ActionPerformed

    CardLayout cl = (CardLayout) getContentPane().getLayout();
    cl.show(getContentPane(), "beranda");

    }//GEN-LAST:event_jButtonBack2ActionPerformed

    private void jButtonBlackPinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBlackPinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBlackPinkActionPerformed

    private void jButtonNCTWISHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNCTWISHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNCTWISHActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        
    String idUpdate = jComboBox2.getSelectedItem().toString();
    int index = cariBarisBerdasarkanId(idUpdate);
    if (index == -1) {
        JOptionPane.showMessageDialog(this, "ID tidak ditemukan!");
        return;
    }

    String namaPembeli = jTextField4.getText().trim();
    if (namaPembeli.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama pembeli wajib diisi!");
        return;
    }

    String idPembeli = jTextField6.getText();
    String namaKonser = jTextField2.getText();
    int idKonser = Integer.parseInt(jTextField5.getText());
    String tanggal = jComboBox1.getSelectedItem().toString();
    int jumlah = (int) jSpinner1.getValue();
    double hargaPerTiket = Double.parseDouble(jTextField1.getText().replace("Rp. ", "").replace(".", ""));
    double totalHarga = Double.parseDouble(jTextField3.getText().replace("Rp. ", "").replace(".", ""));

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE pembeli SET nama_pembeli=?, id_konser=?, jumlah_tiket=?, total_harga=? WHERE id_pembeli=?"
        );
        stmt.setString(1, namaPembeli);
        stmt.setInt(2, idKonser);
        stmt.setInt(3, jumlah);
        stmt.setDouble(4, totalHarga);
        stmt.setString(5, idPembeli);
        stmt.executeUpdate();

        // Update GUI
        DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
        model3.setValueAt(namaPembeli, index, 1);
        model3.setValueAt(idPembeli, index, 2);

        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        model1.setValueAt(namaKonser, index, 1);
        model1.setValueAt(idKonser, index, 2);
        model1.setValueAt(tanggal, index, 3);
        model1.setValueAt(jumlah, index, 4);
        model1.setValueAt("Rp. " + (int)hargaPerTiket, index, 5);
        model1.setValueAt("Rp. " + (int)totalHarga, index, 6);

        refreshForm();
        isiComboBoxIdPembeli();
        JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saat update: " + e.getMessage());
    }

    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonJHopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJHopeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonJHopeActionPerformed

    private void jButtonBlackPinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBlackPinkMouseClicked
         
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 1); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonBlackPinkMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jSpinner1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner1KeyReleased
         // Hanya izinkan angka
        String text = jTextField1.getText();
        if (!text.matches("\\d*")) {
        jTextField1.setText(text.replaceAll("[^\\d]", ""));
    }
    hitungTotal();
    }//GEN-LAST:event_jSpinner1KeyReleased

    private void jButtonGDragonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGDragonMouseClicked

   try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 2); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonGDragonMouseClicked

    private void jButtonJHopeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonJHopeMouseClicked

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 3); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }
    
    }//GEN-LAST:event_jButtonJHopeMouseClicked

    private void jButtonJKT48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonJKT48MouseClicked

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 4); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonJKT48MouseClicked

    private void jButtonNCTWISHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonNCTWISHMouseClicked
        
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 5); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }
    
    }//GEN-LAST:event_jButtonNCTWISHMouseClicked

    private void jButtonSeventeenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSeventeenMouseClicked

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 6); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonSeventeenMouseClicked

    private void jButtonTaeyonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTaeyonMouseClicked

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 7); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonTaeyonMouseClicked

    private void jButtonYoasobiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonYoasobiMouseClicked

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM konser WHERE id_konser = ?");
        stmt.setInt(1, 8); 
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            jTextField2.setText(rs.getString("nama_konser"));
            jTextField5.setText(String.valueOf(rs.getInt("id_konser")));
            jTextField1.setText("Rp. " + rs.getInt("harga")); // ← GUNAKAN getInt()
            jComboBox1.setSelectedItem(rs.getString("tanggal"));
            jTextField6.setText(generateIdPembeli());
            jTextField6.setEditable(false);
            hitungTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Data konser tidak ditemukan di database!");
            return;
        }
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_tiket");
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Gagal ambil data:\n" + e.getMessage());
    }

    }//GEN-LAST:event_jButtonYoasobiMouseClicked

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        hitungTotal();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jButtonDaftarPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDaftarPesertaActionPerformed
   
    CardLayout cl = (CardLayout) getContentPane().getLayout();
    cl.show(getContentPane(), "daftar_peserta");

    }//GEN-LAST:event_jButtonDaftarPesertaActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        
    String idHapus = jComboBox2.getSelectedItem().toString();
    int index = cariBarisBerdasarkanId(idHapus);
    if (index == -1) {
        JOptionPane.showMessageDialog(this, "ID tidak ditemukan!");
        return;
    }

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM pembeli WHERE id_pembeli=?");
        stmt.setString(1, idHapus);
        stmt.executeUpdate();

        // Hapus dari GUI
        DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        model3.removeRow(index);
        model1.removeRow(index);

        for (int i = 0; i < model3.getRowCount(); i++) {
            model3.setValueAt(i + 1, i, 0);
            model1.setValueAt(i + 1, i, 0);
        }

        refreshForm();
        isiComboBoxIdPembeli();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saat hapus: " + e.getMessage());
    }

    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        evt.consume();
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jButtonPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPilihActionPerformed
                                  
    pilihDataBerdasarkanId();
    }//GEN-LAST:event_jButtonPilihActionPerformed

    private void jButtonEksportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEksportActionPerformed

    try {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "❌ Gagal terhubung ke database!");
            return;
        }
        String sql = "SELECT p.nama_pembeli, p.id_pembeli, k.nama_konser, " +
                "k.id_konser, k.tanggal, p.jumlah_tiket, k.harga AS harga_pertiket, " +
                "p.total_harga FROM pembeli p JOIN konser k ON p.id_konser = k.id_konser " +
                "ORDER BY p.id_pembeli";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);
        int no = 1;
        while (rs.next()) {
            Object[] row = {
                no++,
                rs.getString("nama_pembeli"),
                rs.getString("id_pembeli"),
                rs.getString("nama_konser"),
                rs.getInt("id_konser"),
                rs.getDate("tanggal") != null ? rs.getDate("tanggal").toString() : "",
                rs.getInt("jumlah_tiket"),
                "Rp. " + rs.getInt("harga_pertiket"),
                "Rp. " + rs.getInt("total_harga")
            };
            model2.addRow(row);
        }

        // ✅ GUNAKAN CARDLAYOUT, BUKAN setVisible()
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "daftar_peserta");

        JOptionPane.showMessageDialog(this, "✅ Data berhasil diekspor dari database!");
        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error ekspor: " + e.getMessage());
    }

    }//GEN-LAST:event_jButtonEksportActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
  
    }//GEN-LAST:event_jComboBox1MouseClicked

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
            java.util.logging.Logger.getLogger(TabbedPaneGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TabbedPaneGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TabbedPaneGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TabbedPaneGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TabbedPaneGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack1;
    private javax.swing.JButton jButtonBack2;
    private javax.swing.JButton jButtonBeranda;
    private javax.swing.JButton jButtonBlackPink;
    private javax.swing.JButton jButtonDaftarPeserta;
    private javax.swing.JButton jButtonDaftarTiket;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEksport;
    private javax.swing.JButton jButtonGDragon;
    private javax.swing.JButton jButtonJHope;
    private javax.swing.JButton jButtonJKT48;
    private javax.swing.JButton jButtonNCTWISH;
    private javax.swing.JButton jButtonPilih;
    private javax.swing.JButton jButtonSeventeen;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JButton jButtonTaeyon;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JButton jButtonYoasobi;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}

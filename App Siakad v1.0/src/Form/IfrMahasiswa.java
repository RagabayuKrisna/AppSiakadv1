/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Tool.KoneksiDB;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


public class IfrMahasiswa extends javax.swing.JInternalFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    
    String vnim, vid_ta, vkd_prodi, vnama_mhs, vjk, vtmp_lahir, vtgl_lahir, vagama, vnama_ayah, vnama_ibu, valamat, vno_telpon, vfoto;
    
    File imageFile = null;
    
    private DefaultTableModel tblmahasiswa;
    SimpleDateFormat tglInput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglView = new SimpleDateFormat("dd-MM-yyyy");
    
    
    public IfrMahasiswa() {
        initComponents();
        
        listTA();
        listProdi();
        clearInput();
        SetTabelMahasiswa();
        showDataMahasiswa();
        jdInputMahasiswa.setSize(580,610);
        jdInputMahasiswa.setLocationRelativeTo(this);
    }
    
    private void clearInput(){
        txtNIM.setText("");
        cmbTA.setSelectedIndex(0);
        cmbProdi.setSelectedIndex(0);
        cmbJenisKel.setSelectedIndex(0);
        cmbAgama.setSelectedIndex(0);
        txtNamaMhs.setText("");
        txtTmpLahir.setText("");
        dtTglLahir.setDate(new Date());
        txtNamaAyah.setText("");
        txtNamaIbu.setText("");
        txtAlamat.setText("");
        txtNoTelpon.setText("");
        lbFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/no-picture.jpg")));
        imageFile = null;
        btnSimpan.setText("Simpan");
        vnim = "";
        
    }
    
    private void SetTabelMahasiswa(){
        String[] kolom1 = {"NIM","Nama Mahasiswa","L/P","Tempat","Tgl. Lahir","Program Studi","Alamat","No. Telepon",};
        tblmahasiswa = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columIndex){
                return types [columIndex];
            }
            
            public boolean isCellEditable(int row, int col){
                int cola = tblmahasiswa.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        
        tbData.setModel(tblmahasiswa);
        tbData.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbData.getColumnModel().getColumn(1).setPreferredWidth(250);
        tbData.getColumnModel().getColumn(2).setPreferredWidth(25);
        tbData.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbData.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbData.getColumnModel().getColumn(5).setPreferredWidth(150);
        tbData.getColumnModel().getColumn(6).setPreferredWidth(300);
        tbData.getColumnModel().getColumn(7).setPreferredWidth(150);
    }
    
    private void clearTabelMahasiswa(){
        int row = tblmahasiswa.getRowCount();
        for (int i = 0; i < row ;i++){
            tblmahasiswa.removeRow(0);
        }
    }
    
    private void showDataMahasiswa(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelMahasiswa();
            sqlselect = "select * from tbmahasiswa a, tbprodi b where a.kd_prodi = b.kd_prodi order by a.kd_prodi, a.nama_mhs asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnim = res.getString("nim");
                vnama_mhs = res.getString("nama_mhs");
                vjk = res.getString("jk");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = tglView.format(res.getDate("tgl_lahir"));
                vkd_prodi = res.getString("prodi");
                valamat = res.getString("alamat");
                vno_telpon = res.getString("no_telepon");
                Object[] data = {vnim, vnama_mhs, vjk, vtmp_lahir, vtgl_lahir, vkd_prodi, valamat, vno_telpon};
                tblmahasiswa.addRow(data);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method showDataMahasiswa(): " + ex);
        }
    }
    
    private void cariNamaMahasiswa(){
       try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelMahasiswa();
            sqlselect = "select * from tbmahasiswa a, tbprodi b where a.kd_prodi = b.kd_prodi and a.nama_mhs like '%"+txtCari.getText()+"&' order by a.kd_prodi, a.nama_mhs asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnim = res.getString("nim");
                vnama_mhs = res.getString("nama_mhs");
                vjk = res.getString("jk");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = tglView.format(res.getString("tgl_lahir"));
                vkd_prodi = res.getString("prodi");
                valamat = res.getString("alamat");
                vno_telpon = res.getString("no_telepon");
                Object[] data = {vnim, vnama_mhs, vjk, vtmp_lahir, vtgl_lahir, vkd_prodi, valamat, vno_telpon};
                tblmahasiswa.addRow(data);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method cariNamaMahasiswa(): " + ex);
        } 
    }
    
    private void getDataMahasiswa(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbmahasiswa a, tbprodi b, tbthangkatan c where a.kd_prodi = b.kd_prodi and a.id_ta = c.id_ta and a.nim = '"+vnim+"' ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.next()){
                cmbTA.setSelectedItem(res.getString("tahun_angkatan"));
                cmbProdi.setSelectedItem(res.getString("prodi"));
                txtNamaMhs.setText(res.getString("nama_mhs"));
                vjk = res.getString("jk");
                if(vjk.equals("L")){
                    cmbJenisKel.setSelectedItem("Laki-laki");
                    
                }else{
                    cmbJenisKel.setSelectedItem("Perempuan");
                    
                }
                cmbAgama.setSelectedItem(res.getString("agama"));
                txtTmpLahir.setText(res.getString("tmp_lahir"));
                dtTglLahir.setDate(res.getDate("tgl_lahir"));
                txtNamaAyah.setText(res.getString("nama_ayah"));
                txtNamaIbu.setText(res.getString("nama_ibu"));
                txtAlamat.setText(res.getString("alamat"));
                txtNoTelpon.setText(res.getString("no_telepon"));
                
                if(res.getBlob("foto").getBinaryStream() == null){
                    lbFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/no-picture.jpg")));
                }else{
                    InputStream is = res.getBlob("foto").getBinaryStream();
                    ImageIcon icon = new ImageIcon(resize(ImageIO.read(is),lbFoto.getWidth(), lbFoto.getHeight()));
                    lbFoto.setIcon(icon);
                }
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method getDataMahasiswa() : "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);      
        }catch(IOException ex){
            Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    String[] KeyTA;
    private void listTA(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbthangkatan order by id_ta asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbTA.removeAllItems();
            cmbTA.repaint();
            cmbTA.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbTA.addItem(res.getString(2));
                i++;
            }
            res.first();
            KeyTA = new String[i+1];
            for(Integer x = 1;x<i;x++){
                KeyTA[x] = res.getString(1);
                res.next();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listTA() : "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    String[] KeyProdi;
    private  void listProdi(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select kd_prodi, prodi from tbprodi order by kd_jur asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbProdi.removeAllItems();
            cmbProdi.repaint();
            cmbProdi.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbProdi.addItem(res.getString(2));
                i++;
            }
            res.first();
            KeyProdi = new String[i+1];
            for(Integer x = 1;x<i;x++){
                KeyProdi[x] = res.getString(1);
                res.next();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listTA() : "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void aksiSimpan(){
        vnim = txtNIM.getText();
        vid_ta = KeyTA[cmbTA.getSelectedIndex()];
        vkd_prodi = KeyProdi[cmbProdi.getSelectedIndex()];
        vnama_mhs = txtNamaMhs.getText();
        vagama = cmbAgama.getSelectedItem().toString();
        vtmp_lahir = txtTmpLahir.getText();
        vtgl_lahir = tglInput.format(dtTglLahir.getDate());
        vnama_ayah = txtNamaAyah.getText();
        vnama_ibu = txtNamaIbu.getText();
        valamat = txtAlamat.getText();
        vno_telpon = txtNoTelpon.getText();
        if(btnSimpan.getText().equals("Simpan")){
            if(imageFile == null){
                try{
                    _Cnn = null;
                    _Cnn = getCnn.getConnection();
                    sqlinsert = "insert into tbmahasiswa values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                    stat.setString(1, vnim);
                    stat.setString(2, vkd_prodi);
                    stat.setString(3, vid_ta);
                    stat.setString(4, vnama_mhs);
                    stat.setString(5, vjk);
                    stat.setString(6, vtmp_lahir);
                    stat.setString(7, vtgl_lahir);
                    stat.setString(8, vagama);
                    stat.setString(9, valamat);
                    stat.setString(10, vno_telpon);
                    stat.setString(11, vnama_ayah);
                    stat.setString(12, vnama_ibu);
                    stat.executeUpdate();
                    clearInput(); showDataMahasiswa();jdInputMahasiswa.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Data berhasi disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Error method aksiSimpan(): "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                try{
                    InputStream is = new FileInputStream(imageFile);
                    _Cnn = null;
                    _Cnn = getCnn.getConnection();
                    sqlinsert = "insert into tbmahasiswa values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                    stat.setString(1, vnim);
                    stat.setString(2, vkd_prodi);
                    stat.setString(3, vid_ta);
                    stat.setString(4, vnama_mhs);
                    stat.setString(5, vjk);
                    stat.setString(6, vtmp_lahir);
                    stat.setString(7, vtgl_lahir);
                    stat.setString(8, vagama);
                    stat.setString(9, valamat);
                    stat.setString(10, vno_telpon);
                    stat.setString(11, vnama_ayah);
                    stat.setString(12, vnama_ibu);
                    stat.setBlob(13, is);
                    stat.executeUpdate();
                    clearInput(); showDataMahasiswa();jdInputMahasiswa.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Data berhasi disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Error method aksiSimpan(): "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(FileNotFoundException ex){
                    Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }else{
            if(imageFile == null){
                try{
                    _Cnn = null;
                    _Cnn = getCnn.getConnection();
                    sqlinsert = "update tbmahasiswa set kd_prodi=?, nama_mhs=?, jns_kel=?, tmp_lahir=?, tgl_lahir=?, agama=?, nama_ayam=?, nama_ibu=?, alamat=?,no_telepon=? where nim='"+vnim+"' ";
                    PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                    stat.setString(1, vkd_prodi);
                    stat.setString(2, vnama_mhs);
                    stat.setString(3, vjk);
                    stat.setString(4, vtmp_lahir);
                    stat.setString(5, vtgl_lahir);
                    stat.setString(6, vagama);
                    stat.setString(7, valamat);
                    stat.setString(8, vno_telpon);
                    stat.setString(9, vnama_ayah);
                    stat.setString(10, vnama_ibu);
                    stat.executeUpdate();
                    clearInput(); showDataMahasiswa();jdInputMahasiswa.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Data berhasi dirubah", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Error method aksiSimpan(): "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                try{
                    InputStream is = new FileInputStream(imageFile);
                    _Cnn = null;
                    _Cnn = getCnn.getConnection();
                    sqlinsert = "update tbmahasiswa set kd_prodi=?, nama_mhs=?, jns_kel=?, tmp_lahir=?, tgl_lahir=?, agama=?, nama_ayam=?, nama_ibu=?, alamat=?,no_telepon=?, foto=? where nim='"+vnim+"' ";
                    PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                    stat.setString(1, vkd_prodi);
                    stat.setString(2, vnama_mhs);
                    stat.setString(3, vjk);
                    stat.setString(4, vtmp_lahir);
                    stat.setString(5, vtgl_lahir);
                    stat.setString(6, vagama);
                    stat.setString(7, valamat);
                    stat.setString(8, vno_telpon);
                    stat.setString(9, vnama_ayah);
                    stat.setString(10, vnama_ibu);
                    stat.setBlob(11, is);
                    stat.executeUpdate();
                    clearInput(); showDataMahasiswa();jdInputMahasiswa.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Data berhasi diubah", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Error method aksiSimpan(): "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }catch(FileNotFoundException ex){
                    Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
    }
    
    private void aksiHapus(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete="delete from tbmahasiswa where nim = '"+vnim+"' ";
            Statement stat = _Cnn.createStatement();
            stat.executeUpdate(sqldelete);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus(): "+ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static BufferedImage loadImage(String ref){
        BufferedImage bimg = null;
        try{
            bimg = ImageIO.read(new File(ref));
        }catch(Exception e){
            
        }
        return bimg;
    }
    
    public static BufferedImage resize(BufferedImage img, int newW,int newH){
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img,0,0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
        
    }

    private void ambilGambar(){
        javax.swing.JFileChooser jfc = new JFileChooser();
        FileFilter jpgFilter, gifFilter, bothFilter;
        jpgFilter = new FileNameExtensionFilter("Gambar JPEG", "jpg");
        gifFilter = new FileNameExtensionFilter("Gambar GIF", "gif");
        bothFilter = new FileNameExtensionFilter("Gambar JPEG dan GIF", "jpg","gif");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(jpgFilter);
        jfc.addChoosableFileFilter(gifFilter);
        jfc.addChoosableFileFilter(bothFilter);
        if(jfc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            vfoto = jfc.getSelectedFile().toString();
            imageFile = jfc.getSelectedFile();
            BufferedImage loadImg = loadImage(vfoto);
            ImageIcon imageIcon = new ImageIcon(resize(loadImg,lbFoto.getWidth(), lbFoto.getHeight()));
            lbFoto.setIcon(imageIcon);
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

        jdInputMahasiswa = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbFoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        cmbTA = new javax.swing.JComboBox<>();
        cmbProdi = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        txtNIM = new javax.swing.JTextField();
        txtNamaMhs = new javax.swing.JTextField();
        cmbJenisKel = new javax.swing.JComboBox<>();
        cmbAgama = new javax.swing.JComboBox<>();
        txtTmpLahir = new javax.swing.JTextField();
        dtTglLahir = new com.toedter.calendar.JDateChooser();
        txtNamaAyah = new javax.swing.JTextField();
        txtNamaIbu = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JEditorPane();
        txtNoTelpon = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbData = new javax.swing.JTable();
        lbRecord = new javax.swing.JLabel();

        jdInputMahasiswa.setTitle(".: Form Entry Mahasiswa");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Form Entry Mahasiswa");

        jLabel5.setText("Form ini digunakan untuk melakukan input data mahasiswa");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Foto :"));
        jPanel2.setOpaque(false);

        lbFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/no-picture.jpg"))); // NOI18N
        lbFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbFotoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFoto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel3.setOpaque(false);

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnBatal.setText("Hapus");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        cmbTA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbTA.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tahun Angkatan :"));
        cmbTA.setOpaque(false);

        cmbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Program Studi :"));
        cmbProdi.setOpaque(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data"));
        jPanel4.setOpaque(false);

        txtNIM.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "NIM :"));
        txtNIM.setOpaque(false);

        txtNamaMhs.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Mahasiswa :"));
        txtNamaMhs.setOpaque(false);

        cmbJenisKel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Laki-laki", "Perempuan" }));
        cmbJenisKel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Jenis Kelamin :"));
        cmbJenisKel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisKelItemStateChanged(evt);
            }
        });

        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Islam", "Hindu", "Budha", "Kristen", "Katolik", "Konghucu" }));
        cmbAgama.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Agama :"));

        txtTmpLahir.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tempat :"));
        txtTmpLahir.setOpaque(false);

        dtTglLahir.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tanggal Lahir"));

        txtNamaAyah.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Ayah :"));
        txtNamaAyah.setOpaque(false);

        txtNamaIbu.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Ibu :"));
        txtNamaIbu.setOpaque(false);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Alamat :"));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setOpaque(false);
        jScrollPane2.setViewportView(txtAlamat);

        txtNoTelpon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "No. Telepon :"));
        txtNoTelpon.setOpaque(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaMhs))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNamaAyah)
                            .addComponent(txtTmpLahir, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbJenisKel, javax.swing.GroupLayout.Alignment.LEADING, 0, 153, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dtTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                .addComponent(cmbAgama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtNamaIbu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtNoTelpon))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbJenisKel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtTmpLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaAyah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaIbu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdInputMahasiswaLayout = new javax.swing.GroupLayout(jdInputMahasiswa.getContentPane());
        jdInputMahasiswa.getContentPane().setLayout(jdInputMahasiswaLayout);
        jdInputMahasiswaLayout.setHorizontalGroup(
            jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                                .addComponent(cmbTA, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbProdi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jdInputMahasiswaLayout.setVerticalGroup(
            jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle(".: Form Mahasiswa");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel2.setText("Cari Nama");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(5, 5, 5))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Table Mahasiswa : Klik 2x untuk mengubah/menghapus data"));

        tbData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NIM", "Nama Mahasiswa", "L/P", "Tempat Lahir", "Tgl. Lahir", "Alamat", "No.Telepon"
            }
        ));
        tbData.setRowHeight(25);
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbData);

        lbRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRecord.setText("Record : 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRecord)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        clearInput();
        jdInputMahasiswa.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearInput();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(vnim.equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memlih data yang akan dihapus","Inforamasi",JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
            showDataMahasiswa();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        cariNamaMahasiswa();
    }//GEN-LAST:event_txtCariActionPerformed

    private void tbDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseClicked
        if(evt.getClickCount()==1){
            btnHapus.setEnabled(true);
            vnim=tbData.getValueAt(tbData.getSelectedRow(), 0).toString();
            txtNIM.setText(vnim);
        }else if(evt.getClickCount()==2){
            btnSimpan.setText("Ubah");
            jdInputMahasiswa.setVisible(true); jdInputMahasiswa.setLocationRelativeTo(this);
            getDataMahasiswa();
        }
    }//GEN-LAST:event_tbDataMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtNIM.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Data NIM harus diisi", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
            jdInputMahasiswa.setVisible(false);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void lbFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFotoMouseClicked
        ambilGambar();
    }//GEN-LAST:event_lbFotoMouseClicked

    private void cmbJenisKelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisKelItemStateChanged
        if(cmbJenisKel.getSelectedItem().equals("Laki-laki")){
            vjk="L";
        }else{
            vjk="P";
        }
    }//GEN-LAST:event_cmbJenisKelItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbAgama;
    private javax.swing.JComboBox<String> cmbJenisKel;
    private javax.swing.JComboBox<String> cmbProdi;
    private javax.swing.JComboBox<String> cmbTA;
    private com.toedter.calendar.JDateChooser dtTglLahir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JDialog jdInputMahasiswa;
    private javax.swing.JLabel lbFoto;
    private javax.swing.JLabel lbRecord;
    private javax.swing.JTable tbData;
    private javax.swing.JEditorPane txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNamaAyah;
    private javax.swing.JTextField txtNamaIbu;
    private javax.swing.JTextField txtNamaMhs;
    private javax.swing.JTextField txtNoTelpon;
    private javax.swing.JTextField txtTmpLahir;
    // End of variables declaration//GEN-END:variables
}

package Form;

import Tool.KoneksiDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrJurusan extends javax.swing.JInternalFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    String sqlselect, sqlinsert, sqldelete;
    String vkd_jur, vjurursan;
    private DefaultTableModel tbjurusan; 
    
    
    
    public IfrJurusan() {
        initComponents();
        
        formTengah();
        clearForm();
        disableInput();
        setTabelJurusan();
        showDataJurusan();
    }
    
    private void clearForm(){
        txtKdJur.setText("");
        txtJurusan.setText("");
        btnSimpan.setText("Simpan");
    }
    
    private void disableInput(){
        txtKdJur.setEnabled(false);
        txtJurusan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        txtKdJur.setEnabled(true);
        txtJurusan.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelJurusan(){
        String[] kolom1 = {"KD. Jur", "Jurusan"};
        tbjurusan = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int row, int col){
                int cola = tbjurusan.getColumnCount();
                return (col < cola)? false : true;
            }
        };
        tblDataJurusan.setModel(tbjurusan);
        tblDataJurusan.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDataJurusan.getColumnModel().getColumn(1).setPreferredWidth(250);
    }
    
    private void clearTabelJurusan(){
        int row = tbjurusan.getRowCount();
        for(int i=0; i<row ; i++){
            tbjurusan.removeRow(0);
        }
    }
    
    private void showDataJurusan(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelJurusan();
            sqlselect = "select * from tbjurusan order by kd_jur asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vkd_jur = res.getString("kd_jur");
                vjurursan = res.getString("jurusan");
                
                Object[] data = {vkd_jur, vjurursan};
                tbjurusan.addRow(data);
            }
            lbRecord.setText("Record : "+tblDataJurusan.getRowCount());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method showDataJurusan() : " +ex);
        }
    }
    
    private void aksiSimpan(){
        vkd_jur = txtKdJur.getText();
        vjurursan = txtJurusan.getText();
        
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbjurusan values ('"+vkd_jur+"', '"+vjurursan+"') ";
        }else{
            sqlinsert = "update tbjurusan set jurusan = '"+vjurursan+"' where kd_jur = '"+vkd_jur+"'";
        }
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement stat = _Cnn.createStatement();
            stat.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            showDataJurusan(); clearForm(); disableInput();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiSimpan(): "+ex);
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus data ini? Kode : "+vkd_jur,"Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbjurusan where kd_jur = '"+vkd_jur+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                showDataJurusan(); clearForm(); disableInput();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : " +ex);
            }
        }
    }
    
    private void formTengah(){
        Dimension screesize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = this.getSize();
        if(framesize.height < screesize.height){
            framesize.height = screesize.height;
        }
        if(framesize.width > screesize.width){
            framesize.width = screesize.width;
        }
        this.setLocation((screesize.width - framesize.width)/2,(screesize.height - framesize.height)/2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtKdJur = new javax.swing.JTextField();
        txtJurusan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataJurusan = new javax.swing.JTable();
        lbRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle(".: Form Jurusan");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data"));

        txtKdJur.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID. Jurusan :"));
        txtKdJur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdJurActionPerformed(evt);
            }
        });

        txtJurusan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jurusan :"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtKdJur, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtJurusan)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKdJur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Table Data Jurusan : Klik 2x untuk mengubah/menghapus data"));

        tblDataJurusan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Kode Jurusan", "Jurusan"
            }
        ));
        tblDataJurusan.setRowHeight(23);
        tblDataJurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataJurusanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataJurusan);

        lbRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRecord.setText("Record : 0");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Form Jurusan");

        jLabel4.setText("Form ini digunakan untuk mengolah data jurusan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbRecord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRecord)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        enableInput();
        txtKdJur.requestFocus(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtKdJurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdJurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdJurActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtKdJur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode jurursan harus diisi", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(txtJurusan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Jurusan harus diisi", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(txtKdJur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataJurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataJurusanMouseClicked
        if(evt.getClickCount()==2){
            int brs = tblDataJurusan.getSelectedRow();
            vkd_jur = tblDataJurusan.getValueAt(brs, 0).toString();
            vjurursan = tblDataJurusan.getValueAt(brs, 1).toString();
            
            txtKdJur.setText(vkd_jur);
            txtJurusan.setText(vjurursan);
            enableInput();
            btnHapus.setEnabled(true);
            txtKdJur.setEnabled(false);
            btnSimpan.setText("ubah");
            txtJurusan.requestFocus(true);
        }
    }//GEN-LAST:event_tblDataJurusanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbRecord;
    private javax.swing.JTable tblDataJurusan;
    private javax.swing.JTextField txtJurusan;
    private javax.swing.JTextField txtKdJur;
    // End of variables declaration//GEN-END:variables
}

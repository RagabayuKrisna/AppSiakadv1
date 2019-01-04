
package Form;

import Tool.KoneksiDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrTahunAngkatan extends javax.swing.JInternalFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    String sqlselect, sqlinsert, sqldelet;
    private DefaultTableModel tblthangkatan;
    String vid_ta, vtahun_angkatan;
    
    public IfrTahunAngkatan() {
        initComponents();
        formTengah();
        disableForm();
        setTabel();
        showTabel();
    }

    private void clearForm(){
        txtIdTA.setText("[ AUTO ]");
        txtThAngkatan.setText("");
        btnSimpan.setText("Simpan");
    }
    
    private void disableForm(){
        txtIdTA.setEnabled(false);
        txtThAngkatan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableForm(){
        txtIdTA.setEnabled(true);
        txtThAngkatan.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabel(){
        String[] kolom1 = {"Kode TA", "Tahun Angkatan"};
        tblthangkatan = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getcolumClass(int columnIndex){
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int row, int col){
                int cola = tblthangkatan.getColumnCount();
                return (col < cola)?false : true;
            }
        };
        tbThAngkatan.setModel(tblthangkatan);
        tbThAngkatan.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbThAngkatan.getColumnModel().getColumn(1).setPreferredWidth(250);
    }
    
    private void clearTabel(){
        int row = tblthangkatan.getRowCount();
        for(int i = 0; i < row; i++){
            tblthangkatan.removeRow(0);
        }
    }
    
    private void showTabel(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect = "select * from tbthangkatan order by id_ta asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_ta = res.getString(1);
                vtahun_angkatan = res.getString(2);
                Object[] data = {vid_ta, vtahun_angkatan};
                tblthangkatan.addRow(data);
            }
            lbRecord.setText("Record : " + tbThAngkatan.getRowCount());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method showDataTabel(): "+ex);
        }
    }
    
    private void aksiSimpan(){
        vtahun_angkatan = txtThAngkatan.getText();
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbthangkatan values ('"+vid_ta+"', '"+vtahun_angkatan+"')";
        }else{
            sqlinsert = "update tbthangkatan set tahun_angkatan='"+vtahun_angkatan+"' where id_ta='"+vid_ta+"'";
        }
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data berhasi disimpan");
            clearForm();disableForm();showTabel();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID.TA : "+vid_ta, 
                "Konfirmasi", JOptionPane.YES_NO_CANCEL_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelet = "delete from tbthangkatan where id_ta='"+vid_ta+"'";
                Statement state = _Cnn.createStatement();
                state.executeUpdate(sqldelet);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                clearForm();disableForm();showTabel();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
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
    
    private void createAutoID(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select max(id_ta) from tbthangkatan";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                Integer no = res.getInt(1)+1;
                vid_ta = no.toString();
                txtIdTA.setText(vid_ta);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error method createAutoID():"+ex,"Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtIdTA = new javax.swing.JTextField();
        txtThAngkatan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbThAngkatan = new javax.swing.JTable();
        lbRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle(".: Form Tahun Angkatan");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data"));

        txtIdTA.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID. TA :"));
        txtIdTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdTAActionPerformed(evt);
            }
        });

        txtThAngkatan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tahun Angkatan :"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIdTA, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThAngkatan)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Table Data Tahun Angkatan : Klik 2x untuk mengubah/menghapus data"));

        tbThAngkatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Kode TA", "Tahun Angkatan"
            }
        ));
        tbThAngkatan.setRowHeight(23);
        tbThAngkatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbThAngkatanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbThAngkatan);

        lbRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRecord.setText("Record : 0");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Form Tahun Angkatan");

        jLabel4.setText("Form ini digunakan untuk mengolah data tajun angkatan");

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
        enableForm();
        createAutoID();
        txtThAngkatan.requestFocus(true);
                
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtIdTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdTAActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtThAngkatan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Tahun angkatan belum diisi","Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(txtIdTA.getText().equals("") || txtIdTA.getText().equals("[ AUTO ]")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbThAngkatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbThAngkatanMouseClicked
        if(evt.getClickCount()==2){
            int brs = tbThAngkatan.getSelectedRow();
            vid_ta = tbThAngkatan.getValueAt(brs, 0).toString();
            vtahun_angkatan = tbThAngkatan.getValueAt(brs, 1).toString();
            txtIdTA.setText(vid_ta);txtThAngkatan.setText(vtahun_angkatan);
            enableForm();
            btnHapus.setEnabled(true);
            btnSimpan.setText("Ubah");
        }
    }//GEN-LAST:event_tbThAngkatanMouseClicked


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
    private javax.swing.JTable tbThAngkatan;
    private javax.swing.JTextField txtIdTA;
    private javax.swing.JTextField txtThAngkatan;
    // End of variables declaration//GEN-END:variables
}

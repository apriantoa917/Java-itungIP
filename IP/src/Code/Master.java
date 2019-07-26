package Code;

import Connection.Config;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Master extends javax.swing.JFrame {

    public Master() {
        initComponents();
        reset();
    }

    Connection.Config conn = new Config();
    ResultSet res;
    Image icon;
    /*NOTES
    
    
     */
    //deklarasi variabel
    String SQL, mata_kuliah, huruf;
    int semester, status_praktikun = 0, sks, puts, puas, ptugas, pmodul, index, total_sks;
    double uts, kuts, uas, kuas, tugas, ktugas, modul, kmodul, kumulasi, bobot, nilai_akhir, total_bobot, total_nilai_akhir, ips;
    boolean lanjut = false;

    ArrayList<Model> ListNilai = new ArrayList<>();
    ArrayList<Model> ListIP = new ArrayList<>();
    ArrayList ListSmt = new ArrayList();
    ArrayList ListSmtNilai;

    DefaultTableModel tbMN;
    DefaultTableModel tbMI;

    //mengecek data yang diinput ada kesalahan atau tidak
    boolean inputData() {
        //semester
        if (cb_semester.getSelectedIndex() == 0) { //gagal (item = - )
            JOptionPane.showMessageDialog(this, "Semester harus dipilih (antara 1 - 8)");
            lanjut = false;
        } else {
            semester = Integer.parseInt(cb_semester.getSelectedItem().toString());
            lanjut = true;
        }

        //matakuliah
        if (lanjut == true) {
            if (tf_mata_kuliah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mata Kuliah harus diisi");
                lanjut = false;
            } else {
                mata_kuliah = tf_mata_kuliah.getText();
                if (getMatakul(mata_kuliah) == false) {
                    JOptionPane.showMessageDialog(this, "Mata Kuliah sudah ada");
                    tf_mata_kuliah.setText("");
                    lanjut = false;
                } else {
                    lanjut = true;
                }
            }
        }

        //sks
        if (lanjut == true) {
            if (tf_sks.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "SKS tidak boleh kosong");
                lanjut = false;
            } else {
                try {
                    sks = Integer.parseInt(tf_sks.getText());
                    if (sks < 1 || sks > 4) {
                        JOptionPane.showMessageDialog(this, "SKS antara 1 - 4");
                        tf_sks.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "SKS harus berupa angka");
                    tf_sks.setText("");
                    lanjut = false;
                }

            }
        }

        //status praktikum
        if (lanjut == true) {
            if (rd_prak_0.isSelected()) {
                status_praktikun = 0;
                lanjut = true;
            } else if (rd_prak_1.isSelected()) {
                status_praktikun = 1;
                lanjut = true;
            } else {
                JOptionPane.showMessageDialog(this, "Praktikum harus dipilih");
                lanjut = false;
            }
        }

        //uts
        if (lanjut == true) {
            if (tf_uts.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "UTS harus diisi");
                lanjut = false;
            } else {
                try {
                    uts = Double.parseDouble(tf_uts.getText());
                    if (uts < 0 || uts > 100) {
                        JOptionPane.showMessageDialog(this, "UTS antara 0 - 100");
                        tf_uts.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "UTS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //puts
        if (lanjut == true) {
            if (tf_puts.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase UTS harus diisi");
                tf_puts.setText("");
                lanjut = false;
            } else {
                try {
                    puts = Integer.parseInt(tf_puts.getText());
                    if (puts < 10 || puts > 50) {
                        JOptionPane.showMessageDialog(this, "persentase UTS antara 10-50");
                        tf_puts.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase UTS harus berupa angka");
                    lanjut = false;
                }

            }
        }

        //uas
        if (lanjut == true) {
            if (tf_uas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "UAS harus diisi");
                lanjut = false;
            } else {
                try {
                    uas = Double.parseDouble(tf_uas.getText());
                    if (uas < 0 || uas > 100) {
                        JOptionPane.showMessageDialog(this, "UAS antara 0 - 100");
                        tf_uas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "UAS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //puas
        if (lanjut == true) {
            if (tf_puas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase UAS harus diisi");
                tf_puas.setText("");
                lanjut = false;
            } else {
                try {
                    puas = Integer.parseInt(tf_puas.getText());
                    if (puas < 10 || puas > 50) {
                        JOptionPane.showMessageDialog(this, "persentase UAS antara 10-50");
                        tf_puas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase UAS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //tugas
        if (lanjut == true) {
            if (tf_tugas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tugas harus diisi");
                lanjut = false;
            } else {
                try {
                    tugas = Double.parseDouble(tf_tugas.getText());
                    if (tugas < 0 || tugas > 100) {
                        JOptionPane.showMessageDialog(this, "Tugas antara 0 - 100");
                        tf_tugas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Tugas harus berupa angka");
                    lanjut = false;
                }

            }
        }

        //ptugas
        if (lanjut == true) {
            if (tf_ptugas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase Tugas harus diisi");
                tf_ptugas.setText("");
                lanjut = false;
            } else {
                try {
                    ptugas = Integer.parseInt(tf_ptugas.getText());
                    if (ptugas < 10 || ptugas > 50) {
                        JOptionPane.showMessageDialog(this, "persentase Tugas antara 10-50");
                        tf_ptugas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase Tugas harus berupa angka");
                    lanjut = false;
                }
            }
        }

        if (status_praktikun == 1) {  //untuk matkul praktikum
            //modul
            if (lanjut == true) {
                if (tf_modul.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Modul harus diisi");
                    lanjut = false;
                } else {
                    try {
                        modul = Double.parseDouble(tf_modul.getText());
                        if (modul < 0 || modul > 100) {
                            JOptionPane.showMessageDialog(this, "Modul antara 0 - 100");
                            tf_modul.setText("");
                            lanjut = false;
                        } else {
                            lanjut = true;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Modul harus berupa angka");
                        lanjut = false;
                    }
                }
            }

            //pmodul
            if (lanjut == true) {
                if (tf_pmodul.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Presentase Modul harus diisi");
                    lanjut = false;
                } else {
                    try {
                        pmodul = Integer.parseInt(tf_pmodul.getText());
                        if (pmodul < 1 || pmodul > 100) {
                            JOptionPane.showMessageDialog(this, "Presentase Modul antara 10 - 50");
                            tf_pmodul.setText("");
                            lanjut = false;
                        } else {
                            lanjut = true;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "presentase Modul harus berupa angka");
                        lanjut = false;
                    }
                }
            }
        }

        //perhitungan nilai
        if (lanjut == true) {

            kuts = (puts * uts) / 100;
            kuts = (double) Math.round(kuts * 100) / 100;
            kuas = (puas * uas) / 100;
            kuas = (double) Math.round(kuas * 100) / 100;
            ktugas = (ptugas * tugas) / 100;
            ktugas = (double) Math.round(ktugas * 100) / 100;
            kmodul = (pmodul * modul) / 100;
            kmodul = (double) Math.round(kmodul * 100) / 100;

            if (status_praktikun == 0) { //non praktikum
                modul = 0.0;
                pmodul = 0;
                kmodul = 0.0;
                kumulasi = kuts + kuas + ktugas;
            } else { //praktikum
                kumulasi = kuts + kuas + ktugas + kmodul;
            }
            kumulasi = (double) Math.round(kumulasi * 100) / 100;
            huruf = NilaiHuruf(kumulasi);
            bobot = NilaiBobot(kumulasi);
            nilai_akhir = sks * bobot;
            nilai_akhir = (double) Math.round(nilai_akhir * 100) / 100;

            //dump data
            System.out.println("Mata Kuliah : " + mata_kuliah);
            System.out.println("SKS : " + sks);
            System.out.println("UTS : " + uts + " Puts : " + puts + " Kuts : " + kuts);
            System.out.println("UTS : " + uas + " Puts : " + puas + " Kuts : " + kuas);
            System.out.println("Tugas : " + tugas + " PTugas : " + tugas + " Ktugas : " + ktugas);
            System.out.println("Kumulasi : " + kumulasi);
            System.out.println("Huruf : " + NilaiHuruf(kumulasi));
            System.out.println("Bobot : " + NilaiBobot(kumulasi));
            System.out.println("Nilai akhir : " + nilai_akhir + "\n");

            SQL = "insert into tabel_nilai values('" + semester + "','" + status_praktikun + "','" + mata_kuliah + "','" + sks + "','" + uts + "','" + puts + "','" + kuts + "','" + uas + "','" + puas + "','" + kuas + "','" + tugas + "','" + ptugas + "','" + ktugas + "','" + modul + "','" + pmodul + "','" + kmodul + "','" + kumulasi + "','" + huruf + "','" + bobot + "','" + nilai_akhir + "')";
            System.out.println("SQL : " + SQL);

            try {
                conn.executeUpdate(SQL);
                System.out.println("berhasil input (inputnilai()");
                JOptionPane.showMessageDialog(this, "Data berhasil di input");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal di input");
            }
        }
        return lanjut; //boolean untuk memastikan data jika benar lanjut reset field, jika salah tetap tampil data
    }

    //enable field modul dan pmodul untuk pilihan matkul praktikum
    void EnablePrak() {
        label_modul.setEnabled(true);
        label_pmodul.setEnabled(true);
        tf_modul.setEnabled(true);
        tf_pmodul.setEnabled(true);
    }

    //disable field modul dan pmodul untuk pilihan matkul non-praktikum
    void DisablePrak() {
        label_modul.setEnabled(false);
        label_pmodul.setEnabled(false);
        tf_modul.setEnabled(false);
        tf_pmodul.setEnabled(false);
    }

    void disableButton() {
        btn_hapus.setEnabled(false);
        btn_ubah.setEnabled(false);
    }

    void enableButton() {
        btn_hapus.setEnabled(true);
        btn_ubah.setEnabled(true);
    }

    //meneyembunyikan button aksi ubah
    void hideUbah() {
        btn_batal.setVisible(false);
        btn_ubahData.setVisible(false);
    }

    //menampilkan button aksi ubah
    void showUbah() {
        btn_batal.setVisible(true);
        btn_ubahData.setVisible(true);
    }

    //set nilai default untuk puts, puas,ptugas dan pmodul
    void DefaultValue(int status_praktikun) {
        tf_puts.setText("30");
        tf_puas.setText("30");
        if (status_praktikun == 0) {
            tf_ptugas.setText("40");
        } else {
            tf_ptugas.setText("20");
            tf_pmodul.setText("20");
            tf_sks.setText("1");
        }
    }

    //hapus data field 
    void reset() {
        cb_semester.setSelectedIndex(0);
        tf_mata_kuliah.setText("");
        tf_sks.setText("");
        bg_status_prak.clearSelection();
        tf_uts.setText("");
        tf_puts.setText("");
        tf_uas.setText("");
        tf_puas.setText("");
        tf_tugas.setText("");
        tf_ptugas.setText("");
        tf_modul.setText("");
        tf_pmodul.setText("");
        tf_mata_kuliah.setEditable(true);
        hideUbah();
        DefaultValue(0);
        setTabelNilai();
        setTabelIP();
        disableButton();
        DisablePrak();
        enableSimpan();
    }

    void disableSimpan() { //disable btn simpan ketika tekan ubah
        btn_simpan.setEnabled(false);
    }

    void enableSimpan() {
        btn_simpan.setEnabled(true);
    }

    String NilaiHuruf(Double kumulasi) {
        String huruf = "E"; //default nilai huruf
        if (kumulasi < 40) {
            huruf = "E";
        } else if (kumulasi < 55) {
            huruf = "D";
        } else if (kumulasi < 60) {
            huruf = "C";
        } else if (kumulasi < 65) {
            huruf = "C+";
        } else if (kumulasi < 75) {
            huruf = "B";
        } else if (kumulasi < 80) {
            huruf = "B+";
        } else {
            huruf = "A";
        }
        return huruf;
    }

    Double NilaiBobot(Double kumulasi) {
        double bobot = 0;
        if (kumulasi < 40) {
            bobot = 0;
        } else if (kumulasi < 55) {
            bobot = 1.0;
        } else if (kumulasi < 60) {
            bobot = 2.0;
        } else if (kumulasi < 65) {
            bobot = 2.5;
        } else if (kumulasi < 75) {
            bobot = 3.0;
        } else if (kumulasi < 80) {
            bobot = 3.5;
        } else {
            bobot = 4.0;
        }
        return bobot;
    }

    //mencocokan matkul yang diinput agar != matkul database (unique)
    boolean getMatakul(String mata_kuliah) { //untuk cek input mata_kuliah sama dengan db
        boolean hasil = true, output = false;
        SQL = "select mata_kuliah from tabel_nilai";
        res = Config.executeQuery(SQL);
        try {
            ArrayList banding = new ArrayList();
            while (res.next()) { //mendapatkan data mata kuliah tersimpan
                banding.add(res.getString(1));
            }
            for (int i = 0; i < banding.size() && hasil == true; i++) { //hasil true = berbeda
                if (banding.get(i).equals(mata_kuliah)) { //perbandingan mata kuliah input dengan db
                    hasil = false;
                } else {
                    hasil = true;
                }
            }
            if (hasil == true) {
                output = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    //menset table sesuai db
    void setTabelNilai() {
        ListNilai.removeAll(ListNilai);
        String nama_kolom[] = {"SMT", "Mata Kuliah", "SKS", "UTS", "UAS", "Tugas", "Modul", "Kumulasi", "Huruf"};
        ListNilai = getTabelNilai();

        Object isi_tabel[][] = new Object[ListNilai.size()][9];

        for (int i = 0; i < ListNilai.size(); i++) {
            isi_tabel[i][0] = ListNilai.get(i).getSemester();
            isi_tabel[i][1] = ListNilai.get(i).getMata_kuliah();
            isi_tabel[i][2] = ListNilai.get(i).getSks();
            isi_tabel[i][3] = ListNilai.get(i).getUts();
            isi_tabel[i][4] = ListNilai.get(i).getUas();
            isi_tabel[i][5] = ListNilai.get(i).getTugas();
            isi_tabel[i][6] = ListNilai.get(i).getModul();
            isi_tabel[i][7] = ListNilai.get(i).getKumulasi();
            isi_tabel[i][8] = ListNilai.get(i).getHuruf();
        }

        tbMN = new DefaultTableModel(isi_tabel, nama_kolom) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        tabel_nilai.setModel(tbMN);
        TableColumn column;
        column = tabel_nilai.getColumnModel().getColumn(1);
        column.setPreferredWidth(300);
        column.setMaxWidth(350);
    }

    //memasukan data kedalam arraylist
    ArrayList<Model> getTabelNilai() {
        SQL = "select * from tabel_nilai order by semester, mata_kuliah";

        res = conn.executeQuery(SQL);

        try {
            Model m = new Model(semester, status_praktikun, sks, puts, puas, ptugas, pmodul, uts, kuts, uas, kuas, tugas, ktugas, modul, kmodul, kumulasi, bobot, nilai_akhir, mata_kuliah, huruf);

            while (res.next()) {
                semester = Integer.parseInt(res.getString(1));
                status_praktikun = Integer.parseInt(res.getString(2));
                mata_kuliah = res.getString(3);
                sks = Integer.parseInt(res.getString(4));

                uts = Double.parseDouble(res.getString(5));
                puts = Integer.parseInt(res.getString(6));
                kuts = Double.parseDouble(res.getString(7));

                uas = Double.parseDouble(res.getString(8));
                puas = Integer.parseInt(res.getString(9));
                kuas = Double.parseDouble(res.getString(10));

                tugas = Double.parseDouble(res.getString(11));
                ptugas = Integer.parseInt(res.getString(12));
                ktugas = Double.parseDouble(res.getString(13));

                modul = Double.parseDouble(res.getString(14));
                pmodul = Integer.parseInt(res.getString(15));
                kmodul = Double.parseDouble(res.getString(16));

                kumulasi = Double.parseDouble(res.getString(17));
                kumulasi = (double) Math.round(kumulasi * 100) / 100;
                huruf = res.getString(18);
                bobot = Double.parseDouble(res.getString(19));
                nilai_akhir = Double.parseDouble(res.getString(20));
                nilai_akhir = (double) Math.round(nilai_akhir * 100) / 100;

                m = new Model(semester, status_praktikun, sks, puts, puas, ptugas, pmodul, uts, kuts, uas, kuas, tugas, ktugas, modul, kmodul, kumulasi, bobot, nilai_akhir, mata_kuliah, huruf);
                ListNilai.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tereksekusi getNilai()");
        return ListNilai;
    }

    void getIPK() {
        Double IPK = 0.0;
        for (int i = 0; i < tabel_ip.getRowCount(); i++) {
            IPK = IPK + Double.parseDouble(tabel_ip.getValueAt(i, 2).toString());
        }
        IPK = IPK / tabel_ip.getRowCount();
        IPK = (double) Math.round(IPK * 100) / 100;
        int status_lulus = 0;
        String lulus = "normal";
        if (IPK >= 2.76 && IPK <= 3.00) {
            status_lulus = 1;
            lulus = "Memuaskan";
        } else if (IPK > 3.00 && IPK <= 3.50) {
            status_lulus = 2;
            lulus = "Sangat Memuaskan";
        } else if (IPK > 3.50) {
            status_lulus = 3;
            lulus = "Cumlaude";
        }
        ipk.setText(IPK.toString());
        stat_lulus.setText(lulus);
        SQL = "update tabel_ipk set ipk='" + IPK + "',cumlaude='" + status_lulus + "' where id = 1";
        try {
            conn.executeUpdate(SQL);
            System.out.println("tereksekusi getIPK");
            refreshTabelIP();
        } catch (Exception e) {
            System.out.println("getIPK gagal dieksekusi");
        }
    }

    void refreshTabelIP() {
        ListSmt.removeAll(ListSmt);
        ListSmt = distinctSemester();
        int jum = ListSmt.size();
        SQL = "";
        switch (jum) {
            case 1:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + ")";
                break;
            case 2:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + ")";
                break;
            case 3:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + "," + ListSmt.get(2) + ")";
                break;
            case 4:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + "," + ListSmt.get(2) + "," + ListSmt.get(3) + ")";
                break;
            case 5:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + "," + ListSmt.get(2) + "," + ListSmt.get(3) + "," + ListSmt.get(4) + ")";
                break;
            case 6:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + "," + ListSmt.get(2) + "," + ListSmt.get(3) + "," + ListSmt.get(4) + "," + ListSmt.get(5) + ")";
                break;
            case 7:
                SQL = "update tabel_ip set total_sks='0', total_bobot='0', total_nilai_akhir='0', ips='0' where semester not in (" + ListSmt.get(0) + "," + ListSmt.get(1) + "," + ListSmt.get(2) + "," + ListSmt.get(3) + "," + ListSmt.get(4) + "," + ListSmt.get(5) + "," + ListSmt.get(6) + ")";
                break;
        }
        conn.executeUpdate(SQL);
    }

    void setIP() {
        ListIP.removeAll(ListIP);
        ListIP = getTabelIP();
        for (int i = 0; i < ListIP.size(); i++) {
            semester = ListIP.get(i).getSemester();
            total_sks = ListIP.get(i).getTotal_sks();
            total_bobot = ListIP.get(i).getTotal_bobot();
            total_nilai_akhir = ListIP.get(i).getTotal_nilai_akhir();
            ips = ListIP.get(i).getIps();
            SQL = "update tabel_ip set total_sks='" + total_sks + "', total_bobot='" + total_bobot + "', total_nilai_akhir='" + total_nilai_akhir + "', ips='" + ips + "' where semester='" + semester + "'";
            conn.executeUpdate(SQL);
        }
        System.out.println("tereksekusi setIP");
    }

    void setTabelIP() {
        ListIP.removeAll(ListIP);
        String nama_kolom[] = {"Semester", "Total SKS", "IPS"};
        ListIP = getTabelIP();
        Object isi_tabel[][] = new Object[ListIP.size()][3];
        for (int i = 0; i < ListIP.size(); i++) {
            isi_tabel[i][0] = ListIP.get(i).getSemester();
            isi_tabel[i][1] = ListIP.get(i).getTotal_sks();
            isi_tabel[i][2] = ListIP.get(i).getIps();
        }
        tbMI = new DefaultTableModel(isi_tabel, nama_kolom) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        tabel_ip.setModel(tbMI);
        getIPK();
        setIP();
    }

    ArrayList<Model> getTabelIP() {
        ListSmt = distinctSemester();
        for (int i = 0; i < ListSmt.size(); i++) {
            semester = Integer.parseInt(ListSmt.get(i).toString());
            SQL = "select sum(sks),sum(bobot), sum(nilai_akhir) from tabel_nilai where semester='" + semester + "'";
            System.out.println("SQL : " + SQL);
            res = conn.executeQuery(SQL);
            Model m = new Model(semester, total_sks, total_bobot, total_nilai_akhir, ips);

            try {
                while (res.next()) {
                    total_sks = Integer.parseInt(res.getString(1));
                    total_bobot = Double.parseDouble(res.getString(2));
                    total_nilai_akhir = Double.parseDouble(res.getString(3));
                    ips = total_nilai_akhir / total_sks;
                    ips = (double) Math.round(ips * 100) / 100;
                    m = new Model(semester, total_sks, total_bobot, total_nilai_akhir, ips);
                    ListIP.add(m);

                    //dump data
                    System.out.println("semester : " + semester);
                    System.out.println("tot SKS : " + total_sks);
                    System.out.println("tot NA : " + total_nilai_akhir);
                    System.out.println("IPS : " + ips);
                }
            } catch (SQLException ex) {
                System.err.println("eror di getTabelIP");
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ListIP;
    }

    //semester berbeda di tabel nilai untuk tabel IP
    ArrayList distinctSemester() {
        ListSmt.removeAll(ListSmt);
        SQL = "select distinct(semester) from tabel_nilai order by 1";
        res = conn.executeQuery(SQL);
        try {
            while (res.next()) {
                ListSmt.add(Integer.parseInt(res.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ArrayList SMT : " + ListSmt);
        return ListSmt;
    }

    //menghapus nilai yang dipilih
    void hapusNilai(int index) {
        mata_kuliah = tabel_nilai.getValueAt(index, 1).toString();
        SQL = "delete from tabel_nilai where mata_kuliah = '" + mata_kuliah + "'";
        conn.executeUpdate(SQL);
        btn_refresh.requestFocus();
    }

    void setNilai(int index) {
        mata_kuliah = tabel_nilai.getValueAt(index, 1).toString();
        SQL = "select * from tabel_nilai where mata_kuliah = '" + mata_kuliah + "'";
        res = conn.executeQuery(SQL);
        try {
            if (res.next()) {
                cb_semester.setSelectedItem(res.getString(1));
                status_praktikun = Integer.parseInt(res.getString(2));
                if (status_praktikun == 0) {
                    rd_prak_0.setSelected(true);
                } else {
                    rd_prak_1.setSelected(true);
                }
                tf_mata_kuliah.setText(res.getString(3));
                tf_mata_kuliah.setEditable(false);
                tf_sks.setText(res.getString(4));
                tf_uts.setText(res.getString(5));
                tf_puts.setText(res.getString(6));
                tf_uas.setText(res.getString(8));
                tf_puas.setText(res.getString(9));
                tf_tugas.setText(res.getString(11));
                tf_ptugas.setText(res.getString(12));
                tf_modul.setText(res.getString(14));
                tf_pmodul.setText(res.getString(15));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean ubahNilai() {
        //semester
        if (cb_semester.getSelectedIndex() == 0) { //gagal (item = - )
            JOptionPane.showMessageDialog(this, "Semester harus dipilih (antara 1 - 8)");
            lanjut = false;
        } else {
            semester = Integer.parseInt(cb_semester.getSelectedItem().toString());
            lanjut = true;
        }

        //sks
        if (lanjut == true) {
            if (tf_sks.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "SKS tidak boleh kosong");
                lanjut = false;
            } else {
                try {
                    sks = Integer.parseInt(tf_sks.getText());
                    if (sks < 1 || sks > 4) {
                        JOptionPane.showMessageDialog(this, "SKS antara 1 - 4");
                        tf_sks.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "SKS harus berupa angka");
                    tf_sks.setText("");
                    lanjut = false;
                }

            }
        }

        //status praktikum
        if (lanjut == true) {
            if (rd_prak_0.isSelected()) {
                status_praktikun = 0;
                lanjut = true;
            } else if (rd_prak_1.isSelected()) {
                status_praktikun = 1;
                lanjut = true;
            } else {
                JOptionPane.showMessageDialog(this, "Praktikum harus dipilih");
                lanjut = false;
            }
        }

        //uts
        if (lanjut == true) {
            if (tf_uts.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "UTS harus diisi");
                lanjut = false;
            } else {
                try {
                    uts = Double.parseDouble(tf_uts.getText());
                    if (uts < 0 || uts > 100) {
                        JOptionPane.showMessageDialog(this, "UTS antara 0 - 100");
                        tf_uts.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "UTS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //puts
        if (lanjut == true) {
            if (tf_puts.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase UTS harus diisi");
                tf_puts.setText("");
                lanjut = false;
            } else {
                try {
                    puts = Integer.parseInt(tf_puts.getText());
                    if (puts < 10 || puts > 50) {
                        JOptionPane.showMessageDialog(this, "persentase UTS antara 10-50");
                        tf_puts.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase UTS harus berupa angka");
                    lanjut = false;
                }

            }
        }

        //uas
        if (lanjut == true) {
            if (tf_uas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "UAS harus diisi");
                lanjut = false;
            } else {
                try {
                    uas = Double.parseDouble(tf_uas.getText());
                    if (uas < 0 || uas > 100) {
                        JOptionPane.showMessageDialog(this, "UAS antara 0 - 100");
                        tf_uas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "UAS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //puas
        if (lanjut == true) {
            if (tf_puas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase UAS harus diisi");
                tf_puas.setText("");
                lanjut = false;
            } else {
                try {
                    puas = Integer.parseInt(tf_puas.getText());
                    if (puas < 10 || puas > 50) {
                        JOptionPane.showMessageDialog(this, "persentase UAS antara 10-50");
                        tf_puas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase UAS harus berupa angka");
                    lanjut = false;
                }
            }
        }

        //tugas
        if (lanjut == true) {
            if (tf_tugas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tugas harus diisi");
                lanjut = false;
            } else {
                try {
                    tugas = Double.parseDouble(tf_tugas.getText());
                    if (tugas < 0 || tugas > 100) {
                        JOptionPane.showMessageDialog(this, "Tugas antara 0 - 100");
                        tf_tugas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Tugas harus berupa angka");
                    lanjut = false;
                }

            }
        }

        //ptugas
        if (lanjut == true) {
            if (tf_ptugas.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "persentase Tugas harus diisi");
                tf_ptugas.setText("");
                lanjut = false;
            } else {
                try {
                    ptugas = Integer.parseInt(tf_ptugas.getText());
                    if (ptugas < 10 || ptugas > 50) {
                        JOptionPane.showMessageDialog(this, "persentase Tugas antara 10-50");
                        tf_ptugas.setText("");
                        lanjut = false;
                    } else {
                        lanjut = true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "presentase Tugas harus berupa angka");
                    lanjut = false;
                }
            }
        }

        if (status_praktikun == 1) {  //untuk matkul praktikum
            //modul
            if (lanjut == true) {
                if (tf_modul.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Modul harus diisi");
                    lanjut = false;
                } else {
                    try {
                        modul = Double.parseDouble(tf_modul.getText());
                        if (modul < 0 || modul > 100) {
                            JOptionPane.showMessageDialog(this, "Modul antara 0 - 100");
                            tf_modul.setText("");
                            lanjut = false;
                        } else {
                            lanjut = true;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Modul harus berupa angka");
                        lanjut = false;
                    }
                }
            }

            //pmodul
            if (lanjut == true) {
                if (tf_pmodul.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Presentase Modul harus diisi");
                    lanjut = false;
                } else {
                    try {
                        pmodul = Integer.parseInt(tf_pmodul.getText());
                        if (pmodul < 1 || pmodul > 100) {
                            JOptionPane.showMessageDialog(this, "Presentase Modul antara 10 - 50");
                            tf_pmodul.setText("");
                            lanjut = false;
                        } else {
                            lanjut = true;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "presentase Modul harus berupa angka");
                        lanjut = false;
                    }
                }
            }
        }

        if (lanjut == true) {
            kuts = (puts * uts) / 100;
            kuts = (double) Math.round(kuts * 100) / 100;
            kuas = (puas * uas) / 100;
            kuas = (double) Math.round(kuas * 100) / 100;
            ktugas = (ptugas * tugas) / 100;
            ktugas = (double) Math.round(ktugas * 100) / 100;
            kmodul = (pmodul * modul) / 100;
            kmodul = (double) Math.round(kmodul * 100) / 100;
            if (status_praktikun == 0) { //non praktikum
                modul = 0.0;
                pmodul = 0;
                kmodul = 0.0;
                kumulasi = kuts + kuas + ktugas;
            } else { //praktikum
                kumulasi = kuts + kuas + ktugas + kmodul;
            }
            kumulasi = (double) Math.round(kumulasi * 100) / 100;
            huruf = NilaiHuruf(kumulasi);
            bobot = NilaiBobot(kumulasi);
            nilai_akhir = sks * bobot;
            nilai_akhir = (double) Math.round(nilai_akhir * 100) / 100;

            SQL = "update tabel_nilai set semester = '" + semester + "', status_praktikum = '" + status_praktikun + "', sks = '" + sks + "', uts ='" + uts + "', puts='" + puts + "', kuts='" + kuts + "', uas='" + uas + "', puas='" + puas + "', kuas = '" + kuas + "', tugas='" + tugas + "', ptugas= '" + ptugas + "', ktugas='" + ktugas + "', modul='" + modul + "', pmodul='" + pmodul + "', kmodul = '" + kmodul + "', kumulasi = '" + kumulasi + "', huruf='" + huruf + "', bobot='" + bobot + "', nilai_akhir='" + nilai_akhir + "' where mata_kuliah='" + mata_kuliah + "'";
            System.out.println("SQL : " + SQL);
            try {
                conn.executeUpdate(SQL);
                JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal di ubah");
            }
        }
        return lanjut;
    }

    void setTempSmt(int smt) {
        SQL = "update temporary set ti='" + smt + "' where id = 1";
        try {
            conn.executeUpdate(SQL);
            System.out.println("berhasil set Temp");
        } catch (Exception e) {
            System.out.println("gagal set Temp : " + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        bg_status_prak = new javax.swing.ButtonGroup();
        panel_utama = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_ip = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_nilai = new javax.swing.JTable();
        label_semester = new javax.swing.JLabel();
        label_mata_kuliah = new javax.swing.JLabel();
        label_sks = new javax.swing.JLabel();
        label_uts = new javax.swing.JLabel();
        label_uas = new javax.swing.JLabel();
        label_tugas = new javax.swing.JLabel();
        label_modul = new javax.swing.JLabel();
        label_praktikum = new javax.swing.JLabel();
        cb_semester = new javax.swing.JComboBox<>();
        tf_mata_kuliah = new javax.swing.JTextField();
        tf_sks = new javax.swing.JTextField();
        tf_puts = new javax.swing.JTextField();
        tf_uts = new javax.swing.JTextField();
        tf_uas = new javax.swing.JTextField();
        tf_puas = new javax.swing.JTextField();
        tf_tugas = new javax.swing.JTextField();
        tf_ptugas = new javax.swing.JTextField();
        tf_modul = new javax.swing.JTextField();
        tf_pmodul = new javax.swing.JTextField();
        rd_prak_1 = new javax.swing.JRadioButton();
        rd_prak_0 = new javax.swing.JRadioButton();
        label_puts = new javax.swing.JLabel();
        label_puas = new javax.swing.JLabel();
        label_pmodul = new javax.swing.JLabel();
        label_ptugas = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btn_ubah = new javax.swing.JButton();
        btn_ubahData = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        ipk = new javax.swing.JLabel();
        stat_lulus = new javax.swing.JLabel();
        label_IPK2 = new javax.swing.JLabel();
        label_IPK3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        tentang = new javax.swing.JMenuItem();
        print = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        jCheckBox1.setText("jCheckBox1");

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Itung IP");
        setMinimumSize(new java.awt.Dimension(1000, 723));
        setUndecorated(true);
        setResizable(false);

        panel_utama.setBackground(new java.awt.Color(255, 255, 255));
        panel_utama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_utamaMouseClicked(evt);
            }
        });
        panel_utama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_ip.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(tabel_ip);

        panel_utama.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 20, -1, 170));

        tabel_nilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tabel_nilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_nilaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_nilai);
        if (tabel_nilai.getColumnModel().getColumnCount() > 0) {
            tabel_nilai.getColumnModel().getColumn(1).setMinWidth(300);
        }

        panel_utama.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 260, 970, 400));

        label_semester.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_semester.setText("Semester");
        panel_utama.add(label_semester, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 23, -1, -1));

        label_mata_kuliah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_mata_kuliah.setText("Mata Kuliah");
        panel_utama.add(label_mata_kuliah, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 66, -1, -1));

        label_sks.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_sks.setText("SKS");
        panel_utama.add(label_sks, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 108, -1, -1));

        label_uts.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_uts.setText("UTS");
        panel_utama.add(label_uts, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 23, -1, -1));

        label_uas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_uas.setText("UAS");
        panel_utama.add(label_uas, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 66, -1, -1));

        label_tugas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_tugas.setText("Tugas");
        panel_utama.add(label_tugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 108, -1, -1));

        label_modul.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_modul.setText("Modul");
        panel_utama.add(label_modul, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 152, -1, -1));

        label_praktikum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_praktikum.setText("Praktikum");
        panel_utama.add(label_praktikum, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 152, -1, -1));

        cb_semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "1", "2", "3", "4", "5", "6", "7", "8" }));
        panel_utama.add(cb_semester, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 20, 144, -1));
        panel_utama.add(tf_mata_kuliah, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 64, 144, -1));
        panel_utama.add(tf_sks, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 106, 144, -1));
        panel_utama.add(tf_puts, new org.netbeans.lib.awtextra.AbsoluteConstraints(933, 21, 53, -1));
        panel_utama.add(tf_uts, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 26, 53, -1));
        panel_utama.add(tf_uas, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 68, 53, -1));
        panel_utama.add(tf_puas, new org.netbeans.lib.awtextra.AbsoluteConstraints(933, 64, 53, -1));
        panel_utama.add(tf_tugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 110, 53, -1));
        panel_utama.add(tf_ptugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(933, 106, 53, -1));
        panel_utama.add(tf_modul, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 152, 53, -1));
        panel_utama.add(tf_pmodul, new org.netbeans.lib.awtextra.AbsoluteConstraints(933, 150, 53, -1));

        rd_prak_1.setBackground(new java.awt.Color(255, 255, 255));
        bg_status_prak.add(rd_prak_1);
        rd_prak_1.setText("Ya");
        rd_prak_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_prak_1ActionPerformed(evt);
            }
        });
        panel_utama.add(rd_prak_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 148, -1, -1));

        rd_prak_0.setBackground(new java.awt.Color(255, 255, 255));
        bg_status_prak.add(rd_prak_0);
        rd_prak_0.setText("Tidak");
        rd_prak_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_prak_0ActionPerformed(evt);
            }
        });
        panel_utama.add(rd_prak_0, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 148, -1, -1));

        label_puts.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_puts.setText("%");
        panel_utama.add(label_puts, new org.netbeans.lib.awtextra.AbsoluteConstraints(904, 28, -1, -1));

        label_puas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_puas.setText("%");
        panel_utama.add(label_puas, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 70, -1, -1));

        label_pmodul.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_pmodul.setText("%");
        panel_utama.add(label_pmodul, new org.netbeans.lib.awtextra.AbsoluteConstraints(903, 147, -1, -1));

        label_ptugas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_ptugas.setText("%");
        panel_utama.add(label_ptugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(902, 110, -1, -1));

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        panel_utama.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 200, -1, -1));

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        panel_utama.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, -1, -1));

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        panel_utama.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, -1, -1));
        panel_utama.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 960, 10));

        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        panel_utama.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 200, -1, -1));

        btn_ubahData.setText("Ubah Data");
        btn_ubahData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahDataActionPerformed(evt);
            }
        });
        panel_utama.add(btn_ubahData, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 200, -1, -1));

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        panel_utama.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, -1, -1));

        ipk.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ipk.setText("4.0");
        panel_utama.add(ipk, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, -1, -1));

        stat_lulus.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        stat_lulus.setText("IPK");
        panel_utama.add(stat_lulus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, -1, -1));

        label_IPK2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label_IPK2.setText("IPK");
        panel_utama.add(label_IPK2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        label_IPK3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label_IPK3.setText("Status");
        panel_utama.add(label_IPK3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, -1));

        jMenu1.setText("File");

        tentang.setText("Tentang");
        tentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tentangActionPerformed(evt);
            }
        });
        jMenu1.add(tentang);

        print.setText("Print PDF");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jMenu1.add(print);

        exit.setText("Keluar");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_utama, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_utama, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if (inputData() == true) {
            reset();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void rd_prak_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_prak_1ActionPerformed
        EnablePrak();
        DefaultValue(1);
    }//GEN-LAST:event_rd_prak_1ActionPerformed

    private void rd_prak_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_prak_0ActionPerformed
        DisablePrak();
        DefaultValue(0);
    }//GEN-LAST:event_rd_prak_0ActionPerformed

    //ketika tabel diklik -> index didapat -> reset data kecuali tampil data ulang (setTabelNilai) karena looping terus
    private void tabel_nilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_nilaiMouseClicked
        index = tabel_nilai.getSelectedRow();
        //reset
        cb_semester.setSelectedIndex(0);
        tf_mata_kuliah.setText("");
        tf_sks.setText("");
        bg_status_prak.clearSelection();
        tf_uts.setText("");
        tf_puts.setText("");
        tf_uas.setText("");
        tf_puas.setText("");
        tf_tugas.setText("");
        tf_ptugas.setText("");
        tf_modul.setText("");
        tf_pmodul.setText("");
        disableSimpan();
        tf_mata_kuliah.setEditable(true);
        hideUbah();
        enableButton();

    }//GEN-LAST:event_tabel_nilaiMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this, "data akan dihapus ?");
        if (confirm == 0) {
            try {
                hapusNilai(index);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal dihapus");
            }
        }
        reset();
    }//GEN-LAST:event_btn_hapusActionPerformed

    //ketika ubah diklik -> field diisi data yang dipilih -> tombol simpan disable -> tombol batal dan ubah data muncul
    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        setNilai(index);
        disableButton();
        disableSimpan();
        showUbah();
    }//GEN-LAST:event_btn_ubahActionPerformed

    //ketika panel diklik -> hapus pilihan tabel -> enable tombol simpan (untuk simpan data baru) -> disable tombol ubah dan hapus
    private void panel_utamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_utamaMouseClicked
        tabel_nilai.clearSelection();
        enableSimpan();
        disableButton();
    }//GEN-LAST:event_panel_utamaMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        reset();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_ubahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahDataActionPerformed
        if (ubahNilai() == true) {
            reset();
        }
    }//GEN-LAST:event_btn_ubahDataActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        reset();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void tentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tentangActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(tentang, "itung.IP ver 1.0.0 - beta\nCopyright \u00a9 2019 by Aprianto\nJika ada keluhan email ke : apriantoa917@gmail.com");
    }//GEN-LAST:event_tentangActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        String[] options = {"-", "Tabel Nilai", "Tabel IP", "Semester Spesifik"};
        ImageIcon icon = new ImageIcon("");
        String n = (String) JOptionPane.showInputDialog(null, "Pilih tabel untuk dicetak",
                "Cetak ke PDF", JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (n.equals("Tabel Nilai")) {

            try {
                JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("tabel nilai.jasper"), null, conn.setKoneksi());
                JasperViewer.viewReport(jp, false);
                JOptionPane.showMessageDialog(rootPane, "Berhasil generate file PDF");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Mencetak PDF, eror :" + e);
            }

        } else if (n.equals("Tabel IP")) {

            try {
                JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("tabel ip.jasper"), null, conn.setKoneksi());
                JasperViewer.viewReport(jp, false);
                JOptionPane.showMessageDialog(rootPane, "Berhasil generate file PDF");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Mencetak PDF, eror :" + e);
            }

        } else if (n.equals("Semester Spesifik")) {
            ListSmt = distinctSemester();
            int row = ListSmt.size();
            String isi[] = new String[row];
            for (int i = 0; i < row; i++) {
                isi[i] = "Semester " + ListSmt.get(i);
            }
            icon = new ImageIcon("");
            n = (String) JOptionPane.showInputDialog(null, "Pilih semester untuk diproses", "Cetak ke PDF", JOptionPane.QUESTION_MESSAGE, icon, isi, isi[0]);
            int params = 0;
            params = Integer.parseInt(n.substring(9));
            try {
                setTempSmt(params);
                try {
                    JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("nilai.jasper"), null, conn.setKoneksi());
                    JasperViewer.viewReport(jp, false);
                    JOptionPane.showMessageDialog(rootPane, "Berhasil generate file PDF");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Gagal Mencetak PDF, eror :" + e);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal set Data");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Pilih opsi yang valid");
        }

    }//GEN-LAST:event_printActionPerformed

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
            java.util.logging.Logger.getLogger(Master.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_status_prak;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JButton btn_ubahData;
    private javax.swing.JComboBox<String> cb_semester;
    private javax.swing.JMenuItem exit;
    private javax.swing.JLabel ipk;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel label_IPK2;
    private javax.swing.JLabel label_IPK3;
    private javax.swing.JLabel label_mata_kuliah;
    private javax.swing.JLabel label_modul;
    private javax.swing.JLabel label_pmodul;
    private javax.swing.JLabel label_praktikum;
    private javax.swing.JLabel label_ptugas;
    private javax.swing.JLabel label_puas;
    private javax.swing.JLabel label_puts;
    private javax.swing.JLabel label_semester;
    private javax.swing.JLabel label_sks;
    private javax.swing.JLabel label_tugas;
    private javax.swing.JLabel label_uas;
    private javax.swing.JLabel label_uts;
    private javax.swing.JPanel panel_utama;
    private javax.swing.JMenuItem print;
    private javax.swing.JRadioButton rd_prak_0;
    private javax.swing.JRadioButton rd_prak_1;
    private javax.swing.JLabel stat_lulus;
    private javax.swing.JTable tabel_ip;
    private javax.swing.JTable tabel_nilai;
    private javax.swing.JMenuItem tentang;
    private javax.swing.JTextField tf_mata_kuliah;
    private javax.swing.JTextField tf_modul;
    private javax.swing.JTextField tf_pmodul;
    private javax.swing.JTextField tf_ptugas;
    private javax.swing.JTextField tf_puas;
    private javax.swing.JTextField tf_puts;
    private javax.swing.JTextField tf_sks;
    private javax.swing.JTextField tf_tugas;
    private javax.swing.JTextField tf_uas;
    private javax.swing.JTextField tf_uts;
    // End of variables declaration//GEN-END:variables
}

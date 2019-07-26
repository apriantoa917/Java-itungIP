package Code;


public class Model {

    int semester, status_praktikun, sks, puts, puas, ptugas, pmodul, total_sks;
    double uts, kuts, uas, kuas, tugas, ktugas, modul, kmodul, kumulasi, bobot, nilai_akhir, total_bobot, total_nilai_akhir,ips;
    String mata_kuliah, huruf;

//constructor getNilai()
    public Model(int semester, int status_praktikun, int sks, int puts, int puas, int ptugas, int pmodul, double uts, double kuts, double uas, double kuas, double tugas, double ktugas, double modul, double kmodul, double kumulasi, double bobot, double nilai_akhir, String mata_kuliah, String huruf) {
        this.semester = semester;
        this.status_praktikun = status_praktikun;
        this.sks = sks;
        this.puts = puts;
        this.puas = puas;
        this.ptugas = ptugas;
        this.pmodul = pmodul;
        this.uts = uts;
        this.kuts = kuts;
        this.uas = uas;
        this.kuas = kuas;
        this.tugas = tugas;
        this.ktugas = ktugas;
        this.modul = modul;
        this.kmodul = kmodul;
        this.kumulasi = kumulasi;
        this.bobot = bobot;
        this.nilai_akhir = nilai_akhir;
        this.mata_kuliah = mata_kuliah;
        this.huruf = huruf;
    }

    //constructor get IP()

    public Model(int semester, int total_sks, double total_bobot, double total_nilai_akhir, double ips) {
        this.semester = semester;
        this.total_sks = total_sks;
        this.total_bobot = total_bobot;
        this.total_nilai_akhir = total_nilai_akhir;
        this.ips = ips;
    }
    

    //getter
    
    public double getIps() {
        return ips;
    }

    public int getSemester() {
        return semester;
    }

    public int getStatus_praktikun() {
        return status_praktikun;
    }

    public int getSks() {
        return sks;
    }

    public int getPuts() {
        return puts;
    }

    public int getPuas() {
        return puas;
    }

    public int getPtugas() {
        return ptugas;
    }

    public int getPmodul() {
        return pmodul;
    }

    public double getUts() {
        return uts;
    }

    public double getKuts() {
        return kuts;
    }

    public double getUas() {
        return uas;
    }

    public double getKuas() {
        return kuas;
    }

    public double getTugas() {
        return tugas;
    }

    public double getKtugas() {
        return ktugas;
    }

    public double getModul() {
        return modul;
    }

    public double getKmodul() {
        return kmodul;
    }

    public double getKumulasi() {
        return kumulasi;
    }

    public double getBobot() {
        return bobot;
    }

    public double getNilai_akhir() {
        return nilai_akhir;
    }

    public String getMata_kuliah() {
        return mata_kuliah;
    }

    public String getHuruf() {
        return huruf;
    }

    public int getTotal_sks() {
        return total_sks;
    }

    public double getTotal_bobot() {
        return total_bobot;
    }

    public double getTotal_nilai_akhir() {
        return total_nilai_akhir;
    }

    //setter
    
    public void setIps(double ips) {
        this.ips = ips;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setStatus_praktikun(int status_praktikun) {
        this.status_praktikun = status_praktikun;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public void setPuts(int puts) {
        this.puts = puts;
    }

    public void setPuas(int puas) {
        this.puas = puas;
    }

    public void setPtugas(int ptugas) {
        this.ptugas = ptugas;
    }

    public void setPmodul(int pmodul) {
        this.pmodul = pmodul;
    }

    public void setUts(double uts) {
        this.uts = uts;
    }

    public void setKuts(double kuts) {
        this.kuts = kuts;
    }

    public void setUas(double uas) {
        this.uas = uas;
    }

    public void setKuas(double kuas) {
        this.kuas = kuas;
    }

    public void setTugas(double tugas) {
        this.tugas = tugas;
    }

    public void setKtugas(double ktugas) {
        this.ktugas = ktugas;
    }

    public void setModul(double modul) {
        this.modul = modul;
    }

    public void setKmodul(double kmodul) {
        this.kmodul = kmodul;
    }

    public void setKumulasi(double kumulasi) {
        this.kumulasi = kumulasi;
    }

    public void setBobot(double bobot) {
        this.bobot = bobot;
    }

    public void setNilai_akhir(double nilai_akhir) {
        this.nilai_akhir = nilai_akhir;
    }

    public void setMata_kuliah(String mata_kuliah) {
        this.mata_kuliah = mata_kuliah;
    }

    public void setHuruf(String huruf) {
        this.huruf = huruf;
    }

    public void setTotal_sks(int total_sks) {
        this.total_sks = total_sks;
    }

    public void setTotal_bobot(double total_bobot) {
        this.total_bobot = total_bobot;
    }

    public void setTotal_nilai_akhir(double total_nilai_akhir) {
        this.total_nilai_akhir = total_nilai_akhir;
    }

}

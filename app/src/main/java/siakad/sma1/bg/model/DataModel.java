package siakad.sma1.bg.model;

public class DataModel {

    private String id,username,verifikasi,nama,nis,nip,kelas,jurusan,ketabsensi,matpel,tgl,ketabsen,validasi,nama_siswa,nama_guru,matapel,kategori,judul,deskripsi,file,kesimpulan,nilai;

    public DataModel() {
    }

    public DataModel(String id, String username, String nama,String nis, String nip, String verifikasi, String kelas, String jurusan, String ketabsensi, String matpel,String tgl,String ketabsen,String validasi,String nama_siswa,String nama_guru,String matapel,String kategori,String judul,String deskripsi,String file,String kesimpulan,String nilai) {

        this.id = id;
        this.username = username;
        this.nama = nama;
        this.verifikasi = verifikasi;
        this.nis = nis;
        this.nip = nip;
        this.kelas = kelas;
        this.jurusan = jurusan;
        this.ketabsensi = ketabsensi;
        this.matpel = matpel;
        this.tgl = tgl;
        this.ketabsen = ketabsen;
        this.validasi = validasi;
        this.nama_siswa = nama_siswa;
        this.nama_guru = nama_guru;
        this.matapel = matapel;
        this.kategori = kategori;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.file = file;
        this.kesimpulan = kesimpulan;
        this.nilai = nilai;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username;}

    public String getVerifikasi() {
        return verifikasi;
    }

    public void setVerifikasi(String verifikasi) { this.verifikasi = verifikasi;}

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) { this.nama = nama;}

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) { this.nis = nis;}

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) { this.nip = nip;}

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) { this.kelas = kelas;}

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) { this.jurusan = jurusan;}

    public String getKetabsensi() {
        return ketabsensi;
    }

    public void setKetabsensi(String ketabsensi) { this.ketabsensi = ketabsensi;}

    public String getMatpel() {
        return matpel;
    }

    public void setMatpel(String matpel) { this.matpel = matpel;}

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) { this.tgl = tgl;}

    public String getKetabsen() {
        return ketabsen;
    }

    public void setKetabsen(String ketabsen) { this.ketabsen = ketabsen;}

    public String getValidasi() {
        return validasi;
    }

    public void setValidasi(String validasi) { this.validasi = validasi;}

    public String getNama_siswa() {
        return nama_siswa;
    }

    public void setNama_siswa(String nama_siswa) { this.nama_siswa = nama_siswa;}

    public String getNama_guru() {
        return nama_guru;
    }

    public void setNama_guru(String nama_guru) { this.nama_guru = nama_guru;}

    public String getMatapel() {
        return matapel;
    }

    public void setMatapel(String matapel) { this.matapel = matapel;}

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) { this.kategori = kategori;}

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) { this.judul = judul;}

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi;}

    public String getFile() {
        return file;
    }

    public void setFile(String file) { this.file = file;}

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String kesimpulan) { this.kesimpulan = kesimpulan;}

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) { this.nilai = nilai;}
}

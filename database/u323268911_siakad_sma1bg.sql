-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Waktu pembuatan: 06 Okt 2020 pada 09.55
-- Versi server: 10.4.13-MariaDB
-- Versi PHP: 7.2.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u323268911_siakad_sma1bg`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absen_siswa`
--

CREATE TABLE `absen_siswa` (
  `id` int(11) NOT NULL,
  `nis` int(11) NOT NULL,
  `nama_siswa` varchar(255) NOT NULL,
  `nip` int(11) NOT NULL,
  `nama_guru` varchar(255) NOT NULL,
  `matapel` varchar(100) NOT NULL,
  `tgl` date NOT NULL,
  `ketabsen` varchar(200) DEFAULT NULL,
  `validasi` varchar(100) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `modul_guru`
--

CREATE TABLE `modul_guru` (
  `id` int(255) NOT NULL,
  `nip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `matpel` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pertemuan` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gambar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `video` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `word` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pdf` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `modul_guru`
--

INSERT INTO `modul_guru` (`id`, `nip`, `nama`, `matpel`, `pertemuan`, `gambar`, `video`, `word`, `pdf`) VALUES
(20, '197507292008012011', 'lidya Banila', 'Biologi', 'Minggu 1', NULL, NULL, NULL, 'mr6wr6i3sbt64whnjaqt.pdf'),
(21, '197507292008012011', 'lidya Banila', 'Biologi', 'Minggu 3', NULL, NULL, NULL, 'zmwu6kx63msjckmx6d6v.pdf'),
(22, '197507292008012011', 'lidya Banila', 'Biologi', 'Minggu 6', NULL, NULL, NULL, 'xw80hw4vv1p4smx1a2fj.pdf'),
(23, '197507292008012011', 'lidya Banila', 'Biologi', 'Minggu 9', NULL, NULL, NULL, 'eebsezb6k3aadn2dm6qs.pdf'),
(24, '197507292008012011', 'lidya Banila', 'Biologi', 'Minggu 12', NULL, NULL, NULL, '4kp15rddq8ma8jftfp75.pdf');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tugas_siswa`
--

CREATE TABLE `tugas_siswa` (
  `id` int(255) NOT NULL,
  `nip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kategori` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `judul` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deskripsi` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tugas_siswa_last`
--

CREATE TABLE `tugas_siswa_last` (
  `id` int(255) NOT NULL,
  `nip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `judul` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kategori` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nis` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `matpel` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kesimpulan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kelas` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nilai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(3) NOT NULL,
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nis` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kelas` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `jurusan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `matpel` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nis_anak` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `verifikasi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `username`, `nama`, `password`, `level`, `nis`, `nip`, `kelas`, `jurusan`, `matpel`, `nis_anak`, `verifikasi`) VALUES
(27, 'lidya', 'lidya Banila', 'lidya', 'Guru', NULL, '197507292008012011', NULL, NULL, 'Biologi', NULL, 'Aktif'),
(28, 'iid', NULL, 'iid', 'Developer', NULL, NULL, NULL, NULL, NULL, NULL, 'Aktif');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `absen_siswa`
--
ALTER TABLE `absen_siswa`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `modul_guru`
--
ALTER TABLE `modul_guru`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tugas_siswa`
--
ALTER TABLE `tugas_siswa`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tugas_siswa_last`
--
ALTER TABLE `tugas_siswa_last`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `absen_siswa`
--
ALTER TABLE `absen_siswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT untuk tabel `modul_guru`
--
ALTER TABLE `modul_guru`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT untuk tabel `tugas_siswa`
--
ALTER TABLE `tugas_siswa`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT untuk tabel `tugas_siswa_last`
--
ALTER TABLE `tugas_siswa_last`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

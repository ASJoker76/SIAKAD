<?php 
    include 'koneksi.php';
	
	class usr{}
	
		$nis = $_POST['nis'];
		$nama_siswa = $_POST['nama_siswa'];
		$nip = $_POST['nip'];
		$nama_guru = $_POST['nama_guru'];
		$matapel = $_POST['matapel'];
		$tgl = $_POST['tgl'];
		$ketabsen = $_POST['ketabsen'];
		$validasi = $_POST['validasi'];
		
		$query = mysqli_query($con,"INSERT INTO absen_siswa (id,nis,nama_siswa,nip,nama_guru,matapel,tgl,ketabsen,validasi) VALUES (0,'".$nis."','".$nama_siswa."','".$nip."','".$nama_guru."','".$matapel."','".$tgl."','".$ketabsen."','".$validasi."')");

				if ($query){
					$response = new usr();
					$response->success = 1;
					$response->message = "berhasil";
					die(json_encode($response));

				} else {
					$response = new usr();
					$response->success = 0;
					$response->message = "gagal";
					die(json_encode($response));
				}

		mysqli_close($con);
	
?>
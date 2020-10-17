<?php 
    include 'koneksi.php';
	
	class usr{}
	
		$nis = $_POST['nis'];
		$nama = $_POST['nama'];
		$nip = $_POST['nip'];
		$matpel = $_POST['matapel'];
		$kategori = $_POST['kategori'];
		$nilai = $_POST['nilai'];
		
		$query = mysqli_query($con,"INSERT INTO tugas_siswa_last (id,nis,nama,nip,matpel,kategori,nilai) VALUES (0,'".$nis."','".$nama."','".$nip."','".$matpel."','".$kategori."','".$nilai."')");

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
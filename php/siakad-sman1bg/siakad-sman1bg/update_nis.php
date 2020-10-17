<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $username = $_POST['username'];
	 $nama = $_POST['nama'];
	 $nis = $_POST['nis'];	 
	 $kelas = $_POST['kelas'];	 
	 $jurusan = $_POST['jurusan'];	 

     if ((empty($username))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom username tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($nama))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nama tidak boleh kosong";
		die(json_encode($response));
	}
	 else if ((empty($nis))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nis tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($kelas))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom kelas tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($jurusan))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom jurusan tidak boleh kosong";
		die(json_encode($response));
	}
	 else {
		$query = mysqli_query($con, "UPDATE user SET nama='".$nama."',nis='".$nis."',kelas='".$kelas."',jurusan='".$jurusan."' where username='".$username."'");
    
     		if ($query){
     		    $response = new usr();
    			$response->success = 1;
	 			$response->message = "Nis diperbaharui";
	 			die(json_encode($response));
        	} else {
    		 	$response = new usr();
    			$response->success = 0;
	 			$response->message = "Verifikasi Gagal";
	 			die(json_encode($response));
        	}
	}

	mysqli_close($con);

?>	
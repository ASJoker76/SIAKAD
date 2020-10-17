<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $nip = $_POST["nip"];
	 $judul = $_POST["judul"];
	 $kategori = $_POST["kategori"];
     $nis = $_POST["nis"];
	 $nama = $_POST["nama"];
	 $nilai = $_POST["nilai"];

     if ((empty($nip))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nip tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($judul))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom judul tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($kategori))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom kategori tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($nis))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nis tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($nama))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nama tidak boleh kosong";
		die(json_encode($response));
	}
	else if ((empty($nilai))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nilai tidak boleh kosong";
		die(json_encode($response));
	}
	 else {		
		$query = mysqli_query($con, "UPDATE tugas_siswa_last SET nilai='".$nilai."' WHERE nip='".$nip."' AND judul='".$judul."' AND kategori='".$kategori."' AND nis='".$nis."' AND nama='".$nama."' ");
		    
     		if ($query){
     		    $response = new usr();
    			$response->success = 1;
	 			$response->message = "Update berhasil";
	 			die(json_encode($response));
        	} else {
    		 	$response = new usr();
    			$response->success = 0;
	 			$response->message = "Update Gagal";
	 			die(json_encode($response));
        	}
	}

	mysqli_close($con);

?>	
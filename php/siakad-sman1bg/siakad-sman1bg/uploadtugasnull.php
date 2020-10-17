<?php
	
	 include_once "koneksi.php";

	 class usr{}

	$judul = $_POST['judul']; 
    $deskripsi = $_POST['deskripsi']; 
	$nip = $_POST['nip']; 
	$kategori = $_POST['kategori']; 

     if ((empty($judul))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom judul tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($deskripsi))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom deskripsi tidak boleh kosong";
	 	die(json_encode($response));
	 } 
     else if ((empty($nip))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom level tidak boleh kosong";
	 	die(json_encode($response));
	 }else if ((empty($kategori))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom kategori tidak boleh kosong";
	 	die(json_encode($response));
	 } else {
		$query = mysqli_query($con, "INSERT INTO tugas_siswa (id,nip,kategori,judul,deskripsi) VALUE(0,'".$nip."','".$kategori."','".$judul."','".$deskripsi."')");
    
		if ($query){
			$response = new usr();
    		$response->success = 1;
    		$response->message = "Tugas Berhasil di buat.";
    		die(json_encode($response));
    
    	} else {
    		$response = new usr();
    		$response->success = 0;
    		$response->message = "Tugas gagal di buat";
    		die(json_encode($response));
    	} 
	}

	mysqli_close($con);

?>	
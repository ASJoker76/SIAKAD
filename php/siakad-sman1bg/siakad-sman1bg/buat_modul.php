<?php
	
	 include_once "koneksi.php";

	 class usr{}

	 $nip = $_POST["nip"];
	 $nama = $_POST["nama"];
	 $matpel = $_POST["matpel"];
     $pertemuan = $_POST["pertemuan"];

     if ((empty($nip))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nip tidak boleh kosong";
	 	die(json_encode($response));
	 }
	 else if ((empty($nama))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nama tidak boleh kosong";
	 	die(json_encode($response));
	 } 
     else if ((empty($matpel))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom matpel tidak boleh kosong";
	 	die(json_encode($response));
	 }else if ((empty($pertemuan))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom pertemuan tidak boleh kosong";
		die(json_encode($response));
	 }else {
		$query = mysqli_query($con, "INSERT INTO modul_guru (id,nip,nama,matpel,pertemuan) VALUES(0,'".$nip."','".$nama."','".$matpel."','".$pertemuan."')");
    
		if ($query){
			$response = new usr();
			$response->success = 1;
			$response->message = "Modul berhasil dibuat. Siliahkan Upload Berkas";
			die(json_encode($response));

		} else {
			$response = new usr();
			$response->success = 0;
			$response->message = "Modul gagal";
			die(json_encode($response));
		}		 
	}

	mysqli_close($con);

?>	
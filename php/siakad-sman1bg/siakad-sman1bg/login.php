<?php

	 include_once "koneksi.php";

	 class usr{}
	
	 $username = $_POST["username"];
	 $password = $_POST["password"];
	
	 if ((empty($username)) || (empty($password))) { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom tidak boleh kosong"; 
	 	die(json_encode($response));
	 }
	
	 $query = mysqli_query($con, "SELECT * FROM user WHERE username='$username' AND password='$password'");
	
	 $row = mysqli_fetch_array($query);
	
	 if (!empty($row)){
	 	$response = new usr();
	 	$response->success = 1;
	 	$response->message = "Selamat datang ".$row['username'];
	 	$response->id = $row['id'];
		$response->username = $row['username'];
		$response->nama = $row['nama'];
		$response->level = $row['level'];
		$response->verifikasi = $row['verifikasi'];
		$response->nis = $row['nis'];
		$response->nip = $row['nip'];
		$response->matpel = $row['matpel'];
		$response->kelas = $row['kelas'];
		$response->jurusan = $row['jurusan'];
		$response->nis_anak = $row['nis_anak'];
	 	die(json_encode($response));
		
	 } else { 
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Username atau password salah";
	 	die(json_encode($response));
	 }
	
	mysqli_close($con);

?>
<?php
	include_once "koneksi.php";
	
	class emp{}
	
	
	$gambar = $_POST['gambar'];
	$path = $_POST['path'];
	$nip = $_POST['nip'];
	$pertemuan = $_POST['pertemuan'];
	
	if (empty($nip)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Please dont empty Nip."; 
		die(json_encode($response));
	}else if (empty($path)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Please dont empty Path."; 
		die(json_encode($response));
	}
	else if (empty($pertemuan)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Please dont empty Pertemuan."; 
		die(json_encode($response));
	}else if (empty($gambar)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Please dont empty Gambar."; 
		die(json_encode($response));
	}
	else {
    		$random = random_word(20);
    		
    		$path2 = "".$random.".png";
    		
    		// sesuiakan ip address laptop/pc atau URL server
    		$actualpath = "$path2";
    		
    		$link = "uploads/".$path2."";
    		
    		$query = mysqli_query($con, "UPDATE modul_guru SET gambar='".$actualpath."' where nip='".$nip."' AND pertemuan='".$pertemuan."' ");
    	
    		if ($query){
    			file_put_contents($link,base64_decode($gambar));
    			$response = new emp();
    			$response->success = 1;
    			$response->message = "Successfully Uploaded Image";
    			die(json_encode($response));
    		} else{ 
    			$response = new emp();
    			$response->success = 0;
    			$response->message = "Error Upload image";
    			die(json_encode($response)); 
    		}
	}	
	
	// fungsi random string pada gambar untuk menghindari nama file yang sama
	function random_word($id = 20){
		$pool = '1234567890abcdefghijkmnpqrstuvwxyz';
		
		$word = '';
		for ($i = 0; $i < $id; $i++){
			$word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
		}
		return $word; 
	}

	mysqli_close($con);
	
?>	
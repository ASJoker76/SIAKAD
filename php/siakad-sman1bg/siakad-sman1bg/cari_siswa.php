<?php 
	
	include_once "koneksi.php";

	$kelas = $_POST['keyword'];
	$jurusan = $_POST['keyword2'];

	$query = mysqli_query($con, "SELECT * FROM user WHERE kelas = '".$kelas."' AND jurusan = '".$jurusan."'");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
			    "id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
			    "nama": "'.str_replace($char,'`',strip_tags($row['nama'])).'",
			    "nis": "'.str_replace($char,'`',strip_tags($row['nis'])).'",
				"kelas": "'.str_replace($char,'`',strip_tags($row['kelas'])).'",
				"jurusan": "'.str_replace($char,'`',strip_tags($row['jurusan'])).'"
			},';
		}

		$json = substr($json,0,strlen($json)-1);

		$json .= ']}';

	} else {
		$json = '{"value":0, "message": "Data tidak ditemukan."}';
	}

	echo $json;

	mysqli_close($con);
?>
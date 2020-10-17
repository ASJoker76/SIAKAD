<?php 
	
	include_once "koneksi.php";

	$matpel = $_POST['keyword'];
	
	$query = mysqli_query($con, "SELECT * FROM tugas_siswa_last WHERE matpel = '".$matpel."' ");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
			    "id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
			    "kategori": "'.str_replace($char,'`',strip_tags($row['kategori'])).'",
			    "nis": "'.str_replace($char,'`',strip_tags($row['nis'])).'",
			    "nama": "'.str_replace($char,'`',strip_tags($row['nama'])).'",
			    "kelas": "'.str_replace($char,'`',strip_tags($row['kelas'])).'",
			    "nilai": "'.str_replace($char,'`',strip_tags($row['nilai'])).'"
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
<?php 
	
	include_once "koneksi.php";

	$nis = $_POST['keyword'];
	//$tgl = $_POST['keyword2'];
//AND tgl = '".$tgl."'
	$query = mysqli_query($con, "SELECT * FROM absen_siswa WHERE nis = '".$nis."' ");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
			    "id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
			    "nis": "'.str_replace($char,'`',strip_tags($row['nis'])).'",
			    "nama_siswa": "'.str_replace($char,'`',strip_tags($row['nama_siswa'])).'",
			    "nip": "'.str_replace($char,'`',strip_tags($row['nip'])).'",
			    "nama_guru": "'.str_replace($char,'`',strip_tags($row['nama_guru'])).'",
				"matapel": "'.str_replace($char,'`',strip_tags($row['matapel'])).'",
				"tgl": "'.str_replace($char,'`',strip_tags($row['tgl'])).'",
				"ketabsen": "'.str_replace($char,'`',strip_tags($row['ketabsen'])).'",
				"validasi": "'.str_replace($char,'`',strip_tags($row['validasi'])).'"
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
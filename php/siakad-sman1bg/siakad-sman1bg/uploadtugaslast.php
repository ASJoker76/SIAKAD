	
<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){
  	
	include_once "koneksi.php";	
  	
        $originalImgName= $_FILES['filename']['name'];
		$tempName= $_FILES['filename']['tmp_name'];

        $judul = $_POST['judul'];         
		$nip = $_POST['nip']; 
		$kategori = $_POST['kategori']; 
		$nis = $_POST['nis']; 
		$nama = $_POST['nama']; 
		$kesimpulan = $_POST['kesimpulan']; 
		$matpel = $_POST['matpel']; 
        $folder="uploads/";
		
		
        
        
			$query = "INSERT INTO tugas_siswa_last (id,nip,kategori,judul,file,nis,nama,kesimpulan,matpel) VALUE(0,'".$nip."','".$kategori."','".$judul."','".$originalImgName."','".$nis."','".$nama."','".$kesimpulan."','".$matpel."')";
			
                if(mysqli_query($con,$query)){
                move_uploaded_file($tempName,$folder.$originalImgName);    
					$query= "SELECT * FROM tugas_siswa_last WHERE file='$originalImgName'";
	                 $result= mysqli_query($con, $query);
	                 $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $emparray[] = $row;
                                   }
                                   echo json_encode(array( "status" => "true","message" => "Successfully file added!" , "data" => $emparray) );
                                   
	                     }else{
	                     		echo json_encode(array( "status" => "false","message" => "Failed") );
	                     }
			   
                }else{
                	echo json_encode(array( "status" => "false","message" => "Failed Save Database!") );
                }
        	
  }
?>
	
<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){
  	// echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
	//including the database connection file
	include_once "koneksi.php";	
  	  	
  	//$_FILES['image']['name']   give original name from parameter where 'image' == parametername eg. city.jpg
  	//$_FILES['image']['tmp_name']  temporary system generated name
  
        $originalImgName= $_FILES['filename']['name'];
		$tempName= $_FILES['filename']['tmp_name'];
		$nip = $_POST['nip']; 
		$pertemuan = $_POST['pertemuan']; 
        $folder="uploads/";
		$url = $originalImgName; //update path as per your directory structure 
		$random = random_word(20);    		
		$path = "".$random.".pdf";
		$actualpath = "$path";
		
        
        if(move_uploaded_file($tempName,$folder.$actualpath)){
			$query = "UPDATE modul_guru SET pdf='".$actualpath."' where nip='".$nip."' AND pertemuan='".$pertemuan."'" ;
			
                if(mysqli_query($con,$query)){
                
					$query= "SELECT * FROM modul_guru WHERE pdf='$actualpath'";
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
        	//echo "moved to ".$url;
        }else{
        	echo json_encode(array( "status" => "false","message" => "Failed Move PDF!") );
        }
  }

function random_word($id = 20){
	$pool = '1234567890abcdefghijkmnpqrstuvwxyz';
	
	$word = '';
	for ($i = 0; $i < $id; $i++){
		$word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
	}
	return $word; 
}
?>
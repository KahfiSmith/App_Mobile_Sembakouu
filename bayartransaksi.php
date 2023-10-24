<?php
	session_start();
	$timeout = 1; // setting timeout dalam menit
	$logout = "login.php"; // redirect halaman logout

	$timeout = $timeout * 360; // menit ke detik
	if(isset($_SESSION['start_session'])){
		$elapsed_time = time()-$_SESSION['start_session'];
		if($elapsed_time >= $timeout){
			session_destroy();
			echo "<script type='text/javascript'>alert('Sesi telah berakhir');window.location='$logout'</script>";
		}
	}

	$_SESSION['start_session']=time();

	include('config.php');
	if($_SESSION['status_login'] != true){
		echo '<script>window.location="login.php"</script>';
	}
?>
<?php
    $kdPenjualan = $_POST['kdPenjualan'];
    $NamaAdmin = $_POST['NamaAdmin'];
	$bayar = $_POST['bayar'];
    $TotalBayar = $_POST['TotalBayar'];

    $insert = mysqli_query($conn, "INSERT INTO penjualan VALUES ('".$kdPenjualan."', '".$TotalBayar."', '".$bayar."', '".$NamaAdmin."', '', now())");


?>
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
	if($_SESSION['status_login'] != true){
		echo '<script>window.location="login.php"</script>';
	}
    include 'config.php';

    if(isset($_GET['delete'])){
        $cek = mysqli_query($conn, "SELECT * FROM barang WHERE kdBarang = '".$_GET['delete']."' ");
        while ($tampil = mysqli_fetch_array($cek)){
        $NamaBarang = $tampil['NamaBarang'];

        $delete = mysqli_query($conn, "DELETE FROM barang WHERE kdBarang = '".$_GET['delete']."' ");
        $insert = mysqli_query($conn, "INSERT INTO admin_logs VALUES ('', '".$_SESSION['user_global']->NamaAdmin."', 'Menghapus Barang: ".$NamaBarang."')");
        header ("Location: listbarang.php");
        echo 'sukses';
    }
}

    if(isset($_GET['deletes'])){
        $cek = mysqli_query($conn, "SELECT * FROM supplier WHERE kdSupplier = '".$_GET['deletes']."' ");
        while ($tampil = mysqli_fetch_array($cek)){
        $NamaSupplier = $tampil['kdSupplier'];

        $delete = mysqli_query($conn, "DELETE FROM supplier WHERE kdSupplier = '".$_GET['deletes']."' ");
        $insert = mysqli_query($conn, "INSERT INTO admin_logs VALUES ('', '".$_SESSION['user_global']->NamaAdmin."', 'Menghapus Supplier: ".$NamaSupplier."')");
        header ("Location: listsupplier.php");
        echo 'sukses';
    }
}
?>
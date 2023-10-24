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
	include('config.php');
	
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width-device-width, initial-scale=1">
    <title>Supplier || Sembakouu</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css2?family=Quicksand&display=swap" rel="stylesheet">
	<script src="dist/sweetalert2.all.min.js"></script>
</head>
<body>
	
    <!-- header -->
	<header>
		<div class="container">
		<h1><a href="home.php">Sembakouu</a></h1>
			<ul>
				<li><a href="home.php">Dashboard</a></li>
				<li><a href="admin.php">Profile</a></li>
				<li><a href="listbarang.php">Barang</a></li>
				<li><a href="listsupplier.php">Supplier</a></li>
				<li><a href="transaksi.php">Transaksi</a></li>
				<li><a href="laporan.php">Laporan</a></li>
				<li><a href="logout.php" id="logout">Logout</a></li>
			</ul>
		</div>
	</header>
	
	<!-- Content -->
	<div class="section">
		<div class="container">
			<h3>Edit Supplier</h3>
			<div class="box">
				<?php
			
					if(isset($_POST['simpan']) ){
						
						$NamaSupplier = addslashes($_POST['NamaSupplier']);
						$Alamat = addslashes($_POST['Alamat']);
						$NoHP = addslashes($_POST['NoHP']);

						$cek = mysqli_query($conn,"SELECT * FROM supplier WHERE kdSupplier = '".$_GET['kdSupplier']."' ");
						while ($tampil = mysqli_fetch_array($cek)){
							$id = $tampil['kdSupplier'];
							$cek2 = mysqli_query($conn,"SELECT MAX(id) FROM admin_logs");
							while ($tampil2 = mysqli_fetch_array($cek2)){
							$MaxiID = $tampil2[0];
							$ids = (int) substr($MaxiID,1,4);
							$MaxiID++;
							$NewsID = "".sprintf("%04s",$MaxiID++);
						//if(empty($NewID) || ($NewID <= 0)){
							//echo "<b style='color: red'>Isi semua kolom dengan benar!</b>";
						//}else if($NewID > 0){
							//echo "<b style='color: red'>Sudah ada Barang dengan ID itu</b>";
						//}else{
							if(mysqli_query($conn, "UPDATE supplier SET `NamaSupplier` = '".$NamaSupplier."', `Alamat` = '".$Alamat."', `NoHP` = '".$NoHP."' WHERE kdSupplier = '".$_GET['kdSupplier']."' ")){
							mysqli_query($conn,"INSERT INTO admin_logs VALUES ('".$NewsID."', '".$_SESSION['user_global']->NamaAdmin."', 'Mengedit Supplier: ".$NamaSupplier."')");
							
							?>
								<script>
								Swal.fire({
									title: 'Berhasil!',
									text: 'Edit Supplier Berhasil. ID Supplier: <?php echo $id ?>',
									icon: 'success'
								}).then((result) => {
									window.location="listsupplier.php";
								})
								</script>
								<?php
							}else{
							echo "<b style='color: red'>Semua field sudah diisi dengan benar, namun terjadi error saat pengiriman ke database, silahkan coba lagi nanti</b><br /><br />";
							}
						}
					}
					} else{
						$kdSupplier = $_GET['kdSupplier'];
						$cek2 = mysqli_query($conn,"SELECT * FROM supplier WHERE kdSupplier = '".$kdSupplier."' ");
						while ($tampil = mysqli_fetch_array($cek2)){
						
						$kdSupplier = $tampil['kdSupplier'];
						$NamaSupplier = ($tampil['NamaSupplier']);
						$Alamat = ($tampil['Alamat']);
						$NoHP = ($tampil['NoHP']);
						}
					}
				?>
				<form action="" method="POST">
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Nama Supplier : </td><td><input type="text" class="datepicker-trigger input-control hasDatepicker" name="NamaSupplier" value="<?php echo $NamaSupplier ?>" maxlength="255"></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Alamat : </td><td><input type="text" class="datepicker-trigger input-control hasDatepicker" name="Alamat" value="<?php echo $Alamat ?>" maxlength="255"></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">No HP : <font color="red" style="font-size: 10px;">(Only Numbers)</font>: </td><td><input type="number" class="datepicker-trigger input-control hasDatepicker" value="<?php echo $NoHP ?>" name="NoHP" min="9999999999" max="9999999999999"></td>
					</tr>
					
					<input type="submit" name="simpan" value="Simpan" class="btn">
				</form>
			</div>
		</div>
		<!-- Footer -->
	<footer class="container">
		<div class="pull-right">
			<a href="" target="_blank">Group B5</a>
		</div>
		<div class="pull-left">
			<span>Copyright &copy; 2022 - Sembakouu.</span> © <a href="https://www.instagram.com/farishasan_13/" target="_blank">Developer</a>
		</div>
	</footer>
	</div>
	<script src="jquery.js"></script>
	<script>
		$(document).on('click', '#logout', function(e) {
			e.preventDefault();

			Swal.fire({
				title: 'Apakah anda yakin?',
				text: "Anda akan Keluar!",
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Ya, Keluar Saja!'
				}).then((result) => {
				if (result.isConfirmed) {
					window.location ='login.php';				
				}
			})
		})
	</script>
</body>
</html>
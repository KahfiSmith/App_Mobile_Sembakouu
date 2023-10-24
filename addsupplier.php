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
			<h3>Tambah Supplier</h3>
			<div class="box">
				<?php
					if(isset($_POST['tambah'])){
						$cek = mysqli_query($conn,"SELECT MAX(kdSupplier) FROM supplier");
						while ($tampil = mysqli_fetch_array($cek)){

						$NamaSupplier = addslashes($_POST['NamaSupplier']);
						$Alamat = addslashes($_POST['Alamat']);
						$NoHP = addslashes($_POST['NoHP']);

						$MaxID = $tampil[0];
						$id = (int) substr($MaxID,1,4);
						$MaxID++;
						$NewID = "".sprintf("%04s",$MaxID++);

						if(!preg_match("/^[0-9]*$/", $NoHP)){
						?>
							<script>
								Swal.fire({
								icon: 'error',
								title: 'Oops...',
								text: 'Hanya boleh menggunakan Angka!',
								})
							</script>
						<?php
						}

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
							$insert = mysqli_query($conn, "INSERT INTO supplier VALUES ('".$NewID."', '".$NamaSupplier."', '".$Alamat."', '".$NoHP."')");
							$insert = mysqli_query($conn, "INSERT INTO admin_logs VALUES ('".$NewsID."', '".$_SESSION['user_global']->NamaAdmin."', 'Menambahkan Supplier: ".$NamaSupplier."')");
							
							if($insert){
								?>
								<script>
								Swal.fire({
									title: 'Berhasil!',
									text: 'Supplier Berhasil Ditambahkan. ID Supplier: <?php echo $NewID ?>',
									icon: 'success'
								}).then((result) => {
									window.location="listsupplier.php";
								})
								</script>
								<?php
							}else{
								echo "<b style='color: red'>Semua kolom telah diisi dengan benar, tetapi terjadi kesalahan saat mengirim ke database, silakan coba lagi nanti!</b>";
							}
						}
						echo "<br /><br />";
					}
				}
				?>
				<form action="" method="POST">
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Nama Supplier : </td><td><input type="text" class="datepicker-trigger input-control hasDatepicker" name="NamaSupplier" value="<?php if(isset($_POST['NamaSupplier'])){ echo $_POST['NamaSupplier']; }?>" placeholder="Nama Supplier..." maxlength="255" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Alamat : </td><td><input type="text" class="datepicker-trigger input-control hasDatepicker" value="<?php if(isset($_POST['Alamat'])){ echo $_POST['Alamat']; }?>" placeholder="Alamat..." name="Alamat" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">No HP : </td><td><input type="number" class="datepicker-trigger input-control hasDatepicker" value="<?php if(isset($_POST['NoHP'])){ echo $_POST['NoHP']; }?>" placeholder="No HP..." name="NoHP" min="9999999999" max="999999999999" required></td>
					</tr>
					
					<input type="submit" name="tambah" value="Tambah" class="btn">
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
</body>
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
</html>
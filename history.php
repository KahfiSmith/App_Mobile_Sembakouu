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
    <title>Laporan || Sembakouu</title>
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
			<h2>History Admin</h2>
			
			
				<div class="box-list">
				<div class="block-title">
			
			</div>
			<table align="center">
				<tr><td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>No.</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Nama Admin</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Info</b></td>
				</tr>
			
			
				<?php
				if(!(isset($_POST['submit']))){
					
					$cek = mysqli_query($conn,"SELECT * FROM admin_logs ORDER BY id DESC");
					while ($tampil = mysqli_fetch_array($cek)){
					$id = $tampil['id'];
					$nama = $tampil['nama'];
					$info = $tampil['info'];
	
					echo "
						<tr>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'>$id</td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'>$nama</td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'>$info</td>
						</tr>
						";
						}
					}
				?>
		</div>
	</div>
				</div>
	<form action="" method="POST">
		<button type="submit" name="submit" class="btn" id="btn"> Clear History </button>
	</form>
	<script src="jquery.js"></script>
	<script>
		$(document).on('click', '#btn', function(e) {
			e.preventDefault();

			Swal.fire({
				title: 'Apakah anda yakin?',
				text: "Anda akan Menghapus semua History!",
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Ya, Hapus Saja!'
				}).then((result) => {
				if (result.isConfirmed) {
					window.location ='hapushistory.php';				
				}
			})
		})
	</script>
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
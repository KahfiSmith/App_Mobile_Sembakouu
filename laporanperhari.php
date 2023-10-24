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
			<h1>Laporan Perhari</h1>
			<div class="box-list">
			<form action="cetak.php" method="get">
					<a>Tanggal : </a><input type="date" name="tgl" value="Laporan Perhari" class="input-laporan">
					<button type="submit" name="cari" class="input-laporan-btn"><a>Cari</a></button>
			</form>
			</div>
			<table align="center" class="box-list">
				<tr>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>No</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Transaksi</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Jumlah</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Total Harga</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Tanggal</b></td>
				</tr>
			
				
				<?php
				$no = 1;
				if(isset($_GET['cari'])) {
						$cek = mysqli_query($conn,"SELECT penjualan.kdPenjualan, detail_penjualan.kdBarang, 
						detail_penjualan.Jumlah, detail_penjualan.TotalHarga, penjualan.Waktu
						FROM detail_penjualan
						JOIN penjualan
						ON detail_penjualan.kdPenjualan = penjualan.kdPenjualan
						WHERE penjualan.Waktu = '".$_GET['tgl']."' 
						ORDER BY penjualan.Waktu DESC");
					} else{
					$cek = mysqli_query($conn, "SELECT penjualan.kdPenjualan, detail_penjualan.kdBarang, 
						detail_penjualan.Jumlah, detail_penjualan.TotalHarga, penjualan.Waktu
						FROM detail_penjualan
						JOIN penjualan
						ON detail_penjualan.kdPenjualan = penjualan.kdPenjualan
						ORDER BY penjualan.Waktu DESC"); 
						while ($tampil = mysqli_fetch_array($cek)){
							$kdPenjualan = $tampil['kdPenjualan'];
							$kdBarang = $tampil['kdBarang'];
							$Jumlah = $tampil['Jumlah'];
							$TotalHarga = $tampil['TotalHarga'];
							$Waktu = $tampil['Waktu'];
							?>
								<tr>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $no++ ?></td>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $kdPenjualan ?></td>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $kdBarang ?></td>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $Jumlah ?></td>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo 'Rp. ' . number_format($TotalHarga,2,',','.'); ?></td>
									<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $Waktu ?></td>
								</tr>
						<?php }
					} 
				if(isset($_GET['cari']) && ($_GET['tgl'])){
					$cek3 = mysqli_query($conn,"SELECT SUM(TotalHarga) FROM detail_penjualan");
					while ($tampil = mysqli_fetch_array($cek3)){

					}
				}
				?>
				
		</div>
		</table>
			<!--<form action="" method="POST">
				<div class="box">
				<h3>Total Pendapatan</h3>
					<div class="input-control-pendapatan">
						<input type="text" name="pendapatan" id="cari" class="input-control-pendapatan" 
						value="<?php echo 'Rp. ' . number_format($tampil['TotalHarga'],2,',','.'); 
					?>">
					</div>
				</div>
			</form> -->
		
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
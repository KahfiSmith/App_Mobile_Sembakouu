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
    <title>Pesanan || Sembakouu</title>
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
			<form action="pesanan.php" method="POST">
				<h2> Cari Pesanan</h2>
				<div class="box-list">
					<div class="input-control-add">
						<input type="text" name="cari" id="cari" class="input-control-add" autocomplete="off" placeholder="Cari Barang...">
							<button type="submit" name="submit" value="cari" class="input-group-btn" ><a>Cari </a></button>
					</div>
				</div>
			</form>
			<h2>List Pesanan</h2>
				<div class="box-list">
				<div class="block-title">	
			</div>
			<table align="center">
				<tr>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>ID Transaksi.</b></td>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>Tanggal</b></td>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>Total</b></td>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>ID Pembeli</b></td>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>Status</b></td>
					<td style='border: 1px #000; padding: 10px 55px 10px 55px;' align="center"><b>Action</b></td>
				</tr>
			
				<?php
				if(!(isset($_POST['submit']))){
					
					$cek = mysqli_query($conn,"SELECT * FROM transaksi ORDER BY tanggal DESC");
					while ($tampil = mysqli_fetch_array($cek)){
					$id = $tampil['idTransaksi'];
					$tanggal = $tampil['tanggal'];
					$total = $tampil['total'];
					$idPembeli = $tampil['idPembeli'];
					$status = $tampil['status'];
					?>
					<tr>
						<td align='center'><?php echo $id ?></td>
						<td align='center'><?php echo $tanggal ?></td>
						<td align='center'><?php echo 'Rp. ' . number_format($total,2,',','.'); ?></td>
						<td align='center'><?php echo $idPembeli ?></td>
						<td align='center'  class="input-control" disabled><?php echo $status ?></td>
						<td align='center'>
							<a align='center' href='editpesanan.php?id=<?php echo $id?>'><img src='img/edit.jpg' width='16px'></a>
						</td>
					</tr>
					<?php
						}
					}else{
					$cari = $_POST['cari'];
					$cek2 = mysqli_query($conn,"SELECT * FROM transaksi WHERE idTransaksi LIKE '%".$cari."%' OR tanggal LIKE '%".$cari."%' OR status LIKE '%".$cari."%'");
					if(mysqli_num_rows($cek2) > 0){
						while ($tampil2 = mysqli_fetch_array($cek2)){
						$id = $tampil2['idTransaksi'];
						$tanggal = $tampil2['tanggal'];
						$total = $tampil2['total'];
						$idPembeli = $tampil2['idPembeli'];
						$status = $tampil2['status'];
							?>
						<tr>
							<td align='center'><?php echo $id ?></td>
							<td align='center'><?php echo $tanggal ?></td>
							<td align='center'><?php echo 'Rp. ' . number_format($total,2,',','.'); ?></td>
							<td align='center'><?php echo $idPembeli ?></td>
							<td align='center'  class="input-control" disabled><?php echo $status ?></td>
							<td align='center'>
								<a align='center' href='editpesanan.php?id=<?php echo $id?>'><img src='img/edit.jpg' width='16px'></a>
						</td>
					</tr>
					<?php
						}
					}else {
						?>
            <script>
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Pesanan yang anda cari tidak ada!',
            })
            </script>
            <?php
					}
				}
				?>
		</div>
	</div>
</div>
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
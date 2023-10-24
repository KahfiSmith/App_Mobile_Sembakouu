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
    <title>Barang || Sembakouu</title>
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
			<h2>Data Barang</h2>
				<div class="box-list">
				<div class="block-title">
				<form action="" method="POST">
				<h3> Cari Barang</h3>
					<div class="input-control-add">
						<input type="text" name="cari" autocomplete="off" id="cari" class="input-control-add" placeholder="Cari Barang...">
						<button type="submit" name="submit" value="cari" class="input-group-btn"><a>Cari </a></button>
					</div>
				</form>
				<form action="addbarang.php" method="POST">
				<h3> Tambah Barang</h3>
					<div class="input-control-add">
						<button type="submit" value="Tambah" class="input-group-btn"><a>Tambah</a></button>
					</div>
				</form>

				</div>
				</div>
			<table align="center" class="box-list">
				<tr>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Nama Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Jenis Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Gambar</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Harga Jual</b></td>
					<td style='border: 1px #000; padding: 10px 10px 10px 10px;' align="center"><b>Stok</b></td>
					<td style='border: 1px #000; padding: 10px 10px 10px 10px;' align="center"><b>Satuan</b></td>
					<td style='border: 1px #000; padding: 10px 0px 10px 35px;' align="center"><b>Action</b></td>
				</tr>
			
			
				<?php
				if(!(isset($_POST['submit']))){
					
					$cek = mysqli_query($conn,"SELECT * FROM barang ");
					
					while ($tampil = mysqli_fetch_array($cek)){
					
					$id = $tampil['kdBarang'];
					$nama = $tampil['NamaBarang'];
					$jenis_barang = $tampil['jenis_barang'];
					$gambar = $tampil['gambar'];
					$HargaJual = $tampil['HargaJual'];
					$Stok = $tampil['Stok'];
					$Satuan = $tampil['Satuan'];
					?>
						<tr>
							<td align='center'><?php echo $id ?></td>
							<td align='center'><?php echo $nama ?></td>
							<td align='center'><?php echo $jenis_barang ?></td>
							<td align='center'><img src="<?php echo $gambar ?>" width="50px"></td>
							<td align='center'><?php echo 'Rp. ' . number_format($HargaJual,2,',','.'); ?></td>
							<td align='center'><?php echo $Stok ?></td>
							<td align='center'><?php echo $Satuan ?></td>
							<td align='center'>
								<a href='editbarang.php?kdBarang=<?php echo $id?>'><img src='img/edit.jpg' width='16px'></a>
							</td>
							<td align='center'>
								<a href='hapus.php?delete=<?php echo $id?>'><img src='img/hapus.png' width='16px' alt='fungsi hapus' id='btn-hapus'></a>
							</td>
						</tr>
						<?php }
					} else{
					$cari = $_POST['cari'];
					$cek2 = mysqli_query($conn,"SELECT * FROM barang WHERE NamaBarang LIKE '%".$cari."%' OR jenis_barang LIKE '%".$cari."%'");
					if(mysqli_num_rows($cek2) > 0){
						while ($tampil = mysqli_fetch_array($cek2)){
							$id = $tampil['kdBarang'];
							$nama = $tampil['NamaBarang'];
							$jenis_barang = $tampil['jenis_barang'];
							$gambar = $tampil['gambar'];
							$HargaJual = $tampil['HargaJual'];
							$Stok = $tampil['Stok'];
							$Satuan = $tampil['Satuan'];
							?>
								<tr>
									<td align='center'><?php echo $id ?></td>
									<td align='center'><?php echo $nama ?></td>
									<td align='center'><?php echo $jenis_barang ?></td>
									<td align='center'><img src="<?php echo $gambar ?>" width="50px"></td>
									<td align='center'><?php echo 'Rp. ' . number_format($HargaJual,2,',','.'); ?></td>
									<td align='center'><?php echo $Stok ?></td>
									<td align='center'><?php echo $Satuan ?></td>
									<td align='center'>
										<a href='editbarang.php?kdBarang=<?php echo $id?>'><img src='img/edit.jpg' width='16px'></a>
									</td>
									<td align='center'>
										<a href='hapus.php?delete=<?php echo $id?>'><img src='img/hapus.png' width='16px' alt='fungsi hapus' id="btn-hapus"></a>
									</td>
								</tr>
						<?php }
					} else{
						echo "
							<tr>
								<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align='center'>Barang</td>
								<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align='center'>Tidak</td>
								<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align='center'>Ditemukan!</td>
							</tr>";
					}echo "</table>
					";
				} 
				?>
		</div>
			</table>
	</div>
	<footer class="container">
		<div class="pull-right">
			<a href="" target="_blank"></a>
		</div>
		<div class="pull-left">
			<span>Copyright &copy; 2022 - Sembakouu.</span> © <a href="https://www.instagram.com/farishasan_13/" target="_blank">Developer</a>
		</div>
	</footer>

	<script src="jquery.js"></script>
	<script>
		$(document).on('click', '#btn-hapus', function(e) {
			e.preventDefault();

			Swal.fire({
				title: 'Apakah anda yakin?',
				text: "Data akan dihapus!",
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Ya, Hapus Saja!'
				}).then((result) => {
				if (result.isConfirmed) {
					window.location ='hapus.php?delete=<?php echo $id?>';				
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
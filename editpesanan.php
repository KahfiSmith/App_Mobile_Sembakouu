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
			<h3>Konfirmasi Pesanan</h3>
			<div class="box">
			<table align="center">
				<tr>
				<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>No</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>ID Transaksi</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Kode Barang</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>ID Pembeli</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Jumlah</b></td>
					<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align="center"><b>Total Harga</b></td>
				</tr>
				<?php
					if(isset($_POST['simpan']) ){

						$status = addslashes($_POST['status']);

						$cek = mysqli_query($conn,"SELECT * FROM transaksi WHERE idTransaksi = '".$_GET['id']."' ");
						while ($tampil = mysqli_fetch_array($cek)){
							$id = $tampil['idTransaksi'];
							$cek2 = mysqli_query($conn,"SELECT MAX(id) FROM admin_logs");
							while ($tampil2 = mysqli_fetch_array($cek2)){
							$MaxiID = $tampil2[0];
							$ids = (int) substr($MaxiID,1,4);
							$MaxiID++;
							$NewsID = "".sprintf("%04s",$MaxiID++);

							if(mysqli_query($conn, "UPDATE transaksi SET `status` = '".$status."' WHERE idTransaksi = '".$_GET['id']."' ")){
							mysqli_query($conn,"INSERT INTO admin_logs VALUES ('".$NewsID."', '".$_SESSION['user_global']->NamaAdmin."', 'Mengubah Status Pesanan, Status: ".$status."' 'ID Transaksi: ".$_GET['id']."')");
							?>
								<script>
								Swal.fire({
									title: 'Berhasil!',
									text: 'Edit Konfirmasi Pesanan Berhasil. ID Pesanan: <?php echo $id ?>',
									icon: 'success'
								}).then((result) => {
									window.location="pesanan.php";
								})
								</script>
								<?php
							}else{
							echo "<b style='color: red'>Semua field sudah diisi dengan benar, namun terjadi error saat pengiriman ke database, silahkan coba lagi nanti</b><br /><br />";
							}
							}
						}	
					}else {
						$no = 1;
					    $cek3 = mysqli_query($conn, "SELECT detail_transaksi.idTransaksi, detail_transaksi.kdBarang,
						detail_transaksi.idPembeli, detail_transaksi.Jumlah, detail_transaksi.sub_total, transaksi.status
						FROM detail_transaksi
						JOIN transaksi
						ON detail_transaksi.idTransaksi = transaksi.idTransaksi
						WHERE detail_transaksi.idTransaksi = '".$_GET['id']."' 
						ORDER BY detail_transaksi.idTransaksi DESC"); 
						while ($tampil3 = mysqli_fetch_array($cek3)){
						
						$idTransaksi = $tampil3['idTransaksi'];
						$kdBarang = ($tampil3['kdBarang']);
						$idPembeli = ($tampil3['idPembeli']);
						$Jumlah = ($tampil3['Jumlah']);
						$sub_total = ($tampil3['sub_total']);
						$status = ($tampil3['status']);
						
						?>
						<tr>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $no++ ?></td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $idTransaksi ?></td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $kdBarang ?></td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $idPembeli ?></td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $Jumlah ?></td>
							<td style='border: 1px #000; padding: 10px 25px 10px 25px;' align='center'><?php echo $sub_total ?></td>
						</tr>
						<?php
						}
					}
				?>
				<form action="" method="POST">
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Status : </td>
						<td>
							<select name="status"  class="input-control" onchange="exibeMsg(this.value);">
								<option value="<?php echo $status?>"><?php echo $status ?> (Aktif) </option>
								<option value="pending">pending</option>
								<option value="dikemas">dikemas</option>
								<option value="dikirim">dikirim</option>
								<option value="selesai">selesai</option>
								<option value="dibatalkan">dibatalkan</option>
							</select>
						</td>
					</tr>
					</table>
				
					<input type="submit" name="simpan" value="Simpan" class="btn">
					</div>
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
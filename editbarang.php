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
			<h3>Edit Barang</h3>
			<div class="box">
				<?php
					if(isset($_POST['simpan']) ){
										$conn->autocommit(false);
										try{
											$foto_tmp = $_FILES['image']['tmp_name'];
											$nama_foto = $_FILES['image']['name'];
				
											$NamaBarang = addslashes($_POST['NamaBarang']);
											$jenis_barang = addslashes($_POST['jenis_barang']);
											$desk = addslashes($_POST['deskripsi']);
											$gambar = $nama_foto;
											$gambarlawas = addslashes($_POST['gambarlawas']);
											$HargaBeli = addslashes($_POST['HargaBeli']);
											$HargaJual = addslashes($_POST['HargaJual']);
											$Stok = addslashes($_POST['Stok']);
											$Satuan = addslashes($_POST['Satuan']);
											$kdSupplier = addslashes($_POST['kdSupplier']);
				
											$cek = mysqli_query($conn,"SELECT * FROM barang WHERE kdBarang = '".$_POST['kdBarang']."' ");
											while ($tampil = mysqli_fetch_array($cek)){
												$id = $tampil['kdBarang'];
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
											if($nama_foto==""){
												mysqli_query($conn, "UPDATE barang SET `NamaBarang` = '".$NamaBarang."', `jenis_barang` = '".$jenis_barang."', `deskripsi` = '".$desk."',  `gambar` = '".$gambarlawas."', `HargaBeli` = '".$HargaBeli."', `HargaJual` = '".$HargaJual."', `Stok` = '".$Stok."', `Satuan` = '".$Satuan."', `kdSupplier` = '".$kdSupplier."' WHERE kdBarang = '".$_POST['kdBarang']."' ");
												mysqli_query($conn,"INSERT INTO admin_logs VALUES ('".$NewsID."', '".$_SESSION['user_global']->NamaAdmin."', 'Mengedit Barang: ".$NamaBarang."')");
												$conn->commit();
												?>
												<script>
												Swal.fire({
													title: 'Berhasil!',
													text: 'Edit Barang Berhasil. ID Barang: <?php echo $id ?>',
													icon: 'success'
												}).then((result) => {
													window.location="listbarang.php";
												})
												</script>
												<?php
											}else{
												mysqli_query($conn, "UPDATE barang SET `NamaBarang` = '".$NamaBarang."', `jenis_barang` = '".$jenis_barang."', `deskripsi` = '".$desk."',  `gambar` = 'http://sofiyatul.kencang.id/img/produk/".$gambar."', `HargaBeli` = '".$HargaBeli."', `HargaJual` = '".$HargaJual."', `Stok` = '".$Stok."', `Satuan` = '".$Satuan."', `kdSupplier` = '".$kdSupplier."' WHERE kdBarang = '".$_POST['kdBarang']."' ");
												mysqli_query($conn,"INSERT INTO admin_logs VALUES ('', '".$_SESSION['user_global']->NamaAdmin."', 'Mengedit Barang: ".$NamaBarang."')");
												$check_upload = move_uploaded_file($foto_tmp, './img/produk/' . $nama_foto);
												$conn->commit();
												?>
												<script>
												Swal.fire({
													title: 'Berhasil!',
													text: 'Edit Barang Berhasil. ID Barang: <?php echo $NewID ?>',
													icon: 'success'
												}).then((result) => {
													window.location="listbarang.php";
												})
												</script>
												<?php
											}
										}
									}
										}catch(Exception $e){
											$conn->rollback();
											$response['message'] = $e->getMessage();
											echo "<script>
											alert('Ubah Barang Gagal!'); 
											</script>";
										}
									}
								
							$kdBarang = $_GET['kdBarang'];
							$cek2 = mysqli_query($conn,"SELECT * FROM barang WHERE kdBarang = '".$kdBarang."' ");
							while ($tampil = mysqli_fetch_array($cek2)){

							
				?>
				<form action="" method="POST" enctype="multipart/form-data">
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right"></td><td><input type="hidden" class="datepicker-trigger input-control hasDatepicker" name="kdBarang" value="<?php echo $tampil['kdBarang'] ?>" maxlength="255" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Nama Barang : </td><td><input type="text" class="datepicker-trigger input-control hasDatepicker" name="NamaBarang" value="<?php echo $tampil['NamaBarang'] ?>" maxlength="255" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Jenis Barang : </td>
						<td>
							<select name="jenis_barang"  class="input-control" onchange="exibeMsg(this.value);" required>
								<option value="<?php echo $tampil['jenis_barang'] ?>"><?php echo $tampil['jenis_barang'] ?></option>
								<option value="Sembako">Sembako</option>
								<option value="Alat Tulis">Alat Tulis</option>
								<option value="Minuman">Minuman</option>
								<option value="Makanan">Makanan</option>
								<option value="Makanan Ringan">Makanan Ringan</option>
								<option value="Perlengkapan Mencuci">Perlengkapan Mencuci</option>
								<option value="Bumbu Dapur">Bumbu Dapur</option>
								<option value="Kebutuhan Rumah Tangga">Kebutuhan Rumah Tangga</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Deskripsi : </td><td><textarea type="text" class="datepicker-trigger input-control hasDatepicker" placeholder="Deskripsi..." required name="deskripsi"><?php echo $tampil['deskripsi'] ?></textarea></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Gambar : </td>
						<td><input type="file" class="datepicker-trigger input-control hasDatepicker" name="image" accept="image/png, image/jpg, image/jpeg, image/jfif maxlength="255"></td>
						<td><input type="text" class="datepicker-trigger input-control hasDatepicker" value="<?php echo $tampil['gambar'] ?>" name="gambarlawas" maxlength="255" ></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Harga Beli <font color="red" style="font-size: 10px;">(Only Numbers)</font>: </td><td><input type="number" class="datepicker-trigger input-control hasDatepicker" value="<?php echo $tampil['HargaBeli'] ?>" name="HargaBeli" maxlength="13" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Harga Jual <font color="red" style="font-size: 10px;">(Only Numbers)</font>: </td><td><input type="number" class="datepicker-trigger input-control hasDatepicker" value="<?php echo $tampil['HargaJual'] ?>" name="HargaJual" maxlength="13" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Stok <font color="red" style="font-size: 10px;">(Only Numbers)</font>: </td><td><input type="number" class="datepicker-trigger input-control hasDatepicker" value="<?php echo $tampil['Stok'] ?>" name="Stok" maxlength="13" required></td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Satuan : </td>
						<td>
							<select name="Satuan" value="<?php echo $tampil['Satuan'] ?>" class="datepicker-trigger input-control hasDatepicker" onchange="exibeMsg(this.value);">
								<option value="<?php echo $tampil['Satuan'] ?>"><?php echo $tampil['Satuan'] ?></option>
								<option value="Kilogram">Kilogram</option>
								<option value="Liter">Liter</option>
								<option value="Pcs">Pcs</option>
								<option value="Ons">Ons</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style='border: 1px #000; padding: 10px 50px 10px 50px;' align="right">Nama Supplier : </td>
						<td>
							<select name="kdSupplier" class="datepicker-trigger input-control hasDatepicker" required>
								<?php
									$sup = mysqli_query($conn,"SELECT supplier.kdSupplier, supplier.NamaSupplier 
									FROM supplier 
									JOIN barang
									ON supplier.kdSupplier = barang.kdSupplier
									WHERE barang.kdBarang = '".$_GET['kdBarang']."'
									ORDER BY kdSupplier DESC");
									while($s = mysqli_fetch_array($sup)){
									?>
									<option value="<?php echo $s['kdSupplier'] ?>"><?php echo $s['NamaSupplier'] ?></option>
								<?php } ?>
								<?php
									$sup = mysqli_query($conn,"SELECT * FROM supplier ORDER BY kdSupplier DESC");
									while($s = mysqli_fetch_array($sup)){
									?>
									<option value="<?php echo $s['kdSupplier'] ?>"><?php echo $s['NamaSupplier'] ?></option>
								<?php } ?>
							</select>
						</td>
					</tr>
					<?php } ?>
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
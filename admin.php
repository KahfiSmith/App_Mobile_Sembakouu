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
	
	$query = mysqli_query($conn, "SELECT * FROM penjual WHERE idAdmin = '".$_SESSION['idAdmin']."' ");
	$d = mysqli_fetch_object($query);
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width-device-width, initial-scale=1">
    <title>Profile || Sembakouu</title>
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
			<h3>Edit Profile</h3>
			<div class="box">
				<form action="" method="POST">
					<input type="text" name="nama" autocomplete="off" placeholder="Nama Lengkap" class="input-control" value="<?php echo $d->NamaAdmin ?>" required>
					<input type="text" name="user" autocomplete="off" placeholder="Username" class="input-control" value="<?php echo $d->Username ?>" required>
					<input type="submit" name="submit" value="Ubah Profil" class="btn">
				</form>
				<?php
					if(isset($_POST['submit'])){
						
						$nama 	= ucwords($_POST['nama']);
						$user 	= $_POST['user'];
						
						$update = mysqli_query($conn, "UPDATE penjual SET
						NamaAdmin = '".$nama."',
						Username = '".$user."'
						WHERE idAdmin = '".$d->idAdmin."' ");
						if($update){
						?>
						<script>
						Swal.fire({
							title: 'Berhasil!',
							text: 'Ubah Data Profil Berhasil!',
							icon: 'success'
						}).then((result) => {
							window.location="admin.php";
						})
						</script>
						<?php
						}else{
						?>
						<script>
						Swal.fire({
							icon: 'error',
							title: 'Oops...',
							text: 'gagal'
						}).then((result) => {
							window.location="admin.php";
						})
						</script>
						<?php
						}
						
					}
				?>
			</div>
			
			<h3>Ubah Password</h3>
			<div class="box">
				<form action="" method="POST">
					<input type="password" name="pass1" placeholder="Password Baru" class="input-control"  required>
					<input type="password" name="pass2" placeholder="Konfirmasi Password Baru" class="input-control"  required>
					
					<input type="submit" name="ubah_password" value="Ubah Password" class="btn">
				</form>
				<?php
					if(isset($_POST['ubah_password'])){
						
						$pass1 	= $_POST['pass1'];
						$pass2 	= $_POST['pass2'];
						
						if($pass2 != $pass1){
							?>
								<script>
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'Konfirmasi Password Baru Tidak Sesuai!',
								})
								</script>
								<?php
						}else{
							$u_pass = mysqli_query($conn, "UPDATE penjual SET
									Password = '".MD5($pass1)."'
									WHERE idAdmin = '".$d->idAdmin."'");
							if($u_pass){
								?>
								<script>
								Swal.fire({
									title: 'Berhasil!',
									text: 'Ubah Password Berhasil!',
									icon: 'success'
								}).then((result) => {
									window.location="admin.php";
								})
								</script>
								<?php
							}else{
								?>
								<script>
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: 'gagal'
								}).then((result) => {
									window.location="admin.php";
								})
								</script>
								<?php
							}
						}
						
					}
				?>
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<div class="row bg-title">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<h4 class="page-title">Chi tiết thành viên</h4>
		</div>
	</div>
	<!-- /.row -->



	<!-- .row -->
	<div class="row">
		<form class="form-horizontal form-material"
			action='<c:url value="/user/details"></c:url>' method="post">
			<input type="hidden" name="id" value="${ useru.id }" />
			<div class="col-md-4 col-xs-12">
				<div class="white-box">
					<div class="user-bg">
						<img width="100%" alt="user"
							src="<c:url value="/assets/plugins/images/large/img1.jpg" />">
						<div class="overlay-box">
							<div class="user-content">
								<a href="javascript:void(0)"><img
									src="<c:url value="/assets/plugins/images/users/genu.jpg"/>"
									class="thumb-lg img-circle" alt="img"></a>
								<div>
									<h4 class="text-white">${useru.fullname}</h4>
								</div>
								<div>
									<h5 class="text-white">${useru.email}</h5>
								</div>
							</div>
						</div>
					</div>
					<div class="user-btm-box">
						<div class="col-md-4 col-sm-4 text-center">
							<p class="text-purple">
								<i class="ti-facebook"></i>
							</p>
							<h4>20%</h4>
							<h6>Chưa thực hiện</h6>
						</div>
						<div class="col-md-4 col-sm-4 text-center">
							<p class="text-blue">
								<i class="ti-twitter"></i>
							</p>
							<h4>50%</h4>
							<h6>Đang thực hiện</h6>
						</div>
						<div class="col-md-4 col-sm-4 text-center">
							<p class="text-danger">
								<i class="ti-dribbble"></i>
							</p>
							<h4>30%</h4>
							<h6>Hoàn thành</h6>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-8 col-xs-12">
				<div class="white-box">
					<div class="form-group">
						<label for="example-email" class="">Full Name:</label>
						<div class="col-md-12">
							<input type="fullname" name="fullname" value="${useru.fullname }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label for="example-email" class="">Email:</label>
						<div class="col-md-12">
							<input type="email" name="email" value="${useru.email }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label for="example-email" class="">Pass:</label></label>
						<div class="col-md-12">
							<input type="password" name="password" value="${useru.password }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label for="example-email" class="">Phone:</label></label>
						<div class="col-md-12">
							<input type="phone" name="password" value="${useru.phone }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<div class="">
							<button type="submit" class="btn btn-success">Add User</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>


	<br />
	<!-- /.row -->
	<!-- BEGIN DANH SÁCH CÔNG VIỆC -->
	<h4>DANH SÁCH CÔNG VIỆC</h4>
	<div class="row">
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Chưa thực hiện</h3>
				<div class="message-center">
					<a href="#">
						<div class="mail-contnet">
							<h5>Pavan kumar</h5>
							<span class="mail-desc">Just see the my admin!</span> <span
								class="time">9:30 AM</span>
						</div>
					</a> <a href="#">
						<div class="mail-contnet">
							<h5>Sonu Nigam</h5>
							<span class="mail-desc">I've sung a song! See you at</span> <span
								class="time">9:10 AM</span>
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Đang thực hiện</h3>
				<div class="message-center">
					<a href="#">
						<div class="mail-contnet">
							<h5>Pavan kumar</h5>
							<span class="mail-desc">Just see the my admin!</span> <span
								class="time">9:30 AM</span>
						</div>
					</a> <a href="#">
						<div class="mail-contnet">
							<h5>Sonu Nigam</h5>
							<span class="mail-desc">I've sung a song! See you at</span> <span
								class="time">9:10 AM</span>
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Đã hoàn thành</h3>
				<div class="message-center">
					<a href="#">
						<div class="mail-contnet">
							<h5>Pavan kumar</h5>
							<span class="mail-desc">Just see the my admin!</span> <span
								class="time">9:30 AM</span>
						</div>
					</a> <a href="#">
						<div class="mail-contnet">
							<h5>Sonu Nigam</h5>
							<span class="mail-desc">I've sung a song! See you at</span> <span
								class="time">9:10 AM</span>
						</div>

					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- END DANH SÁCH CÔNG VIỆC -->
</div>
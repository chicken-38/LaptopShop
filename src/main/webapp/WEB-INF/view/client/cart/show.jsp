<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <html lang="en">

                <head>
                    <!-- Google Web Fonts -->
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                        rel="stylesheet">

                    <!-- Icon Font Stylesheet -->
                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                        rel="stylesheet">

                    <!-- Libraries Stylesheet -->
                    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                    <!-- Customized Bootstrap Stylesheet -->
                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                    <!-- Template Stylesheet -->
                    <link href="/client/css/style.css" rel="stylesheet">
                </head>

                <body>
                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->

                    <!-- Navbar start -->
                    <jsp:include page="../layout/header.jsp" />
                    <!-- Navbar End -->


                    <!-- Cart Page Start -->
                    <div class="container-fluid py-5">
                        <div class="container py-5">
                            <div class="mt-5">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="#">Trang chủ</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Chi tiết giỏ hàng</li>
                                    </ol>
                                </nav>
                            </div>
                            <c:if test="${not empty cart.cartDetail}">
                                <form:form action="/place-order" method="post" modelAttribute="cart">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <div class="table-responsive">
                                        <table class="table align-middle text-center">
                                            <thead>
                                                <tr>
                                                    <th scope="col"></th>
                                                    <th scope="col">Sản phẩm</th>
                                                    <th scope="col">Tên</th>
                                                    <th scope="col">Giá</th>
                                                    <th scope="col">Số lượng</th>
                                                    <th scope="col">Thành tiền</th>
                                                    <th scope="col">Xử lý</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cartDetail" items="${cart.cartDetail}"
                                                    varStatus="status">
                                                    <tr>
                                                        <td>
                                                            <form:checkbox path="cartDetailId"
                                                                value="${cartDetail.id}" />
                                                        </td>
                                                        <th scope="row">
                                                            <div
                                                                class="d-flex align-items-center justify-content-center">
                                                                <img src="${cartDetail.product.imageURL}"
                                                                    class="img-fluid rounded-circle"
                                                                    style="width: 80px; height: 80px;" alt="">
                                                            </div>
                                                        </th>
                                                        <td>
                                                            <a href="/product/${cartDetail.product.id}"
                                                                target="blank">${cartDetail.product.name}</a>
                                                        </td>
                                                        <td>
                                                            <fmt:formatNumber type="number"
                                                                value="${cartDetail.product.price}" /> VND
                                                        </td>
                                                        <td>
                                                            <form:hidden path="cartDetail[${status.index}].id" />
                                                            <div
                                                                class="d-flex align-items-center justify-content-center">
                                                                <div class="input-group quantity" style="width: 100px;">
                                                                    <div class="input-group-btn">
                                                                        <button type="button"
                                                                            class="btn btn-sm btn-minus rounded-circle bg-light border">
                                                                            <i class="fa fa-minus"></i>
                                                                        </button>
                                                                    </div>
                                                                    <form:input type="text"
                                                                        class="form-control form-control-sm text-center border-0"
                                                                        path="cartDetail[${status.index}].quantity"
                                                                        data-cart-detail-id="${cartDetail.id}"
                                                                        data-cart-detail-price="${cartDetail.product.price}" />
                                                                    <div class="input-group-btn">
                                                                        <button type="button"
                                                                            class="btn btn-sm btn-plus rounded-circle bg-light border">
                                                                            <i class="fa fa-plus"></i>
                                                                        </button>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <p class="mb-0" data-cart-detail-id="${cartDetail.id}">
                                                                <fmt:formatNumber type="number"
                                                                    value="${cartDetail.price}" />
                                                                VND
                                                            </p>
                                                        </td>
                                                        <td>
                                                            <button type="button" style="padding:0.75rem 0.75rem"
                                                                class="btn btn-md rounded-circle bg-light border"
                                                                onclick="window.location.href='/delete-cart-product/${cartDetail.id}'">
                                                                <i class="fa fa-times text-danger"></i>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="row g-4 justify-content-start mt-3">
                                        <div class="col-12 col-md-8">
                                            <div class="bg-light rounded">
                                                <div class="p-4">
                                                    <h1 class="display-6 mb-4">Thông tin đơn hàng</h1>
                                                    <div class="d-flex justify-content-between mb-4">
                                                        <h5 class="mb-0 me-4">Tạm tính:</h5>
                                                        <p class="mb-0" data-cart-total-price="0">
                                                            <fmt:formatNumber type="number" value="0" /> VND
                                                        </p>
                                                    </div>
                                                    <div class="d-flex justify-content-between">
                                                        <h5 class="mb-0 me-4">Phí vận chuyển</h5>
                                                        <div class="">
                                                            <p class="mb-0">0 VND</p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div
                                                    class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
                                                    <h5 class="mb-0 ps-4 me-4">Tổng số tiền</h5>
                                                    <p class="mb-0 pe-4" data-cart-total-price="${0}">
                                                        <fmt:formatNumber type="number" value="0" />
                                                        VND
                                                    </p>
                                                </div>
                                                <button
                                                    class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4"
                                                    type="submit">Đặt hàng</button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </c:if>
                            <c:if test="${empty cart.cartDetail}">
                                <div class="alert alert-danger" role="alert">
                                    Giỏ hàng của bạn trống, hãy lựa chọn vài thứ mà bạn yêu thích!
                                </div>
                                <div class="d-flex justify-content-center mt-5">
                                    <button
                                        class="btn border-secondary rounded-pill py-3 text-primary text-uppercase mb-4 "
                                        type="button" onclick="window.location.href='/'">Quay lại trang chủ</button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <!-- Cart Page End -->


                    <!-- Footer Start -->
                    <jsp:include page="../layout/footer.jsp" />
                    <!-- Footer End -->

                    <!-- Back to Top -->
                    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                            class="fa fa-arrow-up"></i></a>


                    <!-- JavaScript Libraries -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/client/lib/easing/easing.min.js"></script>
                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                    <!-- Template Javascript -->
                    <script src="/client/js/main.js"></script>
                </body>

                </html>
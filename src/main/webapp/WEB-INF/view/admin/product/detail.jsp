<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                    <meta name="author" content="Hỏi Dân IT" />
                    <title>Product Management</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Product Management</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="/admin/user">Products</a></li>
                                        <li class="breadcrumb-item active">View</li>
                                    </ol>
                                    <div class="row">
                                        <div class="col-6 mx-auto">
                                            <c:if test="${not empty message}">
                                                <h3>${message}</h3>
                                                <hr>
                                                <div class="alert alert-danger">
                                                    You cannot view a product that does not exist!
                                                </div>
                                                <a class="btn btn-success" href="/admin/product">Back</a>
                                            </c:if>
                                            <c:if test="${empty message}">
                                                <h3>Product Information</h3>
                                                <hr>
                                                <div class="row">
                                                    <div class="col-md-12 mb-3">
                                                        <img src="${product.imageURL}"
                                                            class="object-fit-md-contain border rounded w-100 h-100 " />
                                                    </div>
                                                    <div class=" col-md-12">
                                                        <div class="card">
                                                            <ul class="list-group list-group-flush">
                                                                <li class="list-group-item"><b>ID:</b> ${product.id}
                                                                </li>
                                                                <li class="list-group-item"><b>Name:</b> ${product.name}
                                                                </li>
                                                                <li class="list-group-item"><b>Price:</b>
                                                                    <fmt:formatNumber type="number"
                                                                        value="${product.price}" />
                                                                </li>
                                                                <li class="list-group-item"><b>Detail description:</b>
                                                                    ${product.detailDesc}</li>
                                                                <li class="list-group-item"><b>Short description:</b>
                                                                    ${product.shortDesc}</li>
                                                                <li class="list-group-item"><b>Quantity:</b>
                                                                    ${product.quantity}</li>
                                                                <li class="list-group-item"><b>Sold:</b> ${product.sold}
                                                                </li>
                                                                <li class="list-group-item"><b>Factory:</b>
                                                                    ${product.factory.name}</li>
                                                                <li class="list-group-item"><b>Category:</b>
                                                                    ${product.category.name}</li>
                                                            </ul>
                                                        </div>
                                                        <button class="btn btn-success mt-3 float-end"
                                                            onclick="window.location.href='/admin/product'">Back</button>
                                                    </div>
                                                </div>

                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/script.js"></script>
                </body>

                </html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                    <meta name="author" content="Hỏi Dân IT" />
                    <title>User Management</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                    <script>
                        $(document).ready(() => {
                            const avatarFile = $("#avatarFile");
                            avatarFile.change(function (e) {
                                const imgURL = URL.createObjectURL(e.target.files[0]);
                                $("#avatarPreview").attr("src", imgURL);
                                $("#avatarPreview").css({ "display": "block" });
                            });
                        });
                    </script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">User Management</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="/admin/user">Users</a></li>
                                        <li class="breadcrumb-item active">Update</li>
                                    </ol>
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <c:if test="${not empty message}">
                                                <h3>${message}</h3>
                                                <hr>
                                                <div class="alert alert-danger">
                                                    You cannot update a product that does not exist!
                                                </div>
                                                <a class="btn btn-success" href="/admin/product">Back</a>
                                            </c:if>
                                            <c:if test="${empty message}">
                                                <h3>Update a user</h3>
                                                <hr>
                                                <form:form class="row g-3" method="post" action="/admin/user/update"
                                                    modelAttribute="user" enctype="multipart/form-data">
                                                    <div class="col-md-6">
                                                        <label class="form-label">Email</label>
                                                        <form:input type="email" class="form-control" path="email"
                                                            readonly="true" />
                                                    </div>
                                                    <div class="col-md-6 d-none">
                                                        <label class="form-label">ID</label>
                                                        <form:input type="text" class="form-control" path="id" />
                                                    </div>
                                                    <div class="col-md-6 d-none">
                                                        <label class="form-label">Avatar</label>
                                                        <form:input type="text" class="form-control" path="avatar" />
                                                    </div>
                                                    <spring:bind path="fullName">
                                                        <div class="col-md-6">
                                                            <label class="form-label">Full Name</label>
                                                            <form:input type="text"
                                                                class="form-control ${status.error ? 'is-invalid' : ''}"
                                                                path="fullName" />
                                                            <form:errors path="fullName" cssClass="invalid-feedback" />
                                                        </div>
                                                    </spring:bind>
                                                    <spring:bind path="phone">
                                                        <div class="col-md-6">
                                                            <label class="form-label">Phone Number</label>
                                                            <form:input type="text"
                                                                class="form-control ${status.error ? 'is-invalid' : ''}"
                                                                path="phone" />
                                                            <form:errors path="phone" cssClass="invalid-feedback" />
                                                        </div>
                                                    </spring:bind>
                                                    <spring:bind path="address">
                                                        <div class="col-md-6">
                                                            <label class="form-label">Address</label>
                                                            <form:input type="text"
                                                                class="form-control ${status.error ? 'is-invalid' : ''}"
                                                                path="address" />
                                                            <form:errors path="address" cssClass="invalid-feedback" />
                                                        </div>
                                                    </spring:bind>
                                                    <div class="col-md-6">
                                                        <label class="form-label">Role</label>
                                                        <form:select class="form-select" path="role">
                                                            <form:options items="${roles}" itemValue="id"
                                                                itemLabel="name" />
                                                        </form:select>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label class="form-label">Avatar</label>
                                                        <input class="form-control" type="file" name="avatarFile"
                                                            id="avatarFile" accept=".png, .jpg, .jpeg" />
                                                    </div>
                                                    <div class="col-md-12">
                                                        <img src="${user.avatar}" style="max-height: 250px"
                                                            class="${empty user.avatar ? 'd-none' : ''}"
                                                            alt="avatar preview" id="avatarPreview" />
                                                    </div>
                                                    <div class="col-md-12">
                                                        <button type="submit" class="btn btn-warning">Update</button>
                                                        <button type="button" class="btn btn-success mx-2"
                                                            onclick="window.location.href='/admin/user'">Back</button>
                                                    </div>
                                                </form:form>
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
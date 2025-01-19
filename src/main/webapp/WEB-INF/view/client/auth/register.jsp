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
                    <meta name="description" content="" />
                    <meta name="author" content="" />
                    <title>Register</title>
                    <link href="css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="bg-primary">
                    <div id="layoutAuthentication">
                        <div id="layoutAuthentication_content">
                            <main>
                                <div class="container">
                                    <div class="row justify-content-center align-items-center" style=" height: 100vh;">
                                        <div class="col-lg-7">
                                            <div class="card shadow-lg border-0 rounded-lg">
                                                <div class="card-header">
                                                    <h3 class="text-center font-weight-light my-4">Create Account</h3>
                                                </div>
                                                <div class="card-body">
                                                    <form:form method="post" action="/register"
                                                        modelAttribute="registerUser">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                        <div class="row mb-3">
                                                            <spring:bind path="firstName">
                                                                <div class="col-md-6">
                                                                    <div class="form-floating mb-3 mb-md-0">
                                                                        <form:input
                                                                            class="form-control ${status.error ? 'is-invalid':''}"
                                                                            path="firstName" type="text"
                                                                            placeholder="Enter your first name" />
                                                                        <label for="inputFirstName">First name</label>
                                                                        <form:errors path="firstName"
                                                                            cssClass="invalid-feedback" />
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind path="lastName">
                                                                <div class="col-md-6">
                                                                    <div class="form-floating">
                                                                        <form:input
                                                                            class="form-control ${status.error ? 'is-invalid':''}"
                                                                            path="lastName" type="text"
                                                                            placeholder="Enter your last name" />
                                                                        <label for="inputLastName">Last name</label>
                                                                        <form:errors path="lastName"
                                                                            cssClass="invalid-feedback" />
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                        </div>
                                                        <spring:bind path="email">
                                                            <div class="form-floating mb-3">
                                                                <form:input
                                                                    class="form-control ${status.error ? 'is-invalid':''}"
                                                                    path="email" type="email"
                                                                    placeholder="name@example.com" />
                                                                <label for="inputEmail">Email address</label>
                                                                <form:errors path="email" cssClass="invalid-feedback" />
                                                            </div>
                                                        </spring:bind>
                                                        <div class="row mb-3">
                                                            <spring:bind path="password">
                                                                <div class="col-md-6">
                                                                    <div class="form-floating mb-3 mb-md-0">
                                                                        <form:input
                                                                            class="form-control ${status.error ? 'is-invalid':''}"
                                                                            path="password" type="password"
                                                                            placeholder="Create a password" />
                                                                        <label for="inputPassword">Password</label>
                                                                        <form:errors path="password"
                                                                            cssClass="invalid-feedback" />
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind path="confirmPassword">
                                                                <div class="col-md-6">
                                                                    <div class="form-floating mb-3 mb-md-0">
                                                                        <form:input
                                                                            class="form-control ${status.error ? 'is-invalid':''}"
                                                                            path="confirmPassword" type="password"
                                                                            placeholder="Confirm password" />
                                                                        <label for="inputPasswordConfirm">Confirm
                                                                            Password</label>
                                                                        <form:errors path="confirmPassword"
                                                                            cssClass="invalid-feedback" />
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                        </div>
                                                        <div class="mt-4 mb-0">
                                                            <div class="d-grid">
                                                                <button type="submit"
                                                                    class="btn btn-primary btn-block">Create
                                                                    Account</button>
                                                            </div>
                                                        </div>
                                                    </form:form>
                                                </div>
                                                <div class="card-footer text-center py-3">
                                                    <div class="small"><a href="/login">Have an account? Go to login</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </main>
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="js/scripts.js"></script>
                </body>

                </html>
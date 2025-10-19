<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Patients - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }
        .container { max-width: 1400px; }
        .header {
            background: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .badge-waiting {
            background: #f39c12;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .badge-done {
            background: #27ae60;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
        }
        .table thead th {
            background: #3498db;
            color: white;
            border: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-list-ul"></i> Liste des Patients Enregistrés</h1>
            <div>
                <a href="${pageContext.request.contextPath}/infirmier/patient-registration" class="btn btn-success me-2">
                    <i class="fas fa-user-plus"></i> Nouveau Patient
                </a>
                <a href="${pageContext.request.contextPath}/infirmier/dashboard" class="btn btn-primary">
                    <i class="fas fa-arrow-left"></i> Retour
                </a>
            </div>
        </div>

        <!-- Success Message -->
        <c:if test="${param.success == 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> Patient enregistré avec succès et ajouté à la file d'attente!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Statistics -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card text-center" style="border-left: 4px solid #3498db;">
                    <h3 class="text-primary">${totalToday != null ? totalToday : 0}</h3>
                    <p class="text-muted mb-0">Total Patients Aujourd'hui</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-center" style="border-left: 4px solid #f39c12;">
                    <h3 class="text-warning">${waitingCount != null ? waitingCount : 0}</h3>
                    <p class="text-muted mb-0">En Attente</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-center" style="border-left: 4px solid #27ae60;">
                    <h3 class="text-success">${totalToday - (waitingCount != null ? waitingCount : 0)}</h3>
                    <p class="text-muted mb-0">Pris en Charge</p>
                </div>
            </div>
        </div>

        <!-- Patients Table -->
        <div class="card">
            <h4 class="mb-3"><i class="fas fa-users"></i> Patients du Jour</h4>

            <c:choose>
                <c:when test="${not empty queue}">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Patient</th>
                                    <th>Date de Naissance</th>
                                    <th>N° Sécurité Sociale</th>
                                    <th>Téléphone</th>
                                    <th>Heure d'Arrivée</th>
                                    <th>Statut</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${queue}" var="fileAttente" varStatus="status">
                                    <tr>
                                        <td><strong>${status.index + 1}</strong></td>
                                        <td>
                                            <i class="fas fa-user-circle text-primary"></i>
                                            <strong>${fileAttente.patient.prenom} ${fileAttente.patient.nom}</strong>
                                        </td>
                                        <td>
                                            ${fileAttente.patient.dateNaissance}
                                        </td>
                                        <td><code>${fileAttente.patient.numeroSecuriteSociale}</code></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty fileAttente.patient.telephone}">
                                                    <i class="fas fa-phone"></i> ${fileAttente.patient.telephone}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">-</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <i class="fas fa-clock"></i>
                                            <c:set var="dateArrivee" value="${fileAttente.dateArrivee.toString()}" />
                                            <c:out value="${dateArrivee.substring(11, 16)}" />
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fileAttente.status == 'EN_ATTENTE'}">
                                                    <span class="badge-waiting">
                                                        <i class="fas fa-hourglass-half"></i> En Attente
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge-done">
                                                        <i class="fas fa-check"></i> Pris en Charge
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="fas fa-inbox fa-4x text-muted mb-3"></i>
                        <h5 class="text-muted">Aucun patient enregistré aujourd'hui</h5>
                        <p class="text-muted">Commencez par enregistrer le premier patient</p>
                        <a href="${pageContext.request.contextPath}/infirmier/patient-registration" class="btn btn-primary mt-3">
                            <i class="fas fa-user-plus"></i> Enregistrer un Patient
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

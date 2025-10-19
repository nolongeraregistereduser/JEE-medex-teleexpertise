<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Infirmier - Medex</title>
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
            border: none;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
        .stats-card {
            background: white;
            padding: 20px;
            border-left: 4px solid #3498db;
        }
        .stats-number {
            font-size: 2rem;
            font-weight: bold;
            color: #3498db;
        }
        .table-container {
            background: white;
            border-radius: 15px;
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
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-user-nurse"></i> Tableau de Bord Infirmier</h1>
            <div class="user-info">
                <span><i class="fas fa-user"></i> ${sessionScope.userName}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                    <i class="fas fa-sign-out-alt"></i> Déconnexion
                </a>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card stats-card">
                    <h6>Patients Enregistrés Aujourd'hui</h6>
                    <div class="stats-number">${todayPatientsCount != null ? todayPatientsCount : 0}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stats-card" style="border-left-color: #f39c12;">
                    <h6>En Attente</h6>
                    <div class="stats-number" style="color: #f39c12;">${waitingCount != null ? waitingCount : 0}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stats-card" style="border-left-color: #27ae60;">
                    <h6>Total File d'Attente</h6>
                    <div class="stats-number" style="color: #27ae60;">${totalInQueue != null ? totalInQueue : 0}</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card" style="background: #3498db; color: white; padding: 20px;">
                    <a href="${pageContext.request.contextPath}/infirmier/patient-registration" class="btn btn-light btn-lg w-100">
                        <i class="fas fa-user-plus"></i> Enregistrer Patient
                    </a>
                </div>
            </div>
        </div>

        <!-- Queue Table -->
        <div class="table-container">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4><i class="fas fa-list"></i> File d'Attente du Jour</h4>
                <a href="${pageContext.request.contextPath}/infirmier/patients" class="btn btn-primary">
                    <i class="fas fa-list-ul"></i> Voir Tous les Patients
                </a>
            </div>

            <c:choose>
                <c:when test="${not empty queue}">
                    <table class="table table-hover">
                        <thead class="table-primary">
                            <tr>
                                <th>Position</th>
                                <th>Patient</th>
                                <th>Numéro Sécu</th>
                                <th>Heure d'Arrivée</th>
                                <th>Statut</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${queue}" var="fileAttente" varStatus="status">
                                <tr>
                                    <td><strong>#${status.index + 1}</strong></td>
                                    <td>
                                        <i class="fas fa-user-circle"></i>
                                        ${fileAttente.patient.prenom} ${fileAttente.patient.nom}
                                    </td>
                                    <td>${fileAttente.patient.numeroSecuriteSociale}</td>
                                    <td>
                                        <c:set var="dateArrivee" value="${fileAttente.dateArrivee.toString()}" />
                                        <c:out value="${dateArrivee.substring(11, 16)}" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fileAttente.status == 'EN_ATTENTE'}">
                                                <span class="badge-waiting">En Attente</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-done">Pris en Charge</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                        <p class="text-muted">Aucun patient dans la file d'attente aujourd'hui</p>
                        <a href="${pageContext.request.contextPath}/infirmier/patient-registration" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i> Enregistrer le Premier Patient
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

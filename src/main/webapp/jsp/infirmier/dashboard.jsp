<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Infirmier - Medex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-hospital-user me-2"></i>Medex - Infirmier
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/infirmier/patient-registration">
                    <i class="fas fa-user-plus me-1"></i>Nouveau Patient
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-1"></i>Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h2><i class="fas fa-tachometer-alt me-2"></i>Tableau de Bord - Infirmier</h2>
                <p class="text-muted">Aperçu des patients d'aujourd'hui</p>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card bg-primary text-white">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <div>
                                <h4>${totalToday}</h4>
                                <p class="mb-0">Patients Aujourd'hui</p>
                            </div>
                            <div class="align-self-center">
                                <i class="fas fa-users fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-warning text-white">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <div>
                                <h4>${waitingCount}</h4>
                                <p class="mb-0">En Attente</p>
                            </div>
                            <div class="align-self-center">
                                <i class="fas fa-clock fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card bg-info text-white">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5>Actions Rapides</h5>
                            </div>
                            <div>
                                <a href="${pageContext.request.contextPath}/infirmier/patient-registration"
                                   class="btn btn-light btn-sm">
                                    <i class="fas fa-plus me-1"></i>Nouveau Patient
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="fas fa-list me-2"></i>Patients Enregistrés Aujourd'hui</h5>
                    </div>
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty todaysPatients}">
                                <div class="text-center p-4">
                                    <i class="fas fa-user-slash fa-3x text-muted mb-3"></i>
                                    <p class="text-muted">Aucun patient enregistré aujourd'hui</p>
                                    <a href="${pageContext.request.contextPath}/infirmier/patient-registration"
                                       class="btn btn-primary">
                                        <i class="fas fa-plus me-1"></i>Enregistrer un Patient
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Patient</th>
                                                <th>Num Sécu</th>
                                                <th>Heure Arrivée</th>
                                                <th>Signes Vitaux</th>
                                                <th>Téléphone</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="patientInfo" items="${todaysPatients}">
                                                <tr>
                                                    <td>
                                                        <strong>${patientInfo.patient.nomComplet}</strong><br>
                                                        <small class="text-muted">
                                                            <fmt:formatDate value="${patientInfo.patient.dateNaissance}" pattern="dd/MM/yyyy"/>
                                                        </small>
                                                    </td>
                                                    <td><code>${patientInfo.patient.numSecu}</code></td>
                                                    <td>
                                                        <fmt:formatDate value="${patientInfo.signesVitaux.dateSaisie}"
                                                                      pattern="HH:mm"/>
                                                    </td>
                                                    <td>
                                                        <small>
                                                            <i class="fas fa-heartbeat text-danger"></i> ${patientInfo.signesVitaux.tensionArterielle}<br>
                                                            <i class="fas fa-heart text-primary"></i> ${patientInfo.signesVitaux.frequenceCardiaque} bpm<br>
                                                            <i class="fas fa-thermometer-half text-warning"></i> ${patientInfo.signesVitaux.temperature}°C
                                                        </small>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty patientInfo.patient.telephone}">
                                                                ${patientInfo.patient.telephone}
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted">-</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="fas fa-clock me-2"></i>File d'Attente Actuelle</h5>
                    </div>
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty currentQueue}">
                                <div class="text-center p-4">
                                    <i class="fas fa-check-circle fa-2x text-success mb-2"></i>
                                    <p class="text-muted mb-0">Aucun patient en attente</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="list-group list-group-flush">
                                    <c:forEach var="fileAttente" items="${currentQueue}" varStatus="status">
                                        <div class="list-group-item d-flex justify-content-between align-items-start">
                                            <div class="ms-2 me-auto">
                                                <div class="fw-bold">${fileAttente.patient.nomComplet}</div>
                                                <small class="text-muted">
                                                    Arrivé à <fmt:formatDate value="${fileAttente.heureArrivee}" pattern="HH:mm"/>
                                                </small>
                                            </div>
                                            <span class="badge ${status.index == 0 ? 'bg-warning' : 'bg-secondary'} rounded-pill">
                                                ${status.index + 1}
                                            </span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="card mt-3">
                    <div class="card-header">
                        <h6 class="mb-0"><i class="fas fa-info-circle me-2"></i>Instructions</h6>
                    </div>
                    <div class="card-body">
                        <ul class="list-unstyled mb-0">
                            <li class="mb-2">
                                <i class="fas fa-search text-info me-2"></i>
                                Recherchez d'abord si le patient existe
                            </li>
                            <li class="mb-2">
                                <i class="fas fa-user-plus text-primary me-2"></i>
                                Enregistrez les nouveaux patients
                            </li>
                            <li class="mb-2">
                                <i class="fas fa-heart text-danger me-2"></i>
                                Prenez les signes vitaux obligatoires
                            </li>
                            <li>
                                <i class="fas fa-clipboard-list text-success me-2"></i>
                                Le patient sera automatiquement ajouté à la file
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>


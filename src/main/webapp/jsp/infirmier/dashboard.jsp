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
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --success-color: #27ae60;
            --warning-color: #f39c12;
            --danger-color: #e74c3c;
            --info-color: #17a2b8;
            --light-bg: #ecf0f1;
        }

        body {
            background: linear-gradient(135deg, var(--light-bg) 0%, #bdc3c7 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            overflow: hidden;
        }

        .card:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
        }

        .stats-card {
            background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
            border-left: 4px solid transparent;
            height: 120px;
        }

        .stats-card.primary { border-left-color: var(--primary-color); }
        .stats-card.warning { border-left-color: var(--warning-color); }
        .stats-card.info { border-left-color: var(--info-color); }
        .stats-card.success { border-left-color: var(--success-color); }

        .stats-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }

        .icon-primary { background: linear-gradient(135deg, var(--primary-color), #34495e); }
        .icon-warning { background: linear-gradient(135deg, var(--warning-color), #e67e22); }
        .icon-info { background: linear-gradient(135deg, var(--info-color), #138496); }
        .icon-success { background: linear-gradient(135deg, var(--success-color), #1e8449); }

        .table {
            border-radius: 10px;
            overflow: hidden;
        }

        .table thead th {
            background: linear-gradient(135deg, var(--secondary-color), #5dade2);
            color: white;
            border: none;
            font-weight: 500;
        }

        .table tbody tr {
            transition: all 0.3s ease;
        }

        .table tbody tr:hover {
            background-color: rgba(52, 152, 219, 0.1);
            transform: scale(1.01);
        }

        .queue-item {
            background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
            border-radius: 10px;
            border-left: 4px solid var(--success-color);
            transition: all 0.3s ease;
        }

        .queue-item:hover {
            transform: translateX(5px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .badge {
            border-radius: 10px;
            padding: 0.5rem 0.75rem;
        }

        .vital-signs {
            background: linear-gradient(135deg, #e8f5e8 0%, #f0f8f0 100%);
            border-radius: 8px;
            padding: 0.5rem;
        }

        .empty-state {
            padding: 3rem;
            text-align: center;
            color: #6c757d;
        }

        .empty-state i {
            font-size: 3rem;
            margin-bottom: 1rem;
            opacity: 0.5;
        }

        .btn {
            border-radius: 10px;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .page-header {
            background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
            border-radius: 15px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .icon-circle {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 1rem;
            font-size: 1.5rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="#">
                <div class="icon-circle bg-white text-primary me-3">
                    <i class="fas fa-hospital-user"></i>
                </div>
                <div>
                    <h5 class="mb-0">Medex</h5>
                    <small class="opacity-75">Interface Infirmier</small>
                </div>
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link btn btn-outline-light me-2" href="${pageContext.request.contextPath}/infirmier/patient-registration">
                    <i class="fas fa-user-plus me-1"></i>Nouveau Patient
                </a>
                <a class="nav-link btn btn-light text-dark" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-1"></i>Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <!-- Page Header -->
        <div class="page-header">
            <div class="d-flex align-items-center justify-content-between">
                <div class="d-flex align-items-center">
                    <div class="icon-circle bg-primary text-white">
                        <i class="fas fa-tachometer-alt"></i>
                    </div>
                    <div>
                        <h2 class="mb-0 text-primary">Tableau de Bord</h2>
                        <p class="mb-0 text-muted">Vue d'ensemble des activités du jour</p>
                    </div>
                </div>
                <div class="text-end">
                    <p class="mb-0 text-muted">Aujourd'hui</p>
                    <h6 class="mb-0"><fmt:formatDate value="<%=new java.util.Date()%>" pattern="dd MMMM yyyy"/></h6>
                </div>
            </div>
        </div>

        <!-- Error Display -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>
                <strong>Erreur:</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Statistics Cards -->
        <div class="row mb-4">
            <div class="col-lg-3 col-md-6 mb-3">
                <div class="card stats-card primary">
                    <div class="card-body d-flex align-items-center">
                        <div class="stats-icon icon-primary text-white">
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="ms-3">
                            <h3 class="mb-0 text-primary">${totalToday}</h3>
                            <p class="mb-0 text-muted">Patients Aujourd'hui</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-3">
                <div class="card stats-card warning">
                    <div class="card-body d-flex align-items-center">
                        <div class="stats-icon icon-warning text-white">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div class="ms-3">
                            <h3 class="mb-0 text-warning">${waitingCount}</h3>
                            <p class="mb-0 text-muted">En Attente</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-3">
                <div class="card stats-card info">
                    <div class="card-body d-flex align-items-center">
                        <div class="stats-icon icon-info text-white">
                            <i class="fas fa-heartbeat"></i>
                        </div>
                        <div class="ms-3">
                            <h3 class="mb-0 text-info">${totalToday}</h3>
                            <p class="mb-0 text-muted">Signes Vitaux</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-3">
                <div class="card stats-card success">
                    <div class="card-body d-flex align-items-center">
                        <div class="stats-icon icon-success text-white">
                            <i class="fas fa-check-circle"></i>
                        </div>
                        <div class="ms-3">
                            <h3 class="mb-0 text-success">100%</h3>
                            <p class="mb-0 text-muted">Taux Réussite</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <!-- Today's Patients -->
            <div class="col-lg-8 mb-4">
                <div class="card">
                    <div class="card-header">
                        <div class="d-flex align-items-center justify-content-between">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-list me-2"></i>
                                <h5 class="mb-0">Patients Enregistrés Aujourd'hui</h5>
                            </div>
                            <span class="badge bg-primary">${totalToday} patients</span>
                        </div>
                    </div>
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty todaysPatients}">
                                <div class="empty-state">
                                    <i class="fas fa-user-slash"></i>
                                    <h5>Aucun patient enregistré aujourd'hui</h5>
                                    <p class="text-muted">Commencez par enregistrer votre premier patient</p>
                                    <a href="${pageContext.request.contextPath}/infirmier/patient-registration"
                                       class="btn btn-primary">
                                        <i class="fas fa-plus me-1"></i>Enregistrer un Patient
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead>
                                            <tr>
                                                <th><i class="fas fa-user me-1"></i>Patient</th>
                                                <th><i class="fas fa-clock me-1"></i>Heure</th>
                                                <th><i class="fas fa-heartbeat me-1"></i>Signes Vitaux</th>
                                                <th><i class="fas fa-phone me-1"></i>Contact</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="patientInfo" items="${todaysPatients}">
                                                <tr>
                                                    <td>
                                                        <div>
                                                            <strong class="text-primary">${patientInfo.patient.nomComplet}</strong><br>
                                                            <small class="text-muted">
                                                                <i class="fas fa-calendar me-1"></i>
                                                                <fmt:formatDate value="${patientInfo.patient.dateNaissance}" pattern="dd/MM/yyyy"/>
                                                            </small>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-info">
                                                            <fmt:formatDate value="${patientInfo.signesVitaux.dateSaisie}" pattern="HH:mm"/>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <div class="vital-signs">
                                                            <small>
                                                                <i class="fas fa-heartbeat text-danger"></i> ${patientInfo.signesVitaux.tensionArterielle}<br>
                                                                <i class="fas fa-heart text-primary"></i> ${patientInfo.signesVitaux.frequenceCardiaque} bpm<br>
                                                                <i class="fas fa-thermometer-half text-warning"></i> ${patientInfo.signesVitaux.temperature}°C
                                                            </small>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty patientInfo.patient.telephone}">
                                                                <span class="text-success">
                                                                    <i class="fas fa-phone me-1"></i>${patientInfo.patient.telephone}
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted">
                                                                    <i class="fas fa-phone-slash me-1"></i>Non renseigné
                                                                </span>
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

            <!-- Current Queue -->
            <div class="col-lg-4 mb-4">
                <div class="card">
                    <div class="card-header">
                        <div class="d-flex align-items-center justify-content-between">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-clock me-2"></i>
                                <h5 class="mb-0">File d'Attente</h5>
                            </div>
                            <span class="badge bg-warning">${waitingCount} en attente</span>
                        </div>
                    </div>
                    <div class="card-body p-3">
                        <c:choose>
                            <c:when test="${empty currentQueue}">
                                <div class="text-center py-4">
                                    <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
                                    <h6 class="text-success">File d'attente vide</h6>
                                    <p class="text-muted mb-0">Aucun patient en attente</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="d-flex flex-column gap-3">
                                    <c:forEach var="fileAttente" items="${currentQueue}" varStatus="status">
                                        <div class="queue-item p-3">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="flex-grow-1">
                                                    <h6 class="mb-1 text-primary">${fileAttente.patient.nomComplet}</h6>
                                                    <small class="text-muted">
                                                        <i class="fas fa-clock me-1"></i>Arrivé à
                                                        <fmt:formatDate value="${fileAttente.heureArrivee}" pattern="HH:mm"/>
                                                    </small>
                                                </div>
                                                <div class="text-end">
                                                    <span class="badge ${status.index == 0 ? 'bg-warning' : 'bg-secondary'} fs-6">
                                                        ${status.index + 1}
                                                    </span>
                                                    <c:if test="${status.index == 0}">
                                                        <br><small class="text-warning">Suivant</small>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Quick Actions -->
                <div class="card mt-3">
                    <div class="card-header">
                        <h6 class="mb-0">
                            <i class="fas fa-bolt me-2"></i>Actions Rapides
                        </h6>
                    </div>
                    <div class="card-body">
                        <a href="${pageContext.request.contextPath}/infirmier/patient-registration"
                           class="btn btn-primary btn-sm w-100 mb-2">
                            <i class="fas fa-user-plus me-1"></i>Nouveau Patient
                        </a>
                        <button class="btn btn-outline-info btn-sm w-100 mb-2" onclick="window.location.reload()">
                            <i class="fas fa-sync-alt me-1"></i>Actualiser
                        </button>
                    </div>
                </div>

                <!-- Instructions -->
                <div class="card mt-3">
                    <div class="card-header">
                        <h6 class="mb-0">
                            <i class="fas fa-info-circle me-2"></i>Guide Rapide
                        </h6>
                    </div>
                    <div class="card-body">
                        <div class="d-flex flex-column gap-2">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-search text-info me-2"></i>
                                <small>Recherchez par nom/prénom</small>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="fas fa-user-plus text-primary me-2"></i>
                                <small>Enregistrez nouveaux patients</small>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="fas fa-heart text-danger me-2"></i>
                                <small>Prenez les signes vitaux</small>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="fas fa-clipboard-list text-success me-2"></i>
                                <small>Ajout automatique à la file</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Auto-refresh every 30 seconds for real-time updates
        setTimeout(function() {
            window.location.reload();
        }, 30000);

        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>
